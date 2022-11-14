package com.program.cherishtime.bean;

import org.jetbrains.annotations.NotNull;

public class Rank {

    /**
     * id : 1
     * img : /1595693453971_-29852d3c9ea8192e.jpg
     * name : 柒
     * num : 1
     * point : 74
     * days : 0
     */

    private int id;
    private String img;
    private String name;
    private int num;
    private int point;
    private int days;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    @NotNull
    @Override
    public String toString() {
        return "Rank{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", point=" + point +
                ", days=" + days +
                '}';
    }
}
