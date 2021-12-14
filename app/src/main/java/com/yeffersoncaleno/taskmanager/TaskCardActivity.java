package com.yeffersoncaleno.taskmanager;


public class TaskCardActivity {

    private String uid;
    private String title;
    private String state;

    public TaskCardActivity(String uid, String title, String state) {
        this.uid = uid;
        this.title = title;
        this.state = state;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}