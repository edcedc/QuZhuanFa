package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.view.View;

import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.base.User;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.databinding.FUserinfoBinding;
import com.yc.quzhuanfa.impl.UserInfoContract;
import com.yc.quzhuanfa.presenter.UserInfoPresenter;
import com.yc.quzhuanfa.utils.GlideLoadingUtils;

import org.json.JSONObject;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/16
 * Time: 16:35
 *  我的信息
 */
public class UserInfoFrg extends BaseFragment<UserInfoPresenter, FUserinfoBinding> implements UserInfoContract.View, View.OnClickListener {

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_userinfo;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.user_info));
        mB.ivHead.setOnClickListener(this);
        mB.tvName.setOnClickListener(this);
        mB.tvSex.setOnClickListener(this);
    }

    @Override
    public void setSuccess(DataBean result) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_head:
                UIHelper.startHeadFrg(this);
                break;
            case R.id.tv_name:
                UIHelper.startUpdateNameFrg(this);
                break;
            case R.id.tv_sex:
                UIHelper.startSexFrg(this);
                break;
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        JSONObject userObj = User.getInstance().getUserObj();
        GlideLoadingUtils.load(act, userObj.optString("head"), mB.ivHead, true);
    }
}
