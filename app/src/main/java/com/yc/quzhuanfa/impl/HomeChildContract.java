package com.yc.quzhuanfa.impl;

import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.base.IBaseListView;
import com.yc.quzhuanfa.bean.DataBean;

import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 11:40
 */
public interface HomeChildContract {

    interface View extends IBaseListView {


        void setBanner(List<DataBean> list);

        void setProfitOne(DataBean result);

        void setCashRecordList(List<DataBean> result);
    }

     abstract class Presenter extends BasePresenter<View> {

        public abstract void onBanner(String id);

        public abstract void onRequest(int pagerNumber, String id);

         public abstract void onProfitOne();

         public abstract void onGetUserCashRecordList();

     }


}
