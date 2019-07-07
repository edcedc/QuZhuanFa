package com.yc.quzhuanfa.bean;


import java.io.Serializable;

/**
 * Created by yc on 2017/8/17.
 */

public class DataBean implements Serializable {

    private String name;
    private String image;
    private int img;
    private String className;
    private String classId;
    private String title;
    private int price;
    private int readNum;//阅读数量
    private int forwardNum;//转发数量
    private int loginPrice;
    private int sharePrice;
    private int apprenticePrice;
    private int share;
    private int login;
    private int apprentice;
    private String content;
    private int bankId;
    private String bankName;
    private String bankDeposit;
    private String phoneNum;
    private String bankNum;
    private String userCard;
    private int balanceType;
    private double balance;
    private int type;
    private String createTime;
    private String head;
    private String userId;
    private double balanceAll;
    private String pic;
    private double all;
    private double now;
    private double yesDay;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public double getAll() {
        return all;
    }

    public double getNow() {
        return now;
    }

    public double getYesDay() {
        return yesDay;
    }

    public String getPic() {
        return pic;
    }

    public double getBalanceAll() {
        return balanceAll;
    }

    public String getUserId() {
        return userId;
    }

    public String getHead() {
        return head;
    }

    public String getCreateTime() {
        return createTime;
    }

    public int getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    public int getBalanceType() {
        return balanceType;
    }

    public int getBankId() {
        return bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankDeposit() {
        return bankDeposit;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getBankNum() {
        return bankNum;
    }

    public String getUserCard() {
        return userCard;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLoginPrice() {
        return loginPrice;
    }

    public int getSharePrice() {
        return sharePrice;
    }

    public int getApprenticePrice() {
        return apprenticePrice;
    }

    public int getShare() {
        return share;
    }

    public int getLogin() {
        return login;
    }

    public int getApprentice() {
        return apprentice;
    }

    public int getForwardNum() {
        return forwardNum;
    }

    public int getReadNum() {
        return readNum;
    }

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getClassId() {
        return classId;
    }

    public String getClassName() {
        return className;
    }

    public String getImage() {
        return image;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}