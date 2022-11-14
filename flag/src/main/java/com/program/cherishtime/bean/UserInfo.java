package com.program.cherishtime.bean;

import org.jetbrains.annotations.NotNull;

public class UserInfo {
    /**
     * birthday : 1999-11-21
     * level : 3
     * sex : 1
     * portrait : /1595693453971_-29852d3c9ea8192e.jpg
     * follow : 4
     * point : 74
     * fan : 3
     * phone : 18163300957
     * nickname : 柒
     * days : 0
     * id : 1
     * vip : 1
     * exp : 928
     * account : 1234561
     * email : 1042360862@qq.com
     * likes : 0
     */

    private String birthday;
    private int level;
    private int sex;
    private String portrait;
    private int follow;
    private int point;
    private int fan;
    private String phone;
    private String nickname;
    private int days;
    private int id;
    private int vip;
    private int exp;
    private String account;
    private String email;
    private int likes;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getFan() {
        return fan;
    }

    public void setFan(int fan) {
        this.fan = fan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return "UserInfo{" +
                "birthday='" + birthday + '\'' +
                ", level=" + level +
                ", sex=" + sex +
                ", portrait='" + portrait + '\'' +
                ", follow=" + follow +
                ", point=" + point +
                ", fan=" + fan +
                ", phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", days=" + days +
                ", id=" + id +
                ", vip=" + vip +
                ", exp=" + exp +
                ", account='" + account + '\'' +
                ", email='" + email + '\'' +
                ", likes=" + likes +
                '}';
    }
}
