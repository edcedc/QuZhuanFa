package com.yc.quzhaunfa.view;

import android.os.Bundle;
import android.view.View;

import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.databinding.FAddBankBinding;
import com.yc.quzhaunfa.impl.AddBankContract;
import com.yc.quzhaunfa.presenter.AddBankPresenter;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/21
 * Time: 18:36
 *  添加银行卡
 */
public class AddBankFrg extends BaseFragment<AddBankPresenter, FAddBankBinding> implements AddBankContract.View{

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_add_bank;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.add_bank));
        mB.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.addBnak(mB.etBankNum.getText().toString(), mB.etOpenBank.getText().toString(), mB.etUserName.getText().toString(), mB.etId.getText().toString(), mB.etVerificationBank.getText().toString(), mB.cbSubmit.isChecked());
            }
        });
    }
}
