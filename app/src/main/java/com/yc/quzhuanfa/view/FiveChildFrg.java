package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.adapter.FiveChildAdapter;
import com.yc.quzhuanfa.adapter.HomeChildAdapter;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.databinding.FHomeBinding;
import com.yc.quzhuanfa.event.CollectInEvent;
import com.yc.quzhuanfa.impl.FiveChildContract;
import com.yc.quzhuanfa.presenter.FiveChildPresenter;
import com.yc.quzhuanfa.utils.GlideImageLoader;
import com.yc.quzhuanfa.weight.LinearDividerItemDecoration;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DefaultTransformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/10
 * Time: 18:41
 */
public class FiveChildFrg extends BaseFragment<FiveChildPresenter, FHomeBinding> implements FiveChildContract.View, OnBannerListener {

    private List<DataBean> listBean = new ArrayList<>();
    private FiveChildAdapter adapter;
    private List<DataBean> listBanner = new ArrayList<>();

    private boolean isRequest = true;
    private String id;

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
        mB.banner.setVisibility(View.GONE);
        mB.simpleMarqueeView.setVisibility(View.GONE);
        mB.banner.updateBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        if (adapter == null) {
            adapter = new FiveChildAdapter(act, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  30));
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
        adapter.setOnClickListener((position, videoId, isTrue) -> mPresenter.onVideoCollect(position, videoId, isTrue));
        EventBus.getDefault().register(this);
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

    @Override
    public void OnBannerClick(int position) {
        DataBean bean = listBanner.get(position);

    }

    @Subscribe
    public void onCollectInEvent(CollectInEvent event){
        if (event.type != 1)return;
        DataBean bean = listBean.get(event.position);
        bean.setIsTrue(event.isTrue);
        adapter.notifyItemChanged(event.position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
