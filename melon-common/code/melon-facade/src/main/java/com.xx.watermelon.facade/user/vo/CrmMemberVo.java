package com.zc.travel.user.vo;

import com.zc.travel.user.mapper.CrmMemberMapper;

import java.util.Date;
import java.util.List;

public class CrmMemberVo extends CrmMemberMapper {
	
	private static final long serialVersionUID = -8105502758318284989L;
	/**
	 * 能否创建店铺 1表示可以,2表示不行;
	 */
	private Integer canCreateShop;
	/**
	 * 关联的实名认证名(企业品牌名,个人姓名)
	 */
	private String shopName;
	/**
	 * 创建人姓名
	 */
	private String createUser;
	/**
	 * 最后修改人姓名
	 */
	private String lastModifyUser;
	/**
	 * 上级会员类型
	 */
	private Integer parentMemberType;
	/**
	 * 上级会员姓名
	 */
	private String parentUser;
	/**
	 * 上级个人实名ID
	 */
	private Long parentPersionalId;
	/**
	 * 设置新密码
	 */
	private String setMemberPwd;
	/**
	 * 确认新密码
	 */
	private String confirmMemberPwd;
	/**
	 * 证件号码
	 */
	private String cardNo;
	/**
	 * 部门父级Id
	 */
	private Long departmentParentId;
	/**
	 * 所属岗位名称
	 */
	private String positionName;
	/**
	 * 性别（1-男；2-女）
	 */
	private Integer sex;
	/**
	 * 是否大陆（1-是；2-否）
	 */
	private Integer isMainland;
	/**
	 * 证件正面照
	 */
	private String cardPhotoZm;
	/**
	 * 证件反面照
	 */
	private String cardPhotoFm;
	/**
	 * 实名信息Id
	 */
	private Long certificationId;
	/**
	 * 审核时间
	 */
	private Date checkTime;
	/**
	 * 审核人ID
	 */
	private Long checkOptUserId;
	/**
	 * 审核人
	 */
	private String checkOptUserName;
	/**
	 * 提交时间
	 */
	private Date submitTime;
	/**
	 * 审核备注
	 */
	private String checkFailReason;
	/**
	 * 省份编码
	 */
	private java.lang.String provinceCode;
	/**
	 * 城市编码
	 */
	private java.lang.String cityCode;
	/**
	 * 区域编码
	 */
	private java.lang.String areaCode;
	/**
	 * 省份名称
	 */
	private java.lang.String provinceName;
	/**
	 * 城市名称
	 */
	private java.lang.String cityName;
	/**
	 * 区域名称
	 */
	private java.lang.String areaName;
	/**
	 * 详细地址
	 */
	private java.lang.String address;
	/**
	 * 子账号使用情况统计数据
	 */
	private Integer useCount;
	/**
	 * 会员绑定的角色名称
	 */
	private String roleName;
	/**
	 * 会员头像
	 */
	private String friendLogo;
	/**
	 * 会员类型：1个人 2企业 3平台，4操作员邀请  5企业员工
	 */
	private List<Long> userTypes;
	
	/**
	 * 会员ID集合
	 */
	private List<Long> memberIds;
	/**
	 * 会员ID
	 */
	private Long memberId;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 真实英文名
	 */
	private String realNameEn;
	/**
	 * 接收验证码
	 */
	private String receiveCode;
	/**
	 * 部门linkIds
	 */
	private String departmentLinkIds;
	/**
	 * 查询职能
	 */
	private Integer dutyType;
	/**
	 * 查询区域
	 */
	private Long areaId;
	/**
	 * 职能名称
	 */
	private String operatorDutyName;
	/**
	 * 统计操作员服务的客户数量
	 */
	private Integer countMember;
	/**
	 * 工单任务Id
	 */
	private String taskId;
	/**
	 * 工单是否为待处理
	 */
	private Integer dealOrNot;
	/**
	 * 流程实例Id
	 */
	private String procInstId;
	/**
	 * 工作流源Id
	 */
	private String sourceNo;
	/**
	 * 工单类型
	 */
	private Integer workOrderType;
	/**
	 * 绑定会员的会员名
	 */
	private String bindMemberName;

	/**
	 * 能否创建店铺 1表示可以,2表示不行;
	 * @return
	 */
	public Integer getCanCreateShop() {
		return canCreateShop;
	}
	/**
	 * 能否创建店铺 1表示可以,2表示不行;
	 * @param canCreateShop
	 */
	public void setCanCreateShop(Integer canCreateShop) {
		this.canCreateShop = canCreateShop;
	}
	/**
	 * 关联的实名认证名(企业品牌名,个人姓名)
	 * @return
	 */
	public String getShopName() {
		return shopName;
	}
	/**
	 * 关联的实名认证名(企业品牌名,个人姓名)
	 * @param shopName
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	/**
	 * 设置新密码
	 */
	public String getSetMemberPwd() {
		return setMemberPwd;
	}
	/**
	 * 设置新密码
	 */
	public void setSetMemberPwd(String setMemberPwd) {
		this.setMemberPwd = setMemberPwd;
	}
	/**
	 * 确认新密码
	 */
	public String getConfirmMemberPwd() {
		return confirmMemberPwd;
	}
	/**
	 * 确认新密码
	 */
	public void setConfirmMemberPwd(String confirmMemberPwd) {
		this.confirmMemberPwd = confirmMemberPwd;
	}
	/**
	 * 创建人姓名
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * 创建人姓名
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * 最后修改人姓名
	 */
	public String getLastModifyUser() {
		return lastModifyUser;
	}
	/**
	 * 最后修改人姓名
	 */
	public void setLastModifyUser(String lastModifyUser) {
		this.lastModifyUser = lastModifyUser;
	}
	/**
	 * 上级会员姓名
	 */
	public String getParentUser() {
		return parentUser;
	}
	/**
	 * 上级会员姓名
	 */
	public void setParentUser(String parentUser) {
		this.parentUser = parentUser;
	}
	/**
	 * 证件号码
	 */
	public String getCardNo() {
		return cardNo;
	}
	/**
	 * 证件号码
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * 所属岗位名称
	 */
	public String getPositionName() {
		return positionName;
	}
	/**
	 * 所属岗位名称
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	/**
	 * 性别（1-男；2-女）
	 */
	public Integer getSex() {
		return sex;
	}
	/**
	 * 性别（1-男；2-女）
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	/**
	 * 是否大陆（1-是；2-否）
	 */
	public Integer getIsMainland() {
		return isMainland;
	}
	/**
	 * 是否大陆（1-是；2-否）
	 */
	public void setIsMainland(Integer isMainland) {
		this.isMainland = isMainland;
	}
	/**
	 * 证件正面照
	 */
	public String getCardPhotoZm() {
		return cardPhotoZm;
	}
	/**
	 * 证件正面照
	 */
	public void setCardPhotoZm(String cardPhotoZm) {
		this.cardPhotoZm = cardPhotoZm;
	}
	/**
	 * 证件反面照
	 */
	public String getCardPhotoFm() {
		return cardPhotoFm;
	}
	/**
	 * 证件反面照
	 */
	public void setCardPhotoFm(String cardPhotoFm) {
		this.cardPhotoFm = cardPhotoFm;
	}
	/**
	 * 审核时间
	 */
	public Date getCheckTime() {
		return checkTime;
	}
	/**
	 * 审核时间
	 */
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	/**
	 * 审核人ID
	 */
	public Long getCheckOptUserId() {
		return checkOptUserId;
	}
	/**
	 * 审核人ID
	 */
	public void setCheckOptUserId(Long checkOptUserId) {
		this.checkOptUserId = checkOptUserId;
	}
	/**
	 * 审核人
	 */
	public String getCheckOptUserName() {
		return checkOptUserName;
	}
	/**
	 * 审核人
	 */
	public void setCheckOptUserName(String checkOptUserName) {
		this.checkOptUserName = checkOptUserName;
	}
	/**
	 * 提交时间
	 */
	public Date getSubmitTime() {
		return submitTime;
	}
	/**
	 * 提交时间
	 */
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	/**
	 * 审核备注
	 */
	public String getCheckFailReason() {
		return checkFailReason;
	}
	/**
	 * 审核备注
	 */
	public void setCheckFailReason(String checkFailReason) {
		this.checkFailReason = checkFailReason;
	}
	/**
	 * 省份编码
	 */
	public java.lang.String getProvinceCode() {
		return provinceCode;
	}
	/**
	 * 省份编码
	 */
	public void setProvinceCode(java.lang.String provinceCode) {
		this.provinceCode = provinceCode;
	}
	/**
	 * 城市编码
	 */
	public java.lang.String getCityCode() {
		return cityCode;
	}
	/**
	 * 城市编码
	 */
	public void setCityCode(java.lang.String cityCode) {
		this.cityCode = cityCode;
	}
	/**
	 * 区域编码
	 */
	public java.lang.String getAreaCode() {
		return areaCode;
	}
	/**
	 * 区域编码
	 */
	public void setAreaCode(java.lang.String areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * 省份名称
	 */
	public java.lang.String getProvinceName() {
		return provinceName;
	}
	/**
	 * 省份名称
	 */
	public void setProvinceName(java.lang.String provinceName) {
		this.provinceName = provinceName;
	}
	/**
	 * 城市名称
	 */
	public java.lang.String getCityName() {
		return cityName;
	}
	/**
	 * 城市名称
	 */
	public void setCityName(java.lang.String cityName) {
		this.cityName = cityName;
	}
	/**
	 * 区域名称
	 */
	public java.lang.String getAreaName() {
		return areaName;
	}
	/**
	 * 区域名称
	 */
	public void setAreaName(java.lang.String areaName) {
		this.areaName = areaName;
	}
	/**
	 * 上级会员类型
	 */
	public Integer getParentMemberType() {
		return parentMemberType;
	}
	/**
	 * 上级会员类型
	 */
	public void setParentMemberType(Integer parentMemberType) {
		this.parentMemberType = parentMemberType;
	}
	/**
	 * 上级个人实名ID
	 */
	public Long getParentPersionalId() {
		return parentPersionalId;
	}
	/**
	 * 上级个人实名ID
	 */
	public void setParentPersionalId(Long parentPersionalId) {
		this.parentPersionalId = parentPersionalId;
	}
	/**
	 * 详细地址
	 */
	public java.lang.String getAddress() {
		return address;
	}
	/**
	 * 详细地址
	 */
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	/**
	 * 子账号使用情况统计数据
	 */
	public Integer getUseCount() {
		return useCount;
	}
	/**
	 * 子账号使用情况统计数据
	 */
	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}
	/**
	 * 会员角色名称
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * 会员角色名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * 会员Logo 
	 */
	public String getFriendLogo() {
		return friendLogo;
	}
	/**
	 * 会员Logo
	 */
	public void setFriendLogo(String friendLogo) {
		this.friendLogo = friendLogo;
	}
	
	public List<Long> getUserTypes() {
		return userTypes;
	}
	
	public void setUserTypes(List<Long> userTypes) {
		this.userTypes = userTypes;
	}
	
	public List<Long> getMemberIds() {
		return memberIds;
	}
	
	public void setMemberIds(List<Long> memberIds) {
		this.memberIds = memberIds;
	}
	/**
	 * 会员ID
	 */
	public Long getMemberId() {
		return memberId;
	}
	/**
	 * 会员ID
	 */
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	/**
	 * 真实姓名
	 */
	public String getRealName() {
		return realName;
	}
	/**
	 * 真实姓名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRealNameEn() {
		return realNameEn;
	}

	public void setRealNameEn(String realNameEn) {
		this.realNameEn = realNameEn;
	}
	
	public String getReceiveCode() {
		return receiveCode;
	}
	
	public void setReceiveCode(String receiveCode) {
		this.receiveCode = receiveCode;
	}

	public Long getDepartmentParentId() {
		return departmentParentId;
	}

	public void setDepartmentParentId(Long departmentParentId) {
		this.departmentParentId = departmentParentId;
	}

	public String getDepartmentLinkIds() {
		return departmentLinkIds;
	}

	public void setDepartmentLinkIds(String departmentLinkIds) {
		this.departmentLinkIds = departmentLinkIds;
	}
	/**
	 * 查询职能
	 */
	public Integer getDutyType() {
		return dutyType;
	}
	/**
	 * 查询职能
	 */
	public void setDutyType(Integer dutyType) {
		this.dutyType = dutyType;
	}
	/**
	 * 查询区域
	 */
	public Long getAreaId() {
		return areaId;
	}
	/**
	 * 查询区域
	 */
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	/**
	 * 职能名称
	 */
	public String getOperatorDutyName() {
		return operatorDutyName;
	}
	/**
	 * 职能名称
	 */
	public void setOperatorDutyName(String operatorDutyName) {
		this.operatorDutyName = operatorDutyName;
	}
	/**
	 * 统计操作员服务的客户数量
	 */
	public Integer getCountMember() {
		return countMember;
	}
	/**
	 * 统计操作员服务的客户数量
	 */
	public void setCountMember(Integer countMember) {
		this.countMember = countMember;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Integer getDealOrNot() {
		return dealOrNot;
	}

	public void setDealOrNot(Integer dealOrNot) {
		this.dealOrNot = dealOrNot;
	}

	public Long getCertificationId() {
		return certificationId;
	}

	public void setCertificationId(Long certificationId) {
		this.certificationId = certificationId;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getSourceNo() {
		return sourceNo;
	}

	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo;
	}

	public Integer getWorkOrderType() {
		return workOrderType;
	}

	public void setWorkOrderType(Integer workOrderType) {
		this.workOrderType = workOrderType;
	}
	/**
	 * 绑定会员的会员名
	 */
	public String getBindMemberName() {
		return bindMemberName;
	}
	/**
	 * 绑定会员的会员名
	 */
	public void setBindMemberName(String bindMemberName) {
		this.bindMemberName = bindMemberName;
	}
}