package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.view.View;

import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.base.User;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.databinding.FNameBinding;
import com.yc.quzhuanfa.impl.UserInfoContract;
import com.yc.quzhuanfa.presenter.UserInfoPresenter;

import org.json.JSONObject;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/24
 * Time: 20:57
 *  更新名字
 */
public class UpdateNameFrg extends BaseFragment<UserInfoPresenter, FNameBinding> implements UserInfoContract.View {

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_name;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.set_name), getString(R.string.submit1));
        JSONObject userObj = User.getInstance().getUserObj();
        String nickName = userObj.optString("userName");
        if (!nickName.equals("null")){
            mB.etText.setHint(nickName);
        }
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        mPresenter.onUpdateUserInfo(-1, mB.etText.getText().toString(), null);
    }

    @Override
    public void setSuccess(DataBean result) {
        pop();
    }

}
