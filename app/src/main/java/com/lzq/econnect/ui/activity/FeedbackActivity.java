package com.lzq.econnect.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lzq.econnect.R;
import com.lzq.econnect.base.BaseActivity;
import com.lzq.econnect.base.BaseBean;
import com.lzq.econnect.ui.helper.FeedbackHelper;
import com.lzq.econnect.ui.helper.FeedbackView;


import butterknife.Bind;
import butterknife.OnClick;

/**
 * function：用户反馈界面
 * Created by lzq
 */

public class FeedbackActivity extends BaseActivity implements FeedbackView{
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rg_assess)
    RadioGroup rgAssess;
    @Bind(R.id.et_feedback)
    EditText etFeedback;

    private int feedback_type = 1;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void doBusiness() {
        initToolbar();

         /*设置radioGrop监听*/
        rgAssess.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rg_assess_one:
                        feedback_type = 1;
                        break;
                    case R.id.rg_assess_two:
                        feedback_type = 2;
                        break;
                    case R.id.rg_assess_three:
                        feedback_type = 3;
                        break;
                }
            }
        });

    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_submit)
    public void submitClick(){
        FeedbackHelper adviceHelper = new FeedbackHelper(this);
        adviceHelper.postData(feedback_type, etFeedback.getText().toString());
    }


    private void initToolbar(){

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("意见反馈");
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void feedbackSuccess(BaseBean baseBean) {
        if (isFinishing() || isDestroyed()) return;
        Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void feedbackFailed(String msg) {
        if (isFinishing() || isDestroyed()) return;
        if (msg == null) return;
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
