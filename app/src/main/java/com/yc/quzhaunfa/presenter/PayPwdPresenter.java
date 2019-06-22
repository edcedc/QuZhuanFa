package com.yc.quzhaunfa.presenter;

import com.blankj.utilcode.util.StringUtils;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.impl.PayPwdContract;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/21
 * Time: 10:03
 */
public class PayPwdPresenter extends PayPwdContract.Presenter{

    @Override
    public void payPwd(String pwd, String pwd1) {
        if (StringUtils.isEmpty(pwd) || StringUtils.isEmpty(pwd1)){
            showToast(act.getString(R.string.input_pwd));
            return;
        }
        if (!pwd.equals(pwd1)){
            showToast(act.getString(R.string.please_pwd2));
            return;
        }

    }

}
