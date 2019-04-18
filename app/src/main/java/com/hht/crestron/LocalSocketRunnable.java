package com.hht.crestron;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.hht.crestron.bean.CrestronReceiveBean;

/**
 * @author Realmo
 * @version 1.0.0
 * @name LocalSocketRunnable
 * @email momo.weiye@gmail.com
 * @time 2019/4/9 19:36
 * @describe
 */
public class LocalSocketRunnable implements Runnable{
    private static final String TAG="LocalSocketRunnable";
    private static final String ADDRESS ="hvcrestrond";
    private int timeout=30000;
    private LocalSocket client;
    private PrintWriter os;
    private BufferedReader is;

    private Handler handler;
    private CrestronReceiveBean crestronReceiveBean = new CrestronReceiveBean();

    public LocalSocketRunnable(Handler handler){
        this.handler=handler;

    }

    //发数据
    public void send(String data){
        if (os!=null) {
            os.println(data);
            os.flush();
        }
    }

    @Override
    public void run() {
        client=new LocalSocket();
        try {
            client.connect(new LocalSocketAddress(ADDRESS, LocalSocketAddress.Namespace.RESERVED));//连接服务器
            Log.i(TAG, "Client=======连接服务器成功=========");
            client.setSoTimeout(timeout);
            os=new PrintWriter(client.getOutputStream());
            is=new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String result="";
        while(true){
            try {
                result=is.readLine();
                parseCrestronContent(result);
                Log.i(TAG, "客户端接到的数据为："+ crestronReceiveBean.toString());
                //将数据带回acitvity显示
                Message msg=handler.obtainMessage();
                msg.arg1=0x12;
                msg.obj= crestronReceiveBean.toString();
                handler.sendMessage(msg);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public void close(){
        try {
            if (os!=null) {
                os.close();
            }
            if (is!=null) {
                is.close();
            }
            if(client!=null){
                client.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void parseCrestronContent(String content){
        //String content = "eType:100,joinNumber:5,joinValue:0";
        content = content.replace(":",",");
        String[] split = content.split(",");
        if(split.length == 6){
            crestronReceiveBean.seteType(split[1]);
            crestronReceiveBean.setJoinNumber(split[3]);
            crestronReceiveBean.setJoinValue(split[5]);
        }


    }


}