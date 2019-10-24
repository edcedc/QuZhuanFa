package com.yc.quzhuanfa.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.base.User;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.databinding.FHeadBinding;
import com.yc.quzhuanfa.event.CameraInEvent;
import com.yc.quzhuanfa.impl.UserInfoContract;
import com.yc.quzhuanfa.presenter.UserInfoPresenter;
import com.yc.quzhuanfa.utils.GlideLoadingUtils;
import com.yc.quzhuanfa.view.bottom.CameraBottomFrg;
import com.yc.quzhuanfa.weight.PictureSelectorTool;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/24
 * Time: 20:18
 *  头像
 */
public class HeadFrg extends BaseFragment<UserInfoPresenter, FHeadBinding> implements UserInfoContract.View {

    private CameraBottomFrg cameraBottomFrg;
    private List<LocalMedia> localMediaList = new ArrayList<>();
    private String headPath;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_head;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.head), "打开相册");
        EventBus.getDefault().register(this);
        ViewGroup.LayoutParams params = mB.ivHead.getLayoutParams();
        params.width = ScreenUtils.getScreenWidth();
        params.height = params.width;
        mB.ivHead.setLayoutParams(params);
        if (cameraBottomFrg == null){
            cameraBottomFrg = new CameraBottomFrg(2);
        }
        cameraBottomFrg.setCameraListener(new CameraBottomFrg.onCameraListener() {
            @Override
            public void camera() {
                PictureSelectorTool.PictureSelectorImage(act, CameraInEvent.HEAD_CAMEAR, true);
                if (cameraBottomFrg != null && cameraBottomFrg.isShowing())cameraBottomFrg.dismiss();
            }

            @Override
            public void photo() {
                PictureSelectorTool.photo(act, CameraInEvent.HEAD_PHOTO, true);
                if (cameraBottomFrg != null && cameraBottomFrg.isShowing())cameraBottomFrg.dismiss();
            }

        });

        JSONObject userObj = User.getInstance().getUserObj();
        GlideLoadingUtils.load(act, userObj.optString("head"), mB.ivHead, true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainThreadInEvent(CameraInEvent event) {
        if (cameraBottomFrg != null && cameraBottomFrg.isShowing())cameraBottomFrg.dismiss();
        localMediaList.clear();
        localMediaList.addAll(PictureSelector.obtainMultipleResult((Intent) event.getObject()));
        String path = localMediaList.get(0).getCompressPath();
        headPath = path;
        Glide.with(act).load(path).into(mB.ivHead);
        mPresenter.onUpdateUserInfo(-1, null, headPath);
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
//        cameraBottomFrg.show(getChildFragmentManager(), "dialog");
        PictureSelectorTool.PictureSelectorImage(act, CameraInEvent.HEAD_CAMEAR, true);
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
