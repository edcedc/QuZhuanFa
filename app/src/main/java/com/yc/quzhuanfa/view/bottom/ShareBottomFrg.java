package com.yc.quzhuanfa.view.bottom;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseBottomSheetFrag;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.base.User;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.utils.ShareTool;
import com.yc.quzhuanfa.utils.cache.ShareSessionIdCache;

/**
 * Created by edison on 2019/4/9.
 */

public class ShareBottomFrg extends BaseBottomSheetFrag implements View.OnClickListener {

    @Override
    protected void initPresenter() {

    }

    private BaseFragment root;
    public  void setBundle(BaseFragment root){
        this.root = root;
    }

    @Override
    protected void initParms(Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.p_share;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.tv_wx).setOnClickListener(this);
        view.findViewById(R.id.tv_pyq).setOnClickListener(this);
        view.findViewById(R.id.tv_zking).setOnClickListener(this);
        view.findViewById(R.id.bt_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_wx:
                ShareTool.getInstance((Activity) act).shareAppointAction(SHARE_MEDIA.WEIXIN, CloudApi.REGISTER_URL + ShareSessionIdCache.getInstance(Utils.getApp()).getUserId());
                break;
            case R.id.tv_pyq:
                ShareTool.getInstance((Activity) act).shareAppointAction(SHARE_MEDIA.WEIXIN_CIRCLE, CloudApi.REGISTER_URL + ShareSessionIdCache.getInstance(Utils.getApp()).getUserId());
                break;
            case R.id.tv_zking:
                UIHelper.startZkingFrg(root);
                break;
            case R.id.bt_submit:
                break;
        }
        dismiss();
    }
}
