package com.lzq.econnect.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import com.lzq.econnect.ui.activity.FileManagerPickActivity;

/**
 * function: 封装文件管理的工具类
 * Created by lzq on 2017/4/15.
 */
@SuppressWarnings("unused")
public class LzqFileManagerTool {
    private Activity mActivity;
    private android.support.v4.app.Fragment mSupportFragment;
    private Fragment mFragment;
    private int mRequestCode;
    private String mRootPath;
    private String mCurrentPath;
    private String mTitle;

    public LzqFileManagerTool(){}

    public LzqFileManagerTool withActivity(Activity activity){
        if (mFragment != null || mSupportFragment != null){
            throw new RuntimeException("you must start only by either activity or fragment, or supportFragment");
        }
        this.mActivity = activity;
        return this;
    }

    public LzqFileManagerTool withSupportFragment(android.support.v4.app.Fragment supportFragment){
        if (mActivity != null || mFragment != null){
            throw new RuntimeException("you must start only by either activity or fragment, or supportFragment");
        }
        this.mSupportFragment = supportFragment;
        return this;
    }

    public LzqFileManagerTool withFragment(Fragment fragment){
        if (mActivity != null || mSupportFragment != null){
            throw new RuntimeException("you must start only by either activity or fragment, or supportFragment");
        }

        this.mFragment = fragment;
        return this;
    }

    public LzqFileManagerTool withRequestCode(int requestCode){
        this.mRequestCode = requestCode;
        return this;
    }

    public LzqFileManagerTool withRootPath(String rootPath){
        this.mRootPath = rootPath;
        return this;
    }

    public LzqFileManagerTool withCurrentPath(String currentPath){
        this.mCurrentPath = currentPath;
        return this;
    }

    public LzqFileManagerTool withTitle(String title){
        this.mTitle = title;
        return this;
    }

    public Intent getIntent(){
        Activity activity = null;
        if (mActivity != null){
            activity = mActivity;
        }else if (mSupportFragment != null){
            activity = mSupportFragment.getActivity();
        }else if (mFragment != null){
            activity = mFragment.getActivity();
        }

        Intent intent = new Intent(activity, FileManagerPickActivity.class);
        if (mRootPath != null){
            intent.putExtra(FileManagerPickActivity.ARG_PATH_START, mRootPath);
        }
        if (mCurrentPath != null){
            intent.putExtra(FileManagerPickActivity.ARG_PATH_CURRENT, mCurrentPath);
        }

        if (mTitle != null){
            intent.putExtra(FileManagerPickActivity.ARG_TITLE, mTitle);
        }

        return intent;
    }

    public void start(){
        if (mActivity == null && mSupportFragment == null && mFragment == null){
            throw new RuntimeException("there must have only one");
        }

        Intent intent = getIntent();
        if (mActivity != null){
            mActivity.startActivityForResult(intent, mRequestCode);
        }else if (mSupportFragment != null){
            mSupportFragment.startActivityForResult(intent, mRequestCode);
        }else if (mFragment != null){
            mFragment.startActivityForResult(intent, mRequestCode);
        }
    }

}
