package com.yc.quzhaunfa.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.adapter.BankAdapter;
import com.yc.quzhaunfa.adapter.HomeChildAdapter;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.controller.UIHelper;
import com.yc.quzhaunfa.databinding.FBankBinding;
import com.yc.quzhaunfa.impl.BankContract;
import com.yc.quzhaunfa.presenter.BankPresenter;
import com.yc.quzhaunfa.weight.LinearDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/21
 * Time: 18:02
 *  银行卡
 */
public class BankFrg extends BaseFragment<BankPresenter, FBankBinding> implements BankContract.View, View.OnClickListener {

    private List<DataBean> listBean = new ArrayList<>();
    private BankAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_bank;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.me_bank));
        mB.btSubmit.setOnClickListener(this);
        if (adapter == null) {
            adapter = new BankAdapter(act, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView);
        showLoadDataing();
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1);
            }
        });
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
        super.setRefreshLayoutMode(listBean.size(), totalRow, mB.refreshLayout);
    }

    @Override
    public void setData(Object data) {
        List<DataBean> list = (List<DataBean>) data;
        if (pagerNumber == 1) {
            listBean.clear();
            mB.refreshLayout.finishRefreshing();
        } else {
            mB.refreshLayout.finishLoadmore();
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:
                UIHelper.startAddBnakFrg(this);
                break;
        }
    }
}
