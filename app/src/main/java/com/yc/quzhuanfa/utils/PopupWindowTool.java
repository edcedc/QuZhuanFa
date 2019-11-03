package com.yc.quzhuanfa.utils;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jungly.gridpasswordview.GridPasswordView;
import com.jungly.gridpasswordview.PasswordType;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.adapter.SignAdapter;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.view.act.HtmlAct;
import com.yc.quzhuanfa.weight.RoundImageView;
import com.yc.quzhuanfa.weight.WPopupWindow;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;


/**
 * 作者：yc on 2018/8/23.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class PopupWindowTool {

    public static void showAdvertisement(final Context act, final DataBean bean){
        View wh = LayoutInflater.from(act).inflate(R.layout.p_adv, null);
        final WPopupWindow popupWindow = new WPopupWindow(wh);
        popupWindow.showAtLocation(wh, Gravity.CENTER, 0, 0);
        RoundImageView iv_img = wh.findViewById(R.id.iv_img);
        GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + bean.getAttachId(), iv_img);
        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = bean.getUrl();
                if (!StringUtils.isEmpty(url)){
                    UIHelper.startHtmlAct(HtmlAct.ADVERTISEMENT, url);
                }
            }
        });
        wh.findViewById(R.id.btn_cancel).setOnClickListener(view -> popupWindow.dismiss());
    }

    public static void showLogin(final Context act, DataBean bean, onLoginClickListener listener){
        View wh = LayoutInflater.from(act).inflate(R.layout.p_login, null);
        final WPopupWindow popupWindow = new WPopupWindow(wh);
        popupWindow.showAtLocation(wh, Gravity.CENTER, 0, 0);
        AppCompatTextView tv_css = wh.findViewById(R.id.tv_css);
        AppCompatTextView tv_login = wh.findViewById(R.id.tv_login);
        AppCompatTextView tv_share = wh.findViewById(R.id.tv_share);
        AppCompatTextView tv_apprentice = wh.findViewById(R.id.tv_apprentice);
        double priceAll = 0;
        if (bean.getLogin() == 0){
            tv_login.setText("首次登陆APP          +" +
                    bean.getLoginPrice() +
                    "元            " +
                    "未获得");
            tv_login.setTextColor(act.getResources().getColor(R.color.orange_FFB502));
        }else {
            priceAll += bean.getLoginPrice();
            tv_login.setText("首次登陆APP          +" +
                    bean.getLoginPrice() +
                    "元            " +
                    "已获得");
            tv_login.setTextColor(act.getResources().getColor(R.color.white));
        }
        if (bean.getShare() == 0){
            tv_share.setText("首次分享文章          +" +
                    bean.getSharePrice() +
                    "元           " +
                    "未获得");
            tv_share.setTextColor(act.getResources().getColor(R.color.orange_FFB502));
        }else {
            priceAll += bean.getSharePrice();
            tv_share.setText("首次分享文章          +" +
                    bean.getSharePrice() +
                    "元           " +
                    "已获得");
            tv_share.setTextColor(act.getResources().getColor(R.color.white));
        }
        if (bean.getShare() == 0){
            tv_apprentice.setText("首次收徒                  +" +
                    bean.getSharePrice() +
                    "元           " +
                    "未获得");
            tv_apprentice.setTextColor(act.getResources().getColor(R.color.orange_FFB502));
        }else {
            priceAll += bean.getApprenticePrice();
            tv_apprentice.setText("首次收徒                  +" +
                    bean.getSharePrice() +
                    "元           " +
                    "已获得");
            tv_apprentice.setTextColor(act.getResources().getColor(R.color.white));
        }
        String str = "恭喜您获得" +
                priceAll +
                "元" + "\n" + "新人红包(可提现）";
        Spannable sp = new SpannableString(str);
        sp.setSpan(new ForegroundColorSpan(act.getResources().getColor(R.color.red_EF402C)), 5, str.length() - 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(15, true), str.length() - 11, str.length() - 9, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(20, true), str.length() - 5, str.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(act.getResources().getColor(R.color.orange_FF942F)), str.length() - 5, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_css.setText(sp);
        wh.findViewById(R.id.bt_submit).setOnClickListener(view -> {
            listener.onClick();
            popupWindow.dismiss();
        });
    }

    private onLoginClickListener onLoginClickListener;
    public interface onLoginClickListener{
        void onClick();
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
                tvText.setText(bean.getClassName());
                return view;
            }
        });
        rv_flow.setOnTagClickListener((view1, position, parent) -> {
            if (listener != null){
                listener.onClick(position);
                popupWindow.dismiss();
            }
            return false;
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

    /**
     *  可已复用的弹窗
     * @param act
     */
    public static XPopup.Builder showDialog(Context act){
        return new XPopup.Builder(act)
                .setPopupCallback(new SimpleCallback() {
                    @Override
                    public void onCreated() {
                    }

                    @Override
                    public void beforeShow() {
                        super.beforeShow();
//                        Log.e("tag", "beforeShow，在每次show之前都会执行，可以用来进行多次的数据更新。");
                    }

                    @Override
                    public void onShow() {
                    }
                    @Override
                    public void onDismiss() {
                    }
                    //如果你自己想拦截返回按键事件，则重写这个方法，返回true即可
                    @Override
                    public boolean onBackPressed() {
//                        ToastUtils.showShort("我拦截的返回按键，按返回键XPopup不会关闭了");
                        return false;
                    }
                });
    }

}
