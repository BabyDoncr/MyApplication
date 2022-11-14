package com.example.jxwoer.myapplication.pojo;

/**
 * Created by louisgeek on 2016/4/27.
 */
public class Buy {

    public static final  int STATUS_INVALID=0;
    public static final  int STATUS_VALID=1;
    //===============================================
    private String bid;
    private String cid;
    private String type;
    /** 商品宣传图片 */
    private String imageLogo;
    /** 商品规格 */
    private String rtype;
    /** 原价，市场价 */
  //  private Float price;
    /** 现价，折扣价 */
    private float price;
    private int number;
    /** 状态 */
    private int status;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRtype() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    /** 是否被选中 */
    private boolean isChecked;
    /** 是否是编辑状态 */
    private boolean isEditing;



    public Buy(String bid, String cid, String type, String rtype, float price, int number) {
        this.bid = bid;
        this.cid=cid;
        this.type = type;
        this.imageLogo = imageLogo;
        this.rtype = rtype;
        this.price = price;
        this.number=number;
     //   this.discountPrice = discountPrice;
     //   this.count = count;
        this.status = status;
        this.isChecked = isChecked;
        this.isEditing = isEditing;
    }
    public Buy(){

    }




    public String getImageLogo() {
        return imageLogo;
    }

    public void setImageLogo(String imageLogo) {
        this.imageLogo = imageLogo;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
    public boolean isEditing() {
        return isEditing;
    }
    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }
}
