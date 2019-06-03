package com.hht.crestronserivce.runnable;

import android.content.Context;
import android.net.LocalSocket;
import android.support.annotation.NonNull;


import com.hht.crestronserivce.bean.CrestronBean;
import com.hht.crestronserivce.utils.CrestronCommandManager;
import com.hht.crestronserivce.utils.DefaultLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @author Realmo
 * @version 1.0.0
 * @name LocalSocketRunnable
 * @email momo.weiye@gmail.com
 * @time 2019/4/9 19:36
 * @describe
 */
public class LocalClientSocketRunnable implements Runnable{


    private LocalSocket mClient;
    private PrintWriter os;
    private BufferedReader is;


    private CrestronBean crestronBean = new CrestronBean();
    private CrestronCommandManager crestronCommandManager;
    private Status mStatus;

    public interface Status{
        /**
         * disconnected localsocket
         * @param client    LocalSocket
         * @param task      LocalClientSocketRunnable
         */
        void disConnect(LocalSocket client,LocalClientSocketRunnable task);

        /**
         * forward msg to other client if have other client
         * @param data  forward content
         * @param client    LocalSocket
         * @param task      LocalClientSocketRunnable
         */
        void forward(String data,LocalSocket client,LocalClientSocketRunnable task);

    }

    public LocalClientSocketRunnable(Context context,LocalSocket client,Status status){
        this.mClient = client;
        this.mStatus = status;
        try {
            os=new PrintWriter(client.getOutputStream());
            is=new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
        crestronCommandManager = CrestronCommandManager.getInstance(context);
    }

    /**
     * Send content to local socket client
     * @param data
     */
    public void send(String data){
        if (os!=null) {
            os.println(data);
            os.flush();
        }
    }

    @Override
    public void run() {

        String result="";
        while(true){
            try {
                result=is.readLine();
                //client disconnect
                if(result ==null){
                    DefaultLogger.debug("client close:"+ mClient.toString());
                    DefaultLogger.debug("client close:"+ this.toString());
                    close();
                    break;

                }
                DefaultLogger.verbose("from client raw data:"+ result);


                parseCrestronContent(result);
                DefaultLogger.verbose("from client parse data:"+ crestronBean.toString());
                if(crestronCommandManager.isForward(crestronBean)){
                    mStatus.forward(crestronCommandManager.getForwardContent(crestronBean),mClient,this);
                }else{
                    crestronCommandManager.doCrestronCommand(crestronBean);
                    send(crestronBean.getSuccesResponse());
                }


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                DefaultLogger.verbose("from client exception");
                close();

            }
        }

    }

    /**
     * close local socket res
     */
    public void close(){
        mStatus.disConnect(mClient,this);
        mStatus = null;
        try {
            if (os!=null) {
                os.close();
            }
            if (is!=null) {
                is.close();
            }
            if(mClient !=null){
                mClient.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void parseCrestronContent(String content){
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