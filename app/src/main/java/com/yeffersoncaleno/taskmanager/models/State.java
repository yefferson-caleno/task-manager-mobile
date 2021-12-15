package com.yeffersoncaleno.taskmanager.models;

import java.util.Date;

public class State {
    private String uid;
    private String stateDescription;
    private Date stateCreated;
    private Date stateUpdated;

    public State() {
    }

    public State(String uid, String stateDescription, Date stateCreated,
                 Date stateUpdated) {
        this.uid = uid;
        this.stateDescription = stateDescription;
        this.stateCreated = stateCreated;
        this.stateUpdated = stateUpdated;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStateDescription() {
        return stateDescription;
    }

    public void setStateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
    }

    public Date getStateCreated() {
        return stateCreated;
    }

    public void setStateCreated(Date stateCreated) {
        this.stateCreated = stateCreated;
    }

    public Date getStateUpdated() {
        return stateUpdated;
    }

    public void setStateUpdated(Date stateUpdated) {
        this.stateUpdated = stateUpdated;
    }
}
