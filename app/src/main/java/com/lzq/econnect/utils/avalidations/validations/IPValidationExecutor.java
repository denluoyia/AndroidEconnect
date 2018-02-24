package com.lzq.econnect.utils.avalidations.validations;

import android.content.Context;
import android.widget.Toast;

import com.lzq.econnect.utils.avalidations.ValidationExecutor;

import java.util.regex.Pattern;

/**
 * function: IP地址校验
 * Created by lzq on 2017/4/27.
 */

public class IPValidationExecutor extends ValidationExecutor {
    @Override
    public boolean doValidate(Context context, String text) {
        Pattern pattern = Pattern
                .compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]"
                        + "|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");

        boolean isOk = pattern.matcher(text).matches();
        if (!isOk) Toast.makeText(context, "IP地址不合法", Toast.LENGTH_SHORT).show();
        return isOk;
    }
}
