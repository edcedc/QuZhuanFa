package com.yc.quzhaunfa.presenter;

import android.os.Handler;

import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.impl.HomeChildContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 11:38
 */
public class HomeChildPresenter extends HomeChildContract.Presenter {

    @Override
    public void onBanner(String id) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> list = new ArrayList<>();
                for (int i = 0;i < 10;i ++){
                    DataBean bean = new DataBean();
                    bean.setImage("http://wx1.sinaimg.cn/mw600/0076BSS5ly1g42uyoi7uej30iz0sg77s.jpg");
                    list.add(bean);
                }
                mView.setBanner(list);
            }
        }, 1000);
    }

    @Override
    public void onRequest(int pagerNumber, String id) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> list = new ArrayList<>();
                for (int i = 0;i < 10;i ++){
                    list.add(new DataBean());
                }
                mView.setData(list);
                mView.hideLoading();
            }
        }, 1000);
    }

}
