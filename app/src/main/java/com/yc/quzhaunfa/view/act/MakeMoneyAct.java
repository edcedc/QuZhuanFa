package com.yc.quzhaunfa.view.act;

import android.os.Bundle;

import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.base.BaseActivity;
import com.yc.quzhaunfa.view.MakeMoneyFrg;

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
        if (findFragment(MakeMoneyFrg.class) == null) {
            loadRootFragment(R.id.fl_container, MakeMoneyFrg.newInstance());
        }
    }
}
