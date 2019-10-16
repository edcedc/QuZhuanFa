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
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseRecyclerviewAdapter;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.utils.GlideLoadingUtils;
import com.yc.quzhuanfa.utils.TimeUtil;
import com.yc.quzhuanfa.weight.RoundImageView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/10
 * Time: 18:46
 */
public class FiveChildAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public FiveChildAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    private final int i_video = 0;
    private final int i_ad = 1;

    @Override
    public int getItemViewType(int position) {
        DataBean bean = listBean.get(position);
        if (StringUtils.isEmpty(bean.getAdTitle())){
            return i_video;
        }else {
            return i_ad;
        }
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        final DataBean bean = listBean.get(position);
        if (holder instanceof ViewHolder){
            ViewHolder viewHolder = (ViewHolder) holder;
            GlideLoadingUtils.loadRounded(act, CloudApi.SERVLET_IMG_URL + bean.getArticleId(), viewHolder.iv_img);
            viewHolder.tv_content.setText(bean.getTitle());
            viewHolder.tv_time.setText(TimeUtil.getTimeFormatText(bean.getCreateTime()));
            viewHolder.tv_collect.setCompoundDrawablesWithIntrinsicBounds(null,
                    null, act.getResources().getDrawable(bean.getIsTrue() == 0 ? R.mipmap.weidianzan1 : R.mipmap.dianzan1, null), null);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UIHelper.startVideoAct(bean);
                }
            });
        }else if (holder instanceof AdvHolder){
            AdvHolder viewHolder = (AdvHolder) holder;
            GlideLoadingUtils.loadRounded(act, CloudApi.SERVLET_IMG_URL + bean.getAdAttachId(), viewHolder.iv_img);
            viewHolder.tv_content.setText(bean.getAdTitle() + "：" + bean.getAdContent());
        }
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        if (viewType == i_video){
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_video, parent, false));
        }else {
            return new AdvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_video_adv, parent, false));
        }
    }

    class AdvHolder extends RecyclerView.ViewHolder{

        RoundImageView iv_img;
        AppCompatTextView tv_content;

        public AdvHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView iv_img;
        AppCompatTextView tv_play_time;
        AppCompatTextView tv_comment;
        AppCompatTextView tv_content;
        AppCompatTextView tv_collect;
        AppCompatTextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_play_time = itemView.findViewById(R.id.tv_play_time);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_collect = itemView.findViewById(R.id.tv_collect);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }

}
