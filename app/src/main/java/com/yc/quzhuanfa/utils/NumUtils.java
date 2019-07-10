package com.yc.quzhuanfa.utils;

import java.util.regex.Pattern;

public class NumUtils {

    public static String getPrice(String price){
        Pattern pa1 = Pattern.compile("^[0-9].[0]+$");
        if(pa1.matcher(price).matches()){
            String s = price.substring(0, price.lastIndexOf("."));
            return s;
        }else{
            return price;
        }
    }

    public static String doubleTrans1(double num){
        if(num % 1.0 == 0){
            return String.valueOf((long)num);
        }
        return String.valueOf(num);
    }
}
