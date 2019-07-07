package com.yc.quzhuanfa.presenter;

import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.model.Response;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.bean.BaseResponseBean;
import com.yc.quzhuanfa.callback.Code;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.impl.AddBankContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/21
 * Time: 19:09
 */
public class AddBankPresenter extends AddBankContract.Presenter {

    @Override
    public void addBnak(String bankNum, String openBank, String userName, String userCard, String phone, boolean checked, String id) {
        if (StringUtils.isEmpty(bankNum) || StringUtils.isEmpty(openBank) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(userCard) || StringUtils.isEmpty(phone)){
            showToast(act.getString(R.string.error_));
            return;
        }
        CloudApi.saveBank(id, openBank, userName, null, phone, bankNum, userCard)
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
//                            DataBean bean = (DataBean) baseResponseBeanResponse.body().result;
                            mView.onSaveBank();
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
