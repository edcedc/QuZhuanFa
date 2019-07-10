package com.yc.quzhuanfa.presenter;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.model.Response;
import com.yc.quzhuanfa.base.User;
import com.yc.quzhuanfa.bean.BaseResponseBean;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.callback.Code;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.impl.CashContract;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/19
 * Time: 12:34
 */
public class CashPresenter extends CashContract.Presenter{
    @Override
    public void cash(DataBean bankBean, double balance, final String cashBalance) {
        if (bankBean == null || StringUtils.isEmpty(cashBalance)){
            showToast("请选择提现方式/金额");
            return;
        }
        if (balance < Double.valueOf(cashBalance)){
            showToast("当前余额不足");
            return;
        }
        CloudApi.userSaveUserCash(bankBean, cashBalance)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            JSONObject userObj = User.getInstance().getUserObj();
                            try {
                                userObj.put("balance", userObj.optDouble("balance") - Double.valueOf(cashBalance));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        showToast("提现审核中");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }
}
