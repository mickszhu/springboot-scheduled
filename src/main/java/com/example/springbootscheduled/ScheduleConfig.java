package com.example.springbootscheduled;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @program: springboot-scheduled
 * @description: 设置Schedule的线程池
 * @author: sjk
 * @create: 2018-08-02 15:45
 **/

@Configuration
//定时任务调用一个线程池中的线程。
public class ScheduleConfig implements SchedulingConfigurer {

    @Value("${delete.dateTime}")
    public  String delDay;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        //参数传入一个size为10的线程池
        scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(2));
    }
}
