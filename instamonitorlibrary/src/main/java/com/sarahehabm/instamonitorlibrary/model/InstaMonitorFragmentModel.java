package com.sarahehabm.instamonitorlibrary.model;

/**
 * Created by Sarah E. Mostafa on 24-May-16.
 */
public class InstaMonitorFragmentModel {
    private int id;
    private String name;
    private int totalTime;

    public InstaMonitorFragmentModel() {
    }

    public InstaMonitorFragmentModel(String name, int totalTime) {
        this.name = name;
        this.totalTime = totalTime;
    }

    public InstaMonitorFragmentModel(int id, String name, int totalTime) {
        this.id = id;
        this.name = name;
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

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
