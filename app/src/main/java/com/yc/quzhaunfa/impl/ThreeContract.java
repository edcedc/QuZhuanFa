package com.yc.quzhaunfa.impl;

import com.yc.quzhaunfa.base.BasePresenter;
import com.yc.quzhaunfa.base.IBaseView;
import com.yc.quzhaunfa.bean.DataBean;

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
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void getUserList();

        public abstract void getTodayYesterdayALl();
    }

}
