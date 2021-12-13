package com.yeffersoncaleno.taskmanager.models;


import java.util.Date;

public class Task {

    private String uid;
    private String taskTitle;
    private String taskDescription;
    private String userCreatedId;
    private String stateId;
    private Date taskInit;
    private Date taskEnd;
    private Date taskCreated;
    private Date taskUpdated;

    public Task() {
    }

    public Task(String uid, String taskTitle, String taskDescription, String userCreatedId,
                String stateId, Date taskInit, Date taskEnd,
                Date taskCreated, Date taskUpdated) {
        this.uid = uid;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.userCreatedId = userCreatedId;
        this.stateId = stateId;
        this.taskInit = taskInit;
        this.taskEnd = taskEnd;
        this.taskCreated = taskCreated;
        this.taskUpdated = taskUpdated;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getUserCreatedId() {
        return userCreatedId;
    }

    public void setUserCreatedId(String userCreatedId) {
        this.userCreatedId = userCreatedId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public Date getTaskInit() {
        return taskInit;
    }

    public void setTaskInit(Date taskInit) {
        this.taskInit = taskInit;
    }

    public Date getTaskEnd() {
        return taskEnd;
    }

    public void setTaskEnd(Date taskEnd) {
        this.taskEnd = taskEnd;
    }

    public Date getTaskCreated() {
        return taskCreated;
    }

    public void setTaskCreated(Date taskCreated) {
        this.taskCreated = taskCreated;
    }

    public Date getTaskUpdated() {
        return taskUpdated;
    }

    public void setTaskUpdated(Date taskUpdated) {
        this.taskUpdated = taskUpdated;
    }
}
