package com.yc.quzhuanfa.presenter;

import com.lzy.okgo.model.Response;
import com.yc.quzhuanfa.bean.BaseListBean;
import com.yc.quzhuanfa.bean.BaseResponseBean;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.callback.Code;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.impl.HistoryContract;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/21
 * Time: 18:53
 */
public class HistoryPresenter extends HistoryContract.Presenter{
    @Override
    public void onRequest(int pagerNumber) {
        CloudApi.list(pagerNumber, CloudApi.articleGetHistoryList)
                .doOnSubscribe(disposable -> {
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
                            mView.hideLoading();
                            if (data != null){
                                List<DataBean> list = data.getList();
                                if (list != null){
                                    mView.setData(list);
                                }else {
                                    mView.showLoadEmpty();
                                }
                                mView.setRefreshLayoutMode(data.getTotalCount());
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
    public void onClear() {
        CloudApi.articleDelHistory()
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<Object>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<Object>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            mView.setClearSuccess();
                        }
//                        showToast(baseResponseBeanResponse.body().description);
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
