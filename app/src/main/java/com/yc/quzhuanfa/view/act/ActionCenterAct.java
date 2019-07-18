package com.yc.quzhuanfa.view.act;

import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseActivity;
import com.yc.quzhuanfa.view.ActionCenterFrg;
import com.yc.quzhuanfa.view.DetailsFrg;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/17
 * Time: 14:58
 */
public class ActionCenterAct extends BaseActivity {

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
        ActionCenterFrg frg = ActionCenterFrg.newInstance();
        if (findFragment(ActionCenterFrg.class) == null) {
            Bundle bundle = new Bundle();
            frg.setArguments(bundle);
            loadRootFragment(R.id.fl_container, frg);
        }
    }
}
