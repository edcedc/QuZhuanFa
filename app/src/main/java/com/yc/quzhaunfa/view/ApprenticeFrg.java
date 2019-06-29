package com.yc.quzhaunfa.view;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.adapter.IncomeAdapter;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.base.BaseListPresenter;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.controller.CloudApi;
import com.yc.quzhaunfa.databinding.FApprenticeBinding;
import com.yc.quzhaunfa.impl.ApprenticeContract;
import com.yc.quzhaunfa.presenter.ApprenticePresenter;
import com.yc.quzhaunfa.utils.GlideLoadingUtils;

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
        GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + bean.getHead(), mB.ivHead, true);
        mB.tvName.setText("ID：" + bean.getUserName());
        mB.tvId.setText("注册时间：" +
                bean.getCreateTime());

        if (adapter == null) {
            adapter = new IncomeAdapter(act, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView);
        showLoadDataing();
        mB.refreshLayout.startRefresh();
        mPresenter.onIncome(bean.getUserId());
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1, bean.getUserId());
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1, bean.getUserId());
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

    @Override
    public void TodayYesterday(DataBean result) {
        mB.tvIncome.setText(result.getNow() + "");
        mB.tvBalance.setText(result.getYesDay() + "");
    }

}
