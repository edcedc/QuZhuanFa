package com.yc.quzhaunfa.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yc.quzhaunfa.R;
import com.yc.quzhaunfa.base.BaseFragment;
import com.yc.quzhaunfa.base.BaseRecyclerviewAdapter;
import com.yc.quzhaunfa.bean.DataBean;
import com.yc.quzhaunfa.controller.UIHelper;
import com.yc.quzhaunfa.event.CashBankInEvent;
import com.yc.quzhaunfa.view.BankFrg;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/21
 * Time: 18:24
 */
public class BankAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public BankAdapter(Context act, BaseFragment root, List<DataBean> listBean) {
        super(act, root, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final DataBean bean = listBean.get(position);

        viewHolder.tv_bank.setText("开户银行：" +
                bean.getBankName());
        String bankNum = bean.getBankNum();
        viewHolder.tv_bank_num.setText("银行账号：" +
                "**** **** **** " + bankNum.substring(bankNum.length() - 4, bankNum.length()));
        viewHolder.tv_name.setText("开户人：" +
                bean.getName());

        viewHolder.fy_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.startAddBnakFrg(root, bean, position);
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new CashBankInEvent(bean));
                root.pop();
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_bank, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_bank;
        AppCompatTextView tv_bank_num;
        AppCompatTextView tv_name;
        View fy_edit;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_bank = itemView.findViewById(R.id.tv_bank);
            tv_bank_num = itemView.findViewById(R.id.tv_bank_num);
            tv_name = itemView.findViewById(R.id.tv_name);
            fy_edit = itemView.findViewById(R.id.fy_edit);
        }
    }

}
