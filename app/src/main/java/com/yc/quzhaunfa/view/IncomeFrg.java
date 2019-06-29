package com.yc.quzhaunfa.view;

import android.os.Bundle;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.adapter.IncomeAdapter;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.base.BaseListContract;
import com.yc.quzhaunfa.base.BaseListPresenter;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.controller.CloudApi;
import com.yc.quzhaunfa.databinding.BRecyclerBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 11:07
 *  收入明细
 */
public class IncomeFrg extends BaseFragment<BaseListPresenter, BRecyclerBinding> implements BaseListContract.View {

    private List<DataBean> listBean = new ArrayList<>();
    private IncomeAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.b_recycler;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.income));
        if (adapter == null) {
            adapter = new IncomeAdapter(act, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView);
        showLoadDataing();
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1, CloudApi.userGetBalanceList);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1, CloudApi.userGetBalanceList);
            }
        });
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
        super.setRefreshLayoutMode(listBean.size(), totalRow, mB.refreshLayout);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
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

}
