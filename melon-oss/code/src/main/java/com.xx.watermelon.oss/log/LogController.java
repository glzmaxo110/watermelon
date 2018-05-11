package com.zc.travel.webboss.log;


import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zc.travel.common.exceptions.BaseCoreException;
import com.zc.travel.common.page.PageBean;
import com.zc.travel.common.page.PageParam;


import com.zc.travel.common.utils.json.JSONException;
import com.zc.travel.common.utils.json.JSONObject;
import com.zc.travel.dto.SuperUITableListResultDto;
import com.zc.travel.enums.LogOptType;
import com.zc.travel.log.exception.LogException;
import com.zc.travel.log.mapper.LogMapper;
import com.zc.travel.log.provider.ILogReadProvider;
import com.zc.travel.log.vo.LogVo;
import com.zc.travel.sys.mapper.BaseDictionaryMapper;
import com.zc.travel.sys.provider.IBaseDictionaryReadProvider;
import com.zc.travel.sys.provider.ISysSqlExecuteErrLogReadProvider;
import com.zc.travel.sys.vo.QueryDataParamsVo;
import com.zc.travel.sys.vo.SysSqlExecuteErrLogVo;
import com.zc.travel.user.mapper.CrmMemberMapper;
import com.zc.travel.webboss.constants.WebConstants;
import com.zc.travel.webboss.utils.FrontUtils;
import com.zc.travel.webboss.utils.WebParamUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/log")

public class LogController {

	private static final Logger LOGGER = Logger.getLogger(LogController.class);
	@Autowired
	private ILogReadProvider logReadProvider;
	@Autowired
	private ISysSqlExecuteErrLogReadProvider sysSqlExecuteErrLogReadProvider;
	@Autowired
	private IBaseDictionaryReadProvider baseDictionaryReadProvider;
	/**
	 * 跳转到系统业务参数管理页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toLogManage")
	public String toLogManage(ModelMap model, HttpServletRequest request) {
		FrontUtils.frontData(request, model);
		return "log/logManage";
	}

	/**
	 * 获取日志信息
	 *
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getLogPage", produces = "application/json;charset=utf-8")
	@ResponseBody

	public String getLogPage(ModelMap model, HttpServletRequest request, @RequestParam Map<String, Object> params) {
		FrontUtils.frontData(request, model);
		CrmMemberMapper crmMemberMapper=new CrmMemberMapper();
		try {
			//分页
			PageParam pageParam = new PageParam(WebParamUtils.getIntegerValue(params.get("offset"), 0),
					WebParamUtils.getIntegerValue(params.get("limit"), WebConstants.PAGE_SIZE),
					WebParamUtils.getStringValue(params.get("sortName")),
					WebParamUtils.getStringValue(params.get("sortType")));
			LogVo logVo = new LogVo();
			logVo.setLogStatus(WebParamUtils.getIntegerValue(params.get("logStatus")));

			logVo.setLogType(WebParamUtils.getIntegerValue(params.get("logType")));
			logVo.setOptLevel(WebParamUtils.getIntegerValue(params.get("optLevel")));
			crmMemberMapper.setMemberName(WebParamUtils.getStringValue(params.get("memberName")));
			logVo.setOptStatus(WebParamUtils.getIntegerValue(params.get("optStatus")));
			logVo.setOptType(WebParamUtils.getIntegerValue(params.get("optType")));
			logVo.setSystemCode(WebParamUtils.getIntegerValue(params.get("systemCode")));
			logVo.setOptKeywords(WebParamUtils.getStringValue(params.get("optKeywords")));
			logVo.setStartTime(WebParamUtils.getStringValue(params.get("startTime")));
			logVo.setEndTime(WebParamUtils.getStringValue(params.get("endTime")));
			logVo.setContent(WebParamUtils.getStringValue(params.get("content")));
			PageBean page = logReadProvider.getLogPage(pageParam, logVo,crmMemberMapper);
			//设置返回值
			SuperUITableListResultDto resultObj = new SuperUITableListResultDto();
			resultObj.total = page.getTotalCount();
			resultObj.rows = page.getRecordList();
			return JSON.toJSONString(resultObj, SerializerFeature.WriteMapNullValue);
		} catch (LogException e) {
			LOGGER.error("####分页查询日志信息异常." + e);
			e.printStackTrace();
			return null;
		} catch (BaseCoreException e) {
			LOGGER.error("####分页查询日志信息异常." + e);
			e.printStackTrace();
			return null;
		}
	}

	//region sql错误日志

	/**
	 * 跳转到系统SQL执行错误日志页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toSqlErrorLogManage")
	public String toSqlErrorLogManage(ModelMap model, HttpServletRequest request) {
		FrontUtils.frontData(request, model);
		return "log/sqlErrorLogManage";
	}

	/**
	 * 获取sql错误日志信息
	 *
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSqlErrorLogPage", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getSqlErrorLogPage(ModelMap model, HttpServletRequest request, @RequestParam Map<String, Object> params) {
		FrontUtils.frontData(request, model);
		try {
			//分页
			PageParam pageParam = new PageParam(WebParamUtils.getIntegerValue(params.get("offset"), 0),
					WebParamUtils.getIntegerValue(params.get("limit"), WebConstants.PAGE_SIZE),
					WebParamUtils.getStringValue(params.get("sortName")),
					WebParamUtils.getStringValue(params.get("sortType")));

			QueryDataParamsVo queryDataParamsVo = new QueryDataParamsVo();
			PageBean page = sysSqlExecuteErrLogReadProvider.getListPage(pageParam, queryDataParamsVo);
			//设置返回值
			SuperUITableListResultDto resultObj = new SuperUITableListResultDto();
			resultObj.total = page.getTotalCount();
			resultObj.rows = page.getRecordList();
			return JSON.toJSONString(resultObj, SerializerFeature.WriteMapNullValue);
		} catch (LogException e) {
			LOGGER.error("####分页查询SQL错误日志信息异常." + e);
			e.printStackTrace();
			return null;
		} catch (BaseCoreException e) {
			LOGGER.error("####分页查询SQL错误日志信息异常." + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 跳转到SQL错误详情页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sqlErrorLogShowDetail/{id}")
	public String sqlErrorLogShowDetail(@PathVariable(value = "id") Long id, ModelMap model, HttpServletRequest request) {
		FrontUtils.frontData(request, model);
		SysSqlExecuteErrLogVo vo = null;
		try {
			vo = sysSqlExecuteErrLogReadProvider.getById(id);
			model.addAttribute("vo", vo);

		} catch (BaseCoreException e) {
			LOGGER.error("####跳转到SQL错误详情页面异常。" + e);
			e.printStackTrace();
		}
		return "log/sqlErrorLogShowDetail";
	}

/*
* 系统日志查看详情
*  @param model
 * @return
*/
	@RequestMapping(value = "/getLogShowDetail/{id}", produces = "application/json;charset=utf-8")
	public String getLogShowDetail(@PathVariable(value = "id") Long id, ModelMap model, HttpServletRequest request) {
		FrontUtils.frontData(request, model);
		LogMapper logMapper = null;
		try {
				 logMapper = logReadProvider.getById(id);
			model.addAttribute("vo", logMapper);
		} catch (BaseCoreException e) {
			LOGGER.error("####跳转到SQL错误详情页面异常。" + e);
			e.printStackTrace();
		}
		return "log/LogShowDetail";
	}
	//endregion


}
