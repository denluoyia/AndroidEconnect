package com.lzq.econnect.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzq.econnect.manager.EventManager;
import com.lzq.econnect.utils.LogUtils;

import butterknife.ButterKnife;

/**
 * function: Fragment基类
 * Created by lzq on 2017/3/15.
 */
@SuppressWarnings("ALL")
public abstract class BaseFragment extends Fragment {
    /**日志输出标志*/
    protected final String TAG = this.getClass().getSimpleName();
    /**根布局*/
    protected View mRootView;

    @Override
    public void onAttach(Activity activity) {
        LogUtils.d(TAG, TAG + "-->onAttach()");
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        LogUtils.d(TAG, TAG + "-->onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        LogUtils.d(TAG, TAG + "-->onCreate()");
        super.onCreate(savedInstanceState);
    }

    /**设置fragment布局视图资源id*/
    protected abstract int setContentViewResId();
    /**处理业务逻辑*/
    protected abstract void doBusiness();

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        int contentViewResId = this.setContentViewResId();
        if (0 == contentViewResId){
            throw new RuntimeException("must set fragment's contentView!");
        }
        if (null != mRootView){
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (null != parent) {
                parent.removeView(mRootView);
            }
        }
        mRootView = inflater.inflate(contentViewResId, container, false);
        if (this.enableEventTrans()) EventManager.register(this);
        if (this.enableButterKnife()) ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        LogUtils.d(TAG, TAG + "-->onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
        this.doBusiness();
    }

    @Override
    public void onResume() {
        LogUtils.d(TAG, TAG + "-->onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        LogUtils.d(TAG, TAG + "-->onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        LogUtils.d(TAG, TAG + "-->onStop()");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        LogUtils.d(TAG, TAG + "-->onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        LogUtils.d(TAG, TAG + "-->onDestroy()");
        if (this.enableEventTrans()) EventManager.unregister(this);
        if (this.enableButterKnife()) ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        LogUtils.d(TAG, TAG + "-->onDetach()");
        super.onDetach();
    }

    /** 是否禁用基类事件分发注册和反注册 */
    protected boolean enableEventTrans() {
        return true;
    }

    /** 是否禁用基类ButterKnife注册和反注册 */
    protected boolean enableButterKnife() {
        return true;
    }
}
