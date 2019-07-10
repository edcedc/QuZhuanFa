package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.ImmersionBar;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.databinding.FLoginBinding;
import com.yc.quzhuanfa.impl.LoginContract;
import com.yc.quzhuanfa.presenter.LoginPresenter;
import com.yc.quzhuanfa.utils.CountDownTimerUtils;
import com.yc.quzhuanfa.utils.ShareTool;
import com.yc.quzhuanfa.utils.TabEntity;
import com.yc.quzhuanfa.utils.cache.ShareIsLoginCache;
import com.yc.quzhuanfa.utils.cache.ShareSessionIdCache;
import com.yc.quzhuanfa.utils.cache.SharedAccount;
import com.yc.quzhuanfa.view.act.HtmlAct;
import com.yc.quzhuanfa.weight.ClipboardUtils;

import java.util.ArrayList;
import java.util.Map;

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
        setTitleTransparent(getString(R.string.login), true);
        ImmersionBar.with(this).navigationBarColor(R.color.red_FF7D78);
        mB.tvCode.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);
        mB.tvCopy.setOnClickListener(this);
        mB.tvWenti.setOnClickListener(this);
        mB.tvRetrieve.setOnClickListener(this);
        mB.ivWx.setOnClickListener(this);
        mB.tvAgreement.setOnClickListener(this);
        mPresenter.getAgreement();

        ShareSessionIdCache.getInstance(act).remove();

        String mobile = SharedAccount.getInstance(act).getMobile();
        String pwd = SharedAccount.getInstance(act).getPwd();
        if (!StringUtils.isEmpty(mobile) && !StringUtils.isEmpty(pwd)){
            mB.etPhone.setText(mobile);
            mB.etPwd.setText(pwd);
        }

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
                    mB.lyPwd.setVisibility(View.VISIBLE);
//                    mB.lyInvitation.setVisibility(View.GONE);
                }else {
                    setTitle(getString(R.string.register));
                    mB.lyCode.setVisibility(View.VISIBLE);
                    mB.gpRegister.setVisibility(View.VISIBLE);
                    mB.tvRetrieve.setVisibility(View.INVISIBLE);
                    mB.lyPwd.setVisibility(View.GONE);
                    mB.gpLogin.setVisibility(View.GONE);
                    mB.lyPwd.setVisibility(View.VISIBLE);
//                    mB.lyInvitation.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    String openid;
    String access_token;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_code:
                mPresenter.code(mB.etPhone.getText().toString());
                break;
            case R.id.bt_submit:
                mPresenter.login(mB.etPhone.getText().toString(), mB.etCode.getText().toString(), mB.etPwd.getText().toString(), mB.etInvitation.getText().toString(), mB.cbSubmit.isChecked(), mPosition);
                break;
            case R.id.tv_agreement:
                UIHelper.startHtmlAct(HtmlAct.REGISTER);
                break;
            case R.id.tv_retrieve:
                UIHelper.startRetrievePwdFrg(this, 0);
                break;
            case R.id.iv_wx:


                ShareTool.getInstance(act).authorization( new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        LogUtils.e("onStart");
                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        LogUtils.e("onComplete");
                        if (map == null)return;
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            LogUtils.e(entry.getKey(), entry.getValue());
                            if (entry.getKey().equals("openid")){
                                openid = entry.getValue();
                            }
                            if (entry.getKey().equals("access_token")){
                                access_token = entry.getValue();
                            }
                         }
                        mPresenter.wxLogin(openid, access_token);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        LogUtils.e("onCancel");
                    }
                });
                break;
            case R.id.tv_copy:
                ClipboardUtils.copyText(mB.tvWxNum.getText().toString());
                showToast("复制成功");
                break;
        }
    }

    @Override
    public void onCode() {
        new CountDownTimerUtils(act, 60000, 1000, mB.tvCode).start();
    }

    @Override
    public void setWxNum(String content) {
        mB.tvWxNum.setText(content);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ShareTool.getInstance(act).release();
    }
}
