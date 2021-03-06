package com.xx.watemelon.bigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description BigDataApplication 2018/8/7
 * @author   Created by  on 2018-08-07 18:53.
 * @copyright 2018-08-07 18:53
 * @version 1.0
 */
@EnableDiscoveryClient
@SpringBootApplication
public class BigDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigDataApplication.class, args);
    }

}
