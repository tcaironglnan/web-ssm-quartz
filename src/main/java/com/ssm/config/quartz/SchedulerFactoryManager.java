package com.ssm.config.quartz;

import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 创建定时器的调度器类，以单例模式（线程安全）
 * @author Lenovo
 * @Created 2018-12-03 14:37
 **/
public class SchedulerFactoryManager {

    private static SchedulerFactory schedulerFactory;

    private SchedulerFactoryManager() {
    }

    public static synchronized SchedulerFactory create() {
        if (schedulerFactory == null)
            schedulerFactory = new StdSchedulerFactory();
        return schedulerFactory;
    }
}
