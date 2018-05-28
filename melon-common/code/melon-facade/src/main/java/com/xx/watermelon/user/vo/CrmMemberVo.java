package com.xx.watermelon.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0, Created by xiesx on 2018-05-27 18:14.
 * @description CrmMemberVo 2018/5/27
 * @remark 用于后台页面数据展示
 * @copyright 2018-05-27 18:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrmMemberVo {
    /**
     * 用户名
     */
    private String memberName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 密码
     */
    private String memberPwd;
    /**
     * 联系电话
     */
    private String contactTel;
    /**
     * 状态：
     1正常 2未激活 3禁用
     4冻结（平台操作员可以冻结，企业管理员无法解冻）
     5注销
     */
    private Integer status;
    /**
     * 来源
     */
    private String sourceFrom;
    /**
     * 最后登录时间
     */
    private java.util.Date lastLoginTime;
    /**
     * 最后登录IP
     */
    private String lastLoginIp;
    /**
     * 备注
     */
    private String comment;
    /**
     * 当前操作时间
     */
    private java.util.Date createTime;
    /**
     * 当前操作员ID
     */
    private Long createUserId;
    /**
     * 当前操作时间
     */
    private java.util.Date lastModifyTime;
    /**
     * 当前操作员ID
     */
    private Long lastModifyUserId;
}
