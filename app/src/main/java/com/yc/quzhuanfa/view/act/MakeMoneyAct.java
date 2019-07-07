package com.yc.quzhuanfa.view.act;

import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseActivity;
import com.yc.quzhuanfa.view.MakeMoneyFrg;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 13:18
 */
public class MakeMoneyAct extends BaseActivity {
    @Override
    protected void initPresenter() {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarColor(R.color.red_FF7D78).init();
        if (findFragment(MakeMoneyFrg.class) == null) {
            loadRootFragment(R.id.fl_container, MakeMoneyFrg.newInstance());
        }
    }
}
