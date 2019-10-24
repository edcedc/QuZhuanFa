package com.yc.quzhuanfa.impl;

import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.base.IBaseListView;
import com.yc.quzhuanfa.base.IBaseView;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.bean.SearchListBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/21
 * Time: 14:22
 */
public interface SearchVideoContract {

    interface View extends IBaseListView {

        void setHotitleList(List<DataBean> result);

        void setCollect(int position, int finalIsTrue);

        void setHistory(List<SearchListBean> all);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagerNumber, String text);

        public abstract void onHotitleList();

        public abstract void onVideoCollect(int position, String videoId, int isTrue);

        public abstract void addHistory(String searchText);

        public abstract void onHistory();
    }

}
