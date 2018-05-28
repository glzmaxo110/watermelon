package com.xx.watermelon.oss.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @version 1.0, Created by xiesx on 2018-05-22 11:32.
 * @description UserController 2018/5/22
 * @copyright 2018-05-22 11:32
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    /**
     * 跳转到用户管理管理页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/toUserManage")
    public String toUserManage(ModelMap model, HttpServletRequest request) {
        return "user/userManage";
    }
    /**
     * 跳转到用户管理管理页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/getUserPage")
    @ResponseBody
    public String getUserPage(ModelMap model, HttpServletRequest request) {
        return "getUserPage";
    }


}


