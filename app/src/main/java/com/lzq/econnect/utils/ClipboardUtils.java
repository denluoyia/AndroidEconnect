package com.lzq.econnect.utils;

import android.content.ClipboardManager;
import android.content.Context;

/**
 * 剪切板工具
 * Created by lzq on 2017/3/30.
 */

public class ClipboardUtils {
    private static ClipboardManager getClipboardManager(){
        return (ClipboardManager) UIUtil.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
    }

    /**
     * 实现复制功能
     */
    public static void copyString2Clipboard(String content){
        getClipboardManager().setText(content.trim());
    }

    /**
     * 实现粘贴字符串文本功能
     */

    public static String pasteStringFromClipboard(){
        return getClipboardManager().getText().toString().trim();
    }
}
