package com.zc.travel.user.mapper;

import com.zc.travel.common.mapper.BaseMapper;

public class CrmMemberMapper extends BaseMapper {

	private static final long serialVersionUID = -8105502758318284989L;
	/**
	 * 用户名
	 */
	private java.lang.String memberName;
	/**
	 * 微信OPENID
	 */
	private java.lang.String wxOpenId;
	/**
	 * 邮箱
	 */
	private java.lang.String email;
	/**
	 * 邮箱激活状态  1、已激活  2、未激活
	 */
	private java.lang.Integer mailActiveStatus;
	/**
	 * 手机号
	 */
	private java.lang.String mobile;
	/**
	 * QQ
	 */
	private java.lang.String qq;
	/**
	 * 注册类型(1-账号名注册；2-手机注册；3-邮箱注册；4-微信注册)
	 */
	private java.lang.Integer registrationType;
	/**
	 * 登录密码
	 */
	private java.lang.String memberPwd;
	/**
	 * 登录密码错误次数
	 */
	private java.lang.Integer memberPwdErrorTime;
	/**
	 * 加密串
	 */
	private String encryption;
	/**
	 * 联系人姓名
	 */
	private java.lang.String contactName;
	/**
	 * 联系电话
	 */
	private java.lang.String contactTel;
	/**
	 * 状态： 1正常 2未激活 3禁用 4冻结（平台操作员可以冻结，企业管理员无法解冻） 5注销
	 */
	private java.lang.Integer status;
	/**
	 * 审核状态：1、待审核 2、已审核 3、审核不通过 4、审核中
	 */
	private java.lang.Integer checkStatus;
	/**
	 * 来源
	 */
	private java.lang.String sourceFrom;
	/**
	 * 最后登录时间
	 */
	private java.util.Date lastLoginTime;
	/**
	 * 最后登录IP
	 */
	private java.lang.String lastLoginIp;
	/**
	 * 是否已经实名认证：1是2否
	 */
	private java.lang.Integer isCertification;
	/**
	 * 会员类型：1个人 2企业 3平台，4操作员邀请  5企业员工   默认1 ，平台操作员不能在前台登录
	 */
	private java.lang.Integer memberType;
	/**
	 * 备注
	 */
	private java.lang.String comment;
	/**
	 * 是否管理员 1是2否 默认2
	 */
	private java.lang.Integer isAdmin;
	/**
	 * 所属公司部门ID，默认是0
	 */
	private java.lang.Long departmentId;
	/**
	 * 所属上级会员ID
	 */
	private java.lang.Long parentId;
	/**
	 * 个人实名ID
	 */
	private java.lang.Long personalId;
	/**
	 * 企业实名ID
	 */
	private java.lang.Long companyId;
	/**
	 * 是否供应商（1是2否 默认2）
	 */
	private java.lang.Integer isSupplier;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 创建人ID
	 */
	private java.lang.Long createUserId;
	/**
	 * 修改时间
	 */
	private java.util.Date lastModifyTime;
	/**
	 * 修改人ID
	 */
	private java.lang.Long lastModifyUserId;
	/**
	 * 所在公司名称
	 */
	private java.lang.String companyName;
	/**
	 * 职务
	 */
	private java.lang.String jobTitle;
	/**
	 * 账号名修改次数
	 */
	private java.lang.Integer memberNameModifyCount;
	/**
	 * 支付密码
	 */
	private java.lang.String payPwd;
	/**
	 * 支付密码错误次数
	 */
	private java.lang.Integer payPwdErrorTime;
	/**
	 * 子账号类型（1-附属账号；2-子账号）
	 */
	private Integer childMemberType;
	/**
	 * 所属岗位（格式:,id1,id2,...）
	 */
	private String belongPositions;
	/**
	 * 所属企业代码
	 */
	private String enterpriseCode;

	/**
	 * 邀请码
	 */
	private String inviteCode;
	/**
	 * QQ登录标识
	 */
	private String qqOpenId;

	/**
	 * 电话号码区号
	 */
	private String mobileAreaCode;
	/**
	 * 操作员姓名
	 */
	private String operatorRealName;
	/**
	 * 操作员性别1男2女
	 */
	private Integer operatorSex;
	/**
	 * 邀请码二维码（保存图片的Base64编码）
	 */
	private String inviteQrCode;
	/**
	 * 部门名称
	 */
	private String departmentName;

	/**
	 * 客户经理ID
	 */
	private Long accountManagerId;

	/**
	 * 销售经理ID
	 */
	private Long saleManagerId;
	/**
	 * 邀请人ID
	 */
	private Long inviteUserId;
	/**
	 * 操作员头像
	 */
	private String operatorLogo;
	/**
	 * 操作员职能列表,逗号分隔
	 */
	private String operatorDuty;
	/**
	 * 操作员权限代码IdLink
	 */
	private String operatorDataAuthCode;
	/**
	 * 实名流程标识:0.未标识,1.正常实名流程,2.个人升级企业流程,3.快速成为供应商流程
	 */
	private Integer certificationSign;
	/**
	 * 关联账号ID
	 */
	private java.lang.Long relatedMemberId;
	/**
	 * 销售助理
	 */
	private String assistantIds;
	/**
	 * 支付密码
	 */
	public java.lang.String getPayPwd() {
		return payPwd;
	}
	/**
	 * 支付密码
	 */
	public void setPayPwd(java.lang.String payPwd) {
		this.payPwd = payPwd;
	}
	/**
	 * 支付密码错误次数
	 */
	public java.lang.Integer getPayPwdErrorTime() {
		return payPwdErrorTime;
	}
	/**
	 * 支付密码错误次数
	 */
	public void setPayPwdErrorTime(java.lang.Integer payPwdErrorTime) {
		this.payPwdErrorTime = payPwdErrorTime;
	}
	/**
	 * 账号名修改次数
	 */
	public java.lang.Integer getMemberNameModifyCount() {
		return memberNameModifyCount;
	}
	/**
	 * 账号名修改次数
	 */
	public void setMemberNameModifyCount(java.lang.Integer memberNameModifyCount) {
		this.memberNameModifyCount = memberNameModifyCount;
	}
	/**
	 * 职务
	 */
	public java.lang.String getJobTitle() {
		return jobTitle;
	}
	/**
	 * 职务
	 */
	public void setJobTitle(java.lang.String jobTitle) {
		this.jobTitle = jobTitle;
	}
	/**
	 * 所在公司名称
	 */
	public java.lang.String getCompanyName() {
		return companyName;
	}
	/**
	 * 所在公司名称
	 */
	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}
	/**
	 * 个人实名ID
	 */
	public java.lang.Long getPersonalId() {
		return personalId;
	}
	/**
	 * 个人实名ID
	 */
	public void setPersonalId(java.lang.Long personalId) {
		this.personalId = personalId;
	}
	/**
	 * 公司实名ID
	 */
	public java.lang.Long getCompanyId() {
		return companyId;
	}
	/**
	 * 公司实名ID
	 */
	public void setCompanyId(java.lang.Long companyId) {
		this.companyId = companyId;
	}
	/**
	 * 用户名
	 */
	public java.lang.String getMemberName() {
		return memberName;
	}
	/**
	 * 用户名
	 */
	public void setMemberName(java.lang.String memberName) {
		this.memberName = memberName;
	}
	/**
	 * 微信OPENID
	 */
	public java.lang.String getWxOpenId() {
		return wxOpenId;
	}
	/**
	 * 微信OPENID
	 */
	public void setWxOpenId(java.lang.String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}
	/**
	 * 邮箱
	 */
	public java.lang.String getEmail() {
		return email;
	}
	/**
	 * 邮箱
	 */
	public void setEmail(java.lang.String email) {
		this.email = email;
	}
	/**
	 * 手机号
	 */
	public java.lang.String getMobile() {
		return mobile;
	}
	/**
	 * 手机号
	 */
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 密码
	 */
	public java.lang.String getMemberPwd() {
		return memberPwd;
	}
	/**
	 * 密码
	 */
	public void setMemberPwd(java.lang.String memberPwd) {
		this.memberPwd = memberPwd;
	}
	/**
	 * 联系电话
	 */
	public java.lang.String getContactTel() {
		return contactTel;
	}
	/**
	 * 联系电话
	 */
	public void setContactTel(java.lang.String contactTel) {
		this.contactTel = contactTel;
	}
	/**
	 * 状态： 1正常 2未激活 3禁用 4冻结（平台操作员可以冻结，企业管理员无法解冻） 5注销
	 */
	public java.lang.Integer getStatus() {
		return status;
	}
	/**
	 * 状态： 1正常 2未激活 3禁用 4冻结（平台操作员可以冻结，企业管理员无法解冻） 5注销
	 */
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	/**
	 * 审核状态：1、待审核 2、已审核 3、审核不通过 4、审核中
	 */
	public java.lang.Integer getCheckStatus() {
		return checkStatus;
	}
	/**
	 * 审核状态：1、待审核 2、已审核 3、审核不通过 4、审核中
	 */
	public void setCheckStatus(java.lang.Integer checkStatus) {
		this.checkStatus = checkStatus;
	}
	/**
	 * 来源
	 */
	public java.lang.String getSourceFrom() {
		return sourceFrom;
	}
	/**
	 * 来源
	 */
	public void setSourceFrom(java.lang.String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}
	/**
	 * 最后登录时间
	 */
	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}
	/**
	 * 最后登录时间
	 */
	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	/**
	 * 最后登录IP
	 */
	public java.lang.String getLastLoginIp() {
		return lastLoginIp;
	}
	/**
	 * 最后登录IP
	 */
	public void setLastLoginIp(java.lang.String lastloginIp) {
		this.lastLoginIp = lastloginIp;
	}
	/**
	 * 是否已经实名认证：1是2否
	 */
	public java.lang.Integer getIsCertification() {
		return isCertification;
	}
	/**
	 * 是否已经实名认证：1是2否
	 */
	public void setIsCertification(java.lang.Integer isCertification) {
		this.isCertification = isCertification;
	}
	/**
	 * 会员类型：1个人 2企业 3平台，默认1 ，平台操作员不能在前台登录
	 */
	public java.lang.Integer getMemberType() {
		return memberType;
	}
	/**
	 * 会员类型：1个人 2企业 3平台，默认1 ，平台操作员不能在前台登录
	 */
	public void setMemberType(java.lang.Integer memberType) {
		this.memberType = memberType;
	}
	/**
	 * 备注
	 */
	public java.lang.String getComment() {
		return comment;
	}
	/**
	 * 备注
	 */
	public void setComment(java.lang.String comment) {
		this.comment = comment;
	}
	/**
	 * 是否管理员 1是2否 默认2
	 */
	public java.lang.Integer getIsAdmin() {
		return isAdmin;
	}
	/**
	 * 是否管理员 1是2否 默认2
	 */
	public void setIsAdmin(java.lang.Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
	/**
	 * 所属公司部门ID，默认是0
	 */
	public java.lang.Long getDepartmentId() {
		return departmentId;
	}
	/**
	 * 所属公司部门ID，默认是0
	 */
	public void setDepartmentId(java.lang.Long departmentId) {
		this.departmentId = departmentId;
	}
	/**
	 * 所属上级会员ID
	 */
	public java.lang.Long getParentId() {
		return parentId;
	}
	/**
	 * 所属上级会员ID
	 */
	public void setParentId(java.lang.Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * 是否供应商（1是2否 默认2）
	 */
	public java.lang.Integer getIsSupplier() {
		return isSupplier;
	}
	/**
	 * 是否供应商（1是2否 默认2）
	 */
	public void setIsSupplier(java.lang.Integer isSupplier) {
		this.isSupplier = isSupplier;
	}
	/**
	 * 创建时间
	 */
	public java.util.Date getCreateTime() {
		return createTime;
	}
	/**
	 * 创建时间
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 创建人ID
	 */
	public void setCreateUserId(java.lang.Long value) {
		this.createUserId = value;
	}
	/**
	 * 创建人ID
	 */
	public java.lang.Long getCreateUserId() {
		return this.createUserId;
	}
	/**
	 * 最后一次修改时间
	 */
	public java.util.Date getLastModifyTime() {
		return lastModifyTime;
	}
	/**
	 * 最后一次修改时间
	 */
	public void setLastModifyTime(java.util.Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	/**
	 * 最后一次修改人ID
	 */
	public java.lang.Long getLastModifyUserId() {
		return lastModifyUserId;
	}
	/**
	 * 最后一次修改人ID
	 */
	public void setLastModifyUserId(java.lang.Long lastModifyUserId) {
		this.lastModifyUserId = lastModifyUserId;
	}
	/**
	 * 子账号类型（1-附属账号；2-子账号）
	 */
	public Integer getChildMemberType() {
		return childMemberType;
	}
	/**
	 * 子账号类型（1-附属账号；2-子账号）
	 */
	public void setChildMemberType(Integer childMemberType) {
		this.childMemberType = childMemberType;
	}
	/**
	 * 所属岗位（格式:,id1,id2,...）
	 */
	public String getBelongPositions() {
		return belongPositions;
	}
	/**
	 * 所属岗位（格式:,id1,id2,...）
	 */
	public void setBelongPositions(String belongPositions) {
		this.belongPositions = belongPositions;
	}
	/**
	 * 登录密码错误次数
	 */
	public java.lang.Integer getMemberPwdErrorTime() {
		return memberPwdErrorTime;
	}
	/**
	 * 登录密码错误次数
	 */
	public void setMemberPwdErrorTime(java.lang.Integer memberPwdErrorTime) {
		this.memberPwdErrorTime = memberPwdErrorTime;
	}
	/**
	 * 联系人姓名
	 */
	public java.lang.String getContactName() {
		return contactName;
	}
	/**
	 * 联系人姓名
	 */
	public void setContactName(java.lang.String contactName) {
		this.contactName = contactName;
	}
	/**
	 * 所属企业代码
	 */
	public String getEnterpriseCode() {
		return enterpriseCode;
	}
	/**
	 * 所属企业代码
	 */
	public void setEnterpriseCode(String enterpriseCode) {
		this.enterpriseCode = enterpriseCode;
	}
	/**
	 * 加密串
	 */
	public String getEncryption() {
		return encryption;
	}
	/**
	 * 加密串
	 */
	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}
	/**
	 * 邮件激活状态
	 */
	public java.lang.Integer getMailActiveStatus() {
		return mailActiveStatus;
	}
	/**
	 * 邮件激活状态
	 */
	public void setMailActiveStatus(java.lang.Integer mailActiveStatus) {
		this.mailActiveStatus = mailActiveStatus;
	}
	/**
	 * QQ
	 */
	public java.lang.String getQq() {
		return qq;
	}
	/**
	 * QQ
	 */
	public void setQq(java.lang.String qq) {
		this.qq = qq;
	}
	/**
	 * 操作员姓名
	 */
	public String getOperatorRealName() {
		return operatorRealName;
	}
	/**
	 * 操作员姓名
	 */
	public void setOperatorRealName(String operatorRealName) {
		this.operatorRealName = operatorRealName;
	}
	/**
	 * 操作员性别1男2女
	 */
	public Integer getOperatorSex() {
		return operatorSex;
	}
	/**
	 * 操作员性别1男2女
	 */
	public void setOperatorSex(Integer operatorSex) {
		this.operatorSex = operatorSex;
	}
	/**
	 * 邀请码
	 */
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	/**
	 * 邀请码
	 */
	public String getInviteCode() {
		return inviteCode;
	}
	/**
	 * 部门名称
	 */
	public String getDepartmentName() {
		return departmentName;
	}
	/**
	 * 部门名称
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	/**
	 * 邀请码二维码（保存图片的Base64编码）
	 */
	public String getInviteQrCode() {
		return inviteQrCode;
	}
	/**
	 * 邀请码二维码（保存图片的Base64编码）
	 */
	public void setInviteQrCode(String inviteQrCode) {
		this.inviteQrCode = inviteQrCode;
	}
	/**
	 * 客户经理ID
	 */
	public Long getAccountManagerId() {
		return accountManagerId;
	}
	/**
	 * 客户经理ID
	 */
	public void setAccountManagerId(Long accountManagerId) {
		this.accountManagerId = accountManagerId;
	}
	/**
	 * 销售经理ID
	 */
	public Long getSaleManagerId() {
		return saleManagerId;
	}
	/**
	 * 销售经理ID
	 */
	public void setSaleManagerId(Long saleManagerId) {
		this.saleManagerId = saleManagerId;
	}
	/**
	 * 邀请人ID
	 */
	public Long getInviteUserId() {
		return inviteUserId;
	}
	/**
	 * 邀请人ID
	 */
	public void setInviteUserId(Long inviteUserId) {
		this.inviteUserId = inviteUserId;
	}
	/**
	 * 操作员头像
	 */
	public String getOperatorLogo() {
		return operatorLogo;
	}
	/**
	 * 操作员头像
	 */
	public void setOperatorLogo(String operatorLogo) {
		this.operatorLogo = operatorLogo;
	}
	/**
	 * 操作员职能列表，逗号分隔
	 */
	public String getOperatorDuty() {
		return operatorDuty;
	}
	/**
	 * 操作员职能列表，逗号分隔
	 */
	public void setOperatorDuty(String operatorDuty) {
		this.operatorDuty = operatorDuty;
	}
	/**
	 * 操作员权限代码IDLink
	 */
	public String getOperatorDataAuthCode() {
		return operatorDataAuthCode;
	}
	/**
	 * 操作员权限代码IDLink
	 */
	public void setOperatorDataAuthCode(String operatorDataAuthCode) {
		this.operatorDataAuthCode = operatorDataAuthCode;
	}

	public String getQqOpenId() {
		return qqOpenId;
	}

	public void setQqOpenId(String qqOpenId) {
		this.qqOpenId = qqOpenId;
	}

	public String getMobileAreaCode() {
		return mobileAreaCode;
	}

	public void setMobileAreaCode(String mobileAreaCode) {
		this.mobileAreaCode = mobileAreaCode;
	}
	/**
	 * 注册类型(1-账号名注册；2-手机注册；3-邮箱注册；4-微信注册)
	 */
	public java.lang.Integer getRegistrationType() {
		return registrationType;
	}
	/**
	 * 注册类型(1-账号名注册；2-手机注册；3-邮箱注册；4-微信注册)
	 */
	public void setRegistrationType(java.lang.Integer registrationType) {
		this.registrationType = registrationType;
	}
	/**
	 * 实名流程标识:0.未标识,1.正常实名流程,2.个人升级企业流程,3.快速成为供应商流程
	 */
	public Integer getCertificationSign() {
		return certificationSign;
	}
	/**
	 * 实名流程标识:0.未标识,1.正常实名流程,2.个人升级企业流程,3.快速成为供应商流程
	 */
	public void setCertificationSign(Integer certificationSign) {
		this.certificationSign = certificationSign;
	}
	/**
	 * 关联账号ID
	 */
	public Long getRelatedMemberId() {
		return relatedMemberId;
	}
	/**
	 * 关联账号ID
	 */
	public void setRelatedMemberId(Long relatedMemberId) {
		this.relatedMemberId = relatedMemberId;
	}
	/**
	 * 销售助理
	 */
	public String getAssistantIds() {
		return assistantIds;
	}
	/**
	 * 销售助理
	 */
	public void setAssistantIds(String assistantIds) {
		this.assistantIds = assistantIds;
	}

	public String toString() {
		  StringBuffer buffer = new StringBuffer();
		  buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
			   buffer.append("Id").append("='").append(getId()).append("' ");
			   buffer.append("MemberName").append("='").append(getMemberName()).append("' ");
			   buffer.append("WxOpenId").append("='").append(getWxOpenId()).append("' ");
			   buffer.append("Email").append("='").append(getEmail()).append("' ");
			   buffer.append("Mobile").append("='").append(getMobile()).append("' "); 
			   buffer.append("mailActiveStatus").append("='").append(getMailActiveStatus()).append("' ");
			   buffer.append("qq").append("='").append(getQq()).append("' ");
			   buffer.append("registrationType").append("='").append(getRegistrationType()).append("' ");
			   buffer.append("MemberPwd").append("='").append(getMemberPwd()).append("' "); 
			   buffer.append("ContactTel").append("='").append(getContactTel()).append("' ");
			   buffer.append("Status").append("='").append(getStatus()).append("' ");
			   buffer.append("SourceFrom").append("='").append(getSourceFrom()).append("' ");
			   buffer.append("LastLoginTime").append("='").append(getLastLoginTime()).append("' ");
			   buffer.append("LastLoginIp").append("='").append(getLastLoginIp()).append("' ");
			   buffer.append("IsCertification").append("='").append(getIsCertification()).append("' ");
			   buffer.append("MemberType").append("='").append(getMemberType()).append("' ");
			   buffer.append("Comment").append("='").append(getComment()).append("' ");
			   buffer.append("IsAdmin").append("='").append(getIsAdmin()).append("' ");
			   buffer.append("DepartmentId").append("='").append(getDepartmentId()).append("' ");
			   buffer.append("ParentId").append("='").append(getParentId()).append("' ");
			   buffer.append("Issupplier").append("='").append(getIsSupplier()).append("' ");
			   buffer.append("CreateTime").append("='").append(getCreateTime()).append("' ");
			   buffer.append("CreateUserId").append("='").append(getCreateUserId()).append("' ");
			   buffer.append("LastModifyTime").append("='").append(getLastModifyTime()).append("' ");
			   buffer.append("LastModifyUserId").append("='").append(getLastModifyUserId()).append("' ");
			   buffer.append("Version").append("='").append(getVersion()).append("' ");
			   buffer.append("DeleteStatus").append("='").append(getDeleteStatus()).append("' ");
			   buffer.append("childMemberType").append("='").append(getChildMemberType()).append("' ");
			   buffer.append("belongPositions").append("='").append(getBelongPositions()).append("' ");
			   buffer.append("memberPwdErrorTime").append("='").append(memberPwdErrorTime).append("' ");
			   buffer.append("contactName").append("='").append(contactName).append("' ");
			   buffer.append("enterpriseCode").append("='").append(enterpriseCode).append("' ");
			   buffer.append("qqOpenId").append("='").append(qqOpenId).append("' ");
			   buffer.append("mobileAreaCode").append("='").append(mobileAreaCode).append("' ");
			   buffer.append("certificationSign").append("='").append(certificationSign).append("' ");
			   buffer.append("relatedMemberId").append("='").append(relatedMemberId).append("' ");
			   buffer.append("assistantIds").append("='").append(assistantIds).append("' ");
		  buffer.append("]");
		  return buffer.toString();
	}

}
