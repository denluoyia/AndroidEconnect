package com.lzq.econnect.ui.activity;


import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.lzq.econnect.R;
import com.lzq.econnect.base.BaseActivity;
import com.lzq.econnect.bean.QABean;


import butterknife.Bind;

public class QADetailActivity extends BaseActivity{

    @Bind(R.id.qa_title)
    TextView qaTitle;
    @Bind(R.id.qa_content)
    TextView qaContent;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_qadetail;
    }

    @Override
    protected void doBusiness() {
        initToolbar();
        QABean bean = getIntent().getParcelableExtra("qaClass");
        if (bean != null){
            qaTitle.setText(bean.getTitle());
            qaContent.setText(bean.getContent());
        }
    }

    private void initToolbar(){

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("QA详情");
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
