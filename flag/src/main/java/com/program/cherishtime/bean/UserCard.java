package com.program.cherishtime.bean;

import org.jetbrains.annotations.NotNull;

public class UserCard {
    /**
     * fan : 2
     * level : 3
     * sex : null
     * nickname : 柒
     * id : 1
     * portrait : /1595155708860_zzx.png
     * follow : 4
     * vip : 1
     * exp : 892
     * type : 0
     * likes : 0
     */

    private int fan;
    private int level;
    private int sex;
    private String nickname;
    private int id;
    private String portrait;
    private int follow;
    private int vip;
    private int exp;
    private int type;
    private int likes;

    public int getFan() {
        return fan;
    }

    public void setFan(int fan) {
        this.fan = fan;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @NotNull
    @Override
    public String toString() {
        return "UserCard{" +
                "fan=" + fan +
                ", level=" + level +
                ", sex=" + sex +
                ", nickname='" + nickname + '\'' +
                ", id=" + id +
                ", portrait='" + portrait + '\'' +
                ", follow=" + follow +
                ", vip=" + vip +
                ", exp=" + exp +
                ", type=" + type +
                ", likes=" + likes +
                '}';
    }
}
