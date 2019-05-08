package com.hht.crestron.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hht.crestron.service.CrestronClientService;

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
                Intent crestronIntent = new Intent(context, CrestronClientService.class);
                context.startService(crestronIntent);
            }break;
        }
    }
}
