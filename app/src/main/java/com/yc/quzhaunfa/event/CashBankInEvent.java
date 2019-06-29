package com.yc.quzhaunfa.event;

import com.yc.quzhaunfa.bean.DataBean;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/27
 * Time: 19:10
 */
public class CashBankInEvent {

    public DataBean bean;

    public CashBankInEvent(DataBean bean) {
        this.bean = bean;
    }
}
