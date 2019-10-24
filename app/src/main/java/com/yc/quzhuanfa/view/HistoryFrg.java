package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.adapter.EvaluateAdapter;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.base.BaseListContract;
import com.yc.quzhuanfa.base.BaseListPresenter;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.databinding.BRecyclerBinding;
import com.yc.quzhuanfa.impl.HistoryContract;
import com.yc.quzhuanfa.presenter.HistoryPresenter;
import com.yc.quzhuanfa.utils.PopupWindowTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/21
 * Time: 18:53
 *  历史记录
 */
public class HistoryFrg extends BaseFragment<HistoryPresenter, BRecyclerBinding> implements HistoryContract.View {


    private List<DataBean> listBean = new ArrayList<>();
    private EvaluateAdapter adapter;

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
        setTitle(getString(R.string.historical), R.mipmap.trash2);
        if (adapter == null) {
            adapter = new EvaluateAdapter(act, listBean);
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
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
        mB.refreshLayout.finishRefreshing();
        mB.refreshLayout.finishLoadmore();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        PopupWindowTool.showDialog(act)
                .asConfirm("是否清除历史记录？", "",
                        "取消", "确定",
                        () -> {
                            mPresenter.onClear();
                        }, null, false)
                .bindLayout(R.layout.p_dialog) //绑定已有布局
                .show();
    }

    @Override
    public void setClearSuccess() {
        listBean.clear();
        adapter.notifyDataSetChanged();
    }
}
