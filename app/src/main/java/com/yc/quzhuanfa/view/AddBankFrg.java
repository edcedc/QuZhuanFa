package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.databinding.FAddBankBinding;
import com.yc.quzhuanfa.event.BankInEvent;
import com.yc.quzhuanfa.impl.AddBankContract;
import com.yc.quzhuanfa.presenter.AddBankPresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/21
 * Time: 18:36
 *  添加银行卡
 */
public class AddBankFrg extends BaseFragment<AddBankPresenter, FAddBankBinding> implements AddBankContract.View{

    private DataBean bean;
    private String id;
    private int position;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        bean = new Gson().fromJson(bundle.getString("bean"), DataBean.class);
        id = bundle.getString("id");
        position = bundle.getInt("position");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_add_bank;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.add_bank));
        if (bean != null){
            mB.etBankNum.setText(bean.getBankNum());
            mB.etOpenBank.setText(bean.getBankDeposit());
            mB.etUserName.setText(bean.getName());
            mB.etId.setText(bean.getUserCard());
            mB.etVerificationBank.setText(bean.getPhoneNum());
        }
        mB.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.addBnak(mB.etBankNum.getText().toString(), mB.etOpenBank.getText().toString(),
                        mB.etUserName.getText().toString(), mB.etId.getText().toString(),
                        mB.etVerificationBank.getText().toString(), mB.cbSubmit.isChecked(), id);
            }
        });
    }

    @Override
    public void onSaveBank() {
        EventBus.getDefault().post(new BankInEvent(0, null));
        pop();
    }
}
