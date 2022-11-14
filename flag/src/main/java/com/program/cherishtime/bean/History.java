package com.program.cherishtime.bean;

import org.jetbrains.annotations.NotNull;

public class History {
    private int uid;
    private int tid;
    private long dateTime;
    private String title;
    private int complete;
    private int del;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public int getDel() {
        return del;
    }

    public void setDel(int del) {
        this.del = del;
    }

    @NotNull
    @Override
    public String toString() {
        return "History{" +
                "uid=" + uid +
                ", tid=" + tid +
                ", dateTime='" + dateTime + '\'' +
                ", title='" + title + '\'' +
                ", complete=" + complete +
                ", del=" + del +
                '}';
    }
}
