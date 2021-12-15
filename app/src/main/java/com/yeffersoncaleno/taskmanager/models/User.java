package com.yeffersoncaleno.taskmanager.models;

import java.time.LocalDateTime;
import java.util.Date;

public class User {
    private String uid;
    private String userName;
    private String userEmail;
    private Date userCreated;
    private Date userUpdated;

    public User() {
    }

    public User(String uid, String userName, String userEmail, Date userCreated,
                Date userUpdated) {
        this.uid = uid;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userCreated = userCreated;
        this.userUpdated = userUpdated;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Date userCreated) {
        this.userCreated = userCreated;
    }

    public Date getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(Date userUpdated) {
        this.userUpdated = userUpdated;
    }
}
