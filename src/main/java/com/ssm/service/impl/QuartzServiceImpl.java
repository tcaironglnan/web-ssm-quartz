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
    public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class cls, String cron) {
        try {
            // 获取调度器
            // 创建一项作业
            JobDetail job = JobBuilder.newJob(cls).withIdentity(jobName, jobGroupName).build();
            // 创建一个触发器
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerName, triggerGroupName)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();
            // 告诉调度器使用该触发器来安排作业
            quartzScheduler.scheduleJob(job, trigger);
            // 启动
            if (!quartzScheduler.isShutdown()) {
                quartzScheduler.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改定时器任务信息
     */
    @Override
    public boolean modifyJobTime(String jobName, String jobGroup, String triggerName, String triggerGroup, String cron) {
        try {
            CronTrigger trigger = (CronTrigger) quartzScheduler.getTrigger(TriggerKey.triggerKey(triggerName, triggerGroup));
            if (trigger == null) {
                return false;
            }

            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);

            JobDetail job = quartzScheduler.getJobDetail(jobKey);
            Class jobClass = job.getJobClass();
            // 停止触发器
            quartzScheduler.pauseTrigger(triggerKey);
            // 移除触发器
            quartzScheduler.unscheduleJob(triggerKey);
            // 删除任务
            quartzScheduler.deleteJob(jobKey);
            addJob(jobName, jobGroup, triggerName, triggerGroup, jobClass, cron);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void modifyJobTime(String triggerName, String triggerGroupName, String cron) {
        try {
            CronTrigger trigger = (CronTrigger) quartzScheduler.getTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                // 修改时间
                trigger.getTriggerBuilder().withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
                // 重启触发器
                quartzScheduler.resumeTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
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
