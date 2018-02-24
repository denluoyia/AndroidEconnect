package com.lzq.econnect.ui.fragment;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lzq.econnect.R;
import com.lzq.econnect.base.BaseFragment;
import com.lzq.econnect.bean.QABean;
import com.lzq.econnect.common.Constant;
import com.lzq.econnect.manager.JsonManager;
import com.lzq.econnect.ui.activity.QADetailActivity;
import com.lzq.econnect.ui.adapter.QAAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Function: 社区问答
 * Created by lzq on 2017/3/19.
 */

public class CommunityFragment extends BaseFragment implements QAAdapter.OnItemClickListener{

    @Bind(R.id.qa_recycler_view)
    RecyclerView mRecyclerView;

    private List<QABean> mDataList;

    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_community;
    }

    @Override
    protected void doBusiness() {
        initData();
        QAAdapter mAdapter = new QAAdapter(mDataList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

    }

    public void initData(){
        mDataList = JsonManager.parseArray(Constant.QALIST, QABean.class);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), QADetailActivity.class);
        intent.putExtra("qaClass", mDataList.get(position));
        startActivity(intent);
    }
}
