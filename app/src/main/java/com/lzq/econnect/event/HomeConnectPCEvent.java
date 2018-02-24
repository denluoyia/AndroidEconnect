package com.lzq.econnect.event;

import com.lzq.econnect.base.BaseEvent;

/**
 * function： 主页的点击点击连接电脑事件
 * Created by lzq on 2017/4/8.
 */

@SuppressWarnings("ALL")
public class HomeConnectPCEvent extends BaseEvent{

    boolean connectState; //true 表示连接成功
    public HomeConnectPCEvent(boolean connectState){
        this.connectState = connectState;
    }

    public boolean getConnectState() {
        return connectState;
    }

    public void setConnectState(boolean connectState) {
        this.connectState = connectState;
    }
}
