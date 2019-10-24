package com.yc.quzhuanfa.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.flyco.roundview.RoundTextView;
import com.gyf.immersionbar.ImmersionBar;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.adapter.FiveChildAdapter;
import com.yc.quzhuanfa.adapter.HomeChildAdapter;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.bean.SearchListBean;
import com.yc.quzhuanfa.databinding.FSearchVideoBinding;
import com.yc.quzhuanfa.impl.SearchVideoContract;
import com.yc.quzhuanfa.presenter.SearchVideoPresenter;
import com.yc.quzhuanfa.weight.LinearDividerItemDecoration;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/21
 * Time: 14:22
 *  搜索视频
 */
public class SearchVideoFrg extends BaseFragment<SearchVideoPresenter, FSearchVideoBinding> implements SearchVideoContract.View, View.OnClickListener {

    private List<DataBean> listBean = new ArrayList<>();
    private HomeChildAdapter adapter;

    private String searchText;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_search_video;
    }

    @Override
    protected void initView(View view) {
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true).init();
        mB.tvClose.setOnClickListener(this);
        mB.ivClear.setOnClickListener(this);
        mPresenter.onHotitleList();
        mB.etText.setOnEditorActionListener((v, actionId, event) -> {
            //判断是否是“完成”键
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                if (StringUtils.isEmpty(mB.etText.getText().toString())){
                    showToast(act.getString(R.string.error_));
                    return false;
                }
                setSearchList(mB.etText.getText().toString());
                return true;
            }
            return false;
        });

        if (adapter == null) {
            adapter = new HomeChildAdapter(act, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView);
        mPresenter.onHistory();
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  30));
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1, searchText);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1, searchText);
            }
        });
//        adapter.setOnClickListener((position, videoId, isTrue) -> mPresenter.onVideoCollect(position, videoId, isTrue));
    }

    @Override
    public void setHotitleList(List<DataBean> list) {
        mB.rvHot.setAdapter(new TagAdapter<DataBean>(list){
            @Override
            public View getView(FlowLayout parent, int position, DataBean bean) {
                View view = View.inflate(act, R.layout.i_hot_video, null);
                RoundTextView tvText = view.findViewById(R.id.tv_text);
                tvText.setText(bean.getTitle());
                return view;
            }
        });
        mB.rvHot.setOnTagClickListener((view1, position, parent) -> {
            DataBean bean = list.get(position);
            setSearchList(bean.getTitle());
            return false;
        });
    }

    private void setSearchList(String title) {
        searchText = title;
        mB.refreshLayout.startRefresh();
        mB.refreshLayout.setVisibility(View.VISIBLE);
        mB.gpHot.setVisibility(View.GONE);
        mPresenter.addHistory(searchText);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_close:
                pop();
                break;
            case R.id.iv_clear:
                LitePal.deleteAll(SearchListBean.class);
                mB.rvHis.removeAllViews();
                break;
        }
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
    public void setCollect(int position, int isTrue) {
        DataBean bean = listBean.get(position);
        bean.setIsTrue(isTrue);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void setHistory(List<SearchListBean> list) {
        mB.rvHis.removeAllViews();
        mB.rvHis.setAdapter(new TagAdapter<SearchListBean>(list){
            @Override
            public View getView(FlowLayout parent, int position, SearchListBean bean) {
                View view = View.inflate(act, R.layout.i_hot_video, null);
                RoundTextView tvText = view.findViewById(R.id.tv_text);
                tvText.setText(bean.getTitle());
                return view;
            }
        });
        mB.rvHis.setOnTagClickListener((view1, position, parent) -> {
            SearchListBean bean = list.get(position);
            setSearchList(bean.getTitle());
            return false;
        });
    }

}
