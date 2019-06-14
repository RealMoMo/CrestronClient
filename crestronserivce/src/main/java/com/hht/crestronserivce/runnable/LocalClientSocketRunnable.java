package com.hht.crestronserivce.runnable;

import android.content.Context;
import android.net.LocalSocket;
import android.support.annotation.NonNull;


import com.hht.crestronserivce.bean.CrestronBean;
import com.hht.crestronserivce.utils.CrestronDeviceManager;
import com.hht.crestronserivce.utils.CrestronCommandManager;
import com.hht.crestronserivce.utils.DefaultLogger;
import com.hht.setting.lib.i.HHTDeviceCallBack;

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
public class LocalClientSocketRunnable implements Runnable, HHTDeviceCallBack {

    private static boolean selfChange = false;

    private LocalSocket mClient;
    private PrintWriter os;
    private BufferedReader is;


    private CrestronBean crestronBean = new CrestronBean();
    private CrestronCommandManager crestronCommandManager;
    private CrestronDeviceManager<LocalClientSocketRunnable> crestronDeviceManager;
    private Status mStatus;

    private boolean selfReply = false;

    @Override
    public void onMuteChange(boolean mute) {
        //TODO
        if(selfChange){
            return;
        }
    }

    @Override
    public void onVolumeChange(int value) {
        if(selfChange){
           return;
        }
            crestronBean.setVolumeValue(value);
            send(crestronBean.getSyncVolumeInfo());
            DefaultLogger.debug("client onVolumeChange");


    }

    @Override
    public void onBrightnessChange(int value) {
        if(selfChange){
            return;
        }
        crestronBean.setBrightValue(value);
        send(crestronBean.getSyncBrightInfo());
        DefaultLogger.debug("client onBrightnessChange");
    }

    public interface Status{

        /**
         * connected localsocket
         * @param client    LocalSocket
         * @param task      LocalClientSocketRunnable
         */
        void connected(LocalSocket client,LocalClientSocketRunnable task);

        /**
         * disconnected localsocket
         * @param client    LocalSocket
         * @param task      LocalClientSocketRunnable
         */
        void disConnected(LocalSocket client, LocalClientSocketRunnable task);

        /**
         * forward msg to other client if have other client
         * @param data  forward content
         * @param client    LocalSocket
         * @param task      LocalClientSocketRunnable
         */
        void forward(String data,LocalSocket client,LocalClientSocketRunnable task);

    }

    public LocalClientSocketRunnable(Context context,LocalSocket client,@NonNull Status status){
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
        crestronDeviceManager = CrestronDeviceManager.getInstance(context);
        crestronDeviceManager.registerDeviceCallBack(this);

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
        mStatus.connected(mClient,this);

        String result="";
        while(true){
            try {
                result=is.readLine();
                //client disconnect when content is null
                if(result ==null){
                    close();
                    break;

                }
                DefaultLogger.debug("from client raw data:"+ result);
                if(result.contains("ID")){
                    selfReply = parseID(result);
                }else{
                    parseCrestronContent(result);
                    DefaultLogger.debug("from client parse data:"+ crestronBean.toString());
                    selfChange = true;
                    crestronCommandManager.doCrestronCommand(crestronBean);
                    mStatus.forward(crestronBean.getReplyInfo(),mClient,this);
                    DefaultLogger.debug("selfReply:"+selfReply );
                    if(selfReply){
                        send(crestronBean.getReplyInfo());
                    }
                    selfChange = false;
                }



//                if(crestronCommandManager.isForward(crestronBean)){
//                    mStatus.forward(crestronCommandManager.getForwardContent(crestronBean),mClient,this);
//                }else{
//                    crestronCommandManager.doCrestronCommand(crestronBean);
//                    send(crestronBean.getSuccesResponse());
//                }



            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                DefaultLogger.error("from client exception");
                close();

            }
        }

    }

    /**
     * close local socket res
     */
    public void close(){
        DefaultLogger.debug("111："+(mStatus == null));
        DefaultLogger.debug("222："+(mClient == null));
        DefaultLogger.debug("333："+(this == null));
        if(mStatus!=null){
            mStatus.disConnected(mClient,this);
            mStatus = null;
        }
        crestronDeviceManager.unregisterDeviceCallBack(this);
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

    private boolean parseID(String content){
        return content.contains("CrestronServer");
    }


}