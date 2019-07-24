package com.hht.crestronserivce.utils;

import android.content.Context;
import android.net.ConnectivityManager;


import com.hht.crestronserivce.bean.CrestronBean;
import com.hht.crestronserivce.bean.event.EmergencyMessageEvent;
import com.hht.crestronserivce.global.HHTDeviceBean;
import com.hht.crestronserivce.runnable.LocalClientSocketRunnable;
import com.hht.setting.lib.HHTDeviceManager;
import com.hht.setting.lib.utils.NetworkUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronClient
 * @email momo.weiye@gmail.com
 * @time 2019/4/10 9:36
 * @describe deal crestron command
 */
public class CrestronCommandManager {


    public static final int ETYPE_DIGITAL_DATA = 100;

    public static final int ETYPE_SIMULATION_DATA = 101;

    public static final int ETYPE_SERIAL_DATA = 102;



    //=============================forware join number ==========================================
    public static final int JOIN_NUMBER_FORWARE = 5045;
    //==================================emergency message========================================
    //TODO do this function
    public static final int JOIN_NUMBER_EMERGENCY = 22;


    //====================================power===========================================
    //TODO do this function
    public static final int JOIN_NUMBER_POWER_ON = 5;
    //TODO do this function
    public static final int JOIN_NUMBER_POWER_OFF = 6;
    //TODO do this function
    public static final int JOIN_NUMBER_POWER_OFF_WARNING = 9;

    //========================================audio==========================================
    public static final int JOIN_NUMBER_VOLUME = 5012;

    public static final int JOIN_NUMBER_VOLUME_UP = 5115;

    public static final int JOIN_NUMBER_VOLUME_DOWN = 5116;

    public static final int JOIN_NUMBER_MUTE_ON = 5117;

    public static final int JOIN_NUMBER_MUTE_OFF = 5118;

    //===================================picture=========================================
    public static final int JOIN_NUMBER_BRIGHT = 5002;



    //================================Network=========================================
    public static final int JOIN_NUMBER_ACTIVE_IP = 5040;

    public static final int JOIN_NUMBER_ACTIVE_SUBNET_MASK = 5041;

    public static final int JOIN_NUMBER_ACTIVE_GATEWAY = 5042;

    public static final int JOIN_NUMBER_ACTIVE_DNS = 5043;

    public static final int JOIN_NUMBER_ACTIVE_MAC_ADDRESS = 5044;

    //====================================Source=======================================

    public static final int JOIN_NUMBER_SOURCE_1 = 5070;
    public static final int JOIN_NUMBER_SOURCE_2 = 5071;
    public static final int JOIN_NUMBER_SOURCE_3 = 5072;
    public static final int JOIN_NUMBER_SOURCE_4 = 5073;
    public static final int JOIN_NUMBER_SOURCE_5 = 5074;
    public static final int JOIN_NUMBER_SOURCE_6 = 5075;
    public static final int JOIN_NUMBER_SOURCE_7 = 5076;
    public static final int JOIN_NUMBER_SOURCE_8 = 5077;

    public static final int JOIN_NUMBER_CURRENT_SOURCE = 5010;

    //===========================Info Advanced===========================
    //TODO do this function
    public static final int JOIN_NUMBER_PROJECT_NAME = 5050;
    //TODO do this function
    public static final int JOIN_NUMBER_LOCATION = 5052;

    public static final int JOIN_NUMBER_RESOLUTION = 5054;
    //TODO do this function
    public static final int JOIN_NUMBER_FIRMWARE_VERSION= 5056;

    //===========================Language================================
    public static final int JOIN_NUMBER_LANGUAGE_CODE = 5090;

    public static final int JOIN_NUMBER_DEFAULT_LANGUAGE = 5091;



    private static CrestronCommandManager mInstance;


    private HHTDeviceManager hhtDeviceManager;
    private Context mContext;

    private CrestronCommandManager(Context context) {
        mContext = context.getApplicationContext();
        hhtDeviceManager = new HHTDeviceManager(mContext, null);
        hhtDeviceManager.initHHTMiddlerwareService(mContext);
        initHHTDeviceStatus();
    }

    private void initHHTDeviceStatus() {
        HHTDeviceBean.getInstance().setSourceTypeList(hhtDeviceManager.getSourceTypeList());
    }

    public static CrestronCommandManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (CrestronCommandManager.class){
                if(mInstance == null){
                    mInstance = new CrestronCommandManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     *
     * @param bean
     * @return need forward content
     */
    public boolean isForward(CrestronBean bean){
        DefaultLogger.debug("isForward:" + bean.toString());
        switch (bean.geteType()) {
            case ETYPE_SERIAL_DATA: {
                return bean.getJoinNumber() == JOIN_NUMBER_FORWARE;
            }
            case ETYPE_SIMULATION_DATA:
            case ETYPE_DIGITAL_DATA:
            default: {
                return false;
            }

        }
    }

    /**
     *
     * @param bean
     * @return get forwardcontent,firstly judgment{@link #isForward(CrestronBean)}
     */
    public String getForwardContent(CrestronBean bean){
        return bean.getJoinValue();
    }

    /**
     * execution command
     * @param bean
     * @param localClient
     */
    public void doCrestronCommand(CrestronBean bean, LocalClientSocketRunnable localClient) {
        DefaultLogger.debug("doCrestronCommand data:" + bean.toString());
        switch (bean.geteType()) {
            case ETYPE_DIGITAL_DATA: {
                doDigitalCommand(bean,localClient);
            }
            break;
            case ETYPE_SIMULATION_DATA: {
                doSimulationCommand(bean,localClient);
            }
            break;
            case ETYPE_SERIAL_DATA: {
                doSerialCommand(bean,localClient);
            }
            break;
            default: {

            }
            break;
        }
    }


    private void doSimulationCommand(CrestronBean bean, LocalClientSocketRunnable localClient) {
        DefaultLogger.debug("doSimulationCommand");
        switch (bean.getJoinNumber()) {
            case JOIN_NUMBER_EMERGENCY:{
               //TODO
            }break;
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


    private void doSerialCommand(CrestronBean bean, LocalClientSocketRunnable localClient) {
        DefaultLogger.debug("doSerialCommand");
        switch (bean.getJoinNumber()) {
            case JOIN_NUMBER_EMERGENCY:{
                //show toast emergency message
                EventBus.getDefault().post(new EmergencyMessageEvent(bean.getJoinValue()));
            }break;
            case JOIN_NUMBER_ACTIVE_IP:{
                localClient.send(NetworkUtils.getIPAddress(false));
            }break;
            case JOIN_NUMBER_ACTIVE_SUBNET_MASK:{
                localClient.send(NetworkUtils.getSubnetMask(mContext));
            }break;
            case JOIN_NUMBER_ACTIVE_GATEWAY:{
                localClient.send(NetworkUtils.getActiveGateway(mContext));
            }break;
            case JOIN_NUMBER_ACTIVE_DNS:{
                localClient.send(NetworkUtils.getDNS1());
            }break;
            case JOIN_NUMBER_ACTIVE_MAC_ADDRESS:{
                int status = NetworkUtils.getNetworkConnectActiveType(mContext);
                if (status == ConnectivityManager.TYPE_WIFI) {
                    localClient.send(NetworkUtils.getMacAddressByWifi(mContext));
                } else {
                    localClient.send(NetworkUtils.getMacAddressByEthernet());
                }
            }break;
            case JOIN_NUMBER_SOURCE_1:
            case JOIN_NUMBER_SOURCE_2:
            case JOIN_NUMBER_SOURCE_3:
            case JOIN_NUMBER_SOURCE_4:
            case JOIN_NUMBER_SOURCE_5:
            case JOIN_NUMBER_SOURCE_6:
            case JOIN_NUMBER_SOURCE_7:
            case JOIN_NUMBER_SOURCE_8:{
                int index = bean.getJoinNumber() - JOIN_NUMBER_SOURCE_1;
                List<String> sourceList = HHTDeviceBean.getInstance().getSourceTypeList();
                if(sourceList != null && sourceList.size()> index){
                    localClient.send(sourceList.get(index));
                }else{
                    localClient.send("Null");
                }
            }break;
            case JOIN_NUMBER_CURRENT_SOURCE:{
                localClient.send(hhtDeviceManager.getTopSourceType());
            }break;
            default:{

            }break;
        }

    }


    private void doDigitalCommand(CrestronBean bean, LocalClientSocketRunnable localClient) {
        DefaultLogger.debug("doDigitalCommand");
        switch (bean.getJoinNumber()) {
            case JOIN_NUMBER_POWER_ON:{
                //TODO
            }break;
            case JOIN_NUMBER_POWER_OFF:{
                //TODO
            }break;
            case JOIN_NUMBER_EMERGENCY:{
                //TODO
            }break;
            case JOIN_NUMBER_VOLUME_UP:{
                hhtDeviceManager.setVolumeUp(true);
            }break;
            case JOIN_NUMBER_VOLUME_DOWN:{
                hhtDeviceManager.setVolumeDown(true);
            }break;
            case JOIN_NUMBER_MUTE_ON:{
                hhtDeviceManager.setMute(true);
            }break;
            case JOIN_NUMBER_MUTE_OFF:{
                hhtDeviceManager.setMute(false);
            }break;
            case JOIN_NUMBER_SOURCE_1:
            case JOIN_NUMBER_SOURCE_2:
            case JOIN_NUMBER_SOURCE_3:
            case JOIN_NUMBER_SOURCE_4:
            case JOIN_NUMBER_SOURCE_5:
            case JOIN_NUMBER_SOURCE_6:
            case JOIN_NUMBER_SOURCE_7:
            case JOIN_NUMBER_SOURCE_8:{
                int index = bean.getJoinNumber() - JOIN_NUMBER_SOURCE_1;
                List<String> sourceList = HHTDeviceBean.getInstance().getSourceTypeList();
                if(sourceList != null && sourceList.size()> index){
                    hhtDeviceManager.openSource(sourceList.get(index));
                }
            }break;
            default:{

            }break;
        }

    }



}
