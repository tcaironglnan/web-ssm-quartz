package com.ssm.service.impl;

import com.ssm.service.QuartzService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("quartzService")
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    private Scheduler quartzScheduler;

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class clazz, String cron) {
        try {
            //获取调度器
            SchedulerFactory schedulerFactory = (SchedulerFactory)quartzScheduler;
            Scheduler scheduler = schedulerFactory.getScheduler();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobName, jobGroupName).build();
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改定时器任务信息
     */
    @Override
    public boolean modifyJobTime(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String cron) {
        try {
            //获取调度器
            SchedulerFactory schedulerFactory = (SchedulerFactory)quartzScheduler;
            Scheduler scheduler = schedulerFactory.getScheduler();
            //初始化触发器的触发器参数
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            //创建触发器
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            //判断触发器是否有创建
            if (trigger == null) {
                return false;
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
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void modifyJobTime(String triggerName, String triggerGroupName, String cron) {
        try {
            //获取调度器
            SchedulerFactory schedulerFactory = (SchedulerFactory)quartzScheduler;
            Scheduler scheduler = schedulerFactory.getScheduler();
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            // 停止触发器
            quartzScheduler.pauseTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
            // 移除触发器
            quartzScheduler.unscheduleJob(TriggerKey.triggerKey(triggerName, triggerGroupName));
            // 删除任务
            quartzScheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startSchedule() {
        try {
            quartzScheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void shutdownSchedule() {
        try {
            if (!quartzScheduler.isShutdown()) {
                quartzScheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void pauseJob(String jobName, String jobGroupName) {
        try {
            quartzScheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void resumeJob(String jobName, String jobGroupName) {
        try {
            quartzScheduler.resumeJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
