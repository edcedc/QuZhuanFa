package com.yc.quzhaunfa.view;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.base.BasePresenter;
import com.yc.quzhaunfa.databinding.FContactBinding;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/19
 * Time: 18:23
 *  联系我们
 */
public class ContactFrg extends BaseFragment<BasePresenter, FContactBinding> {
    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_contact;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.contact));
        mB.tvQq.setText("商务合作QQ：" +
                "497107373 ");
        String str="工作时间：<font color='#FF0000'><small>周一至周五10:00~18:00</small></font>";
        mB.tvTime.setTextSize(15);
        mB.tvTime.setText(Html.fromHtml(str));
        mB.tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 得到剪贴板管理器
                ClipboardManager cmb = (ClipboardManager) act.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(mB.tvWxNum.getText().toString());
                showToast("复制成功");
            }
        });

    }
}
