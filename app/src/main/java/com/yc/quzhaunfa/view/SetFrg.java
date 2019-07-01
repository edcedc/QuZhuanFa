package com.yc.quzhaunfa.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.CacheUtils;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.base.BasePresenter;
import com.yc.quzhaunfa.base.IBaseView;
import com.yc.quzhaunfa.controller.UIHelper;
import com.yc.quzhaunfa.databinding.FSetBinding;
import com.yc.quzhaunfa.view.act.HtmlAct;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/19
 * Time: 22:27
 *  设置
 */
public class SetFrg extends BaseFragment<BasePresenter, FSetBinding> implements IBaseView, View.OnClickListener {
    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_set;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.set));
        mB.lyPrivacy.setOnClickListener(this);
        mB.lyContact.setOnClickListener(this);
        mB.lyReminder.setOnClickListener(this);
        mB.lyClear.setOnClickListener(this);
        long cache = CacheUtils.getInstance().getCacheSize();
        mB.tvClear.setText(cache + "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ly_privacy:
                UIHelper.startHtmlAct(HtmlAct.PRIVACY_PROTOCOL);
                break;
            case R.id.ly_contact:
                UIHelper.startContactFrg(this, 1);
                break;
            case R.id.ly_reminder:

                break;
            case R.id.ly_clear:
                CacheUtils.getInstance().clear();
                showToast("清除成功");
                mB.tvClear.setText("0MB");
                break;
        }
    }
}
