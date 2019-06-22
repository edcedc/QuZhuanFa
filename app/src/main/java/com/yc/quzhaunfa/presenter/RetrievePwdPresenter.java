package com.yc.quzhaunfa.presenter;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.impl.RetrievePwdContract;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/18
 * Time: 22:53
 */
public class RetrievePwdPresenter extends RetrievePwdContract.Presenter{

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
    public void login(String phone, String code, String pwd, String pwd1) {
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
        if (StringUtils.isEmpty(pwd) || StringUtils.isEmpty(pwd1)){
            showToast(act.getString(R.string.please_pwd3));
            return;
        }
        if (pwd.length() < 6 || pwd.length() > 16){
            showToast(act.getString(R.string.error_pwd_length));
            return;
        }
        if (!pwd.equals(pwd1)){
            showToast(act.getString(R.string.please_pwd2));
            return;
        }



    }

}
