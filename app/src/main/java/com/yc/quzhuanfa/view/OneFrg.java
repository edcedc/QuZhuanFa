package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.model.Response;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.adapter.MyPagerAdapter;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.base.BaseListContract;
import com.yc.quzhuanfa.base.BaseListPresenter;
import com.yc.quzhuanfa.bean.BaseResponseBean;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.bean.PhoneBean;
import com.yc.quzhuanfa.callback.Code;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.databinding.FOneBinding;
import com.yc.quzhuanfa.utils.PopupWindowTool;
import com.yc.quzhuanfa.utils.cache.ShareIsLoginCache;
import com.yc.quzhuanfa.utils.cache.SharedAccount;

import org.litepal.LitePal;

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
        mPresenter.onRequest(pagerNumber = 1, CloudApi.articleGetArticleClass);

//        String mobile = SharedAccount.getInstance(act).getMobile();
//        if (!StringUtils.isEmpty(mobile)){//第三方登录进来
//            List<PhoneBean> phoneBeans = LitePal.select("phone = ?", mobile).find(PhoneBean.class);
//            if (phoneBeans != null && phoneBeans.size() == 0){
//                PhoneBean phoneBean = new PhoneBean();
//                phoneBean.setPhone(mobile);
//                phoneBean.save();
//            }
//        }

        getUserOne();
    }

    private void getUserOne(){
        CloudApi.getUserOne()
                .doOnSubscribe(disposable -> {
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<DataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<DataBean>> baseResponseBeanResponse) {
                        if(baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            DataBean result = baseResponseBeanResponse.body().result;
                            PopupWindowTool.showLogin(act, result, () -> onProfitOne());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void onProfitOne() {
        CloudApi.getNewOldUser()
                .doOnSubscribe(disposable -> {
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<List<DataBean>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<List<DataBean>>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            final List<DataBean> list = baseResponseBeanResponse.body().result;
                            if (list != null && list.size() != 0){
                                new Handler().postDelayed(() -> PopupWindowTool.showAdvertisement(act, list.get(0)), 1000);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ly_lock:
//                PopupWindowTool.showxMoneyRules(act);
                                UIHelper.startSearchVideoFrg(this);
                break;
            case R.id.iv_add:
                PopupWindowTool.showHomeLabel(act, mB.titleBar, list, position -> mB.viewPager.setCurrentItem(position));
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
