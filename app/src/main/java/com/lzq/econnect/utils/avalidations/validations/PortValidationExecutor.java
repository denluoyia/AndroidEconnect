package com.lzq.econnect.utils.avalidations.validations;

import android.content.Context;
import android.widget.Toast;

import com.lzq.econnect.utils.avalidations.ValidationExecutor;

/**
 * function: 端口号验证
 * Created by lzq on 2017/4/27.
 */

public class PortValidationExecutor extends ValidationExecutor{

    @Override
    public boolean doValidate(Context context, String text) {
        int port = Integer.parseInt(text);
        if (port > 65535 && port < 0){
            Toast.makeText(context, "端口号应在0~65535之间", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
