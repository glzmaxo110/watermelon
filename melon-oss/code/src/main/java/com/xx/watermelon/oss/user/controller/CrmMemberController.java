package com.xx.watermelon.oss.user.controller;

import com.xx.watermelon.common.utils.web.WebParamUtils;
import com.xx.watermelon.oss.user.service.CrmMemberOssService;
import com.xx.watermelon.user.exception.UserException;
import com.xx.watermelon.user.mapper.CrmMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @version 1.0, Created by xiesx on 2018-05-26 18:33.
 * @description CrmMemberController 2018/5/26
 * @copyright 2018-05-26 18:33
 */
@Controller
@RequestMapping("/crmMember")
public class CrmMemberController {

    @Autowired
    private CrmMemberOssService crmMemberOssService;

    @RequestMapping("/getMember")
    @ResponseBody
    public CrmMemberMapper getMember(HttpServletRequest request, @RequestParam Map<String,Object> params) throws UserException {
        Long id = WebParamUtils.getLongValue(params.get("id"));
        CrmMemberMapper crmMember = null;//crmMemberService.getCrmMemberById(id);
        return crmMember;
    }

}
