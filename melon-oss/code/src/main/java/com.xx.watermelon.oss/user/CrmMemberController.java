package com.zc.travel.webboss.crm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zc.travel.common.page.PageBean;
import com.zc.travel.common.page.PageParam;
import com.zc.travel.common.utils.BeanUtils;
import com.zc.travel.common.utils.RegexUtils;
import com.zc.travel.common.utils.StringUtils;
import com.zc.travel.consts.UserSession;
import com.zc.travel.consts.WebUtils;
import com.zc.travel.crm.exception.CrmException;
import com.zc.travel.dto.JsonResultDto;
import com.zc.travel.dto.SuperUITableListResultDto;
import com.zc.travel.enums.*;
import com.zc.travel.sys.provider.IBaseCodeRuleOptProvider;
import com.zc.travel.user.exception.UserException;
import com.zc.travel.user.mapper.CrmCompanyMapper;
import com.zc.travel.user.mapper.CrmMemberMapper;
import com.zc.travel.user.mapper.CrmPersonalMapper;
import com.zc.travel.user.mapper.ShopShopInfoMapper;
import com.zc.travel.user.provider.*;
import com.zc.travel.user.vo.CrmMemberVo;
import com.zc.travel.webboss.constants.WebConstants;
import com.zc.travel.webboss.utils.FrontUtils;
import com.zc.travel.webboss.utils.HiddenUserPrivacyUtils;
import com.zc.travel.webboss.utils.WebParamUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "/crmMember")
public class CrmMemberController{
	
	private static final Logger LOGGER = Logger.getLogger(CrmMemberController.class);
	
	@Autowired
	private ICrmMemberOptProvider crmMemberOptProvider;
	@Autowired
	private ICrmMemberReadProvider crmMemberReadProvider;
	@Autowired
	private ICrmPersonalReadProvider crmPersonalReadProvider;
	@Autowired
	private ICrmCompanyReadProvider crmCompanyReadProvider;
	@Autowired
	private IBaseCodeRuleOptProvider baseCodeRuleOptProvider;
	@Autowired
	private IShopReadProvider shopReadProvider;
	
	/**
	 * 跳转到个人用户管理界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/personalMember")
	public String personalMember(ModelMap model, HttpServletRequest request) {
		FrontUtils.frontData(request, model);
		return "crm/crmMember/personalMember";
	}
	
	/**
	 * 获取子账号或附属账号列表
	 * @param model
	 * @return	quanx
	 */
	@ResponseBody
	@RequestMapping(value = "/getChildMemberDataList", produces = "application/json;charset=utf-8")
	public String getChildMemberDataList(ModelMap model, HttpServletRequest request, @RequestParam HashMap<String, Object> params) {
		FrontUtils.frontData(request, model);
		Long parentId = WebParamUtils.getLongValue(params.get("parentId"));
		Integer childMemberType = WebParamUtils.getIntegerValue(params.get("childMemberType"));
		UserSession sessionInfo =WebUtils.getUserSession(request);
		try {
			List<CrmMemberVo> dataObj = crmMemberReadProvider.getCrmMemberByParentId(parentId, childMemberType,sessionInfo);
			if(null == dataObj){
				dataObj = Collections.emptyList();
			}
			//设置返回值
			SuperUITableListResultDto resultObj = new SuperUITableListResultDto();
			resultObj.total = dataObj.size();
			resultObj.rows = dataObj;
			return JSON.toJSONString(HiddenUserPrivacyUtils.HiddenUserPrivacyInfo(
					resultObj,HiddenUserPrivacyUtils.HiddenObjectType.SuperUITableListResultDto,
					new HashMap<String,HiddenUserPrivacyUtils.RegexRoleType>(){{
						put("mobile", HiddenUserPrivacyUtils.RegexRoleType.HiddenMobil);
						put("contactTel", HiddenUserPrivacyUtils.RegexRoleType.HiddenMobil);
					}}), SerializerFeature.WriteMapNullValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 分页查询个人用户列表
	 * @param model
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchPersonalMember",produces = "application/json;charset=utf-8")
	public String searchPersonalMember(ModelMap model, HttpServletRequest request, @RequestParam HashMap<String,Object> params) {
		FrontUtils.frontData(request, model);
		//分页
		PageParam pageParam = new PageParam(WebParamUtils.getIntegerValue(params.get("offset"), 0),
				WebParamUtils.getIntegerValue(params.get("limit"), WebConstants.PAGE_SIZE),
				WebParamUtils.getStringValue(params.get("sortName")),
				WebParamUtils.getStringValue(params.get("sortType")));
		try {
			//职能权限控制
			UserSession sessionInfo = WebUtils.getUserSession(request);
			if (null == sessionInfo || null == sessionInfo.getMember()) {
				LOGGER.error("查询当前用户为空");
				throw new UserException("查询当前用户为空");
			}
			
			Map<String, Object> dutyMap = WebUtils.getCurrCrmDuty(request);
			params.put("dutyDataAuthType", "-1");
			//按区域查询  
			List<String> areaIds = (List<String>)dutyMap.get("areaIds");
			if (StringUtils.isEmpty(dutyMap.get("dutyDataAuthType").toString()) && 1L == sessionInfo.getMember().getId()) {
				params.put("dutyDataAuthType", "0");
				areaIds.add("0");
			} else if (StringUtils.isNotEmpty(dutyMap.get("dutyDataAuthType").toString())) {
				String[] dutyDataAuthTypeSort = dutyMap.get("dutyDataAuthType").toString().split(",");
				Arrays.sort(dutyDataAuthTypeSort);
				params.put("dutyDataAuthType", dutyDataAuthTypeSort[dutyDataAuthTypeSort.length - 1]);
			}
			if (null == areaIds || areaIds.size() < 1) {
				//当区域为空时，不能查询出数据
				List<String> areas = new ArrayList<>();
				areas.add("-1");
				params.put("areaIds", areas);
			} else if (!areaIds.contains("0")) {
				//当区域不包含不限时，按区域查询
				params.put("areaIds", dutyMap.get("areaIds"));
			}
			params.put("dataAuthCode", sessionInfo.getMember().getId());
			params.put("departmentDataAuthCode", sessionInfo.getMember().getDepartmentId());
			
			//剔除平台操作员
			params.put("excludeMemberType", UserType.OPERATORS.getIndex());
			PageBean pageBean = crmMemberReadProvider.getList(pageParam, params,sessionInfo);
			SuperUITableListResultDto resultObj = new SuperUITableListResultDto();
			resultObj.total = pageBean.getTotalCount();
			resultObj.rows = pageBean.getRecordList();
			String outString = JSON.toJSONString(HiddenUserPrivacyUtils.HiddenUserPrivacyInfo(
					resultObj,HiddenUserPrivacyUtils.HiddenObjectType.SuperUITableListResultDto,
					new HashMap<String,HiddenUserPrivacyUtils.RegexRoleType>(){{
						put("mobile", HiddenUserPrivacyUtils.RegexRoleType.HiddenMobil);
					}}),SerializerFeature.WriteMapNullValue);
			return outString;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 分页查询个人用户列表（用于绑定ComboBox）
	 * @param model
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchPersonalMemberByComboBox",produces = "application/json;charset=utf-8")
	public String searchPersonalMemberByComboBox(ModelMap model, HttpServletRequest request, @RequestParam HashMap<String,Object> params) {
		FrontUtils.frontData(request, model);
		UserSession sessionInfo =WebUtils.getUserSession(request);
		//分页
		PageParam pageParam = new PageParam(WebParamUtils.getIntegerValue(params.get("offset"), 0),
				WebParamUtils.getIntegerValue(params.get("limit"), WebConstants.PAGE_SIZE));
		try {
			PageBean pageBean = crmMemberReadProvider.getListCanAddDept(pageParam, params,sessionInfo);
			String jsonItemTemplate = "{\"id\":\"${id}\",\"memberName\":\"${memberName}\"}";
			StringBuilder sb = new StringBuilder();
			sb.append("{\"total_count\": "+pageBean.getTotalCount()+",\"incomplete_results\": true,\"items\":[");
			
			List<String> dataItemList = new LinkedList<String>();
			if(pageBean.getRecordList() != null && pageBean.getRecordList().size() > 0){
				for(Object item:pageBean.getRecordList()){
					String memberName = ((CrmMemberMapper)item).getMemberName();
					dataItemList.add(jsonItemTemplate.replace("${id}", ((CrmMemberMapper)item).getId().toString())
							.replace("${memberName}", memberName));
				}
			}
			sb.append(StringUtils.join(dataItemList.toArray(),","));
			sb.append("]}");
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 分页查询企业实名制资料可以绑定的会员 (select2)
	 * @param model
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchNotCertificationMember",produces = "application/json;charset=utf-8")
	public String searchNotCertificationMember(ModelMap model, HttpServletRequest request, @RequestParam HashMap<String,Object> params) {
		FrontUtils.frontData(request, model);
		UserSession sessionInfo = WebUtils.getUserSession(request);
		//分页
		PageParam pageParam = new PageParam(WebParamUtils.getIntegerValue(params.get("offset"), 0),
				WebParamUtils.getIntegerValue(params.get("limit"), WebConstants.PAGE_SIZE));
		try {
			Integer memberType = WebParamUtils.getIntegerValue(params.get("memberType"));
			if (null == memberType) {
				LOGGER.error("用户类型为空！");
				return null;
			}
			
			PageBean pageBean = null;
			if (UserType.ENTERPRISE.getIndex() == memberType) {
				pageBean = crmMemberReadProvider.getNotCertificationMember(pageParam, params, sessionInfo);
			} else if (UserType.PERSONAL.getIndex() == memberType) {
				params.put("status", UserStatus.NORMAL.getIndex());
				params.put("isCertification", YesOrNo.YES.getIndex());
				params.put("parentId", 0L);
				pageBean = crmMemberReadProvider.getList(pageParam, params, sessionInfo);
			}
			
			String jsonItemTemplate = "{\"id\":\"${id}\",\"memberName\":\"${memberName}\"}";
			StringBuilder sb = new StringBuilder();
			sb.append("{\"total_count\": "+pageBean.getTotalCount()+",\"incomplete_results\": true,\"items\":[");
			
			List<String> dataItemList = new LinkedList<String>();
			if(pageBean.getRecordList() != null && pageBean.getRecordList().size() > 0){
				for(Object item:pageBean.getRecordList()){
					String memberName = ((CrmMemberMapper)item).getMemberName();
					dataItemList.add(jsonItemTemplate.replace("${id}", ((CrmMemberMapper)item).getId().toString())
							.replace("${memberName}", memberName));
				}
			}
			sb.append(StringUtils.join(dataItemList.toArray(),","));
			sb.append("]}");
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 分页查询个人实名制资料可以绑定的个人会员 (select2)
	 * @param model
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchNotCertificationPersonalMember",produces = "application/json;charset=utf-8")
	public String searchNotCertificationPersonalMember(ModelMap model, HttpServletRequest request, @RequestParam HashMap<String,Object> params) {
		FrontUtils.frontData(request, model);
		UserSession sessionInfo = WebUtils.getUserSession(request);
		//分页
		PageParam pageParam = new PageParam(WebParamUtils.getIntegerValue(params.get("offset"), 0),
				WebParamUtils.getIntegerValue(params.get("limit"), WebConstants.PAGE_SIZE));
		try {
			Integer memberType = WebParamUtils.getIntegerValue(params.get("memberType"));
			if (null == memberType) {
				LOGGER.error("用户类型为空！");
				return null;
			}
			
			PageBean pageBean = crmMemberReadProvider.getNotCertificationMember(pageParam, params, sessionInfo);
			
			String jsonItemTemplate = "{\"id\":\"${id}\",\"memberName\":\"${memberName}\"}";
			StringBuilder sb = new StringBuilder();
			sb.append("{\"total_count\": "+pageBean.getTotalCount()+",\"incomplete_results\": true,\"items\":[");
			
			List<String> dataItemList = new LinkedList<String>();
			if(pageBean.getRecordList() != null && pageBean.getRecordList().size() > 0){
				for(Object item:pageBean.getRecordList()){
					String memberName = ((CrmMemberMapper)item).getMemberName();
					dataItemList.add(jsonItemTemplate.replace("${id}", ((CrmMemberMapper)item).getId().toString())
							.replace("${memberName}", memberName));
				}
			}
			sb.append(StringUtils.join(dataItemList.toArray(),","));
			sb.append("]}");
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 分页查询个人用户列表（用于绑定ComboBox）
	 * @param model
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchPersonalAddMemberByComboBox",produces = "application/json;charset=utf-8")
	public String searchPersonalAddMemberByComboBox(ModelMap model, HttpServletRequest request, @RequestParam HashMap<String,Object> params) {
		FrontUtils.frontData(request, model);
		UserSession sessionInfo = WebUtils.getUserSession(request);
		//分页
		PageParam pageParam = new PageParam(WebParamUtils.getIntegerValue(params.get("offset"), 0),
				WebParamUtils.getIntegerValue(params.get("limit"), WebConstants.PAGE_SIZE));
		try {
			PageBean pageBean = crmMemberReadProvider.getListAddMember(pageParam, params,sessionInfo);
			String jsonItemTemplate = "{\"id\":\"${id}\",\"memberName\":\"${memberName}\"}";
			StringBuilder sb = new StringBuilder();
			sb.append("{\"total_count\": "+pageBean.getTotalCount()+",\"incomplete_results\": true,\"items\":[");
			
			List<String> dataItemList = new LinkedList<String>();
			if(pageBean.getRecordList() != null && pageBean.getRecordList().size() > 0){
				for(Object item:pageBean.getRecordList()){
					String memberName = ((CrmMemberMapper)item).getMemberName();
					dataItemList.add(jsonItemTemplate.replace("${id}", ((CrmMemberMapper)item).getId().toString())
							.replace("${memberName}", memberName));
				}
			}
			sb.append(StringUtils.join(dataItemList.toArray(),","));
			sb.append("]}");
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 分页查询绑定（用于绑定ComboBox）
	 * @param model
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchCertificationMember",produces = "application/json;charset=utf-8")
	public String searchCertificationMember(ModelMap model, HttpServletRequest request, @RequestParam HashMap<String,Object> params) {
		FrontUtils.frontData(request, model);
		UserSession sessionInfo = WebUtils.getUserSession(request);
		//分页
		PageParam pageParam = new PageParam(WebParamUtils.getIntegerValue(params.get("offset"), 0),
				WebParamUtils.getIntegerValue(params.get("limit"), WebConstants.PAGE_SIZE));
		try {
			PageBean pageBean = crmMemberReadProvider.getListCanAddDept(pageParam, params,sessionInfo);
			String jsonItemTemplate = "{\"id\":\"${id}\",\"memberName\":\"${memberName}\"}";
			StringBuilder sb = new StringBuilder();
			sb.append("{\"total_count\": "+pageBean.getTotalCount()+",\"incomplete_results\": true,\"items\":[");
			
			List<String> dataItemList = new LinkedList<String>();
			if(pageBean.getRecordList() != null && pageBean.getRecordList().size() > 0){
				for(Object item:pageBean.getRecordList()){
					String memberName = ((CrmMemberMapper)item).getMemberName();
					dataItemList.add(jsonItemTemplate.replace("${id}", ((CrmMemberMapper)item).getId().toString())
							.replace("${memberName}", memberName));
				}
			}
			sb.append(StringUtils.join(dataItemList.toArray(),","));
			sb.append("]}");
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 跳转至修改登录密码页面
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/editMemberLoginPwd/{id}")
	public String editMemberLoginPwd(ModelMap model, HttpServletRequest request, @PathVariable(value = "id") Long id) {
		FrontUtils.frontData(request, model);
		UserSession sessionInfo = WebUtils.getUserSession(request);
		try {
			CrmMemberMapper member = crmMemberReadProvider.getCrmMemberById(id,sessionInfo);
			if(member == null){
				LOGGER.error("用户不存在,ID:" + id);
				throw new UserException("用户不存在,ID:" + id);
			}
			request.setAttribute("member", member);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return "crm/crmMember/editMemberLoginPwd";
	}
	/**
	 * 修改登录密码
	 * @param model
	 * @param request
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateMemberLoginPwd", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public String updateMemberLoginPwd(ModelMap model, HttpServletRequest request, CrmMemberVo crmMemberVo) {
		FrontUtils.frontData(request, model);
		//定义返回值
		JsonResultDto errInfo = new JsonResultDto();
		//非空判断
		if (null == crmMemberVo.getId() || StringUtils.isBlank(crmMemberVo.getSetMemberPwd())
				|| StringUtils.isBlank(crmMemberVo.getConfirmMemberPwd()) 
				|| StringUtils.isBlank(crmMemberVo.getMemberPwd())) {
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("登录密码修改参数为空");
			return JSON.toJSONString(errInfo);
		}
		//新旧密码对比
		if(crmMemberVo.getMemberPwd().equals(crmMemberVo.getConfirmMemberPwd())){
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("修改失败！新密码与旧密码不能相同！");
			return JSON.toJSONString(errInfo);
		}
		//后台再比对两次新密码
		if(!crmMemberVo.getSetMemberPwd().equals(crmMemberVo.getConfirmMemberPwd())){
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("修改失败！两次输入密码不一致！");
			return JSON.toJSONString(errInfo);
		}
		try {
			//TODO 设置新密码
			UserSession sessionInfo = WebUtils.getUserSession(request);
			crmMemberOptProvider.updateCrmMemberLoginPwd(1, String.valueOf(crmMemberVo.getId()), 
					crmMemberVo.getConfirmMemberPwd(), crmMemberVo.getMemberPwd(),YesOrNo.YES.getIndex(), sessionInfo);
			errInfo.setErrCode(0);
			errInfo.setErrMessage("修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("修改失败！" + e.getMessage());
		}
		return JSON.toJSONString(errInfo);
	}
	
	/**
	 * 跳转至修改支付密码页面
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/editMemberPayPwd/{id}")
	public String editMemberPayPwd(ModelMap model, HttpServletRequest request, @PathVariable(value = "id") Long id) {
		FrontUtils.frontData(request, model);
		UserSession sessionInfo = WebUtils.getUserSession(request);
		try {
			CrmMemberMapper member = crmMemberReadProvider.getCrmMemberById(id,sessionInfo);
			if(member == null){
				LOGGER.error("用户不存在,ID:" + id);
				throw new UserException("用户不存在,ID:" + id);
			}
			request.setAttribute("member", member);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return "crm/crmMember/editMemberPayPwd";
	}
	
	/**
	 * 修改支付密码
	 * @param model
	 * @param request
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateMemberPayPwd", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public String updateMemberPayPwd(ModelMap model, HttpServletRequest request, CrmMemberVo crmMemberVo) {
		FrontUtils.frontData(request, model);
		//定义返回值
		JsonResultDto errInfo = new JsonResultDto();
		//非空判断
		if (null == crmMemberVo.getId() || StringUtils.isBlank(crmMemberVo.getSetMemberPwd())
				|| StringUtils.isBlank(crmMemberVo.getConfirmMemberPwd())) {
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("支付密码修改参数为空");
			return JSON.toJSONString(errInfo);
		}
		//后台再比对两次新密码
		if(!crmMemberVo.getSetMemberPwd().equals(crmMemberVo.getConfirmMemberPwd())){
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("修改失败！两次输入密码不一致！");
			return JSON.toJSONString(errInfo);
		}
		try {
			//TODO 设置新密码
			String memberPwd = crmMemberVo.getConfirmMemberPwd();
			UserSession sessionInfo = WebUtils.getUserSession(request);
			crmMemberOptProvider.updatePaymentPwd(1, String.valueOf(crmMemberVo.getId()), memberPwd, null,YesOrNo.NO.getIndex(), sessionInfo);
			errInfo.setErrCode(0);
			errInfo.setErrMessage("修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("修改失败！");
		}
		return JSON.toJSONString(errInfo);
	}
	
	/**
	 * 编辑个人基本信息
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/editPersonalMember/{id}")
	public String editPersonalMember(ModelMap model, HttpServletRequest request, @PathVariable(value = "id") Long id) {
		FrontUtils.frontData(request, model);
		UserSession sessionInfo = WebUtils.getUserSession(request);
		try {
			CrmMemberMapper crmMemberById = crmMemberReadProvider.getCrmMemberById(id,sessionInfo);
			if(crmMemberById == null){
				LOGGER.error("crmMemberById = " + id + "，不存在！");
				throw new UserException("crmMemberById = " + id + "，不存在！");
			}
			request.setAttribute("crmMemberById", crmMemberById);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return "crm/crmMember/editPersonalMember";
	}
	
	/**
	 * 会员详情
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/showPersonalMember/{id}")
	public String showPersonalMember(ModelMap model, HttpServletRequest request, @PathVariable(value = "id") Long id) {
		FrontUtils.frontData(request, model);
		UserSession sessionInfo = WebUtils.getUserSession(request);
		try {
			CrmMemberMapper memberDetails = crmMemberReadProvider.getCrmMemberDetailsById(id,sessionInfo);
			if(memberDetails == null){
				LOGGER.error("memberDetails = " + id + "，不存在！");
				throw new UserException("memberDetails = " + id + "，不存在！");
			}
			/** 会员个人基本信息 **/
			request.setAttribute("memberDetails", HiddenUserPrivacyUtils.HiddenUserPrivacyInfo(
					memberDetails,HiddenUserPrivacyUtils.HiddenObjectType.OBJECT,
					new HashMap<String,HiddenUserPrivacyUtils.RegexRoleType>(){{
						put("mobile", HiddenUserPrivacyUtils.RegexRoleType.HiddenMobil);
						put("contactTel", HiddenUserPrivacyUtils.RegexRoleType.HiddenMobil);
					}}));
			/** 会员关联的个人实名信息  去掉实名制资料**/
			CrmPersonalMapper personalMapper = crmPersonalReadProvider.getCrmPersonalById(memberDetails.getPersonalId(),sessionInfo);
			request.setAttribute("personalMapper", personalMapper);
			CrmCompanyMapper companyMapper = crmCompanyReadProvider.getCrmCompanyById(memberDetails.getCompanyId(),sessionInfo);
			request.setAttribute("companyMapper", HiddenUserPrivacyUtils.HiddenUserPrivacyInfo(
					companyMapper,HiddenUserPrivacyUtils.HiddenObjectType.OBJECT,
					new HashMap<String,HiddenUserPrivacyUtils.RegexRoleType>(){{
						put("principalTel", HiddenUserPrivacyUtils.RegexRoleType.HiddenMobil);
						put("contactTel", HiddenUserPrivacyUtils.RegexRoleType.HiddenMobil);
						put("safeMobile", HiddenUserPrivacyUtils.RegexRoleType.HiddenMobil);
					}}));
			/** 会员 店铺基本信息 **/
			ShopShopInfoMapper shopInfoMapper =shopReadProvider.getShopInfoByMemberId(id, sessionInfo);
			request.setAttribute("shopInfoMapper",shopInfoMapper);

			/** 会员ID **/
			request.setAttribute("memberId", id);
			request.setAttribute("memberName", memberDetails.getMemberName());
			request.setAttribute("memberType", memberDetails.getMemberType());
/*
			//邀请码二维码展示
			if (StringUtils.isNotBlank(memberDetails.getInviteCode())) {
				String inviteQrCode = QRCodeUtil.encodeBase64(memberDetails.getInviteCode(), "", true);
				request.setAttribute("inviteQrCode", inviteQrCode);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} 
		return "crm/crmMember/showPersonalMember";
	}
	
	/**
	 * 注销会员信息
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelMember/{id}", produces = "application/json;charset=utf-8")
	public String cancelMember(ModelMap model, HttpServletRequest request, @PathVariable(value = "id") Long id) {
		FrontUtils.frontData(request, model);
		//定义返回值
		JsonResultDto errInfo = new JsonResultDto();
		UserSession sessionInfo = WebUtils.getUserSession(request);
		try {
			CrmMemberVo crmMemberVo = crmMemberReadProvider.getCrmMemberDetailsById(id,sessionInfo);
			if(crmMemberVo == null){
				LOGGER.error("crmMemberVo = " + id + "，不存在！");
				throw new UserException("crmMemberVo = " + id + "，不存在！");
			}
			crmMemberOptProvider.cancelMember(crmMemberVo, sessionInfo);
			errInfo.setErrCode(0);
			errInfo.setErrMessage("注销会员成功！");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("注销会员失败！");
		} 
		return JSON.toJSONString(errInfo);
	}
	
	/**
	 * 删除会员信息
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deletePersonalMember/{id}", produces = "application/json;charset=utf-8")
	public String deletePersonalMember(ModelMap model, HttpServletRequest request, @PathVariable(value = "id") Long id) {
		FrontUtils.frontData(request, model);
		//定义返回值
		JsonResultDto errInfo = new JsonResultDto();
		UserSession sessionInfo = WebUtils.getUserSession(request);
		try {
			CrmMemberMapper crmMemberById = crmMemberReadProvider.getCrmMemberById(id,sessionInfo);
			if(crmMemberById == null){
				LOGGER.error("crmMemberById = " + id + "，不存在！");
				throw new UserException("crmMemberById = " + id + "，不存在！");
			}
			CrmMemberVo crmMemberVo = new CrmMemberVo();
			crmMemberVo.setId(crmMemberById.getId());
			crmMemberVo.setDeleteStatus(DeleteStatus.DELETED.getIndex());
			crmMemberOptProvider.deleteCrmMember(crmMemberVo, sessionInfo);
			errInfo.setErrCode(0);
			errInfo.setErrMessage("删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("无效的会员信息！");
		} 
		return JSON.toJSONString(errInfo);
	}
	
	/**
	 * 删除子账号
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/childMemberDelete/{id}", produces = "application/json;charset=utf-8")
	public String childMemberDelete(ModelMap model, HttpServletRequest request, @PathVariable(value = "id") Long id) {
		FrontUtils.frontData(request, model);
		//定义返回值
		JsonResultDto errInfo = new JsonResultDto();
		UserSession sessionInfo = WebUtils.getUserSession(request);
		try {
			CrmMemberMapper crmMemberById = crmMemberReadProvider.getCrmMemberById(id,sessionInfo);
			if(crmMemberById == null){
				LOGGER.error("crmMemberById = " + id + "，不存在！");
				throw new UserException("crmMemberById = " + id + "，不存在！");
			}
			CrmMemberVo crmMemberVo = new CrmMemberVo();
			crmMemberVo.setId(crmMemberById.getId());
			crmMemberVo.setParentId(0L);
			crmMemberVo.setChildMemberType(null);
			crmMemberOptProvider.updateCrmMember(crmMemberVo, sessionInfo);
			errInfo.setErrCode(0);
			errInfo.setErrMessage("删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("无效的会员信息！");
		} 
		return JSON.toJSONString(errInfo);
	}
	
	/**
	 * 删除附属账号
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/attachMemberDelete/{id}", produces = "application/json;charset=utf-8")
	public String attachMemberDelete(ModelMap model, HttpServletRequest request, @PathVariable(value = "id") Long id) {
		FrontUtils.frontData(request, model);
		//定义返回值
		JsonResultDto errInfo = new JsonResultDto();
		UserSession sessionInfo =WebUtils.getUserSession(request);
		try {
			CrmMemberMapper crmMemberById = crmMemberReadProvider.getCrmMemberById(id,sessionInfo);
			if(crmMemberById == null){
				LOGGER.error("crmMemberById = " + id + "，不存在！");
				throw new UserException("crmMemberById = " + id + "，不存在！");
			}
			CrmMemberVo crmMemberVo = new CrmMemberVo();
			crmMemberVo.setId(crmMemberById.getId());
			crmMemberVo.setParentId(0L);
			crmMemberVo.setChildMemberType(null);
			crmMemberOptProvider.updateCrmMember(crmMemberVo, sessionInfo);
			errInfo.setErrCode(0);
			errInfo.setErrMessage("删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("无效的会员信息！");
		} 
		return JSON.toJSONString(errInfo);
	}
	
	/**
	 * 修改会员状态
	 * @param model
	 * @param request
	 * @param crmMemberVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePersonalStatus", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public String updatePersonalStatus(ModelMap model, HttpServletRequest request, CrmMemberVo crmMemberVo) {
		FrontUtils.frontData(request, model);
		//定义返回值
		JsonResultDto errInfo = new JsonResultDto();
		if(null == crmMemberVo){
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("无效的会员信息！");
			LOGGER.error("无效的会员信息！");
			return JSON.toJSONString(errInfo);
		}
		if(crmMemberVo.getStatus() > UserStatus.values().length){
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("无效的状态码！");
			LOGGER.error("无效的状态码！");
			return JSON.toJSONString(errInfo);
		}
		UserSession sessionInfo = WebUtils.getUserSession(request);
		try {
			crmMemberOptProvider.updateMemberStatus(crmMemberVo.getId(), crmMemberVo.getStatus(), crmMemberVo.getComment(), sessionInfo);
			errInfo.setErrCode(0);
			errInfo.setErrMessage("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("无效的会员信息！");
		} 
		return JSON.toJSONString(errInfo);
	}
	
	/**
	 * 跳转至添加会员页
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/jumpAddPage", produces = "application/json;charset=utf-8")
	public String jumpAddPersonalMember(ModelMap model, HttpServletRequest request, @RequestParam Map<String,Object> params) {
		FrontUtils.frontData(request, model);
		model.addAttribute("childMemberType", WebParamUtils.getIntegerValue(params.get("childMemberType")));
		model.addAttribute("isChildMember", WebParamUtils.getIntegerValue(params.get("isChildMember")));
		model.addAttribute("memberType", WebParamUtils.getIntegerValue(params.get("memberType")));
		model.addAttribute("memberId", WebParamUtils.getIntegerValue(params.get("memberId")));
		model.addAttribute("memberName", WebParamUtils.getStringValue(params.get("memberName")));
		return "crm/crmMember/addPersonalMember";
	}
	
	/**
	 * 新增会员
	 * @param model
	 * @param request
	 * @param crmMemberVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addPersonalMember", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public String addPersonalMember(ModelMap model, HttpServletRequest request, CrmMemberVo crmMemberVo) {
		FrontUtils.frontData(request, model);
		//定义返回值
		JsonResultDto errInfo = new JsonResultDto();
		if(null == crmMemberVo){
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("无效的会员信息！");
			LOGGER.error("无效的会员信息！");
			return JSON.toJSONString(errInfo);
		}
		UserSession sessionInfo = WebUtils.getUserSession(request);
		try {
			crmMemberVo.setSourceFrom(SourceFrom.PC.getIndex());
			crmMemberVo.setCreateUserId(WebUtils.getUserId(request));
			crmMemberVo.setRegistrationType(RegistrationType.OPERATOR_ADD.getIndex());
			CrmMemberMapper memberMapper = crmMemberOptProvider.addCrmMember(crmMemberVo, sessionInfo);
			errInfo.setErrCode(0);
			errInfo.setErrMessage("新增会员成功！");
		} catch (Exception e) {
			e.printStackTrace();
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("新增会员失败，" + e.getMessage());
			LOGGER.error("新增会员失败，" + e.getMessage());
		}
		return JSON.toJSONString(errInfo);
	}
	
	/**
	 * 更新会员信息
	 * @param model
	 * @param request
	 * @param crmMemberVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePersonalMember", produces = "application/json;charset=utf-8",method = RequestMethod.POST)
	public String updatePersonalMember(ModelMap model, HttpServletRequest request, CrmMemberVo crmMemberVo) {
		FrontUtils.frontData(request, model);
		//定义返回值
		JsonResultDto errInfo = new JsonResultDto();
		if(null == crmMemberVo){
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("无效的会员信息！");
			LOGGER.error("无效的会员信息！");
			return JSON.toJSONString(errInfo);
		}
		UserSession sessionInfo = WebUtils.getUserSession(request);
		try {
			CrmMemberMapper crmMember = crmMemberReadProvider.getCrmMemberById(crmMemberVo.getId(),sessionInfo);
			if(null == crmMember){
				LOGGER.error("crmMemberById = " + crmMemberVo.getId() + "，不存在！");
				throw new UserException("crmMemberById = " + crmMemberVo.getId() + "，不存在！");
			}
			crmMemberOptProvider.updateCrmMember(crmMemberVo, sessionInfo);
			errInfo.setErrCode(0);
			errInfo.setErrMessage("更新会员信息成功！");
			LOGGER.info("更新会员信息成功！");
		} catch (Exception e) {
			e.printStackTrace();
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("更新失败,失败原因:"+e.getMessage());
			LOGGER.error("更新失败,失败原因:"+e.getMessage());
		}
		return JSON.toJSONString(errInfo);
	}
	
	/**
	 * 添加附属账号或子账号
	 * @param model
	 * @param request
	 * @param crmMemberVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addChildOrAttachMember", produces = "application/json;charset=utf-8",method = RequestMethod.POST)
	public String addChildOrAttachMember(ModelMap model, HttpServletRequest request, CrmMemberVo crmMemberVo) {
		FrontUtils.frontData(request, model);
		//定义返回值
		JsonResultDto errInfo = new JsonResultDto();
		if(null == crmMemberVo){
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("无效的会员信息！");
			LOGGER.error("无效的会员信息！");
			return JSON.toJSONString(errInfo);
		}
		UserSession sessionInfo = WebUtils.getUserSession(request);
		try {
			CrmMemberMapper crmMember = crmMemberReadProvider.getCrmMemberById(crmMemberVo.getId(),sessionInfo);
			if(null == crmMember){
				LOGGER.error("crmMemberById = " + crmMemberVo.getId() + "，不存在！");
				errInfo.setErrCode(-1);
				errInfo.setErrMessage("无效的会员信息！");
				return JSON.toJSONString(errInfo);
			}
			//该账号已经是附属账号或子账号。只允许两级关系：父-子。不允许有附属账号的附属账号和子账号的子账号
			//该账号已经是附属账号或子账号
			if(null != crmMember.getParentId() && 0L != crmMember.getParentId().longValue()){
				errInfo.setErrCode(-1);
				errInfo.setErrMessage("该账号已经是附属账号或子账号！");
				return JSON.toJSONString(errInfo);
			}
			
			List<CrmMemberVo> mappers = crmMemberReadProvider.getCrmMemberByParentId(crmMemberVo.getParentId(), crmMember.getChildMemberType().intValue(),sessionInfo);
			if (null != mappers && mappers.size() >= 2) {
				errInfo.setErrCode(-1);
				errInfo.setErrMessage("主账号最多只能绑定两个附属账号！");
				return JSON.toJSONString(errInfo);
			}
			
			crmMemberOptProvider.updateCrmMember(crmMemberVo, sessionInfo);
			errInfo.setErrCode(0);
			errInfo.setErrMessage("添加成功！");
			LOGGER.info("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("无效的会员信息！");
			LOGGER.error("无效的会员信息！");
		}
		return JSON.toJSONString(errInfo);
	}
	
	/**
	 * 账号名或邮箱或手机号是否已经存在
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/isExsitMember", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public String isExsitMember(ModelMap model, HttpServletRequest request) {
		FrontUtils.frontData(request, model);
		String accountInfo = request.getParameter("accountInfo");
		//定义返回值
		JsonResultDto errInfo = new JsonResultDto();
		UserSession sessionInfo =WebUtils.getUserSession(request);
		try {
			CrmMemberMapper member = null;
			//账号是否匹配邮箱
			boolean emailRegex = RegexUtils.emailRegex(accountInfo);
			//账号是否匹配手机号码
			boolean mobileRegex = RegexUtils.mobileRegex(accountInfo);
			if(emailRegex){
				member = crmMemberReadProvider.getCrmMemberByValidEmail(accountInfo,YesOrNo.NO.getIndex(),sessionInfo);
			}
			if(mobileRegex){
				member = crmMemberReadProvider.getCrmMemberByMobile(accountInfo,YesOrNo.NO.getIndex(),sessionInfo);
			}
			if(!emailRegex && !mobileRegex){
				member = crmMemberReadProvider.getCrmMemberByMemberName(accountInfo,sessionInfo);
			}
			if(null == member){
				LOGGER.error("crmMember = " + accountInfo + "，不存在！");
				throw new UserException("crmMember = " + accountInfo + "，不存在！");
			}
			errInfo.setErrCode(0);
			errInfo.setOutputData(member);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("会员信息不存在！");
		} 
		return JSON.toJSONString(errInfo);
	}
	
	/**
	 * 检查实名资源是否能绑定会员
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/isBingMember", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public String isBingMember(ModelMap model, HttpServletRequest request) {
		FrontUtils.frontData(request, model);
		String accountInfo = WebParamUtils.getStringValue(request.getParameter("accountInfo"));
		String memberType = WebParamUtils.getStringValue(request.getParameter("memberType"));
		//定义返回值
		JsonResultDto errInfo = new JsonResultDto();
		errInfo.setErrCode(-1);
		errInfo.setErrMessage("无该会员用户！");
		UserSession sessionInfo =WebUtils.getUserSession(request);
		try {
			CrmMemberMapper member = crmMemberReadProvider.getCrmMemberByMemberName(accountInfo,sessionInfo);
			boolean bool = true;
			if(null != member) {
				if (!memberType.contains(String.valueOf(member.getMemberType()))) {
					errInfo.setErrCode(-1);
					errInfo.setErrMessage("该会员类型不匹配！");
					bool = false;
				} else if (memberType.contains(String.valueOf(UserType.PERSONAL.getIndex())) && null != member.getPersonalId() && 0 != member.getPersonalId()) {
					errInfo.setErrCode(-1);
					errInfo.setErrMessage("该会员已绑定个人实名资料！");
					bool = false;
				} else if (memberType.contains(String.valueOf(UserType.ENTERPRISE.getIndex())) && null != member.getCompanyId() && 0!= member.getCompanyId()) {
					errInfo.setErrCode(-1);
					errInfo.setErrMessage("该会员已绑定企业实名资料！");
					bool = false;
				}
				
				if (bool) {
					errInfo.setErrCode(0);
					errInfo.setOutputData(member);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} 
		return JSON.toJSONString(errInfo);
	}
	
	/**
	 * 
	 * @param model
	 * @return	部门岗位编辑页面
	 */
	@RequestMapping(value = "/departmentEdit/{id}")
	public String departmentEdit(ModelMap model, HttpServletRequest request, @PathVariable(value = "id") Long id) {
		FrontUtils.frontData(request, model);
		return "crm/crmMember/departmentEdit";
	}
	/**
	 * 查询能开店的会员列表
	 * @param model
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMemberCanCreateShopPage",produces = "application/json;charset=utf-8")
	public String getMemberCanCreateShopPage(ModelMap model, HttpServletRequest request, @RequestParam  HashMap<String,Object> params) {
		FrontUtils.frontData(request, model);
		UserSession sessionInfo =WebUtils.getUserSession(request);
		try {
			//分页查询数据
			PageParam pageParam = new PageParam(WebParamUtils.getIntegerValue(params.get("offset"), 0),
					WebParamUtils.getIntegerValue(params.get("limit"), WebConstants.PAGE_SIZE));
			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("canCreateShop",WebParamUtils.getIntegerValue(params.get("canCreateShop")));
			hashMap.put("memberName",WebParamUtils.getStringValue(params.get("memberName")));
			PageBean pageBean = crmMemberReadProvider.getMemberListCanCreateShop(pageParam, hashMap,sessionInfo);
			String jsonItemTemplate = "{\"id\":\"${id}\",\"memberName\":\"${memberName}\"}";
			StringBuilder sb = new StringBuilder();
			sb.append("{\"total_count\": "+pageBean.getTotalCount()+",\"incomplete_results\": true,\"items\":[");
			
			List<String> dataItemList = new LinkedList<String>();
			if(pageBean.getRecordList() != null && pageBean.getRecordList().size() > 0){
				for(Object item:pageBean.getRecordList()){
					String memberName = ((CrmMemberMapper)item).getMemberName();
					dataItemList.add(jsonItemTemplate.replace("${id}", ((CrmMemberMapper)item).getId().toString()).replace("${memberName}", memberName));
				}
			}
			sb.append(StringUtils.join(dataItemList.toArray(),","));
			sb.append("]}");
			return sb.toString();
		} catch (Exception e) {
			LOGGER.error("根据条件查询操作员列表异常! " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 根据id查询能关联店铺的会员信息详情
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMemberVoConnectShopById/{id}",produces = "application/json;charset=utf-8")
	public String getMemberVoConnectShopById(ModelMap model, HttpServletRequest request, @PathVariable(value = "id") Long id){
		FrontUtils.frontData(request, model);
		JsonResultDto errInfo = new JsonResultDto();
		UserSession sessionInfo =WebUtils.getUserSession(request);
		try {
			if(null == id) throw new UserException("获取会员详情参数[id]为空");
			 CrmMemberVo crmMemberVo = crmMemberReadProvider.getCrmMemberVoForShopById(id,sessionInfo);
			if(null == crmMemberVo){
				LOGGER.error("crmMemberVo = " + id + "，获取会员详情数据不存在！");
				throw new CrmException("获取会员详情数据不存在！");
			}
			errInfo.setErrCode(0);
			errInfo.setErrMessage("获取成功");
			errInfo.setOutputData(crmMemberVo);
		} catch (Exception e) {
			LOGGER.error("####获取会员信息详情异常." + e);
			e.printStackTrace();
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("获取会员信息错误");
		}
		return JSON.toJSONString(errInfo,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 查询会员信息(角色绑定用户页面select2插件用)
	 * @param model
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMemberDataCanBindRole",produces = "application/json;charset=utf-8")
	public String getMemberDataCanBindRole(ModelMap model, HttpServletRequest request, @RequestParam HashMap<String,Object> params) {
		FrontUtils.frontData(request, model);
		UserSession sessionInfo =WebUtils.getUserSession(request);
		try {
			//分页查询数据
			PageParam pageParam = new PageParam(WebParamUtils.getIntegerValue(params.get("offset"), 0),
					WebParamUtils.getIntegerValue(params.get("limit"), WebConstants.PAGE_SIZE));
			CrmMemberVo vo = new CrmMemberVo();
			vo.setMemberName(WebParamUtils.getStringValue(params.get("memberName")));
			vo.setMemberType(WebParamUtils.getIntegerValue(params.get("memberType")));
			PageBean pageBean = crmMemberReadProvider.getMemberDataCanBindRole(pageParam, vo,sessionInfo);
			String jsonItemTemplate = "{\"id\":\"${id}\",\"memberName\":\"${memberName}\"}";
			StringBuilder sb = new StringBuilder();
			sb.append("{\"total_count\": "+pageBean.getTotalCount()+",\"incomplete_results\": true,\"items\":[");

			List<String> dataItemList = new LinkedList<String>();
			if(pageBean.getRecordList() != null && pageBean.getRecordList().size() > 0){
				for(Object item:pageBean.getRecordList()){
					String memberName = ((CrmMemberVo)item).getMemberName();
					dataItemList.add(jsonItemTemplate.replace("${id}", ((CrmMemberVo)item).getId().toString()).replace("${memberName}", memberName));
				}
			}
			sb.append(StringUtils.join(dataItemList.toArray(),","));
			sb.append("]}");
			return sb.toString();
		} catch (Exception e) {
			LOGGER.error("根据条件查询操作员列表异常! " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * 个人会员升级为企业会员
	 * @param model
	 * @param request
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upgradeCompanyByPersonal/{id}", produces = "application/json;charset=utf-8",method = RequestMethod.POST)
	public String upgradeCompanyByPersonal(ModelMap model, HttpServletRequest request, @PathVariable(value = "id") Long id) {
		FrontUtils.frontData(request, model);
		UserSession sessionInfo = WebUtils.getUserSession(request);
		//定义返回值
		JsonResultDto errInfo = new JsonResultDto();
		if(null == id){
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("会员ID为空！");
			LOGGER.error("会员ID为空！");
			return JSON.toJSONString(errInfo);
		}
		try {
			CrmMemberMapper memberMapper = crmMemberReadProvider.getCrmMemberById(id,sessionInfo);
			CrmMemberVo crmMemberVo = new CrmMemberVo();
			BeanUtils.copyProperties(memberMapper,crmMemberVo);
			crmMemberOptProvider.upgradeCompanyByPersonal(crmMemberVo, sessionInfo);
			errInfo.setErrCode(0);
			errInfo.setErrMessage("升级会员信息成功！");
			LOGGER.info("升级会员信息成功！");
		} catch (Exception e) {
			e.printStackTrace();
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("升级会员失败！");
			LOGGER.error("升级会员失败！");
		}
		return JSON.toJSONString(errInfo);
	}
	
	/**
	 * 跳转修改账号名
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toUpdateMemberName", produces = "application/json;charset=utf-8")
	public String toUpdateMemberName(ModelMap model, HttpServletRequest request, @RequestParam HashMap<String,Object> params) {
		FrontUtils.frontData(request, model);
		model.addAttribute("id",  WebParamUtils.getLongValue(params.get("id")));
		model.addAttribute("memberName", WebParamUtils.getStringValue(params.get("memberName")));
		return "crm/crmMember/editMemberName";
	}
	
	/**
	 * 修改账号名
	 * @param model
	 * @param request
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateMemberName",produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public String updateMemberName(ModelMap model, HttpServletRequest request, @RequestParam HashMap<String,Object> params){
		FrontUtils.frontData(request, model);
		JsonResultDto errInfo = new JsonResultDto();
		UserSession sessionInfo = WebUtils.getUserSession(request);
		Long id = WebParamUtils.getLongValue(params.get("id"));
		String memberName = WebParamUtils.getStringValue(params.get("memberName"));
		try {
			if(null == id) {
				LOGGER.error("会员[id]为空！");
				throw new UserException("会员[id]为空！");
			}
			if(StringUtils.isEmpty(memberName)) {
				LOGGER.error("账号名为空！");
				throw new UserException("账号名为空！");
			}
			
			crmMemberOptProvider.updateMemberName(id, memberName, sessionInfo);
			errInfo.setErrCode(0);
			errInfo.setErrMessage("修改账号名成功！");
		} catch (Exception e) {
			LOGGER.error("####修改账号名失败." + e);
			e.printStackTrace();
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("修改账号名失败，" + e.getMessage());
		}
		return JSON.toJSONString(errInfo,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 生成账号名
	 * @param model
	 * @param request
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createMemberName",produces = "application/json;charset=utf-8")
	public String createMemberName(ModelMap model, HttpServletRequest request){
		FrontUtils.frontData(request, model);
		JsonResultDto errInfo = new JsonResultDto();
		try {
			String memberName = baseCodeRuleOptProvider.buildCode(CodeType.USER_CODE.getIndex());
			errInfo.setErrCode(0);
			errInfo.setOutputData(memberName);
		} catch (Exception e) {
			LOGGER.error("####生成账号名失败." + e);
			e.printStackTrace();
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("生成账号名失败，" + e.getMessage());
		}
		return JSON.toJSONString(errInfo,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 重置登录密码
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/resetLoginPwd",produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public String resetLoginPwd(ModelMap model, HttpServletRequest request, @RequestParam HashMap<String,Object> params){
		FrontUtils.frontData(request, model);
		JsonResultDto errInfo = new JsonResultDto();
		UserSession sessionInfo = WebUtils.getUserSession(request);
		Long id = WebParamUtils.getLongValue(params.get("id"));
		Integer memberType = WebParamUtils.getIntegerValue(params.get("memberType"));
		try {
			if(null == id) {
				LOGGER.error("会员[id]为空！");
				throw new UserException("会员[id]为空！");
			}
			if(null == memberType) {
				LOGGER.error("会员类型为空！");
				throw new UserException("会员类型为空！");
			}
			CrmMemberMapper resetMember = crmMemberOptProvider.resetLoginPwd(id, memberType, sessionInfo);
			errInfo.setErrCode(0);
			if(memberType == UserType.OPERATORS.getIndex()){
				errInfo.setErrMessage("重置会员登录密码成功！重置密码为:" + resetMember.getMemberPwd());
			}else{
				errInfo.setErrMessage("重置会员登录密码成功！");
			}
		} catch (Exception e) {
			LOGGER.error("####重置会员登录密码失败." + e);
			e.printStackTrace();
			errInfo.setErrCode(-1);
			errInfo.setErrMessage("重置会员登录密码失败，" + e.getMessage());
		}
		return JSON.toJSONString(errInfo,SerializerFeature.WriteMapNullValue);
	}
	/**
	 * 查询会员信息select2控件
	 * @param model
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMemberData",produces = "application/json;charset=utf-8")
	public String getMemberData(ModelMap model, HttpServletRequest request, @RequestParam HashMap<String,Object> params) {
		FrontUtils.frontData(request, model);
		UserSession sessionInfo =WebUtils.getUserSession(request);
		try {
			//分页查询数据
			PageParam pageParam = new PageParam(WebParamUtils.getIntegerValue(params.get("offset"), 0),
					WebParamUtils.getIntegerValue(params.get("limit"), WebConstants.PAGE_SIZE));
			Map<String, Object> newParams = new HashMap<>();
			newParams.put("memberName",WebParamUtils.getStringValue(params.get("memberName")));
			newParams.put("excludeMemberType",WebParamUtils.getIntegerValue(params.get("excludeMemberType")));
			PageBean pageBean = crmMemberReadProvider.getList(pageParam, newParams,sessionInfo);
			String jsonItemTemplate = "{\"id\":\"${id}\",\"memberName\":\"${memberName}\"}";
			StringBuilder sb = new StringBuilder();
			sb.append("{\"total_count\": "+pageBean.getTotalCount()+",\"incomplete_results\": true,\"items\":[");

			List<String> dataItemList = new LinkedList<String>();
			if(pageBean.getRecordList() != null && pageBean.getRecordList().size() > 0){
				for(Object item:pageBean.getRecordList()){
					String memberName = ((CrmMemberMapper)item).getMemberName();
					dataItemList.add(jsonItemTemplate.replace("${id}", ((CrmMemberMapper)item).getId().toString()).replace("${memberName}", memberName));
				}
			}
			sb.append(StringUtils.join(dataItemList.toArray(),","));
			sb.append("]}");
			return sb.toString();
		} catch (Exception e) {
			LOGGER.error("根据条件查询会员列表异常! " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据条件查询会员列表(操作员绑定会员)
	 * @param model
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMembers",produces = "application/json;charset=utf-8")
	public String getMembers(ModelMap model, HttpServletRequest request, @RequestParam  HashMap<String,Object> params) {
		FrontUtils.frontData(request, model);
		try {
			UserSession sessionInfo =WebUtils.getUserSession(request);
			//分页查询数据
			PageParam pageParam = new PageParam(WebParamUtils.getIntegerValue(params.get("offset"), 0),
					WebParamUtils.getIntegerValue(params.get("limit"), 100));   //默认100条
			if (StringUtils.isBlank(WebParamUtils.getStringValue(params.get("memberName")))) {
				LOGGER.error("根据条件查询操作员列表参数为空! ");
				return null;
			}
			HashMap<String, Object> newParams = new HashMap<String, Object>();
			newParams.put("memberName", WebParamUtils.getStringValue(params.get("memberName")));    //账号名
			newParams.put("realNameCn", WebParamUtils.getStringValue(params.get("memberName")));    //真实姓名
			newParams.put("mobile", WebParamUtils.getStringValue(params.get("memberName")));   //用户名
			PageBean pageBean = crmMemberReadProvider.getMembersList(pageParam, newParams,sessionInfo);

			String jsonItemTemplate = "{\"id\":\"${id}\",\"text\":\"${memberName}\"}";
			StringBuilder sb = new StringBuilder();
			sb.append("{\"total_count\": "+pageBean.getTotalCount()+",\"incomplete_results\": true,\"items\":[");

			List<String> dataItemList = new LinkedList<String>();
			if (pageBean.getRecordList() != null && pageBean.getRecordList().size() > 0) {
				for (Object item : pageBean.getRecordList()) {
					CrmMemberVo member = (CrmMemberVo) item;
					String memberName = member.getMemberName() + "(" + (member.getRealName() == null ? "暂无姓名" : member.getRealName())
							+ " " + (member.getMobile() == null ? "暂无手机" : member.getMobile()) + ")";
					dataItemList.add(jsonItemTemplate.replace("${id}", ((CrmMemberVo) item).getId().toString()).replace("${memberName}", memberName));
				}
			}
			sb.append(StringUtils.join(dataItemList.toArray(),","));
			sb.append("]}");
			return sb.toString();
		} catch (Exception e) {
			LOGGER.error("根据条件查询h员列表异常! " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

}
