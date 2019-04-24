package com.hht.crestron.utils;

import android.content.Context;

import com.hht.setting.lib.HHTDeviceDelegate;
import com.hht.setting.lib.HHTDeviceManager;
import com.hht.setting.lib.impl.HHTDeviceCallBackImpl;

/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronClient
 * @email momo.weiye@gmail.com
 * @time 2019/4/10 9:36
 * @describe Collection Crestron Command
 */
public class CrestronCommandManager {

    public static final int JOIN_NUMBER_VOLUME = 5012;

    public static final int JOIN_NUMBER_BRIGHT = 5002;

    public static CrestronCommandManager mInstance;

    private HHTDeviceManager hhtDeviceManager;

    private CrestronCommandManager(Context context){
        hhtDeviceManager = new HHTDeviceManager(context,new HHTDeviceCallBackImpl());
    }

    public static CrestronCommandManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new CrestronCommandManager(context);
        }
        return mInstance;
    }


    public void doCrestronCommand(int joinNumber,String value){
        DefaultLogger.verbose("doCrestronCommand tpye:"+joinNumber+",target value:"+value);
        switch (joinNumber){
            case JOIN_NUMBER_VOLUME:{
                //TODO set volume
                hhtDeviceManager.setVolume(Integer.parseInt(value));
            }break;
            case JOIN_NUMBER_BRIGHT:{
                //TODO set bright
                hhtDeviceManager.setBright(Integer.valueOf(value));
            }break;
            default:{

            }break;
        }
    }



}
