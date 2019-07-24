package com.hht.crestronserivce.bean;

import android.content.Context;
import android.net.ConnectivityManager;

import com.hht.crestronserivce.global.HHTDeviceBean;
import com.hht.crestronserivce.utils.CrestronCommandManager;
import com.hht.setting.lib.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronClient
 * @email momo.weiye@gmail.com
 * @time 2019/6/24 15:45
 * @describe
 */
public class CrestronSyncBean {

    private static final String SYNC = "SYNC:";
    private static final String SEPARATE = ",";

    private int brightValue;
    private int volumeValue;
    private boolean isMute;
    private String resolution;
    private Context mContext;


    public CrestronSyncBean(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    public int getBrightValue() {
        return brightValue;
    }

    public void setBrightValue(int brightValue) {
        this.brightValue = brightValue;
    }

    public int getVolumeValue() {
        return volumeValue;
    }

    public void setVolumeValue(int volumeValue) {
        this.volumeValue = volumeValue;
    }

    public boolean isMute() {
        return isMute;
    }

    public void setMute(boolean mute) {
        isMute = mute;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getSyncVolumeInfo() {
        return getSyncInfo(CrestronCommandManager.ETYPE_SIMULATION_DATA, CrestronCommandManager.JOIN_NUMBER_VOLUME, volumeValue);
    }

    public String getSyncMuteOn(){
        return getSyncInfo(CrestronCommandManager.ETYPE_DIGITAL_DATA, CrestronCommandManager.JOIN_NUMBER_MUTE_ON, isMute? 1 : 0);
    }

    public String getSyncMuteOff(){
        return getSyncInfo(CrestronCommandManager.ETYPE_DIGITAL_DATA, CrestronCommandManager.JOIN_NUMBER_MUTE_OFF, isMute? 0 : 1);
    }

    public String getSyncBrightInfo() {
        return getSyncInfo(CrestronCommandManager.ETYPE_SIMULATION_DATA, CrestronCommandManager.JOIN_NUMBER_BRIGHT, brightValue);
    }

    public String getSyncSupportEmergencyMessage() {
        return getSyncInfo(CrestronCommandManager.ETYPE_DIGITAL_DATA, CrestronCommandManager.JOIN_NUMBER_EMERGENCY, 1);
    }

    public String getSyncIpAddress() {
        return getSyncInfo(CrestronCommandManager.ETYPE_SERIAL_DATA, CrestronCommandManager.JOIN_NUMBER_ACTIVE_IP, NetworkUtils.getIPAddress(false));
    }

    public String getSyncSubnetMask() {
        return getSyncInfo(CrestronCommandManager.ETYPE_SERIAL_DATA, CrestronCommandManager.JOIN_NUMBER_ACTIVE_SUBNET_MASK, NetworkUtils.getSubnetMask(mContext));
    }

    public String getSyncGateway() {
        return getSyncInfo(CrestronCommandManager.ETYPE_SERIAL_DATA, CrestronCommandManager.JOIN_NUMBER_ACTIVE_GATEWAY, NetworkUtils.getActiveGateway(mContext));
    }

    public String getSyncDns() {
        return getSyncInfo(CrestronCommandManager.ETYPE_SERIAL_DATA, CrestronCommandManager.JOIN_NUMBER_ACTIVE_DNS, NetworkUtils.getDNS1());
    }

    public String getSyncMacAddress() {
        int status = NetworkUtils.getNetworkConnectActiveType(mContext);
        if (status == ConnectivityManager.TYPE_WIFI) {
            return getSyncInfo(CrestronCommandManager.ETYPE_SERIAL_DATA, CrestronCommandManager.JOIN_NUMBER_ACTIVE_MAC_ADDRESS, NetworkUtils.getMacAddressByWifi(mContext));
        } else {
            return getSyncInfo(CrestronCommandManager.ETYPE_SERIAL_DATA, CrestronCommandManager.JOIN_NUMBER_ACTIVE_MAC_ADDRESS, NetworkUtils.getMacAddressByEthernet());
        }

    }


    public String getSyncDeviceResolution(){
        return getSyncInfo(CrestronCommandManager.ETYPE_SERIAL_DATA, CrestronCommandManager.JOIN_NUMBER_RESOLUTION, resolution);
    }

    public String getSyncSourceName(int index,String sourceName){
        return getSyncInfo(CrestronCommandManager.ETYPE_SERIAL_DATA, CrestronCommandManager.JOIN_NUMBER_SOURCE_1+index, sourceName);
    }


    private String getSyncInfo(int eType, int joinNumber, Object joinValue) {
        StringBuilder info = new StringBuilder();
        info.append(SYNC)
                .append(eType)
                .append(SEPARATE)
                .append(joinNumber)
                .append(SEPARATE)
                .append(joinValue);
        return info.toString();
    }


    public List<String> getSyncData() {
        List<String> list = new ArrayList<>(12);
        //list.add(getSyncSupportEmergencyMessage());
        list.add(getSyncBrightInfo());
        list.add(getSyncVolumeInfo());
        list.add(getSyncMuteOn());
        list.add(getSyncMuteOff());
        list.add(getSyncIpAddress());
        list.add(getSyncSubnetMask());
        list.add(getSyncGateway());
        list.add(getSyncDns());
        list.add(getSyncMacAddress());
        list.add(getSyncDeviceResolution());

        List<String> sourceTypeList = HHTDeviceBean.getInstance().getSourceTypeList();
        for (int i =0;i< sourceTypeList.size();i++){
            list.add(getSyncSourceName(i,sourceTypeList.get(i)));
        }
        return list;
    }

}
