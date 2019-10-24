package com.yc.quzhuanfa.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.blankj.utilcode.util.StringUtils;
import com.flyco.roundview.RoundTextView;
import com.luck.picture.lib.entity.LocalMedia;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.base.BaseFragment;
import com.yc.quzhuanfa.base.BaseRecyclerviewAdapter;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.controller.CloudApi;
import com.yc.quzhuanfa.controller.UIHelper;
import com.yc.quzhuanfa.utils.GlideLoadingUtils;
import com.yc.quzhuanfa.utils.TimeUtil;
import com.yc.quzhuanfa.weight.CircleImageView;
import com.yc.quzhuanfa.weight.NineGridTestLayout;
import com.yc.quzhuanfa.weight.PictureSelectorTool;
import com.yc.quzhuanfa.weight.WithScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/15
 * Time: 11:31
 */
public class PyqAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public PyqAdapter(Context act, BaseFragment root, List<DataBean> listBean) {
        super(act, root, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        GlideLoadingUtils.load(act, bean.getHead(), viewHolder.iv_head);
        viewHolder.tv_name.setText(bean.getUserNickName());
        String context = bean.getContext();
        viewHolder.tv_content.setText(context);
        viewHolder.tv_content.setVisibility(StringUtils.isEmpty(context) == true ? View.INVISIBLE : View.VISIBLE);
        List<DataBean> friendImgs = bean.getFriendImgs();
        viewHolder.tv_time.setText(TimeUtil.getTimeFormatText(bean.getCreateTime()));
        String video = bean.getVideo();
        if (friendImgs != null && friendImgs.size() > 1){
            viewHolder.layout_nine_grid.setVisibility(View.VISIBLE);
            viewHolder.layout_nine_grid.setIsShowAll(true);
            List<String> list = new ArrayList<>();
            for (DataBean image : friendImgs) {
                list.add(CloudApi.SERVLET_IMG_URL + image.getAttachId());
            }
            viewHolder.iv_play.setVisibility(View.GONE);
            viewHolder.layout_nine_grid.setUrlList(list);
        }else if (friendImgs != null && friendImgs.size() == 1){
            viewHolder.layout_nine_grid.setVisibility(View.GONE);
            viewHolder.iv_play.setVisibility(View.GONE);
            viewHolder.iv_img.setVisibility(View.VISIBLE);
            String url = CloudApi.SERVLET_IMG_URL + friendImgs.get(0).getAttachId();
            GlideLoadingUtils.loadMeasuring(act, url, viewHolder.iv_img);
            List<LocalMedia> list = new ArrayList<>();
            LocalMedia media = new LocalMedia();
            media.setPath(url);
            list.add(media);
            viewHolder.iv_img.setOnClickListener(view -> PictureSelectorTool.PictureMediaType((Activity) act, list, 0));
        }else if (!StringUtils.isEmpty(video)){
            viewHolder.layout_nine_grid.setVisibility(View.GONE);
            GlideLoadingUtils.loadMeasuring(act, CloudApi.SERVLET_IMG_URL + bean.getAttachId(), viewHolder.iv_img);
            viewHolder.iv_play.setVisibility(View.VISIBLE);
            viewHolder.iv_img.setVisibility(View.VISIBLE);
            viewHolder.iv_img.setOnClickListener(view -> UIHelper.startPlayVideoAct(CloudApi.SERVLET_VIDEO_URL + bean.getVideo()));
        }else {
            viewHolder.layout_nine_grid.setVisibility(View.GONE);
            viewHolder.iv_play.setVisibility(View.GONE);
        }

        List<DataBean> disList = bean.getDisList();
        if (disList != null && disList.size() != 0){
            viewHolder.recyclerView.setVisibility(View.VISIBLE);
            final CommentChildAdapter adapter = new CommentChildAdapter(act, disList);
            viewHolder.recyclerView.setAdapter(adapter);
            viewHolder.recyclerView.setOnItemClickListener((adapterView, view, i, l) -> {
                DataBean dataBean = disList.get(i);
                listener.OnTwoClick(position, i, bean.getFriendId(), dataBean.getUserId());
            });
            viewHolder.tv_lock.setOnClickListener(view -> {
                if (!adapter.isLock()){
                    adapter.setLock(true);
                    viewHolder.tv_lock.setText("收起回复>");
                }else {
                    adapter.setLock(false);
                    viewHolder.tv_lock.setText("展开" +
                            (disList.size() - CommentChildAdapter.lockNum) +
                            "条回复>");
                }
                adapter.notifyDataSetChanged();
            });
            if (disList.size() > CommentChildAdapter.lockNum){
                viewHolder.tv_lock.setVisibility(View.VISIBLE);
                viewHolder.tv_lock.setText("展开" +
                        (disList.size() - CommentChildAdapter.lockNum) +
                        "条回复>");
            }else {
                viewHolder.tv_lock.setVisibility(View.GONE);
                viewHolder.tv_lock.setText("收起回复>");
            }
        }else {
            viewHolder.recyclerView.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(view -> {
//            UIHelper.startCommentDescFrg(root, bean);

        });
        viewHolder.iv_comment.setOnClickListener(view -> {listener.OnClick(position, bean.getFriendId());});
    }

    private OnClickListener listener;
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }
    public interface OnClickListener{
        void OnClick(int position, String friendId);
        void OnTwoClick(int i, int position, String friendId, String puserId);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_pyq, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView iv_head;
        AppCompatTextView tv_name;
        AppCompatTextView tv_content;
        NineGridTestLayout layout_nine_grid;
        RoundTextView tv_adv;
        AppCompatTextView tv_address;
        AppCompatTextView tv_time;
        AppCompatTextView tv_lock;
        AppCompatImageView iv_comment;
        AppCompatImageView iv_play;
        WithScrollListView recyclerView;
        AppCompatImageView iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_head = itemView.findViewById(R.id.iv_head);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_content = itemView.findViewById(R.id.tv_content);
            layout_nine_grid = itemView.findViewById(R.id.layout_nine_grid);
            tv_adv = itemView.findViewById(R.id.tv_adv);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_time = itemView.findViewById(R.id.tv_time);
            iv_comment = itemView.findViewById(R.id.iv_comment);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            iv_play = itemView.findViewById(R.id.iv_play);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_lock = itemView.findViewById(R.id.tv_look);
        }
    }



}
