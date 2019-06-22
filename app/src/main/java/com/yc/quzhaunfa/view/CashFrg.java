package com.yc.quzhaunfa.view;

import android.os.Bundle;
import android.view.View;

import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.base.BasePresenter;
import com.yc.quzhaunfa.controller.UIHelper;
import com.yc.quzhaunfa.databinding.FCashBinding;
import com.yc.quzhaunfa.impl.CashContract;
import com.yc.quzhaunfa.presenter.CashPresenter;
import com.yc.quzhaunfa.utils.PopupWindowTool;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/19
 * Time: 10:49
 * 立即提现
 */
public class CashFrg extends BaseFragment<CashPresenter, FCashBinding> implements CashContract.View, View.OnClickListener {

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cash:
                PopupWindowTool.showCash(act, new PopupWindowTool.onPopClickListener() {
                    @Override
                    public void onClick(int position) {
                        switch (position) {
                            case 0:
                                UIHelper.startBankFrg(CashFrg.this);
                                break;
                            default:
                                PopupWindowTool.showPay(act);
                                break;
                        }

                    }
                });
                break;
            case R.id.tv_full_withdrawa1:

                break;
            case R.id.bt_submit:

                break;
        }
    }
}
