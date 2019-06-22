package com.yc.quzhaunfa.presenter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;

import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.adapter.MeAdapter;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.controller.UIHelper;
import com.yc.quzhaunfa.impl.FourContract;
import com.yc.quzhaunfa.weight.WithScrollListView;

import java.util.ArrayList;
import java.util.List;

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
            listStr.add(bean);
        }
        MeAdapter adapter = new MeAdapter(act, listStr, true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
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

}
