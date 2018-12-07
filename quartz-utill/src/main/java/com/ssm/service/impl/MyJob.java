package com.ssm.service.impl;

import com.ssm.model.UserModel;
import com.ssm.service.UserService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Job有状态实现类，不允许并发执行
 * 若一个方法一次执行不完下次轮转时则等待该方法执行完后才执行下一次操作
 * 主要是通过注解：@DisallowConcurrentExecution
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
@Component
public class MyJob implements Job {

    @Autowired
    private UserService userService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        UserModel userById = userService.findUserById(1L);
        System.err.println(userById);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " ****************** doing something...");
    }
}