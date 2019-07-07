package com.yc.quzhuanfa.view.act;

import android.content.Intent;
import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.umeng.socialize.UMShareAPI;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseActivity;
import com.yc.quzhuanfa.view.DetailsFrg;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 12:37
 */
public class DetailsAct extends BaseActivity {

    private String id;
    private int type;

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
        type = bundle.getInt("type");
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarColor(R.color.red_FF7D78).init();
        DetailsFrg frg = DetailsFrg.newInstance();
        if (findFragment(DetailsFrg.class) == null) {
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            bundle.putInt("type", type);
            frg.setArguments(bundle);
            loadRootFragment(R.id.fl_container, frg);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

}
