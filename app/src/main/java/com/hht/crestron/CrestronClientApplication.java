package com.hht.crestron;

import android.app.Application;
import android.os.Debug;
import android.os.Environment;
import android.os.Trace;

import com.hht.crestron.utils.CrashHandler;

import java.io.File;

/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronClient
 * @email momo.weiye@gmail.com
 * @time 2019/4/26 14:43
 * @describe
 */
public class CrestronClientApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();




       // Debug.startMethodTracing(Environment.getExternalStorageDirectory()+"/newline/test.trace");

        //TODO something
       initConfig();

       // Debug.stopMethodTracing();

    }

    private void initConfig(){
        CrashHandler.getInstance().init(this);
    }
}
