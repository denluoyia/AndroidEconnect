package com.lzq.econnect.manager;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * function : 事件注册、反注册、发布 帮助类,用于解耦.
 * Created by lzq on 2017/3/15.
 */

public class EventManager {
    private static Bus BUS = new Bus(ThreadEnforcer.MAIN);

    public static Bus getInstance(){
        return BUS;
    }

    /**
     * 发布事件
     * @param event 事件
     */
    public static void post(Object event){
        getInstance().post(event);
    }

    /**
     * 注册
     * @param object 当前为activity或fragment
     */
    public static void register(Object object){
        getInstance().register(object);
    }

    /**
     * 反注册
     * @param object 当前为activity或fragment
     */
    public static void unregister(Object object){
        getInstance().unregister(object);
    }

}
