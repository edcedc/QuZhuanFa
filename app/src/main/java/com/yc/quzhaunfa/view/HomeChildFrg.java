package com.yc.quzhaunfa.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.adapter.HomeChildAdapter;
import com.yc.quzhaunfa.adapter.IncomeAdapter;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.databinding.FHomeBinding;
import com.yc.quzhaunfa.impl.HomeChildContract;
import com.yc.quzhaunfa.presenter.HomeChildPresenter;
import com.yc.quzhaunfa.utils.GlideImageLoader;
import com.yc.quzhaunfa.weight.LinearDividerItemDecoration;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DefaultTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 10:15
 */
public class HomeChildFrg extends BaseFragment<HomeChildPresenter, FHomeBinding> implements HomeChildContract.View, OnBannerListener {

    private String id;

    private List<DataBean> listBean = new ArrayList<>();
    private HomeChildAdapter adapter;

    private List<DataBean> listBanner = new ArrayList<>();

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        id = bundle.getString("id");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_home;
    }

    @Override
    protected void initView(View view) {
        mB.banner.updateBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        if (adapter == null) {
            adapter = new HomeChildAdapter(act, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
        showLoadDataing();
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1, "");
                mPresenter.onBanner("");
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1, "");
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
    public void setBanner(List<DataBean> list) {
        listBanner.clear();
        listBanner.addAll(list);
        List<String> list1 = new ArrayList<>();
        List<String> imgs = new ArrayList<>();
        for (int i = 0;i < list.size();i++){
            imgs.add("http://wx1.sinaimg.cn/mw600/0076BSS5ly1g42uyoi7uej30iz0sg77s.jpg");
            list1.add("失眠最怕这种菜，农村到处有，每天吃一点...");
        }
        mB.banner.setImages(imgs)
                .setBannerTitles(list1)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .setBannerAnimation(DefaultTransformer.class).start();
    }

    @Override
    public void OnBannerClick(int position) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mB.banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mB.banner.stopAutoPlay();
    }

}
