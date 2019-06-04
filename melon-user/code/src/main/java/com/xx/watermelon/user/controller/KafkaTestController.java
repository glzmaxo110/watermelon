package com.xx.watermelon.user.controller;

import com.xx.watermelon.common.util.web.WebParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author user
 * @version 1.0, Created by xiesx on 2018-05-26 18:33.
 * @description CrmMemberController 2018/5/26
 * @copyright 2018-05-26 18:33
 */
@Controller
@RequestMapping("/kafka")
public class KafkaTestController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("/sendMQ")
    @ResponseBody
    public String sendMQ(HttpServletRequest request, @RequestParam Map<String, Object> params) {
        String msg = WebParamUtils.getStringValue(params.get("msg"));
        kafkaTemplate.send("nginxlog", msg);
        return "发送消息成功";
    }

}
