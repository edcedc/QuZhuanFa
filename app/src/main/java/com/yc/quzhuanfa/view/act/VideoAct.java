package com.yc.quzhuanfa.view.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.ListGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.adapter.CommentAdapter;
import com.yc.quzhuanfa.adapter.RandomVideoAdapter;
import com.yc.quzhuanfa.base.GSYBaseActivityDetail;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.databinding.FVideoDescBinding;
import com.yc.quzhuanfa.impl.VideoDescContract;
import com.yc.quzhuanfa.presenter.VideoDescPresenter;
import com.yc.quzhuanfa.utils.GlideLoadingUtils;
import com.yc.quzhuanfa.utils.ShareTool;
import com.yc.quzhuanfa.utils.cache.ShareSessionIdCache;
import com.yc.quzhuanfa.view.bottom.CommentBottomFrg;
import com.yc.quzhuanfa.weight.GSYSampleADVideoPlayer;
import com.yc.quzhuanfa.weight.LinearDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 12:37
 */
public class VideoAct extends GSYBaseActivityDetail<VideoDescPresenter, FVideoDescBinding, ListGSYVideoPlayer> implements VideoDescContract.View, View.OnClickListener {


    private DataBean bean;
    private RandomVideoAdapter randomVideoAdapter;
    private List<DataBean> listRandomBean = new ArrayList<>();

    private List<DataBean> listBean = new ArrayList<>();
    private CommentAdapter adapter;
    private ArrayList<GSYSampleADVideoPlayer.GSYADVideoModel> urls = new ArrayList<>();
    private String videoId;
    private ShareAction shareAction;
    private ShareTool instance;
    private CommentBottomFrg commentBottomFrg;
    private String url;

    @Override
    protected void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_video_desc;
    }

    @Override
    protected void initParms(Bundle bundle) {
        bean = new Gson().fromJson(bundle.getString("bean"), DataBean.class);
        videoId = bean.getVideoId();
    }

    @Override
    protected void initView() {
//        setTitle(getString(R.string.details));
        ImmersionBar.with(this).statusBarColor(R.color.black).init();
        mB.tvCollect.setOnClickListener(this);
        mB.tvComment.setOnClickListener(this);
        mB.tvForwarding.setOnClickListener(this);
        mB.tvShare.setOnClickListener(this);

        instance = ShareTool.getInstance(act);
        instance.setUMListener(platform -> mPresenter.onSaveForward(videoId));
        mB.tvCollect.setCompoundDrawablesWithIntrinsicBounds(null,
                null, act.getResources().getDrawable(bean.getIsTrue() == 0 ? R.mipmap.weidianzan1 : R.mipmap.dianzan1, null), null);
        commentBottomFrg = new CommentBottomFrg();

        url = CloudApi.VIDEO_URL  + ShareSessionIdCache.getInstance(Utils.getApp()).getUserId() +
                "&videoId=" + videoId + "&realType=1";
        mB.webView.loadUrl(url);
        LogUtils.e(url);
//        mB.webView.loadDataWithBaseURL(null, bean.getContext(), "text/html", "utf-8", null);
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
        mPresenter.onSaveHistory(videoId);


        //普通模式
        initVideo();
        resolveNormalVideoUI();
        mB.videoPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        mB.videoPlayer.setRotateViewAuto(false);
        mB.videoPlayer.setLockLand(false);
        mB.videoPlayer.setShowFullAnimation(false);
        mB.videoPlayer.setNeedLockFull(true);
        mB.videoPlayer.setVideoAllCallBack(this);
        mB.videoPlayer.setLockClickListener((view, lock) -> {
            if (orientationUtils != null) {
                //配合下方的onConfigurationChanged
                orientationUtils.setEnable(!lock);
            }
        });

         urls.add(new GSYSampleADVideoPlayer.GSYADVideoModel(CloudApi.SERVLET_VIDEO_URL + bean.getVideo(),
                "", GSYSampleADVideoPlayer.GSYADVideoModel.TYPE_NORMAL));
        mB.videoPlayer.setAdUp(urls, true, 0);
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideLoadingUtils.loadRounded(act, CloudApi.SERVLET_VIDEO_URL + bean.getVideo(), imageView);
        mB.videoPlayer.setThumbImageView(imageView);

        if (randomVideoAdapter == null) {
            randomVideoAdapter = new RandomVideoAdapter(act, listRandomBean);
        }
        mB.recyclerView.setAdapter(randomVideoAdapter);
        setRecyclerViewType(mB.recyclerView);

        if (adapter == null) {
            adapter = new CommentAdapter(act, listBean);
        }
        mB.rvComment.setAdapter(adapter);
        setRecyclerViewType(mB.rvComment);
        mB.rvComment.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL, 2));
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onVideoDiscussList();
                mPresenter.onRequest(pagerNumber = 1, videoId);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1, videoId);
            }
        });
        commentBottomFrg.setOnCommentListener(new CommentBottomFrg.onCommentListener() {
            @Override
            public void onFirstComment(String text) {
                mPresenter.onSaveVideoDiscuss(videoId, text);
            }

            @Override
            public void onSecondComment(int position, String infoId, String discussId, String text, String pUserId) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    public ListGSYVideoPlayer getGSYVideoPlayer() {
        return mB.videoPlayer;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        return null;
    }

    @Override
    public void clickForFullScreen() {

    }

    @Override
    public boolean getDetailOrientationRotateAuto() {
        return false;
    }

    @Override
    public void onEnterFullscreen(String url, Object... objects) {
        super.onEnterFullscreen(url, objects);
        //隐藏调全屏对象的返回按键
        GSYVideoPlayer gsyVideoPlayer = (GSYVideoPlayer) objects[1];
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isPlay) {
            getGSYVideoPlayer().getCurrentPlayer().onVideoPause();
        }
    }

    @Override
    public void onPrepared(String url, Object... objects) {
        super.onPrepared(url, objects);
        LogUtils.e(url);
        for (GSYSampleADVideoPlayer.GSYADVideoModel model : urls) {
            String modelUrl = model.getUrl();
            if (modelUrl.equals(url) && model.getType() == GSYSampleADVideoPlayer.GSYADVideoModel.TYPE_NORMAL) {
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_comment:
                commentBottomFrg.show(getSupportFragmentManager(), "");
                break;
            case R.id.tv_collect:
                mPresenter.onCollect(videoId, bean.getIsTrue(), bean.getPosition());
                break;
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
        }
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

    private void resolveNormalVideoUI() {
        //增加title
        mB.videoPlayer.getTitleTextView().setVisibility(View.GONE);
        mB.videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        mB.videoPlayer.getBackButton().setOnClickListener(view -> finish());
    }

    @Override
    public void setVideoDiscussList(List<DataBean> result) {
        listRandomBean.clear();
        listRandomBean.addAll(result);
        randomVideoAdapter.notifyDataSetChanged();
    }

    @Override
    public void setSaveVideoDiscuss(DataBean result) {
        listBean.add(0, result);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setCollect(int finalIsTrue) {
        bean.setIsTrue(finalIsTrue);
        mB.tvCollect.setCompoundDrawablesWithIntrinsicBounds(null,
                null, act.getResources().getDrawable(finalIsTrue == 0 ? R.mipmap.weidianzan1 : R.mipmap.dianzan1, null), null);
    }

    @Override
    public void setShare(String short_url) {
        shareAction = instance.shareAction(bean.getClassName(), bean.getTitle(), bean.getPic(), short_url);
        shareAction.open();
    }

}
