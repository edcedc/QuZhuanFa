package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.view.View;

import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.base.IBaseView;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.databinding.FActionBinding;
import com.yc.quzhuanfa.view.act.HtmlAct;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/17
 * Time: 14:59
 */
public class ActionCenterFrg extends BaseFragment<BasePresenter, FActionBinding> implements IBaseView, View.OnClickListener {

    public static ActionCenterFrg newInstance() {
        Bundle args = new Bundle();
        ActionCenterFrg fragment = new ActionCenterFrg();
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
        return R.layout.f_action;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.action_center));
        mB.lyVip.setOnClickListener(this);
        mB.lyPrivacy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ly_vip:
                UIHelper.startHtmlAct(HtmlAct.MEMBER);
                break;
            case R.id.ly_privacy:
                UIHelper.startHtmlAct(HtmlAct.REGISTER);
                break;
        }
    }
}
