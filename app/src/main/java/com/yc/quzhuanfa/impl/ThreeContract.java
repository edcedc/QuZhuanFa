package com.yc.quzhuanfa.impl;

import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.base.IBaseView;
import com.yc.quzhuanfa.bean.DataBean;

import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/21
 * Time: 20:40
 */
public interface ThreeContract {

    interface View extends IBaseView {

        void setData(List<DataBean> list);

        void onTodayYesterdayALl(DataBean bean);

        void setShare(String short_url);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void getUserList();

        public abstract void getTodayYesterdayALl();

        public abstract void onShareUrl(String url);
    }

}
