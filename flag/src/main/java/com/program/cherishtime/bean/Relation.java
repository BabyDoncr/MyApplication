package com.program.cherishtime.bean;

import org.jetbrains.annotations.NotNull;

public class Relation {

    /**
     * img : /spongebob.png
     * name : 十三
     * id : 2
     * state : 2
     * type : 1
     * vip : 0
     */

    private String img;
    private String name;
    private int id;
    private int state;
    private int type;
    private int vip;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    @NotNull
    @Override
    public String toString() {
        return "Relation{" +
                "img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", state=" + state +
                ", type=" + type +
                ", vip=" + vip +
                '}';
    }
}
