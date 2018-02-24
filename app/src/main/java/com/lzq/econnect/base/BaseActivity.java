package com.lzq.econnect.base;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lzq.econnect.MyApp;
import com.lzq.econnect.manager.EventManager;
import com.lzq.econnect.utils.LogUtils;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 *
 * Created by lzq on 2017/3/15.
 */

public abstract class BaseActivity extends AppCompatActivity{
    /**日志输出标志，输出当前的类名*/
    protected final String TAG = this.getClass().getSimpleName();

    /**整个应用的Application*/
    private MyApp mApplication;

    /**设置Activity布局资源id*/
    protected abstract int setContentViewId();

    /**处理业务逻辑*/
    protected abstract void doBusiness();

    /**当前的Activity为弱引用，防止内存泄漏*/
    private WeakReference<Activity> activityWeakReference = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, TAG + "-->onCreate()");
        int contentViewResId = setContentViewId();
        if(contentViewResId == 0){
            throw new IllegalArgumentException("must set activity's contentViewId");
        }
        setContentView(contentViewResId);
        mApplication = MyApp.getApplication();
        activityWeakReference = new WeakReference<Activity>(this);
        mApplication.pushTask(activityWeakReference);
        if (this.enableEventTrans()) EventManager.register(this); //事件注册
        if (this.enableButterKnife()) ButterKnife.bind(this);
        this.doBusiness();
    }

    @Override
    protected void onRestart() {
        LogUtils.d(TAG, TAG + "-->onRestart()");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        LogUtils.d(TAG, TAG + "-->onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        LogUtils.d(TAG, TAG + "-->onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        LogUtils.d(TAG, TAG + "-->onPause()");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        LogUtils.d(TAG, TAG + "-->onDestroy()");
        mApplication.removeTask(activityWeakReference); //移除Application栈
        if (this.enableEventTrans()) EventManager.unregister(this); //事件反注册
        if (this.enableButterKnife()) ButterKnife.unbind(this);//ButterKnife注入解绑
        super.onDestroy();
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
