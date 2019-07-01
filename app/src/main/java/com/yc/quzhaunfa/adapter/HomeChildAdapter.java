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
 * Date: 2019/6/16
 * Time: 11:45
 */
public class HomeChildAdapter extends BaseRecyclerviewAdapter<DataBean> {
    public HomeChildAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final DataBean bean = listBean.get(position);

        viewHolder.tv_title.setText(bean.getTitle());
        viewHolder.tv_price.setText(bean.getPrice() +
                "分/阅读");
        viewHolder.tv_read.setText("阅读：" +
                bean.getForwardNum());

        GlideLoadingUtils.load(act, bean.getPic(), viewHolder.iv_img);

        viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.startDetailsAct(bean.getClassId());
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_home, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_title, tv_read;
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

}
