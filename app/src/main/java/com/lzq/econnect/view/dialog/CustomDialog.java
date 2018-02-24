package com.lzq.econnect.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lzq.econnect.R;

/**
 * Function: 自定义弹框
 * Created by lzq on 2017/4/8.
 */

public class CustomDialog extends Dialog{

    private EditText etIp, etPort, etPort2;
    private Button positiveBtn, negativeBtn;

    public CustomDialog(Context context) {
        super(context, R.style.CustomDialogStyle);
        initDialogViews();
    }

    private void initDialogViews(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_custom_dialog, null);
        etIp = (EditText) view.findViewById(R.id.et_ip);
        etPort = (EditText) view.findViewById(R.id.et_port);
        etPort2 = (EditText) view.findViewById(R.id.et_port2);
        negativeBtn = (Button) view.findViewById(R.id.btn_negative);
        positiveBtn = (Button) view.findViewById(R.id.btn_positive);
        super.setContentView(view);
    }

    public void setOnPositiveListener(View.OnClickListener listener){
        positiveBtn.setOnClickListener(listener);
    }

    public void setNegativeListener(View.OnClickListener listener){
        negativeBtn.setOnClickListener(listener);
    }

    //获取需要监听的控件
    public EditText getEtIp(){
        return etIp;
    }

    public EditText getEtPort(){
        return etPort;
    }

    public EditText getEtPort2(){
        return etPort2;
    }
}
