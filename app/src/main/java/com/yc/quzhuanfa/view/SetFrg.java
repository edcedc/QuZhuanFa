package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheDoubleUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.LogUtils;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.base.IBaseView;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.databinding.FSetBinding;
import com.yc.quzhuanfa.utils.cache.ShareSessionIdCache;
import com.yc.quzhuanfa.view.act.HtmlAct;

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
        mB.lyVip.setOnClickListener(this);
        mB.lyReminder.setOnClickListener(this);
        mB.lyClear.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);
        long cache =  CacheMemoryUtils.getInstance().getCacheCount();
        LogUtils.e(cache);
        mB.tvClear.setText(cache + "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ly_privacy:
                UIHelper.startHtmlAct(HtmlAct.REGISTER);
                break;
            case R.id.ly_contact:
                UIHelper.startContactFrg(this);
                break;
            case R.id.ly_reminder:

                break;
            case R.id.ly_clear:
                CacheMemoryUtils.getInstance().clear();
                showToast("清除成功");
                mB.tvClear.setText("0MB");
                break;
            case R.id.bt_submit:
                UIHelper.startLoginAct();
                ShareSessionIdCache.getInstance(act).remove();
                ActivityUtils.finishAllActivities();
                break;
            case R.id.ly_vip:
                UIHelper.startHtmlAct(HtmlAct.MEMBER);
                break;
        }
    }
}
