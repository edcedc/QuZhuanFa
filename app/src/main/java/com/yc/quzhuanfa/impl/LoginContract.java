package com.yc.quzhuanfa.impl;

import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.base.IBaseView;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/18
 * Time: 12:33
 */
public interface LoginContract {

    interface View extends IBaseView {

        void onCode();

        void setWxNum(String content);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void code(String phone);

        public abstract void login(String phone, String code, String pwd, String invitation, boolean checked, int mPosition);

        public abstract void getAgreement();

        public abstract void wxLogin(String openid, String access_token);
    }

}
