package com.yc.quzhaunfa.impl;

import com.yc.quzhaunfa.base.BasePresenter;
import com.yc.quzhaunfa.base.IBaseView;
import com.yc.quzhaunfa.bean.DataBean;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/19
 * Time: 12:34
 */
public interface CashContract {

    interface View extends IBaseView {

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void cash(DataBean bankBean, double balance);
    }

}
