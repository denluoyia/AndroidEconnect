package com.lzq.econnect.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.lzq.econnect.R;
import com.lzq.econnect.base.BaseFragment;
import com.lzq.econnect.common.Configs;
import com.lzq.econnect.common.Constant;
import com.lzq.econnect.event.HomeConnectPCEvent;
import com.lzq.econnect.event.HomeIVHelperEvent;
import com.lzq.econnect.manager.EventManager;
import com.lzq.econnect.ui.activity.FileManagerActivity;
import com.lzq.econnect.utils.LzqFileManagerTool;
import com.lzq.econnect.utils.SPUtils;
import com.lzq.econnect.utils.UIUtil;
import com.lzq.econnect.view.dialog.CustomDialog;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;

/**
 * function: 主页Fragment
 *
 * Created by lzq on 2017/3/19.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.ib_file_manager)
    ImageButton ibFileManager;
    @Bind(R.id.ib_external)
    ImageButton ibExternal;
    @Bind(R.id.ib_econnect)
    ImageButton ibConnect;
    @Bind(R.id.iv_connect_pc)
    ImageView ivConnectPc;
    @Bind(R.id.iv_helper)
    ImageView ivHelper;
    @Bind(R.id.iv_upload)
    ImageView ivUpload;


    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void doBusiness() {
        ibFileManager.setOnClickListener(this);
        ibExternal.setOnClickListener(this);
        ibConnect.setOnClickListener(this);
        ivConnectPc.setOnClickListener(this);
        ivHelper.setOnClickListener(this);
        ivUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_file_manager:
                startActivity(new Intent(getActivity(), FileManagerActivity.class));
                break;

            case R.id.ib_external:
                openFilePicker();
                break;

            case R.id.ib_econnect:
                openSharedLocalPath();
                break;

            case R.id.iv_connect_pc:
                showDialog();
                break;
            case R.id.iv_upload:
                UIUtil.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        uploadFiles(new File(Configs.SHARED_LOCAL_PATH));
                    }
                }, 0);

                break;

            case R.id.iv_helper:
                EventManager.post(new HomeIVHelperEvent());
                break;

            default:
                break;

        }
    }

    private void openFilePicker() {
        new LzqFileManagerTool().withActivity(getActivity())
                .withRequestCode(1)
                .start();
    }

    private void openSharedLocalPath(){
        new LzqFileManagerTool().withActivity(getActivity())
                .withRootPath(Configs.SHARED_LOCAL_PATH)
                .withTitle(UIUtil.getString(R.string.e_connect))
                .withRequestCode(1)
                .start();
    }


    /**
     * 正则表达式验证ip地址：((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)
     * 端口号范围：0~65535之间
     * 如果连接成功，跳转到遥控电脑的界面
     */
    private void showDialog(){
        final CustomDialog dialog = new CustomDialog(getActivity());
        final EditText ipEdt = dialog.getEtIp();
        final EditText portEdt = dialog.getEtPort();
        final EditText portEdt2 = dialog.getEtPort2();

        //设置默认的ip地址和默认的端口号
        ipEdt.setText(UIUtil.getString(R.string.default_ip));
        String spIp = SPUtils.getString(Constant.IP_KEY, null);
        ipEdt.setText(spIp == null ? UIUtil.getString(R.string.default_ip) : spIp);

        portEdt.setText(UIUtil.getString(R.string.default_port1));
        portEdt2.setText(UIUtil.getString(R.string.default_port2));
        dialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFormal = validateIPAndPort(ipEdt.getText().toString().trim(), portEdt.getText().toString().trim(), portEdt2.getText().toString().trim());
                if (isFormal){

                    SPUtils.putString(Constant.IP_KEY, ipEdt.getText().toString().trim()); //保存ip地址到sp中
                    Log.e("ip:", Constant.IP);
                    Log.e("port:",  Constant.UDP_PORT + "");

                    try{

                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    InetAddress serverAddress = InetAddress.getByName(Constant.IP);
                                    DatagramSocket socket = new DatagramSocket();
                                    socket.connect(serverAddress, Constant.UDP_PORT);
                                    if (socket.isConnected()){
                                        Log.e("thread", "连接成功");
                                        myHandler.sendEmptyMessage(100);
                                    }else{
                                        myHandler.sendEmptyMessage(10);
                                    }

                                } catch (IOException e) {
                                    myHandler.sendEmptyMessage(10);
                                }
                            }
                        });
                        thread.start();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else{
                    //端口号验证
                    UIUtil.showToast(UIUtil.getString(R.string.port_input_tips));
                }
                dialog.dismiss();
            }
        });

        dialog.setNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private MyHandler myHandler = new MyHandler();
    private static class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
           if (msg.what == 100){
               EventManager.post(new HomeConnectPCEvent(true));
               UIUtil.showToast(UIUtil.getString(R.string.connect_success));
           }else if (msg.what == 10){
               UIUtil.showToast(UIUtil.getString(R.string.connect_failure));
           }
        }
    }

    private boolean validateIPAndPort(String ip, String uPort, String tPort){

        String regexIp = "(\\d|[1-9]\\d|1\\d{2}|2[0-5][0-5])\\.(\\d|[1-9]\\d|1\\d{2}|2[0-5][0-5])\\.(\\d|[1-9]\\d|1\\d{2}|2[0-5][0-5])\\.(\\d|[1-9]\\d|1\\d{2}|2[0-5][0-5])";
        Pattern pattern = Pattern.compile(regexIp);
        Matcher matcher = pattern.matcher(ip);
        if (!matcher.find()){
            return false;
        }
        int udpPort = 0;
        int tcpPort = 0;
        try {
            udpPort = Integer.parseInt(uPort);
            tcpPort = Integer.parseInt(tPort);
            if (udpPort < 0 || udpPort > 65535) return false;
        }catch (Exception e){
            UIUtil.showToast(UIUtil.getString(R.string.port_out_bounds));
        }

        Constant.IP = ip;
        Constant.UDP_PORT = udpPort;
        Constant.TCP_PORT = tcpPort;
        return true;
    }

    //遍历某个目录下的所有文件上传
    private void uploadFiles(File file){
        if (file.isFile()){
            new Thread(new TcpClient(file.getAbsolutePath())).start();
        }else{ //是目录
            File[] files = file.listFiles();
            if (files == null || files.length == 0) return;
            for (File f : files){
                uploadFiles(f);
            }
        }
    }

    public static class TcpClient extends Socket implements Runnable{
        private Socket client = null;
        private FileInputStream fis = null;
        private DataOutputStream dos = null;
        private String mFilePath;
        private int mRootPathLen = Configs.SHARED_LOCAL_PATH.length();

        public TcpClient(String filePath){
            this.mFilePath = filePath;
        }

        public TcpClient(String filePath, int rootPathLen){
            this.mFilePath = filePath;
            this.mRootPathLen = rootPathLen;
        }

        @Override
        public void run() {
            new TcpClient(mFilePath);
            try {
                client = new Socket(Constant.IP, Constant.TCP_PORT);
                //向服务器端发送文件

                File file = new File(mFilePath);
                if (!file.exists()){
                    UIUtil.showToast(UIUtil.getString(R.string.file_not_exists));
                }
                fis = new FileInputStream(file);
                dos = new DataOutputStream(client.getOutputStream());
                dos.writeUTF(mFilePath.substring(mRootPathLen));
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();

                //开始传输文件
                byte[] bytes = new byte[1024*1024];
                int len;
                while((len = fis.read(bytes)) != -1){
                    dos.write(bytes, 0, len);
                    dos.flush();
                }

                dos.flush();

            } catch (IOException e) {
                Log.e("tcp", "连接失败");
                e.printStackTrace();
            }finally {
                if (fis != null){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (dos != null){
                    try {
                        dos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
