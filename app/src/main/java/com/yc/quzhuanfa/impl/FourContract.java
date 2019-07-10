package com.yc.quzhuanfa.impl;

import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.base.IBaseView;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.weight.WithScrollListView;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/17
 * Time: 10:44
 */
public interface FourContract {

    interface View extends IBaseView {

        void setData(JSONObject userObj);

        void onSignList(List<DataBean> result);

        void onSign();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void listView(WithScrollListView listView, BaseFragment root);

        public abstract void onRequest();

        public abstract void sign();

        public abstract void signList();

        public abstract void setPhone(String phoneNum);
    }


}
