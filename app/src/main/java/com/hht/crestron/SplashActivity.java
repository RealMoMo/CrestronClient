package com.hht.crestron;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;

import com.hht.crestron.service.CrestronClientService;
import com.hht.crestron.utils.DefaultLogger;

/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronClient
 * @email momo.weiye@gmail.com
 * @time 2019/4/28 17:12
 * @describe
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationInfo info = this.getApplicationInfo();
        if((info.flags & ApplicationInfo.FLAG_DEBUGGABLE )!= 0){
            debugEnvironment();
        }else{
            releaseEnvironment();
        }

        finish();
    }


    private void debugEnvironment(){
        DefaultLogger.debug("debug mode");
        Intent intent = new Intent(this,CrestronClientActivity.class);
        startActivity(intent);
    }

    private void releaseEnvironment(){
        DefaultLogger.debug("release mode");
        Intent intent = new Intent(this, CrestronClientService.class);
        startService(intent);
    }
}
