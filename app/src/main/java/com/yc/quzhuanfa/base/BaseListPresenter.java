package com.yc.quzhuanfa.base;

import com.lzy.okgo.model.Response;
import com.yc.quzhuanfa.bean.BaseListBean;
import com.yc.quzhuanfa.bean.BaseResponseBean;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.callback.Code;
import com.yc.quzhuanfa.controller.CloudApi;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者：yc on 2018/9/6.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class BaseListPresenter extends BaseListContract.Presenter{
    @Override
    public void onRequest(int pagerNumber, String url) {
        CloudApi.list(pagerNumber, url)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<BaseListBean<DataBean>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<BaseListBean<DataBean>>> baseResponseBeanResponse) {
                        mView.hideLoading();
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            BaseListBean<DataBean> data = baseResponseBeanResponse.body().result;
                            if (data != null){
                                List<DataBean> list = data.getList();
                                if (list != null && list.size() != 0){
                                    mView.setData(list);
                                    mView.setRefreshLayoutMode(data.getTotalCount());
                                }else {
                                    mView.showLoadEmpty();
                                }
                            }
                        }else {
                            mView.showLoadEmpty();
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
    public void onRequest(String url) {
        CloudApi.list2(url)
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
                            if (data != null && data.size() != 0){
                                mView.hideLoading();
                                mView.setData(data);
                            }
                        }else {
                            mView.showLoadEmpty();
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
