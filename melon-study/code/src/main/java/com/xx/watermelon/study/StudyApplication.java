package com.xx.watermelon.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description StudyApplication 2018/10/10
 * @author   Created by  on 2018-10-10 18:53.
 * @copyright 2018-10-10 18:53
 * @version 1.0
 */
@EnableDiscoveryClient
@SpringBootApplication
public class StudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }

}
