package com.ssm.service.impl;

import com.ssm.service.QuartzService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("quartzService")
public class QuartzServiceImpl implements QuartzService {

    private static final Logger logger = LoggerFactory.getLogger(QuartzServiceImpl.class);
    @Autowired
    private Scheduler quartzScheduler;

    /**
     * 添加一个定时任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param cls              任务执行类
     * @param cron             时间设置，参考quartz说明文档
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
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
            logger.error("Add Task Error,The Error Info is:" + e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改一个任务的触发时间
     *
     * @param jobName      任务名
     * @param jobGroup     任务组名
     * @param triggerName  触发器名
     * @param triggerGroup 触发器组名
     * @param cron         时间设置，参考quartz说明文档
     */
    @Override
    public void modifyJobTime(String jobName, String jobGroup, String triggerName, String triggerGroup, String cron) {
        try {
            CronTrigger trigger = (CronTrigger) quartzScheduler.getTrigger(TriggerKey.triggerKey(triggerName, triggerGroup));
            if (trigger == null) {
                return;
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
        } catch (Exception e) {
            logger.error("Modify Task Error,The Error Info is:" + e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改一个任务的触发时间
     *
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param cron             时间设置，参考quartz说明文档
     */
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
            logger.error("Modify Task Error,The Error Info is:" + e);
            throw new RuntimeException(e);
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
            logger.error("Remove Task Error,The Error Info is:" + e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 启动所有定时任务
     */
    @Override
    public void startSchedule() {
        try {
            quartzScheduler.start();
        } catch (Exception e) {
            logger.error("Start Task Error,The Error Info is:" + e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭所有定时任务
     */
    @Override
    public void shutdownSchedule() {
        try {
            if (!quartzScheduler.isShutdown()) {
                quartzScheduler.shutdown();
            }
        } catch (Exception e) {
            logger.error("Shut Down Task Error,The Error Info is:" + e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 暂停指定的任务
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名称
     */
    @Override
    public void pauseJob(String jobName, String jobGroupName) {
        try {
            quartzScheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (SchedulerException e) {
            logger.error("Pause Task Error,The Error Info is:" + e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 恢复指定的任务
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名称
     */
    @Override
    public void resumeJob(String jobName, String jobGroupName) {
        try {
            quartzScheduler.resumeJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (SchedulerException e) {
            logger.error("Resume Task Error,The Error Info is:" + e);
            throw new RuntimeException(e);
        }
    }
}