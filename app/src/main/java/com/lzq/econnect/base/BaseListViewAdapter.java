package com.lzq.econnect.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by lzq on 2017/3/25.
 */
@SuppressWarnings("ALL")
public abstract class BaseListViewAdapter<T> extends BaseAdapter{
    protected BaseActivity mActivity;
    protected List<T> mDataList = new ArrayList<>();

    public BaseListViewAdapter(BaseActivity activity){
        this.mActivity = activity;
        mDataList.clear();
    }

    /**加载数据*/
    public void refreshDataList(List<T> dataList){
        if (dataList == null || dataList.size() == 0) return;
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    /**设置资源布局*/
    protected abstract int iSetLayoutResId();

    /**设置视图数据及其他操作 */
    protected abstract void iBindViewData(View convertView, int position);

    /**
     * 条目点击监听
     * @param view
     * @param position
     */
    protected abstract void onItemClickListener(View view, int position);

    /**
     * 条目长按监听
     * @param view
     * @param position
     * @return
     */
    protected abstract boolean onItemLongClickListener(View view, int position);

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public T getItem(int i) {
        return ((i < mDataList.size() && i >= 0) ? mDataList.get(i) : null);
    }

    /**移除指定位置的条目*/
    public T removeItem(int position){
        T t = null;
        if (position < mDataList.size() && position >= 0){
            t = mDataList.get(position);
            mDataList.remove(t);
        }
        return t;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(this.iSetLayoutResId(), parent, false);
        }
        if (getItem(position) == null) return convertView;
        convertView.setTag(convertView.getId(), position);
        convertView.setOnClickListener(mInnerItemOnclickListener);
        convertView.setOnLongClickListener(mInnerItemOnLongClickListener);
        this.iBindViewData(convertView, position);
        return convertView;
    }


    private View.OnClickListener mInnerItemOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag(view.getId());
            if (getItem(position) == null) return;
            onItemClickListener(view, position);
        }
    };

    private View.OnLongClickListener mInnerItemOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            int position = (int) view.getTag(view.getId());
            return getItem(position) == null || onItemLongClickListener(view, position);
        }
    };
}
