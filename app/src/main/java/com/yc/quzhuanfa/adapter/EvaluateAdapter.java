package com.yc.quzhuanfa.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseRecyclerviewAdapter;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.utils.GlideLoadingUtils;
import com.yc.quzhuanfa.utils.TimeUtil;
import com.yc.quzhuanfa.weight.RoundImageView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/21
 * Time: 18:15
 */
public class EvaluateAdapter extends BaseRecyclerviewAdapter<DataBean> {
    public EvaluateAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        int cType = bean.getcType();
        if (cType != -1){
            if (bean.getcType() == 1){
                GlideLoadingUtils.load(act, bean.getPic(), viewHolder.iv_img);
                viewHolder.iv_play.setVisibility(View.GONE);
            }else {
                GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + bean.getPic(), viewHolder.iv_img);
                viewHolder.iv_play.setVisibility(View.VISIBLE);
            }
        }else {
            if (bean.getType() == 1){
                GlideLoadingUtils.load(act, bean.getPic(), viewHolder.iv_img);
                viewHolder.iv_play.setVisibility(View.GONE);
            }else {
                GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + bean.getPic(), viewHolder.iv_img);
                viewHolder.iv_play.setVisibility(View.VISIBLE);
            }
        }

        viewHolder.tv_title.setText(bean.getTitle());
        viewHolder.tv_time.setText(TimeUtil.getTimeFormatText(bean.getCreateTime()));
        viewHolder.itemView.setOnClickListener(view -> {});
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_evall, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        RoundImageView iv_img;
        AppCompatImageView iv_play;
        AppCompatTextView tv_title;
        AppCompatTextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            iv_play = itemView.findViewById(R.id.iv_play);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }

}
