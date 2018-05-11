package com.zc.travel.user.provider;

import java.util.List;
import java.util.Map;

import com.zc.travel.common.exceptions.BaseCoreException;
import com.zc.travel.consts.UserSession;
import com.zc.travel.user.exception.UserException;
import com.zc.travel.user.mapper.CrmFriendsMapper;
import com.zc.travel.user.mapper.CrmMemberMapper;
import com.zc.travel.user.vo.CrmFriendsVo;
import com.zc.travel.user.vo.CrmMemberVo;

public interface ICrmMemberOptProvider {
	/**
	 * 添加或注册会员账户
	 * @param crmMemberVo	新增会员信息
	 * @param sessionInfo	当前操作用户session
	 * @return 返回值说明：
	 * 		{key:'message',value:'错误信息'},{key:'member',value:'会员信息'}
	 * @throws UserException
	 * @throws BaseCoreException
	 * @version 1.0,2017年8月3日 下午3:20:16,Liugl,Ins
	 */
	CrmMemberMapper addCrmMember(CrmMemberVo crmMemberVo, UserSession sessionInfo) throws UserException, BaseCoreException;
	/**
	 * 更新会员信息
	 * @param crmMemberVo
	 * @param sessionInfo
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	void updateCrmMember(CrmMemberVo crmMemberVo, UserSession sessionInfo) throws UserException, BaseCoreException;
	/**
	 * 升级个人账户为公司账户
	 * @param crmMemberVo
	 * @param sessionInfo
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	void upgradeCompanyByPersonal(CrmMemberVo crmMemberVo, UserSession sessionInfo) throws UserException, BaseCoreException;
	/**
	 * 激活会员账号
	 * @param memberId		会员ID
	 * @param activateMode	激活方式（1-手机；2-邮件；3-管理员手工激活）
	 * @param session
	 * @throws UserException
	 * @throws BaseCoreException
	 * @version 1.0,2017年8月10日 下午5:48:53,Liugl,Ins
	 */
	void activateMember(Long memberId, Integer activateMode, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 修改登录密码
	 * @param type			密码修改类型（1-memberId；2-mobile；3-email）
	 * @param condition		更新条件
	 * @param loginPwd		登录密码
	 * @param originalPwd	原密码
	 * @param isOperator	是否为操作员
	 * @param sessionInfo
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	void updateCrmMemberLoginPwd(int type, String condition, String loginPwd, String originalPwd, int isOperator,UserSession sessionInfo) throws UserException, BaseCoreException;
	/**
	 * 修改会员支付密码
	 * @param type			密码修改类型（1-memberId；2-mobile；3-email）
	 * @param condition		更新条件
	 * @param paymentPwd	支付密码
	 * @param originalPwd	原密码
	 * @param isOperator	是否为操作员
	 * @param sessionInfo
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	void updatePaymentPwd(int type, String condition, String paymentPwd, String originalPwd,int isOperator, UserSession sessionInfo) throws UserException, BaseCoreException;
	/**
	 * 更新会员状态
	 * @param memberId	会员ID
	 * @param status	会员状态
	 * @param remark	更新描述
	 * @param sessionInfo
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	void updateMemberStatus(Long memberId, Integer status, String remark, UserSession sessionInfo) throws UserException, BaseCoreException;
	/**
	 * 更新会员状态，支持批量修改
	 * @param memberIds	会员ID
	 * @param status	会员状态
	 * @param remark	更新描述
	 * @param sessionInfo
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	void updateMemberStatus(List<Long> memberIds, Integer status, String remark, UserSession sessionInfo) throws UserException, BaseCoreException;
	/**
	 * 更新账号名
	 * @param memberId		会员ID
	 * @param memberName	账号名
	 * @param sessionInfo
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	void updateMemberName(Long memberId, String memberName, UserSession sessionInfo) throws UserException, BaseCoreException;
	/**
	 * 更新微信账号（微信账号绑定/解绑）
	 * @param memberId	会员ID
	 * @param wxOpenId	微信OPENID
	 * @param sessionInfo
	 * @throws UserException 	1、个人；2、企业；3、平台操作员；4、操作员邀请
	 * @throws BaseCoreException
	 */
	void updateWxOpenId(Long memberId, String wxOpenId, UserSession sessionInfo) throws UserException, BaseCoreException;

	/**
	 * 更新QQ账号（QQ账号绑定/解绑）
	 * @param memberId
	 * @param qqOpenId
	 * @param sessionInfo
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	void updateQqOpenId(Long memberId, String qqOpenId, UserSession sessionInfo) throws UserException, BaseCoreException;
	/**
	 * 自动解冻用户   memberType为null解冻所以冻结用户
	 * @param memberType 会员类型 
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	void updateUnfrozen(Integer memberType, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 会员信息审核
	 * @param memberId	会员ID
	 * @param status	审核状态（1-待审核；2-审核通过；3-审核不通过；4-审核中）
	 * @param remark	审核描述
	 * @param session
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	void auditMember(Long memberId, Integer status, String remark, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 批量会员信息审核
	 * @param memberIds	会员ID集合，多个间用英文,分隔
	 * @param status	审核状态（1-待审核；2-审核通过；3-审核不通过；4-审核中）
	 * @param remark	审核描述
	 * @param session
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	void batchAuditMember(String memberIds, Integer status, String remark, UserSession session) throws UserException, BaseCoreException;
	/**
	 * 删除会员（把会员的删除状态置为2）
	 * @param crmMemberVo
	 * @param sessionInfo
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	void deleteCrmMember(CrmMemberVo crmMemberVo, UserSession sessionInfo) throws UserException, BaseCoreException;
	/**
	 * 会员个人或企业实名制资料绑定或解绑
	 * @param certificationType 实名认证资料类型，1个人实名认证 2企业实名认证
	 * @param crmMemberVo
	 * @param userSession
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	void updateCrmMemberCertification(int certificationType, CrmMemberVo crmMemberVo, UserSession userSession) throws UserException, BaseCoreException;
	/**
	 * 更新会员部门和岗位信息，支持批量更新
	 * @param memberIds		会员ID集合，多个间用英文,分隔
	 * @param departmentId	部门ID
	 * @param positionIds	岗位ID集合，多个间用英文,分隔
	 * @param sessoin
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	void updateDepartmentPosition(String memberIds, Long departmentId, String positionIds, Integer isAmdin, UserSession sessoin) throws UserException, BaseCoreException;
	
	/**
	 * 添加会员好友
	 * @param
	 * @param sessionInfo
	 * @return
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	CrmFriendsMapper addCrmFriend(CrmFriendsVo crmFriendsVo, UserSession sessionInfo) throws UserException, BaseCoreException;
	/**
	 * 修改好友信息
	 * @param crmFriendsVo
	 * @param sessionInfo
	 * @throws BaseCoreException 
	 * @throws UserException 
	 */
	void updateFriend(CrmFriendsVo crmFriendsVo, UserSession sessionInfo) throws UserException, BaseCoreException;
	/**
	 * 删除好友
	 * @param crmFriendsVo
	 * @param session
	 * @throws BaseCoreException 
	 * @throws UserException 
	 */
	void deleteFriend(CrmFriendsVo crmFriendsVo, UserSession session) throws UserException, BaseCoreException;
	
	/**
	 * 同步更新手机号
	 * @param memberId	会员ID
	 * @param mobile	手机号
	 * @param areaCode	区号
	 * @param sessionInfo
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	void updateMobile(Long memberId, String mobile,String areaCode, UserSession sessionInfo) throws UserException, BaseCoreException;
	/**
	 * 同步更新邮箱
	 * @param id
	 * @param email
	 * @param mailActiveStatus 1、已激活  2、未激活
	 * @param sessionInfo
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	void updateEmail(Long memberId, String email, Integer mailActiveStatus, UserSession sessionInfo) throws UserException, BaseCoreException;
	/**
	 * 登录错误次数更新
	 * @param userName
	 * @param loginType
	 * @throws UserException
	 * @throws BaseCoreException
     */
	void updateLoginErrorTimeFormCache(String userName, String loginType)throws UserException, BaseCoreException;
	/**
	 * 注销会员账号
	 * @param crmMemberVo
	 * @param sessionInfo
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	void cancelMember(CrmMemberVo crmMemberVo,UserSession sessionInfo) throws UserException, BaseCoreException;
	/**
	 * 重置登录密码
	 * @param id
	 * @param memberType
	 * @param sessionInfo
	 * @return 
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	CrmMemberMapper resetLoginPwd(Long id, Integer memberType, UserSession sessionInfo) throws UserException, BaseCoreException;
	/**
	 * 更新会员id对应的店铺的经营范围到会员表中
	 * @param memberId
	 * @throws UserException
	 * @throws BaseCoreException
	 */
	void updateShopBusinessRangeToMember(Long memberId,UserSession session)throws UserException, BaseCoreException;
}
