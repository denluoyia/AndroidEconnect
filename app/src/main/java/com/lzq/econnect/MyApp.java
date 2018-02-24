package com.lzq.econnect;

import android.app.Activity;
import android.app.Application;
import android.os.Looper;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * function： 应用程序入口
 * Created by lzq on 2017/3/15.
 */

@SuppressWarnings("unused")
public class MyApp extends Application {
    private static final String TAG = MyApp.class.getSimpleName();

    private static MyApp mContext;
    private static android.os.Handler mMainThreadHandler;
    private static Looper mMainThreadLooper;
    private static Thread mMainThread;




    /*** 寄存整个应用Activity **/
    private final Stack<WeakReference<Activity>> mActivities = new Stack<>();

    @Override
    public void onCreate() {
        super.onCreate();
        initMainParams();
        CrashHandler.getInstance().init(this);
    }

    private void initMainParams() {
        mContext = this;
        mMainThreadLooper = getMainLooper();
        mMainThreadHandler = new android.os.Handler(mMainThreadLooper);
        mMainThread = Thread.currentThread();
    }


    /**
     * 获取全局上下文
     * @return the context
     */

    public static MyApp getApplication(){
        return mContext;
    }

    /**
     * 获取主线程Handler
     * @return the mMainThreadHandler
     */
    public static android.os.Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }


    /**
     * 获取主线程
     * @return the mMainThread
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 将Activity压入Application栈
     * @param task 将要压入栈的Activity对象
     */
    public void pushTask(WeakReference<Activity> task){
        mActivities.push(task);
    }

    /**
     * 将传入的Activity对象从栈中移除
     *
     * @param task 将要移除栈的Activity对象
     */
    public void removeTask(WeakReference<Activity> task) {
        mActivities.remove(task);
    }


    /**
     * 关闭某个activity
     *
     * @param activityCls 指定activity的类 eg：MainActivity.class
     */
    public void finishActivity(Class<? extends Activity> activityCls) {
        int end = mActivities.size();
        for (int i = end - 1; i >= 0; i--) {
            Activity cacheActivity = mActivities.get(i).get();
            if (cacheActivity.getClass().getSimpleName().equals(activityCls.getSimpleName()) && !cacheActivity.isFinishing()) {
                cacheActivity.finish();
                removeTask(i);
            }
        }
    }

    /**
     * 根据指定位置从栈中移除Activity
     *
     * @param taskIndex Activity栈索引
     */
    public void removeTask(int taskIndex) {
        if (mActivities.size() > taskIndex)
            mActivities.remove(taskIndex);
    }

    /** 获取顶层activity */
    public Activity getTopActivity() {
        if (mActivities.size() > 0) {
            return mActivities.get(mActivities.size() - 1).get();
        }
        return null;
    }

    /** 移除全部（用于整个应用退出） */
    public void removeAll() {
        int end = mActivities.size();
        for (int i = end - 1; i >= 0; i--) {
            Activity activity = mActivities.get(i).get();
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        mActivities.clear();
    }


    /** 移除除第一个MainActivity之外的全部（主要用于回到MainActivity） */
    public void removeAllExceptFirst() {
        int end = mActivities.size();
        for (int i = end - 1; i >= 1; i--) {
            Activity activity = mActivities.get(i).get();
            if (!activity.isFinishing()) {
                activity.finish();
            }
            removeTask(i);
        }
    }


}
