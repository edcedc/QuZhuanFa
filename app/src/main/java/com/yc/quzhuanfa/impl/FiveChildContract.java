package com.yc.quzhuanfa.impl;

import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.base.IBaseListView;
import com.yc.quzhuanfa.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/10
 * Time: 18:43
 */
public interface FiveChildContract {

    interface View extends IBaseListView {

        void setBanner(List<DataBean> list);

        void setCollect(int position, int isTrue);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onBanner(String id);

        public abstract void onRequest(int pagerNumber, String id);

        public abstract void onCollectList(int pagerNumber);

        public abstract void onVideoCollect(int position, String videoId, int isTrue);
    }

}
