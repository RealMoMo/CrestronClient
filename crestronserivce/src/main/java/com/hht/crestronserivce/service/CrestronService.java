package com.hht.crestronserivce.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;


import com.hht.crestronserivce.runnable.LocalServiceSocketRunnable;
import com.hht.crestronserivce.utils.CrestronThreadPool;
import com.hht.sdk.client.APIManager;

/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronClient
 * @email momo.weiye@gmail.com
 * @time 2019/4/28 16:20
 * @describe
 */
public class CrestronService extends Service {

    private LocalServiceSocketRunnable serviceSocketRunnable;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        APIManager.connectionService(getApplicationContext());
        serviceSocketRunnable = new LocalServiceSocketRunnable(this);
        CrestronThreadPool.getInstance().execute(serviceSocketRunnable);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        serviceSocketRunnable.close();

        APIManager.disconnectService(getApplicationContext());
    }
}
