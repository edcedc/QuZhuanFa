package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.adapter.MyPagerAdapter;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.base.BaseListContract;
import com.yc.quzhuanfa.base.BaseListPresenter;
import com.yc.quzhuanfa.base.BasePresenter;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.databinding.FFiveBinding;
import com.yc.quzhuanfa.databinding.FOneBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/10
 * Time: 18:18
 */
public class FiveFrg extends BaseFragment<BaseListPresenter, FFiveBinding> implements BaseListContract.View, View.OnClickListener{

    public static FiveFrg newInstance() {

        Bundle args = new Bundle();

        FiveFrg fragment = new FiveFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<DataBean> list;


    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_five;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        mB.ivAdd.setOnClickListener(this);
        mPresenter.onRequest(CloudApi.videoGetVideoClassifyList);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_add:
                UIHelper.startSearchVideoFrg(this);
                break;
        }
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {

    }

    @Override
    public void setData(Object data) {
        setSwipeBackEnable(false);
        list = (List<DataBean>) data;
        ArrayList<Fragment> mFragments = new ArrayList<>();
        String[] strings = new String[list.size()];
        for (int i = 0; i < list.size(); i++){
            DataBean bean = list.get(i);
            strings[i] = bean.getName();
            FiveChildFrg frg = new FiveChildFrg();
            Bundle bundle = new Bundle();
            bundle.putString("id", bean.getClassifyId());
            frg.setArguments(bundle);
            mFragments.add(frg);
        }
        mB.viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), mFragments, strings));
        mB.tbLayout.setViewPager(mB.viewPager);
        mB.viewPager.setOffscreenPageLimit(list.size() - 1);
        mB.tbLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mB.viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }
}
