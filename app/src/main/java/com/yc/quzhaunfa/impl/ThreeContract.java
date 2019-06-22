package com.yc.quzhaunfa.impl;

import com.yc.quzhaunfa.base.BasePresenter;
import com.yc.quzhaunfa.base.IBaseView;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/21
 * Time: 20:40
 */
public interface ThreeContract {

    interface View extends IBaseView {

    }

    abstract class Presenter extends BasePresenter<View> {

    }

}
