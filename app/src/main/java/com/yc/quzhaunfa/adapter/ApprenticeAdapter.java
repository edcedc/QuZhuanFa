package com.yc.quzhaunfa.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.StringUtils;
import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.base.BaseRecyclerviewAdapter;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.controller.CloudApi;
import com.yc.quzhaunfa.controller.UIHelper;
import com.yc.quzhaunfa.utils.GlideLoadingUtils;
import com.yc.quzhaunfa.weight.CircleImageView;

import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/21
 * Time: 21:29
 */
public class ApprenticeAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public ApprenticeAdapter(Context act, BaseFragment root, List<DataBean> listBean) {
        super(act, root, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final DataBean bean = listBean.get(position);

        GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + bean.getHead(), viewHolder.iv_head, true);

        viewHolder.tv_id.setText("ID：" +
                bean.getUserName());
        viewHolder.tv_income.setText("+" +
                bean.getBalance());
        viewHolder.tv_time.setText("注册时间：" +
                bean.getCreateTime());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.startApprenticeDescFrg(root, bean);
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_appren, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_id;
        AppCompatTextView tv_income;
        AppCompatTextView tv_time;
        CircleImageView iv_head;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_income = itemView.findViewById(R.id.tv_income);
            tv_time = itemView.findViewById(R.id.tv_time);
            iv_head = itemView.findViewById(R.id.iv_head);
        }
    }

}
