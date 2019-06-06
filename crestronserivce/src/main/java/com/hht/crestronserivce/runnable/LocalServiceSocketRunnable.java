package com.hht.crestronserivce.runnable;

import android.content.Context;
import android.net.LocalServerSocket;
import android.net.LocalSocket;

import com.hht.crestronserivce.bean.SyncStautsBean;
import com.hht.crestronserivce.utils.CrestronDeviceManager;
import com.hht.crestronserivce.utils.CrestronThreadPool;
import com.hht.crestronserivce.utils.DefaultLogger;


import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Realmo
 * @version 1.0.0
 * @name LocalServiceSocketRunnable
 * @email momo.weiye@gmail.com
 * @time 2019/4/9 19:36
 * @describe
 */
public class LocalServiceSocketRunnable implements Runnable, LocalClientSocketRunnable.Status{

    private static final String ADDRESS ="hvcrestrond-socket";


    private LocalServerSocket serverSocket;

    private Context context;

    private ArrayList<LocalSocket> clients;
    private ArrayList<LocalClientSocketRunnable> clientRunnables;
    private CrestronDeviceManager crestronDeviceManager;


    public LocalServiceSocketRunnable(Context context){
        this.context = context.getApplicationContext();
        clients = new ArrayList<>(2);
        clientRunnables = new ArrayList<>(2);
        crestronDeviceManager = CrestronDeviceManager.getInstance(context);
    }



    @Override
    public void run() {

        try {
            serverSocket = new LocalServerSocket(ADDRESS);
        } catch (IOException e) {
            e.printStackTrace();
            DefaultLogger.error("LocalServerSocket instance failed");
        }
        DefaultLogger.debug("LocalServerSocket instance success");
        while (true){
            try {
                LocalSocket localSocket = serverSocket.accept();
                if(localSocket != null){

                    LocalClientSocketRunnable clientRunnable = new LocalClientSocketRunnable(context,localSocket,this);
                    clients.add(localSocket);
                    clientRunnables.add(clientRunnable);

                    DefaultLogger.debug("client count:"+clients.size());
                    DefaultLogger.debug("client runnable count:"+clientRunnables.size());
                    CrestronThreadPool.getInstance().execute(clientRunnable);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * close local socket service res
     */
    public void close(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void connected(LocalSocket client, LocalClientSocketRunnable task) {
        //send sync data to client
        SyncStautsBean syncStautsBean = new SyncStautsBean();
        syncStautsBean.setBrightValue(crestronDeviceManager.getBright());
        syncStautsBean.setVolumeValue(crestronDeviceManager.getVolume());
        task.send(syncStautsBean.getSyncBrightInfo());
        task.send(syncStautsBean.getSyncVolumeInfo());

    }

    @Override
    public void disConnected(LocalSocket client, LocalClientSocketRunnable task) {
        if(clients.remove(client)){
            DefaultLogger.verbose("remove useless client success");
        }else{
            DefaultLogger.warning("can not find useless client");
        }

        if(clientRunnables.remove(task)){
            DefaultLogger.verbose("remove useless runnable success");
        }else{
            DefaultLogger.warning("can not find useless runnable");
        }

    }

    @Override
    public void forward(String data, LocalSocket client,LocalClientSocketRunnable task) {
        int index = clientRunnables.indexOf(task);
        if(index == -1){
            DefaultLogger.warning("forward data can not find other client");
            return;
        }
        for (int i = 0; i <clientRunnables.size(); i++) {
            if(i == index){
                continue;
            }
            clientRunnables.get(i).send(data);
        }

    }
}