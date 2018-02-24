package com.lzq.econnect.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.lzq.econnect.MyApp;

/**
 *
 * Created by lzq on 2017/3/25.
 */
@SuppressWarnings("ALL")
public class UIUtil {
    private static final String TAG = UIUtil.class.getSimpleName();

    /** 获取全局上下文*/
    public static Context getContext(){
        return MyApp.getApplication();
    }

    /** 获取主线程 */
    public static Thread getMainThread(){
        return MyApp.getMainThread();
    }

    /** 获取主线程的Handler */
    public static Handler getHandler(){
        return MyApp.getMainThreadHandler();
    }

    /** 在主线程中延迟一定时间执行 runnable*/
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /**--------------------根据资源id获取资源---------------*/

    /**
     * 填充layout布局文件
     */
    public static View inflate(int resId){
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    /**
     * 获取资源
     */
    public static Resources getResources(){
        return getContext().getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(int resId){
        return getResources().getString(resId);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId){
        return getResources().getStringArray(resId);
    }

    /**
     * 获取大小dimen
     */
    public static int getDimens(int resId){
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId){
        return ContextCompat.getDrawable(getContext(), resId);
    }

    /**
     * 获取Bitmap
     */
    public static Bitmap getBitmap(int resId) {
        return BitmapFactory.decodeResource(getResources(), resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }


    public static String getVersion(){
        PackageManager manager = getContext().getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(getContext().getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Toast封装
     * @param msg 弹出吐司信息
     */
    public static void showToast(String msg){
        if(!TextUtils.isEmpty(msg)){
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
