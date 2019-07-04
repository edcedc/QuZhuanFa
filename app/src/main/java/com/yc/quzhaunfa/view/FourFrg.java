package com.yc.quzhaunfa.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.adapter.MeAdapter;
import com.yc.quzhaunfa.adapter.SignAdapter;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.base.BasePresenter;
import com.yc.quzhaunfa.base.User;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.controller.CloudApi;
import com.yc.quzhaunfa.controller.UIHelper;
import com.yc.quzhaunfa.databinding.FFourBinding;
import com.yc.quzhaunfa.impl.FourContract;
import com.yc.quzhaunfa.presenter.FourPresenter;
import com.yc.quzhaunfa.utils.PopupWindowTool;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 1:14
 */
public class FourFrg extends BaseFragment<FourPresenter, FFourBinding> implements FourContract.View, View.OnClickListener {

    public static FourFrg newInstance() {
        Bundle args = new Bundle();
        FourFrg fragment = new FourFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<DataBean> listBean = new ArrayList<>();

    private boolean isRequest = true;

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (isRequest){
//            isRequest = false;
            mPresenter.signList();
            mPresenter.onRequest();
        }
    }

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_four;
    }

    @Override
    protected void initView(View view) {
        setTitleTransparent(getString(R.string.me), true);
        mPresenter.listView(mB.listView, this);
        mB.ivHead.setOnClickListener(this);
        mB.lySign.setOnClickListener(this);
//        mPresenter.signList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_head:
//                UIHelper.startLoginAct();
                break;
            case R.id.ly_sign:
                mPresenter.sign();
                break;
        }
    }

    @Override
    public void setData(JSONObject userObj) {
        mB.tvIncome.setText("¥ " +
                userObj.optDouble("balanceAll"));
        mB.tvBalance.setText("¥ " +
                userObj.optDouble("balance"));
        mB.tvId.setText(User.getInstance().getUserId());
        mB.tvName.setText(userObj.optString("userName"));
    }

    @Override
    public void onSignList(List<DataBean> result) {
        listBean.clear();
        listBean.addAll(result);
        if (signAdapter != null){
            signAdapter.notifyDataSetChanged();
        }
    }

    private SignAdapter signAdapter;

    @Override
    public void onSign() {
        PopupWindowTool.showSign(act, listBean, new PopupWindowTool.onSignClickListener() {
            @Override
            public void onClick(List<DataBean> list, SignAdapter adapter) {
                listBean = list;
                signAdapter = adapter;
                mPresenter.signList();
            }
        });
    }
}
