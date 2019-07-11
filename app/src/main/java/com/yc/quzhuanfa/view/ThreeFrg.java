package com.yc.quzhuanfa.view;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.adapter.ApprenticeAdapter;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.base.User;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.databinding.FThreeBinding;
import com.yc.quzhuanfa.impl.ThreeContract;
import com.yc.quzhuanfa.presenter.ThreePresenter;
import com.yc.quzhuanfa.utils.ShareTool;
import com.yc.quzhuanfa.view.bottom.ShareBottomFrg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 1:11
 * 收徒
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
        if (isRequest) {
            isRequest = false;
            mB.refreshLayout.startRefresh();
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
        mB.tvInvitation.setOnClickListener(this);
        mB.ivWx.setOnClickListener(this);
        mB.ivWxq.setOnClickListener(this);
        mB.ivZking.setOnClickListener(this);
        shareBottomFrg = new ShareBottomFrg();
        shareBottomFrg.setBundle(this);

        if (adapter == null) {
            adapter = new ApprenticeAdapter(act, this, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView);
        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.getUserList();
                mPresenter.getTodayYesterdayALl();
                mB.refreshLayout.finishRefreshing();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.tv_invitation:
                UIHelper.startMakeMoneyAct();
                break;
            case R.id.iv_wx:
                ShareTool.getInstance(act).shareAppointAction(SHARE_MEDIA.WEIXIN, CloudApi.REGISTER_URL);
                break;
            case R.id.iv_wxq:
                ShareTool.getInstance(act).shareAppointAction(SHARE_MEDIA.WEIXIN_CIRCLE, CloudApi.REGISTER_URL);
                break;
            case R.id.iv_zking:
                UIHelper.startZkingFrg(this);
                break;
        }
    }

    @Override
    public void setData(List<DataBean> list) {
        listBean.clear();
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
        mB.tvApprentice.setText(listBean.size() + "");
    }

    @Override
    public void onTodayYesterdayALl(DataBean bean) {
        mB.tvContribution.setText(bean.getNow() + "");
        mB.tvYesterdayContribution.setText(bean.getYesDay() + "");
        mB.tvIncome.setText(Html.fromHtml("好友共计帮我赚了<font color='#FF3A22'><big>" + bean.getAll() +
                "</big></font>" + "元"));
    }
}
