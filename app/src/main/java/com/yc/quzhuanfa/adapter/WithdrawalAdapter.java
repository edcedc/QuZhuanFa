package com.yc.quzhuanfa.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
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
public class WithdrawalAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public WithdrawalAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        String str = null;
        switch (bean.getCashType()){
            case 1:
                str = "提现中";
                break;
            case 2:
                str = "提现成功";
                break;
            case 4:
                str = "提现失败";
                break;
        }
        viewHolder.tv_title.setText(str);
        viewHolder.tv_time.setText(bean.getCreateTime());
        viewHolder.tv_state.setText(bean.getBalance() + "");
        String reason = bean.getReason();
        if (StringUtils.isEmpty(reason)){
            viewHolder.tv_reason.setVisibility(View.GONE);
        }else {
            viewHolder.tv_reason.setVisibility(View.VISIBLE);
            viewHolder.tv_reason.setText("审核原因：" + reason);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_withdrawal, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_title, tv_state, tv_time, tv_reason;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_state = itemView.findViewById(R.id.tv_state);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_reason = itemView.findViewById(R.id.tv_reason);
        }
    }

}
