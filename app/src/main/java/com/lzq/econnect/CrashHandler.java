package com.lzq.econnect;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.util.Log;

import com.lzq.econnect.common.Constant;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * function: 截获崩溃日志：当程序产生未捕获异常
 * Created by lzq on 2017/8/10.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler{

    //手机存储路径
    private final String logPath = Constant.E_CONNECT_PATH + "crash/";

    private static final String TAG = CrashHandler.class.getSimpleName();

    /** 系统默认的异常处理器（默认情况下，系统会终止当前的异常程序） */
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context                         mContext;
    private String                          mProcessName;

    /** CrashHandler 实例 */
    private static CrashHandler instance = new CrashHandler();

    /** 私有构造 保证只有一个CrashHandler实例*/
    private CrashHandler(){
    }

    /** 单例模式 */
    public static CrashHandler getInstance(){
        return instance;
    }


    /** 初始化 */
    public void init(Context context){
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();

        //将当前实例设置为系统默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);

        mContext = context.getApplicationContext();
        mProcessName = context.getPackageName();
    }

    /** 用于格式化日期,作为日志文件名的一部分. */
    private final DateFormat formatter = new SimpleDateFormat("MMdd-HH:mm:ss", Locale.getDefault());


    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.e(TAG, "---------------uncaughtException start---------------\r\n");
        try {
            dumpExceptionToLocalFile(thread, throwable); //把异常日志在手机本地文件输出
            uploadExceptionToServer();  //上传crash日志到服务器
        } catch (IOException e) {
            Log.e(TAG, "uncaughtException,ex:" + e.getMessage());
            e.printStackTrace();
        }

        if (mDefaultCrashHandler != null){
            mDefaultCrashHandler.uncaughtException(thread, throwable);
        }else{
            Process.killProcess(Process.myPid());
        }

        Log.e(TAG, "---------------uncaughtException end---------------\r\n");
    }

    /** dump UncaughtException into local file */
    private void dumpExceptionToLocalFile(Thread thread, Throwable throwable) throws IOException{
        //记录数量达到10个就清理数据
        File logDir = new File(logPath);
        if (logDir.exists()){
            clearExLogWhenMax(logDir);
        }else{
            logDir.mkdirs();
        }

        //错误信息文件名
        Date   date        = new Date();
        String logFileName = formatter.format(date) + String.format("[%s]", thread.getName())+ ".txt";
        File   logExFile   = new File(logDir, logFileName);
        logExFile.createNewFile();

        //写入信息到文件中
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(logExFile)));

            //时间戳
            pw.println("Time stamp:" + date);

            pw.println("Process[" + mProcessName + "," + Process.myPid() + "]");

            pw.println();

            //手机信息
            dumpPhoneInfo(pw);

            pw.println();

            //导出异常调用栈信息
            throwable.printStackTrace(pw);
            pw.close();

        }catch (IOException ex){
            Log.e(TAG, "dump info failed");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }


    /**
     * 设置最大日志数量 10
     * @param logDir 日志目录
     */
    private void clearExLogWhenMax(File logDir){

        File[] logFileList = logDir.listFiles();
        if (logFileList == null || logFileList.length == 0){
            return;
        }

        int length = logFileList.length;
        if (length > 10){
            for (File aFile : logFileList){
                try {
                    if (aFile.delete()){
                        Log.d(TAG, "clearExLogWhenMax:" + aFile.getName());
                    }
                }catch (Exception ex){
                    Log.d(TAG, "clearExLogWhenMax:" + ex);
                }
            }
        }

    }


    private void dumpPhoneInfo(PrintWriter pw) throws IOException, PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo    pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.println("App Version:" + pi.packageName + "_" + pi.versionCode);
        //Android版本号
        pw.println("OS Version:" + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);

        //手机制造商
        pw.println("Vendor:" + Build.MANUFACTURER);

        //手机型号
        pw.println("Model:" + Build.MODEL);

        //CPU架构
        pw.println("CPU ABI:" + Build.CPU_ABI);

    }


    private void uploadExceptionToServer() {
        //TODO Upload Exception Message To Your Web Server
    }

}
