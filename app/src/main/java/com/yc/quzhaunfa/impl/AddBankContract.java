package com.yc.quzhaunfa.impl;

import com.yc.quzhaunfa.base.BasePresenter;
import com.yc.quzhaunfa.base.IBaseView;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/21
 * Time: 19:07
 */
public interface AddBankContract {

    interface View extends IBaseView {

        void onSaveBank();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void addBnak(String bankNum, String openBank, String userName, String userCard, String phone, boolean checked, String id);
    }

}
