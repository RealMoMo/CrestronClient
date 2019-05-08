package com.hht.crestron.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.hht.crestron.LocalSocketRunnable;
import com.hht.crestron.utils.CrestronThreadPool;
import com.hht.sdk.client.APIManager;

/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronClient
 * @email momo.weiye@gmail.com
 * @time 2019/4/28 16:20
 * @describe
 */
public class CrestronClientService extends Service {

    private CrestronThreadPool threadPool;
    private LocalSocketRunnable client;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        APIManager.connectionService(getApplicationContext());
        client=new LocalSocketRunnable(this);

        threadPool = CrestronThreadPool.getInstance();
        threadPool.execute(client);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        client.close();

        APIManager.disconnectService(getApplicationContext());
    }
}
