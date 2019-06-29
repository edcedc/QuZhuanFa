package com.yc.quzhaunfa.view;

import android.os.Bundle;
import android.view.View;

import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.base.BasePresenter;
import com.yc.quzhaunfa.base.User;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.controller.UIHelper;
import com.yc.quzhaunfa.databinding.FCashBinding;
import com.yc.quzhaunfa.event.CashBankInEvent;
import com.yc.quzhaunfa.impl.CashContract;
import com.yc.quzhaunfa.presenter.CashPresenter;
import com.yc.quzhaunfa.utils.PopupWindowTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/19
 * Time: 10:49
 * 立即提现
 */
public class CashFrg extends BaseFragment<CashPresenter, FCashBinding> implements CashContract.View, View.OnClickListener {

    private DataBean bankBean;
    private double balance;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_cash;
    }

    @Override
    protected void initView(View view) {
        setTitleTransparent(getString(R.string.withdrawal), false);
        mB.tvCash.setOnClickListener(this);
        mB.tvFullWithdrawa1.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);
        EventBus.getDefault().register(this);
        JSONObject userObj = User.getInstance().getUserObj();
        balance = userObj.optDouble("balance");
        mB.tvIncome.setText(balance + "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void CashBankInEvent(CashBankInEvent event){
        bankBean = event.bean;
        mB.tvCash.setText(bankBean.getBankDeposit());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cash:
                UIHelper.startBankFrg(CashFrg.this);
                break;
            case R.id.tv_full_withdrawa1:
                mB.etPrice.setText(balance + "");
                break;
            case R.id.bt_submit:
                mPresenter.cash(bankBean, balance);
                break;
        }
    }
}
