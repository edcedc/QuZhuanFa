package com.yc.quzhuanfa.view.act;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.ListGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.base.GSYBaseActivityDetail;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.databinding.AVideoBinding;
import com.yc.quzhuanfa.databinding.FVideoDescBinding;
import com.yc.quzhuanfa.impl.VideoDescContract;
import com.yc.quzhuanfa.presenter.VideoDescPresenter;
import com.yc.quzhuanfa.utils.GlideLoadingUtils;
import com.yc.quzhuanfa.weight.GSYSampleADVideoPlayer;

import java.util.ArrayList;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/22
 * Time: 18:25
 */
public class PlayVideoAct extends GSYBaseActivityDetail<BasePresenter, AVideoBinding, ListGSYVideoPlayer>  {

    private ArrayList<GSYSampleADVideoPlayer.GSYADVideoModel> urls = new ArrayList<>();
    private String videoUrl;

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
    protected void initPresenter() {

    }

    @Override
    protected int bindLayout() {
        return R.layout.a_video;
    }

    @Override
    protected void initParms(Bundle bundle) {
        videoUrl = bundle.getString("videoUrl");
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarColor(R.color.black).init();
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

        urls.add(new GSYSampleADVideoPlayer.GSYADVideoModel(videoUrl,
                "", GSYSampleADVideoPlayer.GSYADVideoModel.TYPE_NORMAL));
        mB.videoPlayer.setAdUp(urls, true, 0);
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideLoadingUtils.loadRounded(act, videoUrl, imageView);
        mB.videoPlayer.setThumbImageView(imageView);
    }

    private void resolveNormalVideoUI() {
        //增加title
        mB.videoPlayer.getTitleTextView().setVisibility(View.GONE);
        mB.videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        mB.videoPlayer.getBackButton().setOnClickListener(view -> finish());
    }

}
