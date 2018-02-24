package com.lzq.econnect.common;

import android.os.Environment;


import java.io.File;

/**
 * function: 程序常量
 * Created by lzq on 2017/4/10.
 */

public class Constant {

    public static final String E_CONNECT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator + "Econnect" + File.separator;

    public static String QALIST = "[{\"content\": \" 首先确保PC端的程序已经运行，点击开始。然后在Android应用的击首页点击连接电脑，会弹出一个对话框，输入同一个局域网中PC端的ip地址和配置的端口号。然后连接成功后会提示“连接成功”，然后跳转到遥控电脑界面。\",\"title\": \"怎么样进行遥控电脑操作？\" },{\"content\": \"如果电脑的端口号默认不变，程序已经开启，那么手机再次打开的时候会记得上次配置的端口号和连接的电脑的ip地址，所以手机端再次启动应用的时候不需要在首页中点击连接电脑来配置ip地址和端口号信息了。\",\"title\": \"退出手机应用后，再次打开程序，可以直接遥控电脑操作吗？\"},{ \"content\": \"为了避免和遥控电脑的端口号发生冲突，给重新配置一个端口号。同样，下一次打开手机之后，直接点击映射文件的按钮，就可以直接上传到Econnect目录到电脑了，不用弹出对话框进行重新配置了。\",\"title\": \"怎么样才能使电脑映射出手机的特定文件夹？\"},{\"content\": \"我们使用的是局域网的ip地址，所以可能有时候本机的ip地址会和局域网的ip地址不一样，最好在PC端的控制台中输入ipconfig进行确认电脑的ip地址，然后在手机端配置。\",\"title\": \"为什么PC端的本机ip地址是连接不了的？\"},{\"content\": \"为了方便浏览，默认设置在电脑的桌面的Ecoonect文件里。\",\"title\": \"映射到电脑上的文件存放在哪里？\" }]";
    public static final String SPName = "ipSp";
    public static final String IP_KEY = "ip_num";


    /**全部静态变量*/
    public static String IP = "192.168.1.1";
    public static int UDP_PORT = 55555;
    public static int TCP_PORT = 33333;
}
