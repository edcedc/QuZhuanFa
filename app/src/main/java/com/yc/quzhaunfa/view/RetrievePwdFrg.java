package com.yc.quzhaunfa.view;

import android.os.Bundle;
import android.view.View;

import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.databinding.FRetrieveBinding;
import com.yc.quzhaunfa.impl.RetrievePwdContract;
import com.yc.quzhaunfa.presenter.RetrievePwdPresenter;
import com.yc.quzhaunfa.utils.CountDownTimerUtils;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/18
 * Time: 22:46
 *  找回密码
 */
public class RetrievePwdFrg extends BaseFragment<RetrievePwdPresenter, FRetrieveBinding> implements RetrievePwdContract.View, View.OnClickListener {

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_retrieve;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.register));
        mB.tvCode.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);
    }

    @Override
    public void onCode() {
        new CountDownTimerUtils(act, 60000, 1000, mB.tvCode).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_code:
                mPresenter.code(mB.etPhone.getText().toString());
                break;
            case R.id.bt_submit:
                mPresenter.login(mB.etPhone.getText().toString(), mB.etCode.getText().toString(), mB.etPwd.getText().toString(), mB.etPwd1.getText().toString());
                break;
        }
    }

}
