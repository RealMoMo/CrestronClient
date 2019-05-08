package com.hht.crestron;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import android.content.Context;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Handler;
import android.os.Message;


import com.hht.crestron.bean.CrestronBean;
import com.hht.crestron.utils.CrestronCommandManager;
import com.hht.crestron.utils.DefaultLogger;

/**
 * @author Realmo
 * @version 1.0.0
 * @name LocalSocketRunnable
 * @email momo.weiye@gmail.com
 * @time 2019/4/9 19:36
 * @describe
 */
public class LocalSocketRunnable implements Runnable{

    private static final String ADDRESS ="hvcrestrond";
    private int timeout=30000;
    private LocalSocket client;
    private PrintWriter os;
    private BufferedReader is;

    private Handler handler;
    private CrestronBean crestronBean = new CrestronBean();
    private CrestronCommandManager crestronCommandManager;

    public LocalSocketRunnable(Handler handler, Context context){
        this.handler=handler;
        crestronCommandManager = CrestronCommandManager.getInstance(context);
    }

    public LocalSocketRunnable(Context context){
        crestronCommandManager = CrestronCommandManager.getInstance(context);
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
            client.connect(new LocalSocketAddress(ADDRESS, LocalSocketAddress.Namespace.RESERVED));
            DefaultLogger.verbose("Connect succes");
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
                DefaultLogger.verbose("from server raw data:"+ result);
                parseCrestronContent(result);
                DefaultLogger.verbose("from server parse data:"+ crestronBean.toString());
                if(handler !=null){
                    Message msg=handler.obtainMessage();
                    msg.arg1=0x12;
                    msg.obj= crestronBean.toString();
                    handler.sendMessage(msg);
                }
                crestronCommandManager.doCrestronCommand(crestronBean);
                //reponse data to server
                send(crestronBean.getSuccesResponse());

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
            crestronBean.seteType(split[1]);
            crestronBean.setJoinNumber(split[3]);
            crestronBean.setJoinValue(split[5]);
        }


    }


}