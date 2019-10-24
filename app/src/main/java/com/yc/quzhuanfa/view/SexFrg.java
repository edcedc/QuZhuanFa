package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.view.View;

import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.databinding.FSexBinding;
import com.yc.quzhuanfa.impl.UserInfoContract;
import com.yc.quzhuanfa.presenter.UserInfoPresenter;


/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 11:36
 */
public class SexFrg extends BaseFragment<UserInfoPresenter, FSexBinding> implements UserInfoContract.View, View.OnClickListener {

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    private int type = -1;

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_sex;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.set_sex), getString(R.string.submit1));
        mB.lyNan.setOnClickListener(this);
        mB.lyNv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ly_nan:
                type = 1;
                mB.ivNan.setVisibility(View.VISIBLE);
                mB.ivNv.setVisibility(View.GONE);
                break;
            case R.id.ly_nv:
                type = 2;
                mB.ivNan.setVisibility(View.GONE);
                mB.ivNv.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        mPresenter.onUpdateUserInfo(type, null, null);
    }

    @Override
    public void setSuccess(DataBean result) {
        pop();
    }
}
