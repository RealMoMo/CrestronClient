package com.hht.crestronserivce.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;

import com.hht.crestronserivce.service.CrestronService;

/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronClient
 * @email momo.weiye@gmail.com
 * @time 2019/4/28 16:56
 * @describe
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action){
            default:{
              startCrestronService(context);
            }break;
        }
    }

    private void startCrestronService(Context context){
        ApplicationInfo info = context.getApplicationInfo();
        if((info.flags & ApplicationInfo.FLAG_DEBUGGABLE )!= 0){
           //do nothing
        }else{
            Intent crestronIntent = new Intent(context, CrestronService.class);
            context.startService(crestronIntent);
        }
    }
}
