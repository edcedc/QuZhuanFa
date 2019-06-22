package com.yc.quzhaunfa.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.adapter.MyPagerAdapter;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.base.BasePresenter;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.databinding.FMakeBinding;

import java.util.ArrayList;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 13:18
 *  赚钱攻略
 */
public class MakeMoneyFrg extends BaseFragment<BasePresenter, FMakeBinding> {

    public static MakeMoneyFrg newInstance() {
        Bundle args = new Bundle();
        MakeMoneyFrg fragment = new MakeMoneyFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_make;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.make));
        setSwipeBackEnable(false);
        ArrayList<Fragment> mFragments = new ArrayList<>();
        String[] strings = new String[2];
        strings[0] = getString(R.string.finger_guide);
        strings[1] = getString(R.string.make1);
        for (int i = 0; i < strings.length; i++){
            MakeMoneyChildFrg frg = new MakeMoneyChildFrg();
            Bundle bundle = new Bundle();
            bundle.putString("url", "https://www.baidu.com/");
            frg.setArguments(bundle);
            mFragments.add(frg);
        }
        mB.viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), mFragments, strings));
        mB.tbLayout.setViewPager(mB.viewPager);
        mB.viewPager.setOffscreenPageLimit(strings.length - 1);
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
