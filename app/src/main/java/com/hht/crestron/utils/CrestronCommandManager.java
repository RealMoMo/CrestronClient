package com.hht.crestron.utils;

import android.content.Context;

import com.hht.crestron.bean.CrestronBean;
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

    public static final int ETYPE_DIGITAL_DATA = 100;

    public static final int ETYPE_SIMULATION_DATA = 101;

    public static final int ETYPE_SERIAL_DATA = 102;


    public static CrestronCommandManager mInstance;

    private HHTDeviceManager hhtDeviceManager;

    private CrestronCommandManager(Context context) {
        hhtDeviceManager = new HHTDeviceManager(context, new HHTDeviceCallBackImpl());
    }

    public static CrestronCommandManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CrestronCommandManager(context);
        }
        return mInstance;
    }


    public void doCrestronCommand(CrestronBean bean) {
        DefaultLogger.verbose("doCrestronCommand data:" + bean.toString());
        switch (bean.geteType()) {
            case ETYPE_DIGITAL_DATA: {
                doDigitalCommand(bean);
            }
            break;
            case ETYPE_SIMULATION_DATA: {
                doSimulationCommand(bean);
            }
            break;
            case ETYPE_SERIAL_DATA: {
                doSerialCommand(bean);
            }
            break;
            default: {

            }
            break;
        }
    }


    private void doSimulationCommand(CrestronBean bean) {
        DefaultLogger.verbose("doSimulationCommand");
        switch (bean.getJoinNumber()) {
            case JOIN_NUMBER_VOLUME: {
                //set volume
                hhtDeviceManager.setVolume(Integer.parseInt(bean.getJoinValue()));
            }
            break;
            case JOIN_NUMBER_BRIGHT: {
                //set bright
                hhtDeviceManager.setBright(Integer.valueOf(bean.getJoinValue()));
            }
            break;
            default: {

            }
            break;
        }

    }


    private void doSerialCommand(CrestronBean bean) {
        DefaultLogger.verbose("doSerialCommand");
        switch (bean.getJoinNumber()) {
            default:{

            }break;
        }

    }


    private void doDigitalCommand(CrestronBean bean) {
        DefaultLogger.verbose("doDigitalCommand");
        switch (bean.getJoinNumber()) {
            default:{

            }break;
        }

    }


}
