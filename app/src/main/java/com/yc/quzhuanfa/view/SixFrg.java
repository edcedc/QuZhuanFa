package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.adapter.PyqAdapter;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.databinding.BRecyclerBinding;
import com.yc.quzhuanfa.impl.SixContract;
import com.yc.quzhuanfa.presenter.SixPresenter;
import com.yc.quzhuanfa.view.bottom.CommentBottomFrg;
import com.yc.quzhuanfa.weight.LinearDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/10
 * Time: 18:19
 */
public class SixFrg extends BaseFragment<SixPresenter, BRecyclerBinding> implements SixContract.View {

    private String friendId;
    private int position;
    private String puserId;

    public static SixFrg newInstance() {
        Bundle args = new Bundle();
        SixFrg fragment = new SixFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<DataBean> listBean = new ArrayList<>();
    private PyqAdapter adapter;
    private CommentBottomFrg commentBottomFrg;

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
        setTitle(getString(R.string.pyq), R.mipmap.xj1, false);
        setSwipeBackEnable(false);
        commentBottomFrg = new CommentBottomFrg();
        if (adapter == null) {
            adapter = new PyqAdapter(act, this, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        ((SimpleItemAnimator)mB.recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
        mB.recyclerView.setAdapter(adapter);
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
        adapter.setOnClickListener(new PyqAdapter.OnClickListener() {
            @Override
            public void OnClick(int position, String friendId) {
                SixFrg.this.friendId = friendId;
                SixFrg.this.position = position;
                SixFrg.this.puserId = null;
                commentBottomFrg.show(getFragmentManager(), "");
            }

            @Override
            public void OnTwoClick(int position, int i, String friendId, String puserId) {
                SixFrg.this.friendId = friendId;
                SixFrg.this.position = position;
                SixFrg.this.puserId = puserId;
                commentBottomFrg.show(getFragmentManager(), "");
            }
        });
        commentBottomFrg.setOnCommentListener(new CommentBottomFrg.onCommentListener() {
            @Override
            public void onFirstComment(String text) {
                mPresenter.onSaveFriendsDiscuss(friendId, text, puserId);
            }

            @Override
            public void onSecondComment(int position, String infoId, String discussId, String text, String pUserId) {
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
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
        mB.refreshLayout.finishRefreshing();
        mB.refreshLayout.finishLoadmore();
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        UIHelper.startReleaseFrg(this);
    }


    @Override
    public void setSaveDiscuss(DataBean result) {
        DataBean bean = listBean.get(position);
        List<DataBean> disList = bean.getDisList();
        disList.add(0, result);
        adapter.notifyItemChanged(position);
//        adapter.notifyItemInserted(position);
    }
}
