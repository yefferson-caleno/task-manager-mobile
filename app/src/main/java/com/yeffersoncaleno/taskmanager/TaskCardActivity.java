package com.yeffersoncaleno.taskmanager;


public class TaskCardActivity {

    private String title;
    private String state;

    public TaskCardActivity(String title, String state) {
        this.title = title;
        this.state = state;
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