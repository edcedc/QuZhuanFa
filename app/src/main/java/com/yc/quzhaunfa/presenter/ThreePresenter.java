package com.yc.quzhaunfa.presenter;

import com.lzy.okgo.model.Response;
import com.yc.quzhaunfa.bean.BaseListBean;
import com.yc.quzhaunfa.bean.BaseResponseBean;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.callback.Code;
import com.yc.quzhaunfa.controller.CloudApi;
import com.yc.quzhaunfa.impl.ThreeContract;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/21
 * Time: 20:42
 */
public class ThreePresenter extends ThreeContract.Presenter {

    @Override
    public void getUserList() {
        CloudApi.list2( CloudApi.userGetUserList)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
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
                            List<DataBean> result = baseResponseBeanResponse.body().result;
                            mView.setData(result);
                            mView.setData(result);
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

    @Override
    public void getTodayYesterdayALl() {
        CloudApi.getTodayYesterdayALl()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
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
                            DataBean bean = baseResponseBeanResponse.body().result;
                            mView.onTodayYesterdayALl(bean);
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
