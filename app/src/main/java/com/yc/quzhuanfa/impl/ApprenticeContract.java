package com.yc.quzhuanfa.impl;

import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.base.IBaseListView;
import com.yc.quzhuanfa.bean.DataBean;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/28
 * Time: 21:46
 */
public interface ApprenticeContract {

    interface View extends IBaseListView {

        void TodayYesterday(DataBean result);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagerNumber, String userId);

        public abstract void onIncome(String userId);
    }


}
