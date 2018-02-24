package com.lzq.econnect.utils;

import android.util.Log;

import com.lzq.econnect.common.Configs;

/**
 * function: 封装Log工具类
 * Created by lzq on 2017/3/15.
 */

public class LogUtils {
    private static boolean DEBUG = Configs.DEBUG;

    public static void v(String tag, String content){
        if(!DEBUG) return;
        Log.v(tag, content);
    }
    public static void v(final String tag, Object... objs){
        if (!DEBUG) return;
        Log.v(tag, getInfo(objs));
    }

    public static void w(String tag, String content){
        if(!DEBUG) return;
        Log.w(tag, content);
    }

    public static void w(final String tag, Object... objs){
        if (!DEBUG) return;
        Log.w(tag, getInfo(objs));
    }

    public static void i(String tag, String content){
        if(!DEBUG) return;
        Log.i(tag, content);
    }

    public static void i(final String tag, Object... objs){
        if (!DEBUG) return;
        Log.i(tag, getInfo(objs));
    }

    public static void d(String tag, String content){
        if(!DEBUG) return;
        Log.d(tag, content);
    }

    public static void d(final String tag, Object... objs){
        if (!DEBUG) return;
        Log.d(tag, getInfo(objs));
    }

    public static void e(String tag, String content){
        if(!DEBUG) return;
        Log.e(tag, content);
    }

    public static void e(final String tag, Object... objs){
        if (!DEBUG) return;
        Log.e(tag, getInfo(objs));
    }


    private static String getInfo(Object... objs) {
        StringBuilder sb = new StringBuilder();
        if (null == objs) {
            sb.append("no message.");
        } else {
            for (Object object : objs) {
                sb.append("--");
                sb.append(object);
            }
        }
        return sb.toString();
    }

    public static void sysOut(Object msg){
        if(!DEBUG) return;
        System.out.println(msg);
    }

    public static void sysErr(Object msg){
        if(!DEBUG) return;
        System.err.println(msg);
    }

}
