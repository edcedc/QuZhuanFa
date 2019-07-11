package com.yc.quzhuanfa.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.roundview.RoundTextView;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseRecyclerviewAdapter;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.utils.GlideLoadingUtils;
import com.yc.quzhuanfa.weight.RoundImageView;

import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/20
 * Time: 23:40
 */
public class SignAdapter extends BaseRecyclerviewAdapter<DataBean> {
    public SignAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final DataBean bean = listBean.get(position);
        viewHolder.tv_title.setText(bean.getTitle());
        viewHolder.tv_price.setText(bean.getPrice() +
                "分/阅读");
        GlideLoadingUtils.load(act, bean.getPic(), viewHolder.iv_img);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.startDetailsAct(bean.getArticleId(), bean.getType(), bean.getTitle(), bean.getPic(), bean.getClassName());
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_sign, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_title;
        RoundImageView iv_img;
        RoundTextView tv_price;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_price = itemView.findViewById(R.id.tv_price);
        }
    }

}
