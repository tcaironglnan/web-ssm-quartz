package com.ssm.model;

import com.google.common.base.MoreObjects;

/**
 * @author Lenovo
 * @Created 2018-12-03 13:52
 **/
public class TaskModel {

    private Long uuid;
    private String jobName;
    private String jobGroupName;
    private String triggerName;
    private String triggerGroupName;
    private String cron;
    private String createDate;
    private String createBy;
    private String lastUpdateBy;
    private String lastUpdateDate;
    private String status;

    public TaskModel() {
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uuid", uuid)
                .add("jobName", jobName)
                .add("jobGroupName", jobGroupName)
                .add("triggerName", triggerName)
                .add("triggerGroupName", triggerGroupName)
                .add("cron", cron)
                .add("createDate", createDate)
                .add("createBy", createBy)
                .add("lastUpdateBy", lastUpdateBy)
                .add("lastUpdateDate", lastUpdateDate)
                .add("status", status)
                .toString();
    }

    public Long getUuid() {
        return uuid;
    }

    public TaskModel setUuid(Long uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getJobName() {
        return jobName;
    }

    public TaskModel setJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public TaskModel setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
        return this;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public TaskModel setTriggerName(String triggerName) {
        this.triggerName = triggerName;
        return this;
    }

    public String getTriggerGroupName() {
        return triggerGroupName;
    }

    public TaskModel setTriggerGroupName(String triggerGroupName) {
        this.triggerGroupName = triggerGroupName;
        return this;
    }

    public String getCron() {
        return cron;
    }

    public TaskModel setCron(String cron) {
        this.cron = cron;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public TaskModel setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getCreateBy() {
        return createBy;
    }

    public TaskModel setCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public TaskModel setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
        return this;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public TaskModel setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TaskModel setStatus(String status) {
        this.status = status;
        return this;
    }
}
