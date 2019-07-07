package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.view.View;

import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.databinding.FPayPwdBinding;
import com.yc.quzhuanfa.impl.PayPwdContract;
import com.yc.quzhuanfa.presenter.PayPwdPresenter;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/21
 * Time: 9:55
 *  设置支付密码
 */
public class PayPwdFrg extends BaseFragment<PayPwdPresenter, FPayPwdBinding> implements PayPwdContract.View {
    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_pay_pwd;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.set_pay_pwd));
        mB.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.payPwd(mB.etPwd.getText().toString(), mB.etPwd1.getText().toString());
            }
        });
    }
}
