package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.databinding.FZkingBinding;

/**
 *  二维码收徒
 */
public class ZkingFrg extends BaseFragment<BasePresenter, FZkingBinding> {

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_zking;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.apprentice1), false);
        view.findViewById(R.id.title_bar).setBackgroundColor(act.getColor(R.color.white));
        view.findViewById(R.id.toolbar).setBackgroundColor(act.getColor(R.color.white));
        TextView top_title = view.findViewById(R.id.top_title);
        top_title.setTextColor(act.getColor(R.color.tab_black));

    }

}
