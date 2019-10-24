package com.yc.quzhuanfa.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.adapter.ImageAdapter;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.databinding.FReleaseBinding;
import com.yc.quzhuanfa.event.CameraInEvent;
import com.yc.quzhuanfa.impl.ReleaseContract;
import com.yc.quzhuanfa.presenter.ReleasePresenter;
import com.yc.quzhuanfa.utils.DatePickerUtils;
import com.yc.quzhuanfa.view.bottom.ReleaseBottomFrg;
import com.yc.quzhuanfa.weight.FullyGridLayoutManager;
import com.yc.quzhuanfa.weight.PictureSelectorTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/15
 * Time: 11:24
 *  发布
 */
public class ReleaseFrg extends BaseFragment<ReleasePresenter, FReleaseBinding> implements ReleaseContract.View, View.OnClickListener {

    private ReleaseBottomFrg releaseBottomFrg;
    private List<LocalMedia> localMediaList = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private int type;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_release;
    }

    @Override
    protected void initView(View view) {
        EventBus.getDefault().register(this);
        mB.tvCan.setOnClickListener(this);
        mB.tvLocation.setOnClickListener(this);
        mB.tvRelease.setOnClickListener(this);
        imageAdapter = new ImageAdapter(act, () -> {
            if (localMediaList.size() == 0){
                releaseBottomFrg.show(getFragmentManager(), "");
            }else if (type == 1){
                imageAdapter.setSelectMax(9);
                PictureSelectorTool.PictureSelectorImage(act, localMediaList, PictureConfig.CHOOSE_REQUEST);
            }
        });
        mB.recyclerView.setLayoutManager(new FullyGridLayoutManager(act, 3, GridLayoutManager.VERTICAL, false));
        mB.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mB.recyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener((position, v) -> {
            LocalMedia media = localMediaList.get(position);
            PictureSelectorTool.PictureMediaType(act, localMediaList, position);
        });
        releaseBottomFrg = new ReleaseBottomFrg();
        releaseBottomFrg.setCameraListener(new ReleaseBottomFrg.onCameraListener() {
            @Override
            public void photo() {
                type = 0;
                imageAdapter.setSelectMax(1);
                imageAdapter.setVideoImg(true);
                PictureSelectorTool.PictureSelectorVideo(act);
            }

            @Override
            public void camera() {
                type = 1;
                imageAdapter.setSelectMax(9);
                PictureSelectorTool.PictureSelectorImage(act, localMediaList, PictureConfig.CHOOSE_REQUEST);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainThreadInEvent(CameraInEvent event) {
        localMediaList.clear();
        localMediaList.addAll(PictureSelector.obtainMultipleResult((Intent) event.getObject()));
        imageAdapter.setList(localMediaList);
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_can:
                pop();
                break;
            case R.id.tv_release:
                mPresenter.onRelease(mB.etText.getText().toString(), type, localMediaList, mB.tvAddress.getText().toString());
                break;
            case R.id.tv_location:
                DatePickerUtils.onAddressPicker(act, add -> mB.tvAddress.setText(add));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setSuccess(DataBean result) {
        pop();
    }
}
