package com.lzq.econnect.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzq.econnect.R;
import com.lzq.econnect.base.BaseItemViewHolder;
import com.lzq.econnect.bean.QABean;
import com.lzq.econnect.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * function: Question and answer 帮助中心的数据适配器， 最好使用json格式的数据进行显示
 * Created by lzq on 2017/4/10.
 */

public class QAAdapter extends RecyclerView.Adapter<QAAdapter.ItemViewHolder>{
    private List<QABean> mDataList = new ArrayList<>();

    public QAAdapter(List<QABean> dataList){
        if (dataList == null || dataList.size() == 0) return;
        mDataList.clear();
        mDataList.addAll(dataList);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_qa, null));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        QABean item = getItem(position);
        if (item == null) return;
        if (position == 0){
            holder.topDiver.setVisibility(View.VISIBLE);
        }else{
            holder.topDiver.setVisibility(View.GONE);
        }
        holder.tvNum.setText(String.valueOf(position+1));
        switch (position % 4){
            case 0:
                holder.tvNum.setBackground(UIUtil.getDrawable(R.drawable.bg_tv_num_qa_green));
                break;
            case 1:
                holder.tvNum.setBackground(UIUtil.getDrawable(R.drawable.bg_tv_num_qa_red));
                break;
            case 2:
                holder.tvNum.setBackground(UIUtil.getDrawable(R.drawable.bg_tv_num_qa_blue));
                break;
            case 3:
                holder.tvNum.setBackground(UIUtil.getDrawable(R.drawable.bg_tv_num_qa_orange));
                break;
            default:
                break;

        }

        holder.tvTitle.setText(item.getTitle());
        holder.tvContent.setText(item.getContent());
        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                listener.onItemClick(view, pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    private QABean getItem(int position){
        if (position < 0 || position >= getItemCount()) return null;
        return mDataList.get(position);
    }

    static class ItemViewHolder extends BaseItemViewHolder{
        View topDiver;
        TextView tvNum;
        TextView tvTitle;
        TextView tvContent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            topDiver = itemView.findViewById(R.id.top_diver);
            tvNum = (TextView) itemView.findViewById(R.id.tv_num);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_qa_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content_qa);
        }
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    //实现一个监听点击事件的接口
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

}
