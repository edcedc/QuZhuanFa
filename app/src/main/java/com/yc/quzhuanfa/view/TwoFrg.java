package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.view.View;

import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseFragment;

public class TwoFrg extends BaseFragment {

    public static TwoFrg newInstance() {
        Bundle args = new Bundle();
        TwoFrg fragment = new TwoFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_two;
    }

    @Override
    protected void initView(View view) {

    }
}
