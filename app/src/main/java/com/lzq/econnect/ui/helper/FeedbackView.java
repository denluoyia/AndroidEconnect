package com.lzq.econnect.ui.helper;

import com.lzq.econnect.base.BaseBean;

/**
 * 意见反馈交互接口
 * Created by luozhenqin on 2017/4/7.
 */

public interface FeedbackView {
    void feedbackSuccess(BaseBean baseBean);

    void feedbackFailed(String msg);
}
