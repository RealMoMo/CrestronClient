package com.hht.crestronserivce;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hht.crestronserivce.service.CrestronService;
import com.hht.crestronserivce.utils.DefaultLogger;


/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronService
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
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void releaseEnvironment(){
        Intent intent = new Intent(this, CrestronService.class);
        startService(intent);
    }
}
