package com.yc.quzhuanfa.impl;

import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.base.IBaseListView;
import com.yc.quzhuanfa.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/17
 * Time: 17:48
 */
public interface VideoDescContract {

    interface View extends IBaseListView {


        void setVideoDiscussList(List<DataBean> result);

        void setSaveVideoDiscuss(DataBean result);

        void setCollect(int finalIsTrue);

        void setShare(String short_url);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int i, String videoId);
        public abstract void onVideoDiscussList();

        public abstract void onSaveVideoDiscuss(String videoId, String text);

        public abstract void onCollect(String videoId, int isTrue, int position);

        public abstract void onShareUrl(String s);

        public abstract void onSaveForward(String articleId);

        public abstract void onSaveHistory(String videoId);
    }

}
