package com.ssm.service;

public interface QuartzService {

    /**
     * addJob(方法描述：添加一个定时任务) <br />
     * (方法适用条件描述： – 可选)
     *
     * @param jobName          作业名称
     * @param jobGroupName     作业组名称
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     * @param cls              定时任务的class
     * @param cron             时间表达式 void
     * @throws
     * @since 1.0.0
     */
    void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class cls, String cron);

    /**
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @param cron
     * @return
     */
    boolean modifyJobTime(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String cron);

    /**
     * 修改触发器调度时间
     *
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     * @param cron             cron表达式
     */
    void modifyJobTime(String triggerName,
                       String triggerGroupName, String cron);


    /**
     * 暂停指定的任务
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名称
     * @return
     */
    void pauseJob(String jobName, String jobGroupName);

    /**
     * 恢复指定的任务
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名称
     * @return
     */
    void resumeJob(String jobName, String jobGroupName);

    /**
     * 删除指定组任务
     *
     * @param jobName          作业名称
     * @param jobGroupName     作业组名称
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     */
    void removeJob(String jobName, String jobGroupName,
                   String triggerName, String triggerGroupName);


    /**
     * 开始所有定时任务。启动调度器
     */
    void startSchedule();

    /**
     * 关闭调度器
     */
    void shutdownSchedule();
}
