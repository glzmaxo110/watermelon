package com.xx.watermelon.study.serviceroute;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xiesx
 * @version 1.0.0
 * @description ServiceRouteController 服务路由测试控制器
 * @copyright 2018-11-06 18:33
 */
@Controller
@RequestMapping("/serviceRoute")
public class ServiceRouteController {

    /**
     * 测试spring方式获取服务路由
     * @param request
     * @param params
     * @return
     */
    @RequestMapping("/getServiceRouteTest")
    @ResponseBody
    public String getServiceRouteTest(HttpServletRequest request, @RequestParam Map<String, Object> params) {

        // 阿里云短信
        Integer aliyunIndexId = 1;
        // 网易
        Integer wangYiIndexId = 2;

        IMessageService aliyunMsgService = (IMessageService) ServiceRouteFactory.getBean(aliyunIndexId);
        // 调用抽象类的方法
        aliyunMsgService.checkParams(null);
        aliyunMsgService.sendMessage("", "");
        aliyunMsgService.acceptReceipt("");

        IMessageService wangYiMsgService = (IMessageService) ServiceRouteFactory.getBean(wangYiIndexId);
        // 调用抽象类的方法
        aliyunMsgService.checkParams(null);
        wangYiMsgService.sendMessage("", "");
        wangYiMsgService.acceptReceipt("");

        return aliyunMsgService.toString() + wangYiMsgService.toString();
    }

}
