package com.lzq.econnect.common;

/**
 * function: 事件ID协议规定
 * Created by lzq on 2017/4/6.
 */

public class EventID {

    public static final String EVENT_UP = "up";    //向上
    public static final String EVENT_LEFT = "left"; //向左
    public static final String EVENT_DOWN = "down"; //向下
    public static final String EVENT_RIGHT = "right"; //向右
    public static final String EVENT_BACK = "back"; //返回
    public static final String EVENT_ENTER = "enter"; //Enter键
    public static final String EVENT_SPACE = "space"; //空格键
    public static final String EVENT_SLEEP = "sleep"; //休眠
    public static final String EVENT_TIME_SHUTDOWN = "shutdown_pc"; //定时关机

    public static final String CLIPBOARD = "clipCopy"; //粘贴板 粘贴手机上复制的内容 ctrl+c, ctrl + v
    public static final String OPEN_WEB = "clipWeb"; //直接在电脑上打开相应的指令

}
