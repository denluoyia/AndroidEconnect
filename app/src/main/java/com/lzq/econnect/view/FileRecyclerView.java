package com.lzq.econnect.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * function: 文件列表RecyclerView
 * Created by lzq on 2017/4/11.
 */

public class FileRecyclerView extends RecyclerView{

    private View mEmptyView;

    public FileRecyclerView(Context context) {
        super(context);
    }

    public FileRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FileRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void checkEmpty(){
        if (mEmptyView != null){
            mEmptyView.setVisibility(getAdapter().getItemCount() > 0 ? GONE : VISIBLE);
        }
    }

    private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkEmpty();
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        Adapter old = getAdapter();
        if (old != null){
            old.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null){
            adapter.registerAdapterDataObserver(observer);
        }
    }

    public void setmEmptyView(View emptyView){
        this.mEmptyView = emptyView;
        checkEmpty();
    }


}
