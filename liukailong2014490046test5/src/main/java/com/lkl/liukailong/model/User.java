package com.lkl.liukailong.model;

public class User {
    private int id;
    private String theme;
    private String article;
    private String writer;
    private String time;

    public User(int id, String theme, String article, String writer, String time) {
        this.id = id;
        this.theme = theme;
        this.article = article;
        this.writer = writer;
        this.time = time;
    }

    public User(String theme, String article, String writer, String time) {
        this.theme = theme;
        this.article = article;
        this.writer = writer;
        this.time = time;
    }

    public User() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", theme='" + theme + '\'' +
                ", article='" + article + '\'' +
                ", writer='" + writer + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
