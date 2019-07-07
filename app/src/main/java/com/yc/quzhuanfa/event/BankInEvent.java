package com.yc.quzhuanfa.event;

import com.yc.quzhuanfa.bean.DataBean;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/27
 * Time: 18:37
 */
public class BankInEvent {

    public int position;
    public DataBean bean;

    public BankInEvent(int position, DataBean bean) {
        this.position = position;
        this.bean = bean;
    }
}
