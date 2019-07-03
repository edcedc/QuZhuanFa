package com.yc.quzhaunfa.view.act;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.model.Response;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.base.BaseActivity;
import com.yc.quzhaunfa.base.BasePresenter;
import com.yc.quzhaunfa.bean.BaseResponseBean;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.callback.Code;
import com.yc.quzhaunfa.controller.CloudApi;
import com.yc.quzhaunfa.databinding.AHtmlBinding;
import com.yc.quzhaunfa.utils.cache.ShareSessionIdCache;

import org.json.JSONObject;

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

    public static final int PRIVACY_PROTOCOL = 1;//隐私协议
    public static final int REGISTER_PROTOCOL = 4;//注册协议

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
        ImmersionBar.with(this)
                .transparentBar()
                .init();
        switch (type){
            case PRIVACY_PROTOCOL:
                setTitle("隐私协议");
                break;
            case REGISTER_PROTOCOL:
                setTitle("注册协议");
                break;
            default:
                setTitle("广告");
                break;
        }
        CloudApi.commonQueryAPPAgreement(type)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<DataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<DataBean>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            DataBean data = baseResponseBeanResponse.body().result;
                            if (data != null){
                                mB.webView.loadUrl(CloudApi.SERVLET_URL + "#/H5?articleId=" +
                                        data.getContent() +
                                        "&userId=" +
                                        ShareSessionIdCache.getInstance(act).getUserId());
                            }
                        }else {
                            act.finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        mB.webView.setInitialScale(100);
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
