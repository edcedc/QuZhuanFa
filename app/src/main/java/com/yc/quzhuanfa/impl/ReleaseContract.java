package com.yc.quzhuanfa.impl;

import com.luck.picture.lib.entity.LocalMedia;
import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.base.IBaseView;
import com.yc.quzhuanfa.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/15
 * Time: 11:25
 */
public interface ReleaseContract {

    interface View extends IBaseView {

        void setSuccess(DataBean result);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRelease(String text, int type, List<LocalMedia> localMediaList, String address);
    }

}
