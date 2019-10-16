package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.adapter.CommentAdapter;
import com.yc.quzhuanfa.adapter.PyqAdapter;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.databinding.FCommentDescBinding;
import com.yc.quzhuanfa.impl.SixContract;
import com.yc.quzhuanfa.presenter.SixPresenter;
import com.yc.quzhuanfa.view.bottom.CommentBottomFrg;
import com.yc.quzhuanfa.weight.LinearDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/16
 * Time: 14:07
 *  评论详情
 */
public class CommentDescFrg extends BaseFragment<SixPresenter, FCommentDescBinding> implements SixContract.View, View.OnClickListener {

    private List<DataBean> listBean = new ArrayList<>();
    private List<DataBean> listCommentBean = new ArrayList<>();
    private CommentAdapter adapter;
    private String friendId;
    private CommentBottomFrg commentBottomFrg;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        DataBean bean = new Gson().fromJson(bundle.getString("bean"), DataBean.class);
        friendId = bean.getFriendId();
        listBean.add(bean);
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_comment_desc;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.comments));
        mB.tvComment.setOnClickListener(this);
        PyqAdapter pyqAdapter = new PyqAdapter(act, this, listBean);
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(pyqAdapter);
        pyqAdapter.notifyDataSetChanged();
        commentBottomFrg = new CommentBottomFrg();
        showLoadDataing();
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onComment(pagerNumber = 1, friendId);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onComment(pagerNumber += 1, friendId);
            }
        });
        if (adapter == null) {
            adapter = new CommentAdapter(act, listCommentBean);
        }
        mB.rvComment.setAdapter(adapter);
        setRecyclerViewType(mB.rvComment);
        mB.rvComment.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
        super.setRefreshLayoutMode(listCommentBean.size(), totalRow, mB.refreshLayout);
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
            listCommentBean.clear();
        }
        listCommentBean.addAll(list);
        adapter.notifyDataSetChanged();
        mB.refreshLayout.finishRefreshing();
        mB.refreshLayout.finishLoadmore();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_comment:

                break;
        }
    }

    @Override
    public void setSaveDiscuss(DataBean result) {

    }
}
