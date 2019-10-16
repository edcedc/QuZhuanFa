package com.yc.quzhuanfa.view.bottom;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseBottomSheetFrg;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/15
 * Time: 15:33
 */
public class ReleaseBottomFrg extends BaseBottomSheetFrg implements View.OnClickListener {

    private AppCompatTextView tv_photo;
    private AppCompatTextView tv_camera;
    private int type;

    @Override
    protected void initParms(Bundle bundle) {
        type = bundle.getInt("type");
    }

    @Override
    public int bindLayout() {
        return R.layout.p_camera;
    }

    @Override
    public void initView(View view) {
        tv_photo = view.findViewById(R.id.tv_photo);
        tv_photo.setOnClickListener(this);
        view.findViewById(R.id.tv_camera).setOnClickListener(this);
        view.findViewById(R.id.tv_cancel).setOnClickListener(view1 -> dismiss());
        if (type == 1){
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_photo:
                listener.photo();
                dismiss();
                break;
            case R.id.tv_camera:
                listener.camera();
                dismiss();
                break;
        }
    }

    private onCameraListener listener;
    public void setCameraListener(onCameraListener listener){
        this.listener = listener;
    }
    public interface onCameraListener{
        void photo();
        void camera();
    }

}
