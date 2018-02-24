package com.lzq.econnect.utils.avalidations;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.lzq.econnect.R;
import com.lzq.econnect.utils.UIUtil;

import java.util.ArrayList;
/**
 * function: EditText校验执行器
 * Created by lzq on 2017/4/27.
 */

public class EditTextValidator {
    private Context context;
    private ArrayList<ValidationModel> validationModels;
    private TextView button;

    public EditTextValidator(Context context){
        this(context, null);
    }

    public EditTextValidator(Context context, TextView button){
        this.context = context;
        this.button = button;
        validationModels = new ArrayList<>();
    }

    public EditTextValidator setButton(TextView button){
        this.button = button;
        return this;
    }

    public EditTextValidator add(ValidationModel validationModel){
        validationModels.add(validationModel);
        return this;
    }

    public EditTextValidator execute(){
        for (ValidationModel validationModel : validationModels){
            if (validationModel.getEditText() == null) return this;
            validationModel.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    setEnable();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
        setEnable();
        return this;
    }

    private void setEnable(){
        for (ValidationModel validationModel : validationModels){
            if (button != null){
                if (validationModel.isTextEmpty()){
                    //为空，则不可点击
                    button.setEnabled(false);
                    button.setTextColor(UIUtil.getColor(R.color.white_halftransparency));
                }else{
                    if (!button.isEnabled()){
                        button.setEnabled(true);
                        button.setTextColor(UIUtil.getColor(R.color.white));
                    }
                }
            }
        }
    }

    /**
     * 判断是否校验成功
     * @return
     */
    public boolean validate(){
        for (ValidationModel validationModel : validationModels){
            if (validationModel.getValidationExecutor() == null || validationModel.getEditText() == null){
                //如果某个校验模型为空，则直接返回正确
                return true;
            }

            if (!validationModel.getValidationExecutor().doValidate(context, validationModel.getEditText().getText().toString())){
                //如果其中某个模型验证不成功，则直接返回错误
                return false;
            }
        }

        return true;
    }

    public TextView getButton(){
        return this.button;
    }
}
