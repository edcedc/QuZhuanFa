package com.yc.quzhuanfa.impl;

import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.base.IBaseListView;
import com.yc.quzhuanfa.base.IBaseView;
import com.yc.quzhuanfa.bean.DataBean;

import java.util.List;

public interface DetailsContract {

    interface View extends IBaseListView {

        void setShare(String short_url);

        void onRandom(List<DataBean> result);

        void setSaveDiscuss(DataBean bean);

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onSaveForward(String id);

        public abstract void setType(int type);

        public abstract void onShareUrl(String url);

        public abstract void onRandom();

        public abstract void onArticleDiscussesList(int i, String articleId);

        public abstract void onSaveDiscuss(String articleId, String content);

    }
    
}
