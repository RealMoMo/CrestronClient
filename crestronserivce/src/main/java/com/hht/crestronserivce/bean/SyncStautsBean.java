package com.hht.crestronserivce.bean;

import com.hht.crestronserivce.utils.CrestronCommandManager;

/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronClient
 * @email momo.weiye@gmail.com
 * @time 2019/6/5 10:50
 * @describe
 */
public class SyncStautsBean {

    private int brightValue;
    private int volumeValue;

    private static final String SYNC="SYNC:";
    private static final String SEPARATE = ",";

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

    public String getSyncVolumeInfo(){
        return getSyncInfo(CrestronCommandManager.ETYPE_SIMULATION_DATA, CrestronCommandManager.JOIN_NUMBER_VOLUME,volumeValue);
    }

    public String getSyncBrightInfo(){
        return getSyncInfo(CrestronCommandManager.ETYPE_SIMULATION_DATA, CrestronCommandManager.JOIN_NUMBER_BRIGHT,brightValue);
    }

    private String getSyncInfo(int eType,int joinNumber,Object joinValue ){
        StringBuilder info = new StringBuilder();
        info.append(SYNC)
                .append(eType)
                .append(SEPARATE)
                .append(joinNumber)
                .append(SEPARATE)
                .append(joinValue);
        return info.toString();
    }

}
