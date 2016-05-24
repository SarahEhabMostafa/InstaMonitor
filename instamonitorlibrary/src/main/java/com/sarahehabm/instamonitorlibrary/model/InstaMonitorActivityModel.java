package com.sarahehabm.instamonitorlibrary.model;

/**
 Created by Sarah E. Mostafa on 23-May-16.
 */
public class InstaMonitorActivityModel {
    private int id;
    private String name;
    private boolean isIgnored;
    private int totalTime;

    public InstaMonitorActivityModel() {
    }

    public InstaMonitorActivityModel(String name, boolean isIgnored, int totalTime) {
        this.name = name;
        this.isIgnored = isIgnored;
        this.totalTime = totalTime;
    }

    public InstaMonitorActivityModel(int id, String name, boolean isIgnored, int totalTime) {
        this.id = id;
        this.name = name;
        this.isIgnored = isIgnored;
        this.totalTime = totalTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIgnored() {
        return isIgnored;
    }

    public void setIgnored(boolean ignored) {
        isIgnored = ignored;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
