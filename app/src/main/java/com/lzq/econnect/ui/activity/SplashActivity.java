package com.lzq.econnect.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.lzq.econnect.R;
import com.lzq.econnect.utils.FileStorageUtils;
import com.lzq.econnect.utils.UIUtil;

/**
 * function: 启动页: 初始化准备，高版本动态获取权限
 * Created by lzq on 2017/3/16
 */
public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkInitPermissions();
        UIUtil.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }


    private void checkInitPermissions(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])){
                    ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION_CODE);
                }else{
                    ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION_CODE);
                }
            }
        } else {
            handleAfterPermissions();
        }
    }

    private void  handleAfterPermissions(){
        FileStorageUtils.initAppDir();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE){
            handleAfterPermissions();
        }
    }
}
