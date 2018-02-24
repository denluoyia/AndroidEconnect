package com.lzq.econnect.utils;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * function: SnackBar工具类
 * Created by lzq on 2017/3/16.
 */
@SuppressWarnings("unused")
public class SnackBarUtils {
    public static final int DURATION = Snackbar.LENGTH_LONG / 2;

    public static void show(View view, int content){
        Snackbar.make(view, content, Snackbar.LENGTH_SHORT).show();
    }

    public static void show(Activity activity, int content){
        View view = activity.getWindow().getDecorView();
        show(view, content);
    }

    private static void showAction(View view, int content, int action, View.OnClickListener listener){
        Snackbar.make(view, content, Snackbar.LENGTH_SHORT)
                .setAction(action, listener)
                .show();
    }

    public static void showAction(Activity activity, int message, int action, View.OnClickListener listener) {
        View view = activity.getWindow().getDecorView();
        showAction(view, message, action, listener);
    }
}

