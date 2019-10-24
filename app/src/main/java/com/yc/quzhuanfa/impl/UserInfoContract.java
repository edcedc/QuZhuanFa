package com.yc.quzhuanfa.impl;

import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.base.IBaseView;
import com.yc.quzhuanfa.bean.DataBean;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/16
 * Time: 21:17
 */
public interface UserInfoContract {

    interface View extends IBaseView {

        void setSuccess(DataBean result);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onUpdateUserInfo(int sex, String userName, String head);

    }

}
