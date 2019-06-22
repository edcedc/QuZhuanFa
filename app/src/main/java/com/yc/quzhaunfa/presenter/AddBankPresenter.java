package com.yc.quzhaunfa.presenter;

import com.blankj.utilcode.util.StringUtils;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.impl.AddBankContract;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/21
 * Time: 19:09
 */
public class AddBankPresenter extends AddBankContract.Presenter {

    @Override
    public void addBnak(String bankNum, String openBank, String name, String userId, String userBank, boolean checked) {
        mView.showLoading();
        if (StringUtils.isEmpty(bankNum) || StringUtils.isEmpty(openBank) || StringUtils.isEmpty(name) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(userBank)){
            showToast(act.getString(R.string.error_));
            return;
        }
    }

}
