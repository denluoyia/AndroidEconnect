package com.lzq.econnect.ui.fragment;

import android.view.View;
import android.widget.Button;

import com.lzq.econnect.R;
import com.lzq.econnect.base.BaseFragment;
import com.lzq.econnect.common.Constant;
import com.lzq.econnect.common.EventID;
import com.lzq.econnect.utils.ClipboardUtils;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import butterknife.Bind;


/**
 * function: 控制PC端的一些操作
 * Created by lzq on 2017/3/19.
 */

public class ControlPCFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.upBtn)
    Button upBtn;
    @Bind(R.id.leftBtn)
    Button leftBtn;
    @Bind(R.id.downBtn)
    Button downBtn;
    @Bind(R.id.rightBtn)
    Button rightBtn;
    @Bind(R.id.backBtn)
    Button backBtn;
    @Bind(R.id.spaceBtn)
    Button spaceBtn;
    @Bind(R.id.enterBtn)
    Button enterBtn;
    @Bind(R.id.sleepBtn)
    Button sleepBtn;
    @Bind(R.id.timeShutBtn)
    Button timeShutBtn;
    @Bind(R.id.openWebBtn)
    Button openWebBtn;
    @Bind(R.id.pastClipBtn)
    Button pastClipBtn;

    private DatagramSocket socket = null;

    @Override
    protected int setContentViewResId() {
        return R.layout.fragment_control_pc;
    }

    @Override
    protected void doBusiness() {
        initViews();
    }

    private void initViews(){
        upBtn.setOnClickListener(this);
        leftBtn.setOnClickListener(this);
        downBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        spaceBtn.setOnClickListener(this);
        enterBtn.setOnClickListener(this);
        sleepBtn.setOnClickListener(this);
        timeShutBtn.setOnClickListener(this);
        openWebBtn.setOnClickListener(this);
        pastClipBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.upBtn:
                //sendMessage2Server(EventID.EVENT_UP);
                new SendMsgThread(EventID.EVENT_UP).start();
                break;
            case R.id.leftBtn:
                //sendMessage2Server(EventID.EVENT_LEFT);
                new SendMsgThread(EventID.EVENT_LEFT).start();
                break;
            case R.id.downBtn:
                //sendMessage2Server(EventID.EVENT_DOWN);
                new SendMsgThread(EventID.EVENT_DOWN).start();
                break;

            case R.id.rightBtn:
                //sendMessage2Server(EventID.EVENT_RIGHT);
                new SendMsgThread(EventID.EVENT_RIGHT).start();
                break;

            case R.id.backBtn:
                //sendMessage2Server(EventID.EVENT_BACK);
                new SendMsgThread(EventID.EVENT_BACK).start();
                break;

            case R.id.spaceBtn:
                //sendMessage2Server(EventID.EVENT_SPACE);
                new SendMsgThread(EventID.EVENT_SPACE).start();
                break;

            case R.id.enterBtn:
                //sendMessage2Server(EventID.EVENT_ENTER);
                new SendMsgThread(EventID.EVENT_ENTER).start();
                break;
            case R.id.sleepBtn:
                //sendMessage2Server(EventID.EVENT_SLEEP);
                new SendMsgThread(EventID.EVENT_SLEEP).start();
                break;

            case R.id.timeShutBtn:
                //sendMessage2Server(EventID.EVENT_TIME_SHUTDOWN + ";3600");
                new SendMsgThread(EventID.EVENT_TIME_SHUTDOWN + ";3600").start();
                break;

            case R.id.pastClipBtn:
                //sendMessage2Server(EventID.CLIPBOARD + ";" + ClipboardUtils.pasteStringFromClipboard());
                new SendMsgThread(EventID.CLIPBOARD + ";" + ClipboardUtils.pasteStringFromClipboard()).start();
                break;

            case R.id.openWebBtn:
                //sendMessage2Server(EventID.OPEN_WEB + ";" + ClipboardUtils.pasteStringFromClipboard());
                new SendMsgThread(EventID.OPEN_WEB + ";" + ClipboardUtils.pasteStringFromClipboard()).start();
                break;

            default:
                break;
        }
    }

    private class SendMsgThread extends Thread{
        private String msg;
        public SendMsgThread(String msg){
            this.msg = msg;
        }

        @Override
        public void run() {
            sendMessage2Server(msg);
        }
    }
    private void sendMessage2Server(String msg){
        try {
            socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName(Constant.IP);
            byte[] data = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, address, Constant.UDP_PORT);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
