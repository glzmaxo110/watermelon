package com.zc.travel.user.provider;

import com.zc.travel.common.exceptions.BaseCoreException;
import com.zc.travel.common.page.PageBean;
import com.zc.travel.common.page.PageParam;
import com.zc.travel.consts.UserSession;
import com.zc.travel.crm.exception.CrmException;
import com.zc.travel.user.exception.UserException;
import com.zc.travel.user.mapper.CrmMemberMapper;
import com.zc.travel.user.vo.CrmFriendsVo;
import com.zc.travel.user.vo.CrmMemberVo;
import com.zc.travel.user.vo.CrmOperatorAreaContactVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICrmMemberReadProvider {
	/**
	 * 获取实名认证资料绑定的会员信息
	 * @param certificationType：1个人实名认证 2企业实名认证
	 * @param certificationId： 实名认证资料ID
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	CrmMemberMapper getCrmMemberByCertification(int certificationType, Long certificationId, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 查询会员详细信息
	 * @param memberId
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	CrmMemberMapper getCrmMemberById(Long memberId, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据ID获取会员详细信息
	 * @param memberId
	 * @return crmMemberVo 扩展类对象
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	CrmMemberVo getCrmMemberDetailsById(Long memberId, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据上级会员ID获取子账户信息
	 * @param parentId 
	 * @param childMemberType :为NULL时，查询所有的子账号与附属账号
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	List<CrmMemberVo> getCrmMemberByParentId(Long parentId, Integer childMemberType, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据部门ID获取会员信息
	 * @param departmentId
	 * isShow true 表示显示企业会员  false不显示企业会员
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	List<CrmMemberVo> getCrmMemberByDepartmentId(Long departmentId, boolean isShow, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据邮箱获取会员信息，邮箱验证状态为已验证
	 * @param email
	 * @param isOperator 是否为操作员
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	CrmMemberMapper getCrmMemberByValidEmail(String email,int isOperator, UserSession session) throws UserException, BaseCoreException;

	/**
	 * 根据用户类型,实名认证Id查询对应的会员信息
	 * @param vo
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
     */
	CrmMemberMapper getRealMember(CrmMemberVo vo, UserSession session)throws UserException, BaseCoreException;
	/**
	 * 根据账号名获取会员信息
	 * @param memberName
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	CrmMemberMapper getCrmMemberByMemberName(String memberName, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据微信OPENID获取会员信息
	 * @param wxOpenId
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	CrmMemberMapper getCrmMemberByWxOpenId(String wxOpenId, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据微信qqOpenId获取会员信息
	 * @param qqOpenId
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	CrmMemberMapper getByQqOpenId(String qqOpenId, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据手机号获取会员信息
	 * @param mobile
	 * @param isOperator 是否为操作员
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	CrmMemberMapper getCrmMemberByMobile(String mobile,int isOperator, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据分页查询会员列表
	 * @param pageParam
	 * @param params
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	PageBean getList(PageParam pageParam, Map<String, Object> params, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 查询关联账号
	 * @param pageParam	分页条件
	 * @param vo		查询条件
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	PageBean getRelatedMember(PageParam pageParam, CrmMemberVo vo, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 查询子账号/附属账号
	 * @param pageParam	分页条件
	 * @param vo		查询条件
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	PageBean getChildMember(PageParam pageParam, CrmMemberVo vo, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 查询子账号使用情况统计数据
	 * @param vo
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	List<CrmMemberVo> getChildStatistics(CrmMemberVo vo, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 微信OPENID进行登录
	 * @param wxOpenId		微信号
	 * @param sessionInfo	用户Session对象
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	CrmMemberMapper loginCrmMemberByWxOpenId(String wxOpenId, UserSession sessionInfo) throws UserException, BaseCoreException;
	/**
	 * 会员账户、邮箱、手机号与密码进行登录
	 * @param loginName
	 * @param loginPwd
	 * @param loginType： 1为账号名，2为邮箱，3为手机号
	 * @param isOperator：是否为操作员
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	CrmMemberMapper loginCrmMember(String loginName, String loginPwd, int loginType,int isOperator, UserSession sessionInfo) throws UserException, BaseCoreException;
	/**
	 * 根据岗位模糊查询会员信息
	 * @param positions	岗位ID集合，值格式',id1,id2,..'
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	List<CrmMemberMapper> getMemberByPosition(String positions, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据参数获取用户的登录密码
	 * @param type： 1为ID，2为账号名，3为邮箱号，4为手机号
	 * @param param 参数值：ID号、账号名、邮箱号、手机号
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	String getMemberLoginPwd(int type, String param, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据id查询关联店铺的会员信息详情
	 * @param id
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	CrmMemberVo getCrmMemberVoForShopById(Long id, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 查询会员信息(绑定用户页面select2插件用)
	 * @param pageParam
	 * @param vo
	 * @return
	 * @throws BaseCoreException 
	 * @throws UserException 
	 */
	PageBean getMemberDataCanBindRole(PageParam pageParam, CrmMemberVo vo, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据ID和会员类型查询认证信息
	 * @param id
	 * @param memberType
	 */
	CrmMemberVo getCrmMemberVoByIdAndMemberType(Long id, Integer memberType, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 分页查询可以新增部门的会员
	 * @param pageParam
	 * @param params
	 * @return
	 * @throws BaseCoreException 
	 * @throws UserException 
	 */
	PageBean getListCanAddDept(PageParam pageParam, Map<String, Object> params, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 分页查询可以新增的会员的主账户
	 * @param pageParam
	 * @param params
	 * @return
	 * @throws BaseCoreException 
	 * @throws UserException 
	 */
	PageBean getListAddMember(PageParam pageParam, Map<String, Object> params, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 分页查询可以新增
	 * @param pageParam
	 * @param params
	 * @return
	 * @throws BaseCoreException 
	 * @throws UserException 
	 */
	PageBean getMemberListCanCreateShop(PageParam pageParam, HashMap<String, Object> params, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 分页查询好友信息
	 * @param crmFriendsVo
	 * @param pageParam
	 * @return
	 * @throws BaseCoreException 
	 * @throws CrmException 
	 */
	PageBean getFriendsPage(CrmFriendsVo crmFriendsVo, PageParam pageParam, UserSession session) throws CrmException, BaseCoreException;
	/**
	 * 分页查询实名制资料可以绑定的用户 (select2)
	 * @param pageParam
	 * @param params
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	PageBean getNotCertificationMember(PageParam pageParam, HashMap<String, Object> params, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据用户名状态和用户名查询用户信息
	 * @param loginName
	 * @param loginType 1表示用户名,2表示邮箱,3.表示手机
	 * @param isOperator 是否为操作员
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	CrmMemberMapper getMemberByLoginType(String loginName, int loginType,int isOperator, UserSession session) throws UserException, BaseCoreException;
	/**
	 *	在缓存中获取登录错误次数
	 * @param userName
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	CrmMemberMapper getLoginErrorTimeFromCache(String userName)throws UserException, BaseCoreException;
	/**
	 * 根据岗位ID查询所在部门的所有员工
	 * @param positionId
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	List<CrmMemberVo> getPositionMember(Long positionId, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据岗位ID查询所关联的员工(无返回密码相关字段)
	 * @param positionId
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	List<CrmMemberVo> getPositionRelationMember(Long positionId, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据企业代码查询会员信息
	 * @param enterpriseCode
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
     */
	CrmMemberMapper getManagerByEnterpriseCode(String enterpriseCode, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据职能查询操作员 
	 * @param crmMemberVo
	 * @return
	 * @throws BaseCoreException
	 */
	List<CrmMemberVo> getMemberByOperatorDuty(CrmMemberVo crmMemberVo, UserSession session) throws BaseCoreException;
	/**
	 * 根据会员类型查询会员
	 * @param memberType
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	List<CrmMemberVo> getCrmMemberVoListByMemberType(Integer memberType, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 查询会员区域ID
	 * @param crmMemberVo
	 * @return
	 * @throws BaseCoreException
	 */
	Long getMemberAreaByMemberId(CrmMemberVo crmMemberVo, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 查询会员所在的区域ID链条
	 * @param crmMemberVo
	 * @return
	 * @throws BaseCoreException
	 */
	String getMemberAreasLinkByMemberId(CrmMemberVo crmMemberVo, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据部门ID查询本部门及下级部门员工
	 * @param departmentId
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	List<CrmMemberVo> getLinkMemberByDepartmentId(Long departmentId, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据邀请人id,分页查询被邀人信息
	 * @param pageParam
	 * @param params
	 * @return
	 */
	PageBean getCrmMemberPageByInviteUserId(PageParam pageParam, Map<String, Object> params, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据邀请码获取会员/操作员信息
	 * @param inviteCode
	 * @param isOperator 是否为操作员
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	CrmMemberMapper getCrmMemberByInviteCode(String inviteCode,Integer isOperator, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据店铺Id查询经营模式
	 * @param memberId
	 * @return 经营模式
	 * @throws CrmException
	 * @throws BaseCoreException
	 */
	String getBusinessModelByMemberId(Long memberId, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 查询部门属于的哪个会员
	 * @param departmentId
	 * @return
	 * @throws BaseCoreException
	 */
    CrmMemberMapper getMemberByDepartmentId(Long departmentId, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 分页查询会员及实名信息
	 * @param pageParam
	 * @param params
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	PageBean getMembersList(PageParam pageParam, HashMap<String, Object> params, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据会员ID查询会员及实名信息
	 * @param memberId
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	CrmMemberVo getMemberAndCertificationByMemberId(Long memberId, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据会员信息查询会员列表
	 * @param crmMemberVo 查询条件
	 * @param session
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	List<CrmMemberMapper> getMemberList(CrmMemberVo crmMemberVo, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 根据会员ID,查询客户经理、销售经理
	 * @param memberId
	 * @param session
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	CrmOperatorAreaContactVo getAreaContactByMemberId(Long memberId, UserSession session) throws UserException, BaseCoreException;
}
