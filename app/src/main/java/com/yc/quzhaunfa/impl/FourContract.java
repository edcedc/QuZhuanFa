package com.yc.quzhaunfa.impl;

import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.base.BasePresenter;
import com.yc.quzhaunfa.base.IBaseListView;
import com.yc.quzhaunfa.base.IBaseView;
import com.yc.quzhaunfa.weight.WithScrollListView;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/17
 * Time: 10:44
 */
public interface FourContract {

    interface View extends IBaseView {

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void listView(WithScrollListView listView, BaseFragment root);
    }


}
