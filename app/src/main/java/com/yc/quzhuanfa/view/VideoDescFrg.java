package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.view.View;

import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.databinding.FVideoDescBinding;
import com.yc.quzhuanfa.impl.VideoDescContract;
import com.yc.quzhuanfa.presenter.VideoDescPresenter;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/10
 * Time: 20:15
 */
public class VideoDescFrg extends BaseFragment {

    public static VideoDescFrg newInstance() {

        Bundle args = new Bundle();

        VideoDescFrg fragment = new VideoDescFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_video_desc;
    }

    @Override
    protected void initView(View view) {
//        setTitle(getString(R.string.details));

    }

}
