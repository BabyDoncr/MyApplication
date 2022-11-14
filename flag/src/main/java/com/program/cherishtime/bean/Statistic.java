package com.program.cherishtime.bean;

import org.jetbrains.annotations.NotNull;

//@SuppressWarnings("unused")
public class Statistic {
    /**
     * sid : 1
     * uid : 1
     * time : 20200726
     * total : 8
     * done : 3
     * giveUp : 5
     * study : 4
     * rest : 3
     * sports : 1
     * other : 0
     */

    private int sid;
    private int uid;
    private String time;
    private int total;
    private int done;
    private int giveUp;
    private int study;
    private int rest;
    private int sports;
    private int other;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getGiveUp() {
        return giveUp;
    }

    public void setGiveUp(int giveUp) {
        this.giveUp = giveUp;
    }

    public int getStudy() {
        return study;
    }

    public void setStudy(int study) {
        this.study = study;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public int getSports() {
        return sports;
    }

    public void setSports(int sports) {
        this.sports = sports;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    @NotNull
    @Override
    public String toString() {
        return "Statistic{" +
                "sid=" + sid +
                ", uid=" + uid +
                ", time='" + time + '\'' +
                ", total=" + total +
                ", done=" + done +
                ", giveUp=" + giveUp +
                ", study=" + study +
                ", rest=" + rest +
                ", sports=" + sports +
                ", other=" + other +
                '}';
    }
}
