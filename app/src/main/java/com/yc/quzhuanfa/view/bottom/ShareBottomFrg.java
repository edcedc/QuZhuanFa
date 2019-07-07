package com.yc.quzhuanfa.view.bottom;

import android.os.Bundle;
import android.view.View;

import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseBottomSheetFrag;

/**
 * Created by edison on 2019/4/9.
 */

public class ShareBottomFrg extends BaseBottomSheetFrag implements View.OnClickListener {

    @Override
    protected void initPresenter() {

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

                break;
            case R.id.tv_pyq:

                break;
            case R.id.tv_zking:

                break;
            case R.id.bt_submit:
                dismiss();
                break;
        }
    }
}
