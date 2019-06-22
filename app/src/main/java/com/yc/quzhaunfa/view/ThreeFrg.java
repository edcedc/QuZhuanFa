package com.yc.quzhaunfa.view;

import android.content.ComponentName;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;

import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.adapter.ApprenticeAdapter;
import com.yc.quzhaunfa.adapter.HomeChildAdapter;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.databinding.FThreeBinding;
import com.yc.quzhaunfa.impl.ThreeContract;
import com.yc.quzhaunfa.presenter.ThreePresenter;
import com.yc.quzhaunfa.weight.LinearDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 1:11
 *  收徒
 */
public class ThreeFrg extends BaseFragment<ThreePresenter, FThreeBinding> implements ThreeContract.View, View.OnClickListener {

    private List<DataBean> listBean = new ArrayList<>();
    private ApprenticeAdapter adapter;

    public static ThreeFrg newInstance() {
        Bundle args = new Bundle();
        ThreeFrg fragment = new ThreeFrg();
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
        return R.layout.f_three;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.apprentice), false);
        mB.fyInviteFriends.setOnClickListener(this);
        mB.fyMyApprentice.setOnClickListener(this);

        mB.tvIncome.setText(Html.fromHtml("好友共计帮我赚了<font color='#FF3A22'><big>" + "188" +
                "</big></font>"+ "元"));

        listBean.add(new DataBean());
        listBean.add(new DataBean());
        listBean.add(new DataBean());
        if (adapter == null) {
            adapter = new ApprenticeAdapter(act, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView);

        mB.tvApprentice.setText("3");
        mB.tvContribution.setText("0.06");
        mB.tvYesterdayContribution.setText("0.06");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fy_invite_friends:
                mB.tvInviteFriends.setCompoundDrawablesWithIntrinsicBounds(act.getDrawable(R.mipmap.yqhy01),
                        null, null, null);
                mB.tvMyApprentice.setCompoundDrawablesWithIntrinsicBounds(act.getDrawable(R.mipmap.y4),
                        null, null, null);
                mB.lyFriends.setVisibility(View.VISIBLE);
                mB.lyApprentice.setVisibility(View.GONE);
                mB.fyInviteFriends.setBackgroundResource(0);
                mB.fyMyApprentice.setBackgroundResource(R.mipmap.hng01);
                break;
            case R.id.fy_my_apprentice:
                mB.tvInviteFriends.setCompoundDrawablesWithIntrinsicBounds(act.getDrawable(R.mipmap.hy20),
                        null, null, null);
                mB.tvMyApprentice.setCompoundDrawablesWithIntrinsicBounds(act.getDrawable(R.mipmap.td20),
                        null, null, null);
                mB.lyFriends.setVisibility(View.GONE);
                mB.lyApprentice.setVisibility(View.VISIBLE);
                mB.fyInviteFriends.setBackgroundResource(R.mipmap.qq01);
                mB.fyMyApprentice.setBackgroundResource(0);
                break;
        }
    }
}
