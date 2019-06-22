package com.yc.quzhaunfa.presenter;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.model.Response;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.bean.BaseResponseBean;
import com.yc.quzhaunfa.callback.Code;
import com.yc.quzhaunfa.controller.CloudApi;
import com.yc.quzhaunfa.impl.LoginContract;

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
        mView.onCode();
    }

    @Override
    public void login(String phone, String code, String pwd, boolean checked, int mPosition) {
        if (StringUtils.isEmpty(phone)){
            showToast(act.getString(R.string.error_phone1));
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(act.getString(R.string.error_phone));
            return;
        }
        if (StringUtils.isEmpty(pwd)){
            showToast(act.getString(R.string.please_pwd3));
            return;
        }
        if (mPosition == 0){


        }else {
            if (StringUtils.isEmpty(code)){
                showToast(act.getString(R.string.error_phone1));
                return;
            }
            if (!checked){
                showToast(act.getString(R.string.error_1));
                return;
            }

        }
    }

}
