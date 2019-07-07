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

import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 11:14
 */
public class IncomeAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public IncomeAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        String str = null;
        switch (bean.getType()){
            case 1:
                str = "每日签到";
                break;
            case 2:
                str = "首次分享";
                break;
            case 4:
                str = "首次登录";
                break;
            case 8:
                str = "绑定手机";
                break;
            case 16:
                str = "文章被阅读";
                break;
            case 32:
                str = "下级文章被阅读";
                break;
            case 64:
                str = "下下级文章被阅读";
                break;
            case 128:
                str = "首次收徒";
                break;
        }
        viewHolder.tv_title.setText(str);
        viewHolder.tv_time.setText(bean.getCreateTime());
        viewHolder.tv_state.setText((bean.getBalanceType() == 1 ? "+" : "-") + bean.getBalance());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_income, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_title, tv_state, tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_state = itemView.findViewById(R.id.tv_state);
            tv_time = itemView.findViewById(R.id.tv_time);

        }
    }

}