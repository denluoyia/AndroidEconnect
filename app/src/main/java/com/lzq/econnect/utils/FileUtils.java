package com.lzq.econnect.utils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * function: 文件工具类
 * Created by lzq on 24.10.15.
 */
public class FileUtils{
    public static List<File> getFileListByDirPath(String path) {
        File dirFile = new File(path);
        File[] files = dirFile.listFiles(new SampleFilter()); //简单的过滤了系统的一些文件

        if (files == null) {
            return new ArrayList<>();
        }

        List<File> result = Arrays.asList(files);
        Collections.sort(result, new FileComparator());
        return result;
    }


    public static String cutLastSegmentOfPath(String path) {
        return path.substring(0, path.lastIndexOf("/"));
    }



    public static String getReadableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    //删除某个目录下的所有文件
    public static void deleteFile(File file){
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }
            for (File childFile : childFiles) {
                deleteFile(childFile);
            }
            file.delete();
        }
    }


    //剪切复制文件
    public static void copyFile(String oldPath, String newPath) {
        try {
            int byteSum = 0;

            File oldFile = new File(oldPath);
            if (oldFile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((length = inStream.read(buffer)) != -1) {
                    byteSum += length; //字节数 文件大小
                    System.out.println(byteSum);
                    fs.write(buffer, 0, length);
                }
                inStream.close();
            }
        } catch (Exception e) {
            Log.e("copy file", "复制单个文件操作出错");
            e.printStackTrace();
        }
    }

    //复制文件夹
    public static void copyFolder(String oldPath, String newPath) {
        try {
            //如果文件夹不存在 则建立新文件夹
            (new File(newPath)).mkdirs();
            File a = new File(oldPath);
            String[] file = a.list();
            File temp;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + temp.getName());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            Log.e("copy dictionary", "复制整个文件夹内容操作出错");
            e.printStackTrace();
        }
    }

    //文件筛选过滤器
    private static class SampleFilter implements FilenameFilter {
        @Override
        public boolean accept(File file, String s) {
           return !s.startsWith(".");
        }
    }



}
