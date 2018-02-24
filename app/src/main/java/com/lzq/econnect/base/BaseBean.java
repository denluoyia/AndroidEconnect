package com.lzq.econnect.base;

/**
 * function: 基类Bean 响应状态类
 * Created by lzq on 2017/3/15.
 */
@SuppressWarnings("ALL")
public abstract class BaseBean {
    protected int status;
    protected String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
