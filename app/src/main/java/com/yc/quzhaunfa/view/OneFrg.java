package com.yc.quzhaunfa.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.model.Response;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.adapter.MyPagerAdapter;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.base.BaseListContract;
import com.yc.quzhaunfa.base.BaseListPresenter;
import com.yc.quzhaunfa.base.BasePresenter;
import com.yc.quzhaunfa.bean.BaseResponseBean;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.callback.Code;
import com.yc.quzhaunfa.controller.CloudApi;
import com.yc.quzhaunfa.controller.UIHelper;
import com.yc.quzhaunfa.databinding.FOneBinding;
import com.yc.quzhaunfa.utils.PopupWindowTool;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class OneFrg extends BaseFragment<BaseListPresenter, FOneBinding> implements BaseListContract.View, View.OnClickListener{

    private List<DataBean> list;

    public static OneFrg newInstance() {
        Bundle args = new Bundle();
        OneFrg fragment = new OneFrg();
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
        return R.layout.f_one;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        mB.ivAdd.setOnClickListener(this);
        mB.lyLock.setOnClickListener(this);
        mB.ivIncome.setOnClickListener(this);
        mPresenter.onRequest(CloudApi.articleGetArticleClass);

        onProfitOne();
    }

    private void onProfitOne() {
        CloudApi.getUserOne()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable){
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<DataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(final Response<BaseResponseBean<DataBean>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    PopupWindowTool.showLogin(act, baseResponseBeanResponse.body().result);
                                }
                            }, 5000);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        OneFrg.this.onError(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        ImmersionBar.with(this).navigationBarColor(R.color.red_EF402C);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ly_lock:
                PopupWindowTool.showxMoneyRules(act);
                break;
            case R.id.iv_add:
                PopupWindowTool.showHomeLabel(act, mB.titleBar, list, new PopupWindowTool.onPopClickListener() {
                    @Override
                    public void onClick(int position) {
                        mB.viewPager.setCurrentItem(position);
                    }
                });
                break;
            case R.id.iv_income:
                UIHelper.startIncomeFrg(this);
                break;
        }
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {

    }

    @Override
    public void setData(Object data) {
        list = (List<DataBean>) data;
        ArrayList<Fragment> mFragments = new ArrayList<>();
        String[] strings = new String[list.size()];
        for (int i = 0; i < list.size(); i++){
            DataBean bean = list.get(i);
            strings[i] = bean.getClassName();
            HomeChildFrg frg = new HomeChildFrg();
            Bundle bundle = new Bundle();
            bundle.putString("id", bean.getClassId());
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
