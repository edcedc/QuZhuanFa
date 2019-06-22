package com.yc.quzhaunfa.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.roundview.RoundTextView;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.base.BaseRecyclerviewAdapter;
import com.yc.quzhaunfa.bean.DataBean;
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
        viewHolder.tv_title.setText("夏天出汗正常主要了！这些部位 爱出汗，是...");
        viewHolder.tv_price.setText("8分" +
                "/阅读");
        viewHolder.tv_read.setText("阅读：" +
                "1029");
        GlideLoadingUtils.load(act, "http://wx1.sinaimg.cn/mw600/0076BSS5ly1g42uyoi7uej30iz0sg77s.jpg", viewHolder.iv_img);

        viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.startDetailsAct("");
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
