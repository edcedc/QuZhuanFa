package com.yc.quzhaunfa.controller;

import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.yc.quzhaunfa.MainActivity;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.view.AddBankFrg;
import com.yc.quzhaunfa.view.BankFrg;
import com.yc.quzhaunfa.view.CashFrg;
import com.yc.quzhaunfa.view.ContactFrg;
import com.yc.quzhaunfa.view.IncomeFrg;
import com.yc.quzhaunfa.view.MainFrg;
import com.yc.quzhaunfa.view.PayPwdFrg;
import com.yc.quzhaunfa.view.RetrievePwdFrg;
import com.yc.quzhaunfa.view.SetFrg;
import com.yc.quzhaunfa.view.act.DetailsAct;
import com.yc.quzhaunfa.view.act.HtmlAct;
import com.yc.quzhaunfa.view.act.LoginAct;
import com.yc.quzhaunfa.view.act.MakeMoneyAct;


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
     *  收入明细
     */
    public static void startIncomeFrg(BaseFragment root){
        IncomeFrg frg = new IncomeFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     *  设置
     */
    public static void startSetFrg(BaseFragment root){
        SetFrg frg = new SetFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     *  文章详情
     */
    public static void startDetailsAct(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        ActivityUtils.startActivity(bundle, DetailsAct.class);
    }

    /**
     *  赚钱攻略
     */
    public static void startMakeMoneyAct() {
        Bundle bundle = new Bundle();
        ActivityUtils.startActivity(bundle, MakeMoneyAct.class);
    }

    /**
     *  登录
     */
    public static void startLoginAct() {
        Bundle bundle = new Bundle();
        ActivityUtils.startActivity(bundle, LoginAct.class);
    }

    /**
     *  html
     */
    public static void startHtmlAct(int type){
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        ActivityUtils.startActivity(bundle, HtmlAct.class);
    }

    /**
     *  找回密码
     */
    public static void startRetrievePwdFrg(BaseFragment root){
        RetrievePwdFrg frg = new RetrievePwdFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  绑定手机
     */
    public static void startBindPhoneFrg(){

    }


    /**
     *  立即提现
     */
    public static void startCashFrg(BaseFragment root){
        CashFrg frg = new CashFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
    }

    /**
     *  联系我们
     * @param root
     */
    public static void startContactFrg(BaseFragment root, int type) {
        ContactFrg frg = new ContactFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        if (type == 0){
            ((MainFrg) root.getParentFragment()).startBrotherFragment(frg);
        }else {
            root.start(frg);
        }
    }

    /**
     *  设置支付密码
     */
    public static void startPayPwd(BaseFragment root){
        PayPwdFrg frg = new PayPwdFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  银行卡列表
     */
    public static void startBankFrg(BaseFragment root){
        BankFrg frg = new BankFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }

    /**
     *  添加银行卡
     * @param root
     */
    public static void startAddBnakFrg(BaseFragment root) {
        AddBankFrg frg = new AddBankFrg();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        root.start(frg);
    }



}