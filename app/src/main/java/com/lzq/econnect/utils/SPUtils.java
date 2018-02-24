package com.lzq.econnect.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.lzq.econnect.common.Constant;

/**
 * function: SharedPreferences 管理类
 * Created by lzq on 2017/4/11.
 */

public class SPUtils {
    private static SharedPreferences sp;

    private static SharedPreferences getSp(Context context){
        if (sp == null){
            sp = context.getSharedPreferences(Constant.SPName, Context.MODE_PRIVATE);
        }

        return sp;
    }

    /**
     * 缓存String数据
     */
    public static void putString(String key, String value){
        getSp(UIUtil.getContext()).edit().putString(key, value).commit();
    }

    /**
     *获取String数据
     */
    public static String getString(String key, String defValue){
        return getSp(UIUtil.getContext()).getString(key, defValue);
    }
}
