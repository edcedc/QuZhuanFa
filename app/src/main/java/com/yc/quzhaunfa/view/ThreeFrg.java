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
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.adapter.ApprenticeAdapter;
import com.yc.quzhaunfa.adapter.HomeChildAdapter;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.databinding.FThreeBinding;
import com.yc.quzhaunfa.impl.ThreeContract;
import com.yc.quzhaunfa.presenter.ThreePresenter;
import com.yc.quzhaunfa.utils.PopupWindowTool;
import com.yc.quzhaunfa.view.bottom.ShareBottomFrg;
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
    private ShareBottomFrg shareBottomFrg;

    public static ThreeFrg newInstance() {
        Bundle args = new Bundle();
        ThreeFrg fragment = new ThreeFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private boolean isRequest = true;

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (isRequest){
            isRequest = true;
            mPresenter.getUserList();
            mPresenter.getTodayYesterdayALl();
        }
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
        view.findViewById(R.id.title_bar).setBackgroundColor(act.getColor(R.color.white));
        view.findViewById(R.id.toolbar).setBackgroundColor(act.getColor(R.color.white));
        TextView top_title = view.findViewById(R.id.top_title);
        top_title.setTextColor(act.getColor(R.color.tab_black));
        ImmersionBar.with(this).statusBarDarkFont(true).init();

        mB.fyInviteFriends.setOnClickListener(this);
        mB.fyMyApprentice.setOnClickListener(this);
        mB.tvInvite.setOnClickListener(this);
        shareBottomFrg = new ShareBottomFrg();

        if (adapter == null) {
            adapter = new ApprenticeAdapter(act, this, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView);
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
            case R.id.tv_invite:
                shareBottomFrg.show(getChildFragmentManager(), "dialog");
                break;
        }
    }

    @Override
    public void setData(List<DataBean> list) {
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
        mB.tvApprentice.setText(listBean.size() + "");
    }

    @Override
    public void onTodayYesterdayALl(DataBean bean) {
        mB.tvContribution.setText(bean.getNow() + "");
        mB.tvYesterdayContribution.setText(bean.getYesDay() + "");
        mB.tvIncome.setText(Html.fromHtml("好友共计帮我赚了<font color='#FF3A22'><big>" + bean.getAll() +
                "</big></font>"+ "元"));
    }
}
