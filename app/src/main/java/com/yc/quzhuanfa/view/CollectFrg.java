package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.adapter.CollectAdapter;
import com.yc.quzhuanfa.adapter.FiveChildAdapter;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.databinding.BRecyclerBinding;
import com.yc.quzhuanfa.databinding.FHomeBinding;
import com.yc.quzhuanfa.impl.FiveChildContract;
import com.yc.quzhuanfa.presenter.FiveChildPresenter;
import com.yc.quzhuanfa.weight.LinearDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/21
 * Time: 11:30
 *  视频收藏
 */
public class CollectFrg extends BaseFragment<FiveChildPresenter, BRecyclerBinding> implements FiveChildContract.View{

    private List<DataBean> listBean = new ArrayList<>();
    private CollectAdapter adapter;

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
        setTitle(getString(R.string.my_collection));
        if (adapter == null) {
            adapter = new CollectAdapter(act, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  30));
        showLoadDataing();
        mB.refreshLayout.startRefresh();
        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onCollectList(pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onCollectList(pagerNumber += 1);
            }
        });
        adapter.setOnClickListener((position, videoId, isTrue) -> mPresenter.onVideoCollect(position, videoId, isTrue));
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
        mB.refreshLayout.finishRefreshing();
        mB.refreshLayout.finishLoadmore();
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

    @Override
    public void setBanner(List<DataBean> list) {
    }

    @Override
    public void setCollect(int position, int isTrue) {
        DataBean bean = listBean.get(position);
        bean.setIsTrue(isTrue);
        adapter.notifyItemChanged(position);
    }

}
