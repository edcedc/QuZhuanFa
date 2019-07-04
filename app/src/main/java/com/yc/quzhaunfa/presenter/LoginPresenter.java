package com.yc.quzhaunfa.presenter;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.model.Response;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.base.User;
import com.yc.quzhaunfa.bean.BaseResponseBean;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.callback.Code;
import com.yc.quzhaunfa.controller.CloudApi;
import com.yc.quzhaunfa.controller.UIHelper;
import com.yc.quzhaunfa.impl.LoginContract;
import com.yc.quzhaunfa.utils.cache.ShareSessionIdCache;
import com.yc.quzhaunfa.utils.cache.SharedAccount;
import com.yc.quzhaunfa.view.act.LoginAct;

import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/18
 * Time: 12:32
 */
public class LoginPresenter extends LoginContract.Presenter{
    @Override
    public void code(String phone) {
        if (StringUtils.isEmpty(phone)){
            showToast(act.getString(R.string.error_phone1));
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(act.getString(R.string.error_phone));
            return;
        }
        CloudApi.getCode(phone, 1)
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
                            mView.onCode();
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

    @Override
    public void login(String phone, String code, String pwd, String invitation, boolean checked, int mPosition) {
        if (StringUtils.isEmpty(phone)){
            showToast(act.getString(R.string.error_phone1));
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(act.getString(R.string.error_phone));
            return;
        }

        if (mPosition == 0){
            if (StringUtils.isEmpty(pwd)){
                showToast(act.getString(R.string.please_pwd3));
                return;
            }
            CloudApi.login(phone, pwd)
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            mView.showLoading();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JSONObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mView.addDisposable(d);
                        }

                        @Override
                        public void onNext(JSONObject jsonObject) {
                            if (jsonObject.optInt("code") == Code.CODE_SUCCESS){
                                login(jsonObject);
                            }
                            showToast(jsonObject.optString("description"));
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
        }else {
            if (StringUtils.isEmpty(code) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(invitation)){
                showToast(act.getString(R.string.error_));
                return;
            }
            if (!checked){
                showToast(act.getString(R.string.error_1));
                return;
            }
            CloudApi.register(phone, code, invitation)
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            mView.showLoading();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JSONObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mView.addDisposable(d);
                        }

                        @Override
                        public void onNext(JSONObject jsonObject) {
                            if (jsonObject.optInt("code") == Code.CODE_SUCCESS){
                                login(jsonObject);
                            }
                            showToast(jsonObject.optString("description"));
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

    @Override
    public void getAgreement() {
        CloudApi.commonQueryAPPAgreement(16)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<DataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<DataBean>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            DataBean data = baseResponseBeanResponse.body().result;
                            if (data != null){
                                mView.setWxNum(data.getContent());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void login(JSONObject jsonObject){
        JSONObject data = jsonObject.optJSONObject("result");
        JSONObject user = data.optJSONObject("user");
        ShareSessionIdCache.getInstance(act).save(data.optString("token"));
        ShareSessionIdCache.getInstance(act).saveUserId(user.optString("userId"));
        SharedAccount.getInstance(act).save(data.optString("phoneNum"), data.optString("password"));
        User.getInstance().setUserObj(user);
        User.getInstance().setLogin(true);
        UIHelper.startMainAct();
        ActivityUtils.finishActivity(LoginAct.class);
    }

}
