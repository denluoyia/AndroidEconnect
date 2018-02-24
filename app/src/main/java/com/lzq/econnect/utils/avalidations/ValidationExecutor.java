package com.lzq.econnect.utils.avalidations;

import android.content.Context;

/**
 * function: 校验执行器
 * Created by lzq on 2017/4/27.
 */

public abstract class ValidationExecutor {

    /**
     * 校验成功返回true, 否则返回false
     */
    public abstract boolean doValidate(Context context, String text);
}
