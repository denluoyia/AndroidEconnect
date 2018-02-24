package com.lzq.econnect.base;

/**
 * function: UI的逻辑处理类
 * Created by lzq on 2017/3/15.
 */
@SuppressWarnings("ALL")
public abstract class BaseHelper {
    /**日志输出标记*/
    protected final String TAG = this.getClass().getSimpleName();

    public abstract void onDestroy();

}
