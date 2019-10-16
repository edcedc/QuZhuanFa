package com.yc.quzhuanfa.impl;

import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.base.IBaseListView;
import com.yc.quzhuanfa.bean.DataBean;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/10
 * Time: 20:20
 */
public interface SixContract {

    interface View extends IBaseListView {

        void setSaveDiscuss(DataBean result);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagerNumber);

        public abstract void onComment(int pagerNumber, String friendId);

        public abstract void onSaveFriendsDiscuss( String friendId, String text, String puserId);
    }


}
