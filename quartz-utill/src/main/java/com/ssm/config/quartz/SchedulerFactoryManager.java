package com.ssm.config.quartz;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 创建定时器的调度器类，以单例模式（线程安全）
 * @author Lenovo
 * @Created 2018-12-03 14:37
 **/
@Component
public class SchedulerFactoryManager {

    @Autowired
    private static Scheduler quartzScheduler;
//    private static SchedulerFactory quartzScheduler;

    private SchedulerFactoryManager() {
    }

    public static synchronized Scheduler create() {
        return quartzScheduler;
    }

    public Scheduler getQuartzScheduler() {
        return quartzScheduler;
    }

    public void setQuartzScheduler(Scheduler quartzScheduler) {
        SchedulerFactoryManager.quartzScheduler = quartzScheduler;
    }
}
