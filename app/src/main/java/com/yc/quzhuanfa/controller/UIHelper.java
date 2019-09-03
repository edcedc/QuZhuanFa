package com.yc.quzhuanfa.controller;

import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.google.gson.Gson;
import com.yc.quzhuanfa.MainActivity;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.view.AddBankFrg;
import com.yc.quzhuanfa.view.ApprenticeFrg;
import com.yc.quzhuanfa.view.BankFrg;
import com.yc.quzhuanfa.view.BindPhoneFrg;
import com.yc.quzhuanfa.view.CashFrg;
import com.yc.quzhuanfa.view.ContactFrg;
import com.yc.quzhuanfa.view.IncomeFrg;
import com.yc.quzhuanfa.view.MainFrg;
import com.yc.quzhuanfa.view.PayPwdFrg;
import com.yc.quzhuanfa.view.RetrievePwdFrg;
import com.yc.quzhuanfa.view.SetFrg;
import com.yc.quzhuanfa.view.ThreeFrg;
import com.yc.quzhuanfa.view.WithdrawalFrg;
import com.yc.quzhuanfa.view.ZkingFrg;
import com.yc.quzhuanfa.view.act.ActionCenterAct;
import com.yc.quzhuanfa.view.act.DetailsAct;
import com.yc.quzhuanfa.view.act.HtmlAct;
import com.yc.quzhuanfa.view.act.LoginAct;
import com.yc.quzhuanfa.view.act.MakeMoneyAct;


/**
 * Created by Administrator on 2017/2/22.
 */

public final class UIHelper {

    private UIHelper() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void startMainAct() {
        ActivityUtils.startActivity(MainActivity.class);
    }

    /**
     * 收入明细
     */
    public static void startIncomeFrg(BaseFragment root) {
        IncomeFrg frg = new IncomeFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 银行卡提现记录
     */
    public static void startWithdrawalFrg(BaseFragment root) {
        WithdrawalFrg frg = new WithdrawalFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 徒弟详情
     */
    public static void startApprenticeDescFrg(BaseFragment root, DataBean bean) {
        ApprenticeFrg frg = new ApprenticeFrg();
        Bundle bundle = new Bundle();
        bundle.putString("bean", new Gson().toJson(bean));
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 设置
     */
    public static void startSetFrg(BaseFragment root) {
        SetFrg frg = new SetFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 文章详情
     */
    public static void startDetailsAct(DataBean bean) {
        Bundle bundle = new Bundle();
        bundle.putString("bean", new Gson().toJson(bean));
        ActivityUtils.startActivity(bundle, DetailsAct.class);
    }

    /**
     * 赚钱攻略
     */
    public static void startMakeMoneyAct() {
        Bundle bundle = new Bundle();
        ActivityUtils.startActivity(bundle, MakeMoneyAct.class);
    }

    /**
     * 登录
     */
    public static void startLoginAct() {
        Bundle bundle = new Bundle();
        ActivityUtils.startActivity(bundle, LoginAct.class);
    }

    /**
     * html
     */
    public static void startHtmlAct(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        ActivityUtils.startActivity(bundle, HtmlAct.class);
    }
    public static void startHtmlAct(int type, String url) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("url", url);
        ActivityUtils.startActivity(bundle, HtmlAct.class);
    }

    /**
     * 找回密码
     */
    public static void startRetrievePwdFrg(BaseFragment root, int type) {
        RetrievePwdFrg frg = new RetrievePwdFrg();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        frg.setArguments(bundle);
        if (type == 0) {
            root.start(frg);
        } else {
            ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
        }
    }

    /**
     * 绑定手机
     */
    public static void startBindPhoneFrg(BaseFragment root) {
        BindPhoneFrg frg = new BindPhoneFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }


    /**
     * 立即提现
     */
    public static void startCashFrg(BaseFragment root) {
        CashFrg frg = new CashFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 二维码收徒
     */
    public static void startZkingFrg(BaseFragment root, String short_url) {
        ZkingFrg frg = new ZkingFrg();
        Bundle bundle = new Bundle();
        bundle.putString("url", short_url);
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     * 活动中心
     */
    public static void startActionCenterAct() {
        ActivityUtils.startActivity(ActionCenterAct.class);
    }

    /**
     * 联系我们
     *
     * @param root
     */
    public static void startContactFrg(BaseFragment root, int type) {
        ContactFrg frg = new ContactFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        if (type == 0) {
            ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
        } else {
            root.start(frg);
        }
    }

    /**
     * 设置支付密码
     */
    public static void startPayPwd(BaseFragment root) {
        PayPwdFrg frg = new PayPwdFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 银行卡列表
     */
    public static void startBankFrg(BaseFragment root) {
        BankFrg frg = new BankFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 添加/更新银行卡
     *
     * @param root
     * @param position
     */
    public static void startAddBnakFrg(BaseFragment root, DataBean bean, int position) {
        AddBankFrg frg = new AddBankFrg();
        Bundle bundle = new Bundle();
        if (bean != null) {
            bundle.putString("id", bean.getBankId() + "");
        }
        bundle.putInt("position", position);
        bundle.putString("bean", new Gson().toJson(bean));
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     * 收徒
     */
    public static void startApprenticeFrg(BaseFragment root) {
        ThreeFrg frg = ThreeFrg.newInstance();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }
}