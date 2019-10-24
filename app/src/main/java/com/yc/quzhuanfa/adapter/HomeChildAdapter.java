package com.yc.quzhuanfa.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseRecyclerviewAdapter;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.utils.GlideLoadingUtils;
import com.yc.quzhuanfa.view.act.HtmlAct;
import com.yc.quzhuanfa.weight.RoundImageView;

import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 11:45
 */
public class HomeChildAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public HomeChildAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    private final int i_article = 0;
    private final int i_ad = 1;

    @Override
    public int getItemViewType(int position) {
        DataBean bean = listBean.get(position);
        return i_article;
    }


    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        final DataBean bean = listBean.get(position);
        if (holder instanceof ViewHolder){
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tv_title.setText(bean.getTitle());
            viewHolder.tv_price.setText(bean.getPrice() +
                    "分/阅读");
            viewHolder.tv_read.setText(bean.getAdTitle());
            viewHolder.tv_read.setVisibility(bean.getAdType() <= 1 ? View.GONE : View.VISIBLE);
            GlideLoadingUtils.loadFirstFrame(act, bean.getPic(), viewHolder.iv_img);
            viewHolder.iv_delete.setOnClickListener(view -> {

            });
            bean.setPosition(position);
            viewHolder.itemView.setOnClickListener(view -> UIHelper.startDetailsAct(bean));
            viewHolder.tv_read.setOnClickListener(view -> {
                if (bean.getAdType() == 1){
                    UIHelper.startHtmlAct(HtmlAct.ADDETAILS, bean.getAdContent());
                }else {
                    LogUtils.e(bean.getAdContent());
                    UIHelper.startHtmlAct(HtmlAct.ADDETAILSURL, bean.getAdContent());
                }
            });
        }else if (holder instanceof AdvHolder){
            AdvHolder viewHolder = (AdvHolder) holder;
            viewHolder.tv_title.setText(bean.getAdTitle());
            RoundViewDelegate delegate = viewHolder.tv_price.getDelegate();
            delegate.setStrokeColor(act.getResources().getColor(R.color.tab_gray));
            viewHolder.tv_title.setTextColor(act.getResources().getColor(R.color.tab_gray));
            GlideLoadingUtils.load(act, bean.getPic(), viewHolder.iv_img);
        }
    }



    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
       if (viewType == i_article){
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_home, parent, false));
        }else {
            return new AdvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_home, parent, false));
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_title;
        RoundTextView tv_read;
        RoundImageView iv_img;
        RoundTextView tv_price;
        AppCompatImageView iv_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_read = itemView.findViewById(R.id.tv_read);
            iv_delete = itemView.findViewById(R.id.iv_delete);
        }
    }

    class AdvHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_title, tv_read;
        RoundImageView iv_img;
        RoundTextView tv_price;
        AppCompatImageView iv_delete;

        public AdvHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_read = itemView.findViewById(R.id.tv_read);
            iv_delete = itemView.findViewById(R.id.iv_delete);
        }
    }

}
