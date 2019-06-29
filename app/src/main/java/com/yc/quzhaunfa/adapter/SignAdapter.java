package com.yc.quzhaunfa.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.StringUtils;
import com.flyco.roundview.RoundTextView;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.base.BaseRecyclerviewAdapter;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.controller.CloudApi;
import com.yc.quzhaunfa.controller.UIHelper;
import com.yc.quzhaunfa.utils.GlideLoadingUtils;
import com.yc.quzhaunfa.weight.RoundImageView;

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
        DataBean bean = listBean.get(position);
        viewHolder.tv_title.setText(bean.getTitle());
        viewHolder.tv_price.setText(bean.getPrice() +
                "分/阅读");
        String pic = bean.getPic();
        if (!StringUtils.isEmpty(pic)){
            GlideLoadingUtils.load(act, pic, viewHolder.iv_img);
        }else {
            GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + bean.getHead(), viewHolder.iv_img, true);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
