package com.yc.quzhuanfa.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.google.zxing.WriterException;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.base.User;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.databinding.FZkingBinding;
import com.yc.quzhuanfa.utils.ShareTool;
import com.yc.quzhuanfa.utils.ZXingUtils;
import com.yc.quzhuanfa.utils.cache.ShareSessionIdCache;

/**
 *  二维码收徒
 */
public class ZkingFrg extends BaseFragment<BasePresenter, FZkingBinding> implements View.OnClickListener {

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_zking;
    }

    @Override
    protected void initView(View view) {
        final AppCompatActivity mAppCompatActivity = (AppCompatActivity) act;
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        //需要调用该函数才能设置toolbar的信息
        mAppCompatActivity.setSupportActionBar(toolbar);
        mAppCompatActivity.getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.onBackPressed();
            }
        });


        mB.ivWx.setOnClickListener(this);
        mB.ivWxq.setOnClickListener(this);

        mB.ivZking.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mB.ivZking.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                try {
                    Bitmap bitmap = ZXingUtils.creatBarcode(CloudApi.REGISTER_URL + ShareSessionIdCache.getInstance(Utils.getApp()).getUserId(), mB.ivZking.getWidth());
                    mB.ivZking.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_wx:
                ShareTool.getInstance(act).shareAppointAction(SHARE_MEDIA.WEIXIN, CloudApi.REGISTER_URL + ShareSessionIdCache.getInstance(Utils.getApp()).getUserId());
                break;
            case R.id.iv_wxq:
                ShareTool.getInstance(act).shareAppointAction(SHARE_MEDIA.WEIXIN_CIRCLE, CloudApi.REGISTER_URL + ShareSessionIdCache.getInstance(Utils.getApp()).getUserId());
                break;
        }
    }
}
