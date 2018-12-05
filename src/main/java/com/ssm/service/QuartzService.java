package com.ssm.service;

/**
 * 定时器管理接口
 * 默认会找quartz.properties配置文件中的配置参数
 * 如果不存在该配置文件，则会使用自定义的配置。
 * 如果存在两者，或是该配置文件存在问题，则会抛异常
 *
 * @author FeeMo
 */
public interface QuartzService {

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
    void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class cls, String cron);

    /**
     * 修改一个任务的触发时间
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param cron             时间设置，参考quartz说明文档
     */
    void modifyJobTime(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String cron);

    /**
     * 修改一个任务的触发时间
     *
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param cron             时间设置，参考quartz说明文档
     */
    void modifyJobTime(String triggerName, String triggerGroupName, String cron);

    /**
     * 暂停指定的任务
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名称
     */
    void pauseJob(String jobName, String jobGroupName);

    /**
     * 恢复指定的任务
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名称
     */
    void resumeJob(String jobName, String jobGroupName);

    /**
     * 移除指定任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     */
    void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName);

    /**
     * 开始所有定时任务/启动调度器
     */
    void startSchedule();

    /**
     * 关闭所有调度器/关闭调度器
     */
    void shutdownSchedule();
}
