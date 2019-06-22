package com.yc.quzhaunfa.impl;

import com.yc.quzhaunfa.base.BasePresenter;
import com.yc.quzhaunfa.base.IBaseView;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/18
 * Time: 22:53
 */
public interface RetrievePwdContract {

    interface View extends IBaseView {

        void onCode();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void code(String phone);

        public abstract void login(String phone, String code, String pwd, String pwd1);
    }

}
