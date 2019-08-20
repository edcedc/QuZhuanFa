package com.yc.quzhuanfa.impl;

import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.base.IBaseView;

public interface DetailsContract {

    interface View extends IBaseView {

        void setShare(String short_url);
    }

    abstract class Presenter extends BasePresenter<View> {


        public abstract void onSaveForward(String id);

        public abstract void setType(int type);

        public abstract void onShareUrl(String url);
    }
    
}
