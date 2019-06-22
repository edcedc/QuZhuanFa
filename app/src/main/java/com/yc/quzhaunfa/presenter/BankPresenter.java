package com.yc.quzhaunfa.presenter;

import android.os.Handler;

import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.impl.BankContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/21
 * Time: 18:21
 */
public class BankPresenter extends BankContract.Presenter {
    @Override
    public void onRequest(int pagerNumber) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> list = new ArrayList<>();
                for (int i =0;i<5;i++){
                    list.add(new DataBean());
                }
                mView.setData(list);
                mView.hideLoading();
            }
        }, 1000);
    }
}
