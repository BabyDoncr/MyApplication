package com.example.jxwoer.myapplication.pojo;

/**
 * Created by JXwoer on 2018/6/28.
 */

public class Icream   {
    private String  name;
    private  int price;
    private int imageId;

    public Icream(String  name ,int price,int imageId){
      this.name=name;
        this.price=price;
        this.imageId=imageId;

    }
    public int getPrice() {
        return price;
    }


    public String getName() {
        return name;
    }



    public int getImageId() {
        return imageId;
    }

}
