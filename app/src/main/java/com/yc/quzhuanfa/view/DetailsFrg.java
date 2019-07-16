package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.databinding.FDetailsBinding;
import com.yc.quzhuanfa.impl.DetailsContract;
import com.yc.quzhuanfa.presenter.DetailsPresenter;
import com.yc.quzhuanfa.utils.ShareTool;
import com.yc.quzhuanfa.utils.cache.ShareSessionIdCache;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 12:40
 */
public class DetailsFrg extends BaseFragment<DetailsPresenter, FDetailsBinding> implements DetailsContract.View, View.OnClickListener {

    private ShareAction shareAction;
    private DataBean bean;

    public static DetailsFrg newInstance() {
        Bundle args = new Bundle();
        DetailsFrg fragment = new DetailsFrg();
        fragment.setArguments(args);
        return fragment;
    }

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
        return R.layout.f_details;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.details));
        setSwipeBackEnable(false);
        String url = CloudApi.ARTICLE_URL  + ShareSessionIdCache.getInstance(Utils.getApp()).getUserId() +
                "&articleId=" + bean.getArticleId();
        ShareTool instance = ShareTool.getInstance(act);
        shareAction = instance.shareAction(bean.getClassName(), bean.getTitle(), bean.getPic(), url + "&realType=1");
        instance.setUMListener(new ShareTool.UMListener() {
            @Override
            public void onResult(SHARE_MEDIA platform) {
                mPresenter.onSaveForward(bean.getArticleId());
            }
        });
        mB.tvForwarding.setOnClickListener(this);
        mB.tvShare.setOnClickListener(this);
        mB.webView.loadUrl(url + "&realType=2");
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
        mB.tvLook.setVisibility(bean.getType() == 1 ? View.VISIBLE : View.GONE);
        mB.tvLook.setOnClickListener(this);
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
                shareAction.open();
                break;
            case R.id.tv_look:
                break;
        }
    }

}
