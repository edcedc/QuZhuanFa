package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.view.View;

import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.databinding.FBindBinding;
import com.yc.quzhuanfa.impl.RetrievePwdContract;
import com.yc.quzhuanfa.presenter.RetrievePwdPresenter;
import com.yc.quzhuanfa.utils.CountDownTimerUtils;
import com.yc.quzhuanfa.weight.ClipboardUtils;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/18
 * Time: 23:05
 *  绑定手机
 */
public class BindPhoneFrg extends BaseFragment<RetrievePwdPresenter, FBindBinding> implements RetrievePwdContract.View, View.OnClickListener {

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_bind;
    }

    @Override
    protected void initView(View view) {
        setTitleTransparent(getString(R.string.bind_phone), false);
        mB.lyCode.setVisibility(View.VISIBLE);
        mB.gpRegister.setVisibility(View.VISIBLE);
        mB.tvRetrieve.setVisibility(View.INVISIBLE);
        mB.gpLogin.setVisibility(View.GONE);
        mB.lyPwd.setVisibility(View.VISIBLE);
        mB.btSubmit.setText(act.getText(R.string.submit));

        mB.tvCode.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);
        mB.tvCopy.setOnClickListener(this);
        mPresenter.getAgreement();

    }

    @Override
    public void onCode() {
        new CountDownTimerUtils(act, 60000, 1000, mB.tvCode).start();
    }

    @Override
    public void onRetrievePwd() {
        pop();
    }

    @Override
    public void setWxNum(String content) {
        mB.tvWxNum.setText(content);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_code:
                mPresenter.code(mB.etPhone.getText().toString(), 1);
                break;
            case R.id.bt_submit:
                mPresenter.login(mB.etPhone.getText().toString(), mB.etCode.getText().toString(), mB.etPwd.getText().toString(), null, 1);
                break;
            case R.id.tv_copy:
                ClipboardUtils.copyText(mB.tvWxNum.getText().toString());
                showToast("复制成功");
                break;
        }
    }
}
