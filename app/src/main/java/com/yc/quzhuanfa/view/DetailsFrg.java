package com.yc.quzhuanfa.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.adapter.CommentAdapter;
import com.yc.quzhuanfa.adapter.HomeChildAdapter;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.databinding.FDetailsBinding;
import com.yc.quzhuanfa.impl.DetailsContract;
import com.yc.quzhuanfa.presenter.DetailsPresenter;
import com.yc.quzhuanfa.utils.GlideImageLoader;
import com.yc.quzhuanfa.utils.ShareTool;
import com.yc.quzhuanfa.utils.cache.ShareSessionIdCache;
import com.yc.quzhuanfa.view.bottom.CommentBottomFrg;
import com.yc.quzhuanfa.weight.LinearDividerItemDecoration;
import com.youth.banner.transformer.DefaultTransformer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 12:40
 */
public class DetailsFrg extends BaseFragment<DetailsPresenter, FDetailsBinding> implements DetailsContract.View, View.OnClickListener {

    private ShareAction shareAction;
    private DataBean bean;
    private ShareTool instance;
    private String url;
    private String articleId;
    private CommentBottomFrg commentBottomFrg;

    public static DetailsFrg newInstance() {
        Bundle args = new Bundle();
        DetailsFrg fragment = new DetailsFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<DataBean> listBean = new ArrayList<>();
    private CommentAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        bean = new Gson().fromJson(bundle.getString("bean"), DataBean.class);
        articleId = bean.getArticleId();
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_details;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.details));
        setSwipeBackEnable(false);
        mB.tvComment.setOnClickListener(this);
        commentBottomFrg = new CommentBottomFrg();
        url = CloudApi.ARTICLE_URL  + ShareSessionIdCache.getInstance(Utils.getApp()).getUserId() +
                "&articleId=" + bean.getArticleId() + "&realType=1";
        instance = ShareTool.getInstance(act);
        instance.setUMListener(new ShareTool.UMListener() {
            @Override
            public void onResult(SHARE_MEDIA platform) {
                mPresenter.onSaveForward(bean.getArticleId());
            }
        });
        mB.tvForwarding.setOnClickListener(this);
        mB.tvShare.setOnClickListener(this);
        mB.webView.loadUrl(url);
        mPresenter.onRandom();
//        mB.webView.loadDataWithBaseURL(null, bean.getContent(), "text/html", "utf-8", null);
        mB.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView var1, int var2, String var3, String var4) {
                mB.progressBar.setVisibility(View.GONE);
                ToastUtils.showShort("网页加载失败");
            }
        });
        //进度条
        mB.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mB.progressBar.setVisibility(View.GONE);
                    return;
                }
                mB.progressBar.setVisibility(View.VISIBLE);
                mB.progressBar.setProgress(newProgress);
            }
        });
//        mB.tvLook.setVisibility(bean.getType() == 1 ? View.VISIBLE : View.GONE);
        mB.tvLook.setOnClickListener(this);

        if (adapter == null) {
            adapter = new CommentAdapter(act, listBean);
        }
        mB.rvComment.setAdapter(adapter);
        setRecyclerViewType(mB.rvComment);
        mB.rvComment.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onArticleDiscussesList(pagerNumber = 1, articleId);

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onArticleDiscussesList(pagerNumber += 1, articleId);
            }
        });
        commentBottomFrg.setOnCommentListener(new CommentBottomFrg.onCommentListener() {
            @Override
            public void onFirstComment(String text) {
                mPresenter.onSaveDiscuss(articleId, text);
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
        mB.refreshLayout.finishRefreshing();
        mB.refreshLayout.finishLoadmore();
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onBackPressedSupport() {
        mB.webView.goBack();// 返回前一个页面
        return super.onBackPressedSupport();
    }

    @Override
    public void onDestroy() {
        mB.webView.removeAllViews();
        mB.webView.destroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forwarding:
                UIHelper.startMakeMoneyAct();
                break;
            case R.id.tv_share:
                try {
                    String s = CloudApi.SHARE_URL + url;
                    s = s.replaceAll("#", "%23");
                    LogUtils.e(s);
                    mPresenter.onShareUrl(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_comment:
                commentBottomFrg.show(getFragmentManager(), "");
                break;
        }
    }

    @Override
    public void setShare(String short_url) {
        shareAction = instance.shareAction(bean.getClassName(), bean.getTitle(), bean.getPic(), short_url);
        shareAction.open();
    }

    @Override
    public void onRandom(List<DataBean> result) {
        HomeChildAdapter adapter = new HomeChildAdapter(act, result);
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setSaveDiscuss(DataBean bean) {
        listBean.add(0, bean);
        adapter.notifyDataSetChanged();
    }
}
