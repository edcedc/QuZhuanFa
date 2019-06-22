package com.yc.quzhaunfa.utils;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jungly.gridpasswordview.GridPasswordView;
import com.jungly.gridpasswordview.PasswordType;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.adapter.SignAdapter;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.weight.WPopupWindow;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：yc on 2018/8/23.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class PopupWindowTool {

    public static void showLogin(final Context act){
        View wh = LayoutInflater.from(act).inflate(R.layout.p_login, null);
        final WPopupWindow popupWindow = new WPopupWindow(wh);
        popupWindow.showAtLocation(wh, Gravity.CENTER, 0, 0);
    }

    public static void showSign(final Context act, final List<DataBean> listBean, final onSignClickListener listener){
        View wh = LayoutInflater.from(act).inflate(R.layout.p_sign, null);
        final WPopupWindow popupWindow = new WPopupWindow(wh);
        popupWindow.showAtLocation(wh, Gravity.CENTER, 0, 0);
        RecyclerView recyclerView = wh.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(act));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final SignAdapter adapter = new SignAdapter(act, listBean);
        recyclerView.setAdapter(adapter);

        wh.findViewById(R.id.iv_huan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onClick(listBean, adapter);
                }
            }
        });

        wh.findViewById(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    public interface onSignClickListener{

        void onClick(List<DataBean> listBean, SignAdapter adapter);

    }


    public static void showxMoneyRules(final Context act){
        View wh = LayoutInflater.from(act).inflate(R.layout.p_money, null);
        final WPopupWindow popupWindow = new WPopupWindow(wh);
        popupWindow.showAtLocation(wh, Gravity.CENTER, 0, 0);
        wh.findViewById(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }


    public static void showHomeLabel(final Context act, View view, List<DataBean> list, final onPopClickListener listener) {
        View wh = LayoutInflater.from(act).inflate(R.layout.p_home_label, null);
        final WPopupWindow popupWindow = new WPopupWindow(wh);
        popupWindow.showAsDropDown(view, Gravity.CENTER, 0, 0);
        wh.findViewById(R.id.tv_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        TagFlowLayout rv_flow = wh.findViewById(R.id.rv_flow);
        rv_flow.setAdapter(new TagAdapter<DataBean>(list){
            @Override
            public View getView(FlowLayout parent, int position, DataBean bean) {
                View view = View.inflate(act, R.layout.i_home_flow, null);
                TextView tvText = view.findViewById(R.id.tv_text);
                tvText.setText("标题" + position);
                return view;
            }
        });
        rv_flow.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (listener != null){
                    listener.onClick(position);
                    popupWindow.dismiss();
                }
                return false;
            }
        });
    }

    public interface onPopClickListener{
        void onClick(int position);
    }

    private static int mCashPosition = -1;
    public static void showCash(final Context act, final onPopClickListener listener) {
        View wh = LayoutInflater.from(act).inflate(R.layout.p_cash, null);
        final WPopupWindow popupWindow = new WPopupWindow(wh);
        popupWindow.showAtLocation(wh, Gravity.CENTER, 0, 0);
        final AppCompatImageView iv_yhk = wh.findViewById(R.id.iv_yhk);
        final AppCompatImageView iv_zfb = wh.findViewById(R.id.iv_zfb);
        final AppCompatImageView iv_wx = wh.findViewById(R.id.iv_wx);
        wh.findViewById(R.id.ly_yhk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_yhk.setBackgroundResource(R.mipmap.dg01);
                iv_zfb.setBackgroundResource(R.mipmap.mg01);
                iv_wx.setBackgroundResource(R.mipmap.mg01);
                mCashPosition = 0;
            }
        });
        wh.findViewById(R.id.ly_zfb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_yhk.setBackgroundResource(R.mipmap.mg01);
                iv_zfb.setBackgroundResource(R.mipmap.dg01);
                iv_wx.setBackgroundResource(R.mipmap.mg01);
                mCashPosition = 1;
            }
        });
        wh.findViewById(R.id.ly_wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_yhk.setBackgroundResource(R.mipmap.mg01);
                iv_zfb.setBackgroundResource(R.mipmap.mg01);
                iv_wx.setBackgroundResource(R.mipmap.dg01);
                mCashPosition = 2;
            }
        });
        wh.findViewById(R.id.bt_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCashPosition == -1){
                    ToastUtils.showShort("请选择支付方式");
                    return;
                }
                if (listener != null){
                    listener.onClick(mCashPosition);
                    popupWindow.dismiss();
                }
            }
        });
    }

    public static void showPay(Context act){
        View wh = LayoutInflater.from(act).inflate(R.layout.p_pay, null);
        final WPopupWindow popupWindow = new WPopupWindow(wh);
        popupWindow.showAtLocation(wh, Gravity.CENTER, 0, 0);
        wh.findViewById(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        final AppCompatTextView tv_price = wh.findViewById(R.id.tv_price);
        GridPasswordView gpv_normal = wh.findViewById(R.id.gpv_normal);
        gpv_normal.setPasswordType(PasswordType.TEXTVISIBLE);
        gpv_normal.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                LogUtils.e(psw);
                tv_price.setText(psw);
            }

            @Override
            public void onInputFinish(String psw) {
                ToastUtils.showShort(psw);
                popupWindow.dismiss();
            }
        });


    }

}
