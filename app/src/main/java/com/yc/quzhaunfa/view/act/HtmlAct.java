package com.yc.quzhaunfa.view.act;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.base.BaseActivity;
import com.yc.quzhaunfa.base.BasePresenter;
import com.yc.quzhaunfa.databinding.AHtmlBinding;

import org.json.JSONObject;

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
    public static final int REGISTER_PROTOCOL = 2;//注册协议

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
        mB.webView.loadUrl("https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_10480665791014585474%22%7D&n_type=0&p_from=1");
        mB.webView.setInitialScale(100);
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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mB.webView.canGoBack()) {
            mB.webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void getHtmlUrl(){
        /*CloudApi.commonQueryAPPAgreement()
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
                            DataBean data = baseResponseBeanResponse.body().data;
                            if (data != null){
                                String url = null;
                                switch (type){
                                    case 0:
                                        url = data.getRegistration_protocol();
                                        break;
                                }
                                mB.webView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
                            }
                        }else {
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        HtmlAct.this.onError(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });*/
    }

    @Override
    protected void onDestroy() {
        mB.webView.removeAllViews();
        mB.webView.destroy();
        super.onDestroy();
    }
}
