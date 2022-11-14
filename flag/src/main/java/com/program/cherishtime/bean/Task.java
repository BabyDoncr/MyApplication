package com.program.cherishtime.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

//@SuppressWarnings("unused")
public class Task implements Parcelable {

    /**
     * id : 1
     * startTime : 07:00
     * endTime : 07:10
     * title : 早起
     * content : 早起的鸟儿有虫吃！请在既定时间段内起床打卡签到！并为早上复习做好准备！
     * type : 1
     * taskDate : 20200814
     * del : 0
     */

    private int id;
    private String startTime;
    private String endTime;
    private String title;
    private String content;
    private int type;
    private int taskDate;
    private int del;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(startTime);
        parcel.writeString(endTime);
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeInt(type);
        parcel.writeInt(taskDate);
        parcel.writeInt(del);
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel parcel) {
            Task task = new Task();
            task.id = parcel.readInt();
            task.startTime = parcel.readString();
            task.endTime = parcel.readString();
            task.title = parcel.readString();
            task.content = parcel.readString();
            task.type = parcel.readInt();
            task.taskDate = parcel.readInt();
            task.del = parcel.readInt();
            return task;
        }

        @Override
        public Task[] newArray(int i) {
            return new Task[i];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(int taskDate) {
        this.taskDate = taskDate;
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
        return "Task2{" +
                "id=" + id +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", taskDate=" + taskDate +
                ", del=" + del +
                '}';
    }
}

