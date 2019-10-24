package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.adapter.HomeChildAdapter;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.databinding.FHomeBinding;
import com.yc.quzhuanfa.event.CollectInEvent;
import com.yc.quzhuanfa.impl.HomeChildContract;
import com.yc.quzhuanfa.presenter.HomeChildPresenter;
import com.yc.quzhuanfa.utils.GlideImageLoader;
import com.yc.quzhuanfa.weight.LinearDividerItemDecoration;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DefaultTransformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
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

    private boolean isRequest = true;

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (isRequest){
            isRequest = false;
            showLoadDataing();
            mB.refreshLayout.startRefresh();
        }
    }

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
        setSwipeBackEnable(false);
        mB.banner.updateBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        if (adapter == null) {
            adapter = new HomeChildAdapter(act, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
        mPresenter.onProfitOne();
        mPresenter.onGetUserCashRecordList();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1, id);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1, id);
            }
        });
        EventBus.getDefault().register(this);
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
        super.setRefreshLayoutMode(listBean.size() + 1, totalRow, mB.refreshLayout);
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

            for (DataBean bean : list){

            }

            DataBean bean = list.get(0);
            listBanner.clear();
            listBanner.add(bean);
            list.remove(0);
            List<String> list1 = new ArrayList<>();
            List<String> imgs = new ArrayList<>();
            imgs.add(bean.getPic());
            list1.add(bean.getTitle());
            mB.banner.setImages(imgs)
                    .setBannerTitles(list1)
                    .setImageLoader(new GlideImageLoader())
                    .setOnBannerListener(this)
                    .setBannerAnimation(DefaultTransformer.class).start();
        } else {
            mB.refreshLayout.finishLoadmore();
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setBanner(List<DataBean> list) {

    }

    @Override
    public void setProfitOne(DataBean result) {
    }

    @Subscribe
    public void onCollectInEvent(CollectInEvent event){
        if (event.type != 0)return;
        DataBean bean = listBean.get(event.position);
        bean.setIsTrue(event.isTrue);
        adapter.notifyItemChanged(event.position);
    }

    @Override
    public void setCashRecordList(List<DataBean> result) {
        List<String> list = new ArrayList<>();
        for (DataBean bean : result){
            String phoneNum = bean.getPhoneNum();
            String str = phoneNum.substring(0, 3);
            String str2 = phoneNum.substring(phoneNum.length() - 4);
            phoneNum = str + "****" + str2;
            list.add("用户" + phoneNum + "提现" + bean.getBalance() + "成功");
        }
        SimpleMF<String> marqueeFactory = new SimpleMF(act);
        marqueeFactory.setData(list);
        mB.simpleMarqueeView.setMarqueeFactory(marqueeFactory);
        mB.simpleMarqueeView.startFlipping();
    }

    @Override
    public void OnBannerClick(int position) {
        DataBean bean = listBanner.get(position);
        UIHelper.startDetailsAct(bean);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
