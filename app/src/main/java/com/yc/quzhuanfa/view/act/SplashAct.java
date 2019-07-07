package com.yc.quzhuanfa.view.act;

import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseActivity;
import com.yc.quzhuanfa.view.SplashFrg;

/**
 * Created by Administrator on 2018/5/9.
 *  启动页
 */

public class SplashAct extends BaseActivity {


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
        ImmersionBar.with(this)
                .transparentBar()
                .init();
        if (findFragment(SplashFrg.class) == null) {
            loadRootFragment(R.id.fl_container, SplashFrg.newInstance());
        }
    }
}
