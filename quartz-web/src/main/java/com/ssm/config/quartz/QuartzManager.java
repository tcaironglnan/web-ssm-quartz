package com.ssm.config.quartz;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时器管理类
 * 默认会找quartz.properties配置文件中的配置参数
 * 如果不存在该配置文件，则会使用自定义的配置。
 * 如果存在两者，或是该配置文件存在问题，则会抛异常
 * 一个job可以被多个Trigger 绑定，但是一个Trigger只能绑定一个job
 *
 * @author FeeMo
 */
public class QuartzManager {

    //region 变量定义部分
    private static final Logger logger = LoggerFactory.getLogger(QuartzManager.class);
//    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    //endregion

    //region 方法部分

    /**
     * 添加一个定时任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass         任务执行类
     * @param cron             时间设置，参考quartz说明文档
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, String cron) throws SchedulerException {
        //获取调度器
        Scheduler scheduler = SchedulerFactoryManager.create().getScheduler();
        // 任务名，任务组，任务执行类
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
        // 触发器
        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
        // 触发器名,触发器组
        triggerBuilder.withIdentity(triggerName, triggerGroupName);
        triggerBuilder.startNow();
        // 触发器时间设定
        triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
        // 创建Trigger对象
        CronTrigger trigger = (CronTrigger) triggerBuilder.build();
        // 调度容器设置JobDetail和Trigger
        scheduler.scheduleJob(jobDetail, trigger);
        // 启动
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }
    }

    /**
     * 修改一个任务的触发时间
     *
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param cron             时间设置，参考quartz说明文档
     */
    public static void modifyJobTime(String triggerName, String triggerGroupName, String cron) throws SchedulerException {
        //获取调度器
        Scheduler scheduler = SchedulerFactoryManager.create().getScheduler();
        //初始化触发器的触发器参数
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
        //创建触发器
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        //判断触发器是否有创建
        if (trigger == null) {
            return;
        }
        //获取上一次的定时时间表达式
        String oldTime = trigger.getCronExpression();
        //如果更新的时间一致的话，则不进行修改操作
        if (!oldTime.equalsIgnoreCase(cron)) {
            // 触发器 创建新的触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            //开始执行触发器
            triggerBuilder.startNow();
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            trigger = (CronTrigger) triggerBuilder.build();
            //修改一个任务的触发时间 即：用新的触发器替换旧的触发器
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    /**
     * 修改一个任务的触发时间
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param cron             时间设置，参考quartz说明文档
     */
    public static void modifyJobTime(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String cron) throws SchedulerException {
        //获取调度器
        Scheduler scheduler = SchedulerFactoryManager.create().getScheduler();
        //初始化触发器的触发器参数
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
        //创建触发器
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        //判断触发器是否有创建
        if (trigger == null) {
            return;
        }
        //获取上一次的定时时间表达式
        String oldTime = trigger.getCronExpression();
        //判断两次的时间是否一样
        if (!oldTime.equalsIgnoreCase(cron)) {
            //获取上一次的定义定时任务实例
            JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
            //获取业务执行类
            Class<? extends Job> jobClass = jobDetail.getJobClass();
            //调用移除定时器方法
            removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
            //调用添加定时器方法
            addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
        }
    }

    /**
     * 移除指定任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     */
    public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) throws SchedulerException {
        //获取调度器
        Scheduler scheduler = SchedulerFactoryManager.create().getScheduler();
        //初始化触发器
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
        // 停止触发器
        scheduler.pauseTrigger(triggerKey);
        // 移除触发器
        scheduler.unscheduleJob(triggerKey);
        // 删除任务
        scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
    }

    /**
     * 启动所有定时任务
     */
    public static void startJobs() throws SchedulerException {
        //获取调度器
        Scheduler scheduler = SchedulerFactoryManager.create().getScheduler();
        //开始执行任务
        scheduler.start();
    }

    /**
     * 关闭所有定时任务
     */
    public static void shutdownJobs() throws SchedulerException {
        //获取调度器
        Scheduler scheduler = SchedulerFactoryManager.create().getScheduler();
        //如果调度器还未关闭，则进入
        if (!scheduler.isShutdown()) {
            //关闭调度器（计划任务）
            scheduler.shutdown();
        }
    }
    //endregion
}