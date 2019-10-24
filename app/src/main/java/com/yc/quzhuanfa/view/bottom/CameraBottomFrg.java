package com.yc.quzhuanfa.view.bottom;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.luck.picture.lib.entity.LocalMedia;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseBottomSheetFrg;

import java.util.ArrayList;


/**
 * 作者：yc on 2018/8/4.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 *  打开相册或相机
 */
@SuppressLint("ValidFragment")
public class CameraBottomFrg extends BaseBottomSheetFrg implements View.OnClickListener{


    private int type;

    public CameraBottomFrg() {
    }

    public CameraBottomFrg(int type) {
        this.type = type;
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.p_camera;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        view.findViewById(R.id.tv_camera).setOnClickListener(this);
        TextView tv_photo = view.findViewById(R.id.tv_photo);
        tv_photo.setOnClickListener(this);
        view.findViewById(R.id.layout).setOnClickListener(this);

        switch (type){
            case 0:
                break;
            case 1:
                view.findViewById(R.id.tv_camera).setVisibility(View.GONE);
                view.findViewById(R.id.tv_photo).setVisibility(View.GONE);
                break;
            case 2:
                tv_photo.setText("");
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cancel:
            case R.id.layout:
                dismiss();
                break;
             case R.id.tv_camera:
                if (listener != null){
                    listener.camera();
                }
                break;
             case R.id.tv_photo:
                 if (listener != null){
                     listener.photo();
                 }
                break;
        }
    }

    private onCameraListener listener;
    public void setCameraListener(onCameraListener listener){
        this.listener = listener;
    }

    public interface onCameraListener{
        void camera();
        void photo();
    }


}
