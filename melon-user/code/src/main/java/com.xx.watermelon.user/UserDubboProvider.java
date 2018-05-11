package com.zc.travel.log;

import com.zc.travel.common.utils.string.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.TimeZone;

/**
 * 日志服务启动类
 */
public class LogDubboProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogDubboProvider.class);

    public static void main(String[] args) {
        try {
            System.setProperty("dubbo.application.logger","slf4j");
            System.setProperty("user.timezone", "Asia/Shanghai");
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
            context.start();
            LOGGER.info("##INFO: LogDubboProvider context start success.");
            StrUtil.BoringPrint();
        } catch (Exception e) {
            LOGGER.error("##ERROR: LogDubboProvider context start error:", e);
        }
        synchronized (LogDubboProvider.class) {
            while (true) {
                try {
                    LogDubboProvider.class.wait();
                } catch (InterruptedException e) {
                    LOGGER.error("##ERROR: LogDubboProvider synchronized error:", e);
                }
            }
        }
    }

}