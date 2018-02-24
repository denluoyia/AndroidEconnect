package com.lzq.econnect.utils;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;


import com.lzq.econnect.common.Constant;


import java.io.File;

/**
 * Function: 文件存储工具类
 * Created by lzq on 2017/4/2.
 */

public class FileStorageUtils {
    private static final String TAG = FileStorageUtils.class.getSimpleName();
    /**
     * 初始化App涉及到的文件夹目录，创建所需要的目录文件
     */

    public static void initAppDir(){
        getPublicEconnectDir();
    }

    /**
     * 获取需要电脑共享的目录
     */

    public static File getPublicEconnectDir() {
        File file = new File(Constant.E_CONNECT_PATH);
        if(!file.exists()) {
            file.mkdirs();
        }
        Log.e("file", file.getAbsolutePath());
        return file;
    }


    //获取SD卡，内部存储空间的大小和剩余空间
    public static String getSDCardStorage() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            File sdDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdDir.getPath());
            long blockSize = sf.getBlockSize();
            long blockCount = sf.getBlockCount();
            long availCount = sf.getAvailableBlocks();
            return String.format("总共: %.2f GB; 可用: %.2f GB", 1.0 * blockSize*blockCount/1024/1024/1024, 1.0 * availCount*blockSize/1024/1024/1024);
        }
        return null;
    }

}
