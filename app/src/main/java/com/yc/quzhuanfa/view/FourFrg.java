package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.adapter.SignAdapter;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.base.User;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.databinding.FFourBinding;
import com.yc.quzhuanfa.impl.FourContract;
import com.yc.quzhuanfa.presenter.FourPresenter;
import com.yc.quzhuanfa.utils.GlideLoadingUtils;
import com.yc.quzhuanfa.utils.NumUtils;
import com.yc.quzhuanfa.utils.PopupWindowTool;

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
            isRequest = false;
            mB.refreshLayout.startRefresh();
        }
        setData(User.getInstance().getUserObj());
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

        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
//                mPresenter.onRequest();
                mPresenter.signList();
                mB.refreshLayout.finishRefreshing();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_head:
                UIHelper.startUserInfoFrg(this);
                break;
            case R.id.ly_sign:
                mPresenter.sign();
                break;
        }
    }

    @Override
    public void setData(JSONObject userObj) {
        if (userObj == null)return;
        mB.tvIncome.setText("¥ " +
                NumUtils.doubleTrans1(userObj.optDouble("balanceAll")));
        mB.tvBalance.setText("¥ " +
                NumUtils.doubleTrans1(userObj.optDouble("balance")));
        mB.tvId.setText(userObj.optString("memberTip"));
        mB.tvName.setText(userObj.optString("userName"));
        GlideLoadingUtils.load(act, userObj.optString("head"), mB.ivHead, true);

        mPresenter.setPhone(userObj.optString("phoneNum"));
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
        PopupWindowTool.showSign(act, listBean, (list, adapter) -> {
            listBean = list;
            signAdapter = adapter;
            mPresenter.signList();
        });
    }
}
