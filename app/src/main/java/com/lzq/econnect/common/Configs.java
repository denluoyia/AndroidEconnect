package com.lzq.econnect.common;

import android.os.Environment;

/**
 * function: 配置信息
 * Created by lzq on 2017/3/15.
 */

public class Configs {
    //0-测试模式 1-生产模式
    public static int STATE = 1;

    //调试开关
    public static boolean DEBUG = true;

    static {
        if(0 == STATE){
            DEBUG = true;
        }else if (1 == STATE){
            DEBUG = false;
        }else {
            throw new RuntimeException("STATE IS WRONG!");
        }
    }

    public static final String SHARED_LOCAL_PATH = Environment.getExternalStorageDirectory().getPath() + "/Econnect";

}
