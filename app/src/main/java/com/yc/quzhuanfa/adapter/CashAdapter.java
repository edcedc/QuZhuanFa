package com.yc.quzhuanfa.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseListViewAdapter;
import com.yc.quzhuanfa.bean.DataBean;

import java.util.List;

public class CashAdapter extends BaseListViewAdapter<Integer> {

    public CashAdapter(Context act, List<Integer> listBean) {
        super(act, listBean);
    }

    private int position = -1;

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    protected View getCreateVieww(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_cash, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_text.setText(listBean.get(position) + "");
        RoundViewDelegate delegate = viewHolder.tv_text.getDelegate();
        if (this.position == position){
            viewHolder.iv_img.setVisibility(View.VISIBLE);
            delegate.setStrokeColor(act.getColor(R.color.red_FF7D78));
            viewHolder.tv_text.setTextColor(act.getColor(R.color.red_FF7D78));
        }else {
            viewHolder.iv_img.setVisibility(View.GONE);
            delegate.setStrokeColor(act.getColor(R.color.tab_gray));
            viewHolder.tv_text.setTextColor(act.getColor(R.color.tab_gray));
        }
        return convertView;
    }


    class ViewHolder{

        RoundTextView tv_text;
        AppCompatImageView iv_img;

        public ViewHolder(View convertView) {
            tv_text = convertView.findViewById(R.id.tv_text);
            iv_img = convertView.findViewById(R.id.iv_img);
        }
    }

}
