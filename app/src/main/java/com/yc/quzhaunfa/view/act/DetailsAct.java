package com.yc.quzhaunfa.view.act;

import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.base.BaseActivity;
import com.yc.quzhaunfa.view.DetailsFrg;
import com.yc.quzhaunfa.view.SplashFrg;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 12:37
 */
public class DetailsAct extends BaseActivity {

    private String id;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParms(Bundle bundle) {
        id = bundle.getString("id");
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarColor(R.color.red_FF7D78).init();
        DetailsFrg frg = DetailsFrg.newInstance();
        if (findFragment(DetailsFrg.class) == null) {
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            frg.setArguments(bundle);
            loadRootFragment(R.id.fl_container, frg);
        }
    }
}
