package com.lzq.econnect.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzq.econnect.R;
import com.lzq.econnect.bean.LeftMenuBean;

import java.util.List;


/**
 * function: 主页的左侧抽屉适配器
 * Created by lzq on 2017/3/25.
 */

public class LeftDrawerAdapter extends BaseAdapter{

    private List<LeftMenuBean> mDataList;
    private Context mContext;

    public LeftDrawerAdapter(Context context, List<LeftMenuBean> dataList){
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return (i >= 0 && i < mDataList.size()) ? mDataList.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_left_drawer,parent, false);
            holder = new ViewHolder();
            holder.leftImage = (ImageView) convertView.findViewById( R.id.left_img);
            holder.menuText = (TextView) convertView.findViewById(R.id.menu_text);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        LeftMenuBean item = (LeftMenuBean) getItem(position);
        holder.leftImage.setImageResource(item.getImageId());
        holder.menuText.setText(item.getMenuText());
        return convertView;
    }

    private static class ViewHolder{
        ImageView leftImage;
        TextView menuText;
    }
}
