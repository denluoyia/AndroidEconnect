package com.lzq.econnect.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lzq.econnect.R;
import com.lzq.econnect.base.BaseActivity;
import com.lzq.econnect.common.Configs;
import com.lzq.econnect.utils.FileStorageUtils;
import com.lzq.econnect.utils.LzqFileManagerTool;
import com.lzq.econnect.utils.UIUtil;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 分别为两个按钮入口：
 * 一个是进入手机内部存储
 * 另一个是：共享到电脑上的目录
 */

public class FileManagerActivity extends BaseActivity {

    @Bind(R.id.list_view)
    ListView mListView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private List<Integer> mListIcon = new ArrayList<>();

    @Override
    protected int setContentViewId() {
        return R.layout.activity_file_manager;
    }

    @Override
    protected void doBusiness() {
        initToolbar();
        init();
        FMAdapter mAdapter = new FMAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    openFilePicker();
                }else if (i == 1){
                    openEConnect();
                }
            }
        });
    }


    private void init(){
        mListIcon.clear();
        mListIcon.add(R.drawable.img_local_external_path);
        mListIcon.add(R.drawable.img_econnect_path);
    }

    private void initToolbar(){

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(UIUtil.getString(R.string.file_manager));
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void openFilePicker() {
       startActivity(new Intent(this, FileManagerPickActivity.class));
    }

    private void openEConnect(){
        new LzqFileManagerTool()
                .withRootPath(Configs.SHARED_LOCAL_PATH)
                .withActivity(this)
                .withRequestCode(1)
                .withTitle(UIUtil.getString(R.string.e_connect))
                .start();
    }

    private class FMAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mListIcon.size();
        }

        @Override
        public Object getItem(int i) {
            if (i < 0 || i >= getCount()) return null;
            return mListIcon.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (getItem(i) == null) return null;

            if (view == null){
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_file_list, null);
            }
            ImageView fileImg = (ImageView) view.findViewById(R.id.file_img);
            TextView fileName = (TextView) view.findViewById(R.id.file_name);
            TextView fileDescribe = (TextView) view.findViewById(R.id.file_describe);
            if (i == 0){
                fileImg.setImageResource(R.drawable.img_local_external_path);
                fileName.setText(UIUtil.getString(R.string.inner_storage));
                fileDescribe.setVisibility(View.VISIBLE);
                fileDescribe.setText(FileStorageUtils.getSDCardStorage());
            }else if (i == 1){
                fileImg.setImageResource(R.drawable.img_local_econnect_path);
                fileName.setText(UIUtil.getString(R.string.e_connect));
                fileDescribe.setVisibility(View.GONE);
            }else {
                return null;
            }
            return view;
        }
    }
}
