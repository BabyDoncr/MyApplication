package com.example.jxwoer.myapplication.pojo;

/**
 * Created by JXwoer on 2018/7/8.
 */

public class Good {

    private int gid;
    private int gtnumber;
    private int gnumber;
    private Float gtprice;
    private Float gprice;
    private String gtype;
    private int uid;
    private String rtype;
    private Integer rnumber;

    public Good(int gid,int gnumber,int gtnumber,Float gtprice,int uid,int rnumber,Float gprice,String gtype,String rtype){
        this.gid=gid;

        this.gtnumber=gtnumber;
        this.gnumber=gnumber;
        this.gprice=gprice;
        this.gtprice=gtprice;
        this.gtype=gtype;
        this.rtype=rtype;
        this.uid=uid;
        this.rnumber=rnumber;

    }

    public int getGid() {
        return gid;
    }

    public int getGtnumber() {
        return gtnumber;
    }

    public int getGnumber() {
        return gnumber;
    }

    public Float getGtprice() {
        return gtprice;
    }

    public Float getGprice() {
        return gprice;
    }

    public String getGtype() {
        return gtype;
    }

    public int getUid() {
        return uid;
    }

    public String getRtype() {
        return rtype;
    }

    public Integer getRnumber() {
        return rnumber;
    }


}
