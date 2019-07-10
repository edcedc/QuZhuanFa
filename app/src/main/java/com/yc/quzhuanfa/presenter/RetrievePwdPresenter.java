package com.yc.quzhuanfa.presenter;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.model.Response;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.User;
import com.yc.quzhuanfa.bean.BaseResponseBean;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.callback.Code;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.impl.RetrievePwdContract;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/18
 * Time: 22:53
 */
public class RetrievePwdPresenter extends RetrievePwdContract.Presenter{

    @Override
    public void code(String phone, int type) {
        if (StringUtils.isEmpty(phone)){
            showToast(act.getString(R.string.error_phone1));
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(act.getString(R.string.error_phone));
            return;
        }

        if (type == 0){
            type = 2;
        }else {
            type = 8;
        }

        CloudApi.getCode(phone, type)
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
                        showToast("获取验证码成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });    }

    @Override
    public void login(final String phone, String code,  String pwd, String pwd1, int type) {
        if (StringUtils.isEmpty(phone)){
            showToast(act.getString(R.string.error_phone1));
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(act.getString(R.string.error_phone));
            return;
        }
        if (StringUtils.isEmpty(code)){
            showToast(act.getString(R.string.error_phone1));
            return;
        }
//        if (StringUtils.isEmpty(pwd) || StringUtils.isEmpty(pwd1)){
//            showToast(act.getString(R.string.please_pwd3));
//            return;
//        }
//        if (pwd.length() < 6 || pwd.length() > 16){
//            showToast(act.getString(R.string.error_pwd_length));
//            return;
//        }
//        if (!pwd.equals(pwd1)){
//            showToast(act.getString(R.string.please_pwd2));
//            return;
//        }

        String url;
        if (type == 0){
            url = CloudApi.userRetrievePwd;
        }else {
            url = CloudApi.userBingPhone;
        }

        CloudApi.retrievePwd(phone, code, pwd, url)
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
                        userObj.put("phoneNum", phone);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mView.onRetrievePwd();
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

}
