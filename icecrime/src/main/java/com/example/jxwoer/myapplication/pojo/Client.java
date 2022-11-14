package com.example.jxwoer.myapplication.pojo;

/**
 * Created by JXwoer on 2018/7/4.
 */

public class Client {
  private int  cid;
 private  String cname;
    private String  cpaw;
    private String ctel;
    private String caddress;
    private Integer flag;

    public String getCtel() {
        return ctel;
    }

    public void setCtel(String ctel) {
        this.ctel = ctel;
    }

    public String getCaddress() {
        return caddress;
    }

    public void setCaddress(String caddress) {
        this.caddress = caddress;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCpaw() {
        return cpaw;
    }

    public void setCpaw(String cpaw) {
        this.cpaw = cpaw;
    }
}
