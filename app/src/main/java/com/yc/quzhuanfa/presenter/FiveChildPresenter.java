package com.yc.quzhuanfa.presenter;

import com.lzy.okgo.model.Response;
import com.yc.quzhuanfa.bean.BaseListBean;
import com.yc.quzhuanfa.bean.BaseResponseBean;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.callback.Code;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.impl.FiveChildContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/10
 * Time: 18:43
 */
public class FiveChildPresenter extends FiveChildContract.Presenter {

    @Override
    public void onBanner(String id) {

    }

    @Override
    public void onRequest(int pagerNumber, String id) {
        CloudApi.videoGetVideoList(pagerNumber, id, null)
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
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            BaseListBean<DataBean> data = baseResponseBeanResponse.body().result;
                            if (data != null){
                                List<DataBean> list = data.getList();
                                if (list != null){
                                    mView.setData(list);
                                }
                                mView.setRefreshLayoutMode(data.getTotalCount());
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
    public void onCollectList(int pagerNumber) {
        CloudApi.articleGetArticleCollectList(pagerNumber)
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
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            BaseListBean<DataBean> data = baseResponseBeanResponse.body().result;
                            if (data != null){
                                List<DataBean> list = data.getList();
                                if (list != null){
                                    mView.setData(list);
                                 }
                                mView.setRefreshLayoutMode(data.getTotalCount());
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
    public void onVideoCollect(int position, String videoId, int isTrue) {
        if (isTrue == 0){
            isTrue = 1;
        }else {
            isTrue = 0;
        }
        int finalIsTrue = isTrue;
        CloudApi.videoVideoCollect(videoId, isTrue)
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
                            mView.setCollect(position, finalIsTrue);
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
