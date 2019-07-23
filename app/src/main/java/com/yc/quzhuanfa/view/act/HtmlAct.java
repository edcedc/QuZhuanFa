package com.yc.quzhuanfa.view.act;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.model.Response;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseActivity;
import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.bean.BaseResponseBean;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.callback.Code;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.databinding.AHtmlBinding;
import com.yc.quzhuanfa.utils.cache.ShareSessionIdCache;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者：yc on 2018/7/25.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 *  统一H5
 */

public class HtmlAct extends BaseActivity<BasePresenter, AHtmlBinding> {

    private int type = -1;
    private String url;

    public static final int GUIDE  = 1;//新手指南
    public static final int MONEY = 2;//挣钱秘笈
    public static final int REGISTER = 4;//隐私协议
    public static final int ABOUT_ME = 8;//联系我们
    public static final int ABOUT_ME_WECAT = 16;//联系我们的微信
    public static final int ABOUT_ME_QQ = 32;//联系我们的QQ
    public static final int MEMBER = 64;//会员类型
    public static final int CENTER = 128;//会员类型
    public static final int ADVERTISEMENT = 99;//登录进来的弹窗


    @Override
    public void initPresenter() {mPresenter.init(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.a_html;
    }

    @Override
    protected void initParms(Bundle bundle) {
        type = bundle.getInt("type");
        url = bundle.getString("url");
    }

    @Override
    protected void initView() {
        switch (type){
            case GUIDE:
                setTitle("新手指南");
                break;
            case MONEY:
                setTitle("挣钱秘笈");
                break;
            case REGISTER:
                setTitle("隐私协议");
                break;
            case MEMBER:
                setTitle("会员协议");
            case CENTER:
                setTitle("活动中心");
                break;
            default:
                setTitle("详情");
                break;
        }
        if(type == ADVERTISEMENT){
            mB.webView.loadUrl(url);
        }else {
            mB.webView.loadUrl(CloudApi.AGREEMENT_URL + type);
        }
        mB.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                LogUtils.e(url);
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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mB.webView.canGoBack()) {
            mB.webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        mB.webView.removeAllViews();
        mB.webView.destroy();
        super.onDestroy();
    }
}
