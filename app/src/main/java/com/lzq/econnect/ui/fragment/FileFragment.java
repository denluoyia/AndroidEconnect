package com.lzq.econnect.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.lzq.econnect.R;
import com.lzq.econnect.base.BaseFragment;
import com.lzq.econnect.ui.adapter.FileListAdapter;
import com.lzq.econnect.utils.FileUtils;
import com.lzq.econnect.view.FileRecyclerView;


import java.io.File;

import butterknife.Bind;

/**
 * function: 加载本地
 * A simple {@link Fragment} subclass.
 */
public class FileFragment extends BaseFragment implements FileListAdapter.OnItemClickListener, FileListAdapter.OnItemLongClickListener{

    @Override
    public void onItemClick(View view, int position) {
        if (fileClickListener != null){
            fileClickListener.onFileClicked(mAdapter.getItem(position));
        }
    }

    @Override
    public boolean onItemLongClick(View view, int position) {
        if (fileClickListener != null){
            fileClickListener.onFileLongClick(mAdapter.getItem(position));
        }
        return true;
    }

    public interface FileClickListener {
        void onFileClicked(File clickedFile);
        void onFileLongClick(File longClickFile);
    }

    @Bind(R.id.no_data_view)
    View mEmptyView;
    @Bind(R.id.file_recycler_view)
    FileRecyclerView mFileRecyclerView;

    private FileListAdapter mAdapter;
    private static final String ARG_FILE_PATH = "arg_file_path"; //获取当前文件目录的key

    String mPath;


    private FileClickListener fileClickListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fileClickListener = (FileClickListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fileClickListener = null;
    }

    public static FileFragment getInstance(String path){
        FileFragment instance = new FileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FILE_PATH, path);
        instance.setArguments(args);
        return instance;
    }

    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_file;
    }

    @Override
    protected void doBusiness() {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments().getString(ARG_FILE_PATH) != null){
            mPath = getArguments().getString(ARG_FILE_PATH);
        }
        mAdapter = new FileListAdapter(FileUtils.getFileListByDirPath(mPath));
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        mFileRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        mFileRecyclerView.setAdapter(mAdapter);
        mFileRecyclerView.setmEmptyView(mEmptyView);

    }


}