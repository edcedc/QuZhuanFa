package com.yc.quzhaunfa.presenter;

import com.lzy.okgo.model.Response;
import com.yc.quzhaunfa.bean.BaseListBean;
import com.yc.quzhaunfa.bean.BaseResponseBean;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.callback.Code;
import com.yc.quzhaunfa.controller.CloudApi;
import com.yc.quzhaunfa.impl.ApprenticeContract;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/28
 * Time: 21:45
 */
public class ApprenticePresenter extends ApprenticeContract.Presenter{

    @Override
    public void onRequest(int pagerNumber, String userId) {
        CloudApi.userApprenticeBalanceList(userId)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<List<DataBean>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<List<DataBean>>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            List<DataBean> data = baseResponseBeanResponse.body().result;
                            if (data != null){
                                mView.setData(data);
                            }
                        }
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

    @Override
    public void onIncome(String userId) {
        CloudApi.getApprenticeToy(userId)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<DataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<DataBean>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            mView.TodayYesterday(baseResponseBeanResponse.body().result);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
