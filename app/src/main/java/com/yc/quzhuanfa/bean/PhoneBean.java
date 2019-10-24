package com.yc.quzhuanfa.bean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/22
 * Time: 20:16
 */
public class PhoneBean extends LitePalSupport {

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
