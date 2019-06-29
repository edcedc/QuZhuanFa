package com.yc.quzhaunfa.presenter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;

import com.blankj.utilcode.util.ActivityUtils;
import com.lzy.okgo.model.Response;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.adapter.MeAdapter;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.base.User;
import com.yc.quzhaunfa.bean.BaseResponseBean;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.callback.Code;
import com.yc.quzhaunfa.controller.CloudApi;
import com.yc.quzhaunfa.controller.UIHelper;
import com.yc.quzhaunfa.impl.FourContract;
import com.yc.quzhaunfa.utils.cache.ShareSessionIdCache;
import com.yc.quzhaunfa.view.act.LoginAct;
import com.yc.quzhaunfa.weight.WithScrollListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/17
 * Time: 10:44
 */
public class FourPresenter extends FourContract.Presenter{

    @Override
    public void listView(WithScrollListView listView, final BaseFragment root) {
        String[] laberStr = {act.getString(R.string.bind_phone), act.getString(R.string.withdrawal), act.getString(R.string.income_details), act.getString(R.string.contact), act.getString(R.string.set)};
        int[] laberImg = {R.mipmap.sjh40, R.mipmap.ljtx01, R.mipmap.srmx01, R.mipmap.lxwm01, R.mipmap.szyy01};
        List<DataBean> listStr = new ArrayList<>();
        for (int i = 0; i < laberStr.length; i++) {
            DataBean bean = new DataBean();
            bean.setName(laberStr[i]);
            bean.setImg(laberImg[i]);

            if (i == 0){
                JSONObject userObj = User.getInstance().getUserObj();
                String phoneNum = userObj.optString("phoneNum");
                if (phoneNum.equals("null")){
                    bean.setContent("领取现金0.1元");
                }
            }

            listStr.add(bean);
        }
        MeAdapter adapter = new MeAdapter(act, listStr, true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        UIHelper.startRetrievePwdFrg(root, 1);
                        break;
                    case 1:
                        UIHelper.startCashFrg(root);
                        break;
                    case 2:
                        UIHelper.startIncomeFrg(root);
                        break;
                    case 3:
                        UIHelper.startContactFrg(root, 0);
                        break;
                    case 4:
                        UIHelper.startSetFrg(root);
                        break;
                }
            }
        });

    }

    @Override
    public void onRequest() {
        CloudApi.userInfo()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        if (jsonObject.optInt("code") == Code.CODE_SUCCESS){
                            JSONObject result = jsonObject.optJSONObject("result");
                            User.getInstance().setLogin(true);
                            User.getInstance().setUserObj(result);
                            mView.setData(User.getInstance().getUserObj());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void sign() {
        CloudApi.userSaveSign()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            onRequest();
                        }
                        mView.onSign();
                        showToast(baseResponseBeanResponse.body().description);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void signList() {
        CloudApi.list2(CloudApi.userSignList)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<List<DataBean>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<List<DataBean>>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            List<DataBean> result = baseResponseBeanResponse.body().result;
                            mView.onSignList(result);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
