package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.adapter.IncomeAdapter;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.databinding.FApprenticeBinding;
import com.yc.quzhuanfa.impl.ApprenticeContract;
import com.yc.quzhuanfa.presenter.ApprenticePresenter;
import com.yc.quzhuanfa.utils.GlideLoadingUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/28
 * Time: 20:27
 *  徒弟详情
 */
public class ApprenticeFrg extends BaseFragment<ApprenticePresenter, FApprenticeBinding> implements ApprenticeContract.View {

    private DataBean bean;
    private List<DataBean> listBean = new ArrayList<>();
    private IncomeAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        bean = new Gson().fromJson(bundle.getString("bean"), DataBean.class);
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_apprentice;
    }

    @Override
    protected void initView(View view) {
        setTitleTransparent(getString(R.string.apprentice_desc), false);
        GlideLoadingUtils.load(act, bean.getHead(), mB.ivHead, true);
        mB.tvName.setText("ID：" + bean.getUserName());
        mB.tvId.setText("注册时间：" +
                TimeUtils.millis2String(Long.valueOf(bean.getCreateTime())));

        if (adapter == null) {
            adapter = new IncomeAdapter(act, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView);
        showLoadDataing();
        mB.refreshLayout.startRefresh();
        mB.refreshLayout.setEnableLoadmore(false);
        mPresenter.onIncome(bean.getLevelUserId());
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1, bean.getLevelUserId());
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
        listBean.clear();
        mB.refreshLayout.finishRefreshing();
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void TodayYesterday(DataBean result) {
        mB.tvIncome.setText(result.getNow() + "");
        mB.tvBalance.setText(result.getYesDay() + "");
    }

}
