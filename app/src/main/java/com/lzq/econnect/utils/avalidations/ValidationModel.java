package com.lzq.econnect.utils.avalidations;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * function: 校验模型
 * Created by lzq on 2017/4/27.
 */

public class ValidationModel {
    private ValidationExecutor validationExecutor;
    private EditText editText;

    public ValidationModel(){} //无参构造方法

    public ValidationModel(EditText editText, ValidationExecutor validationExecutor){
        this.editText = editText;
        this.validationExecutor = validationExecutor;
    }

    public ValidationModel setEditText(EditText editText){
        this.editText = editText;
        return this;
    }

    public ValidationModel setValidationExecutor(ValidationExecutor validationExecutor){
        this.validationExecutor = validationExecutor;
        return this;
    }

    public EditText getEditText(){
        return this.editText;
    }

    public ValidationExecutor getValidationExecutor(){
        return this.validationExecutor;
    }

    public boolean isTextEmpty(){
        return editText == null || TextUtils.isEmpty(editText.getText());
    }

}
