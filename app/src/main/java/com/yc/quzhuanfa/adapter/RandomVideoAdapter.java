package com.yc.quzhuanfa.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Date: 2019/10/17
 * Time: 19:48
 */
public class RandomVideoAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public RandomVideoAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        viewHolder.tv_title.setText(bean.getTitle());
        viewHolder.tv_time.setText(TimeUtil.getTimeFormatText(bean.getCreateTime()));
        GlideLoadingUtils.loadRounded(act, CloudApi.SERVLET_VIDEO_URL + bean.getVideo(), viewHolder.iv_img);
        viewHolder.itemView.setOnClickListener(view -> UIHelper.startVideoAct(bean));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_ra_video, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        RoundImageView iv_img;
        AppCompatTextView tv_play_time;
        AppCompatTextView tv_title;
        AppCompatTextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_play_time = itemView.findViewById(R.id.tv_play_time);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }

}
