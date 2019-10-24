package com.yc.quzhuanfa.presenter;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.yc.quzhuanfa.base.User;
import com.yc.quzhuanfa.bean.BaseResponseBean;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.callback.Code;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.impl.UserInfoContract;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/16
 * Time: 21:17
 */
public class UserInfoPresenter extends UserInfoContract.Presenter{
    @Override
    public void onUpdateUserInfo(int sex, String userName, String head) {
        CloudApi.userSaveUserInfo(sex, userName, head)
                .doOnSubscribe(disposable -> {
                    mView.showLoading();
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

                            String toJson = new Gson().toJson(baseResponseBeanResponse.body().result);
                            try {
                                JSONObject object = new JSONObject(toJson);
                                User.getInstance().setUserObj(object);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            mView.setSuccess(null);
                        }
                        showToast(baseResponseBeanResponse.body().description);
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
