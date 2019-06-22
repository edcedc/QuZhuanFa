package com.yc.quzhaunfa.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.adapter.MyPagerAdapter;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.controller.UIHelper;
import com.yc.quzhaunfa.databinding.FLoginBinding;
import com.yc.quzhaunfa.impl.LoginContract;
import com.yc.quzhaunfa.presenter.LoginPresenter;
import com.yc.quzhaunfa.utils.CountDownTimerUtils;
import com.yc.quzhaunfa.utils.TabEntity;
import com.yc.quzhaunfa.view.act.HtmlAct;

import java.util.ArrayList;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/18
 * Time: 12:08
 *  登录
 */
public class LoginFrg extends BaseFragment<LoginPresenter, FLoginBinding> implements LoginContract.View, View.OnClickListener {

    public static LoginFrg newInstance() {
        Bundle args = new Bundle();
        LoginFrg fragment = new LoginFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private int mPosition;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_login;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.login));
        view.findViewById(R.id.top_view).setVisibility(View.GONE);
        view.findViewById(R.id.title_bar).setBackgroundColor(act.getColor(R.color.transparent));
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(act.getColor(R.color.transparent));

        mB.tvCode.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);
        mB.tvCopy.setOnClickListener(this);
        mB.tvWenti.setOnClickListener(this);
        mB.tvRetrieve.setOnClickListener(this);

        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        String[] strings = {getString(R.string.login), getString(R.string.register)};
        for (String s : strings){
            mTabEntities.add(new TabEntity(s));
        }
        mB.tbLayout.setTabData(mTabEntities);
        mB.tbLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mPosition = position;
                if (position == 0){
                    setTitle(getString(R.string.login));
                    mB.lyCode.setVisibility(View.GONE);
                    mB.gpRegister.setVisibility(View.GONE);
                    mB.gpLogin.setVisibility(View.VISIBLE);
                    mB.tvRetrieve.setVisibility(View.VISIBLE);

                }else {
                    setTitle(getString(R.string.register));
                    mB.lyCode.setVisibility(View.VISIBLE);
                    mB.gpRegister.setVisibility(View.VISIBLE);
                    mB.tvRetrieve.setVisibility(View.INVISIBLE);
                    mB.gpLogin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_code:
                mPresenter.code(mB.etPhone.getText().toString());
                break;
            case R.id.bt_submit:
                mPresenter.login(mB.etPhone.getText().toString(), mB.etCode.getText().toString(), mB.etPwd.getText().toString(), mB.cbSubmit.isChecked(), mPosition);
                break;
            case R.id.tv_agreement:
                UIHelper.startHtmlAct(HtmlAct.REGISTER_PROTOCOL);
                break;
            case R.id.tv_retrieve:
                UIHelper.startRetrievePwdFrg(this);
                break;
        }
    }

    @Override
    public void onCode() {
        new CountDownTimerUtils(act, 60000, 1000, mB.tvCode).start();
    }

}
