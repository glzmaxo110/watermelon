package com.xx.watermelon.oss.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @version 1.0, Created by xiesx on 2018-06-07 19:01.
 * @description CrmMemberOssService 2018/6/7
 * @copyright 2018-06-07 19:01
 */
@Service
public class CrmMemberOssService {

    @Autowired
    RestTemplate restTemplate;

    public String getMember(Long id) {
        return restTemplate.getForObject("http://MELON-USER-CLIENT/hi?id=" + id, String.class);
    }

}
