package com.hht.crestronserivce.bean;


import com.hht.crestronserivce.utils.CrestronCommandManager;
import com.hht.crestronserivce.utils.DefaultLogger;

/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronClient
 * @email momo.weiye@gmail.com
 * @time 2019/4/18 13:49
 * @describe
 */
public class CrestronBean {
    /**
     * eType: 100 数字数据 joinNumber(int)对应大屏数字化控制资源,joninValue(boolean 0/1)
     *        101 模拟数据 joinNumber(int)对应大屏模拟化控制资源,joninValue(int)
     *        102 串行数据 joinNumber(int)对应大屏串行资源，joinValue(String)
     */
    /**
     * data format:
        eType:100,joinNumber:5,joinValue:0
     */
    private int eType;
    private int joinNumber;
    private String joinValue;


    public CrestronBean() {
    }

    public CrestronBean(int eType, int joinNumber, String joinValue) {
        this.eType = eType;
        this.joinNumber = joinNumber;
        this.joinValue = joinValue;
    }

    public CrestronBean(String[] data){
        this.eType = Integer.parseInt(data[0]);
        this.joinNumber = Integer.parseInt(data[1]);
        this.joinValue = data[2];
    }

    public int geteType() {
        return eType;
    }

    public void seteType(int eType) {
        this.eType = eType;
    }

    public void seteType(String eType) {
        this.eType = Integer.parseInt(eType);
    }

    public int getJoinNumber() {
        return joinNumber;
    }

    public void setJoinNumber(int joinNumber) {
        this.joinNumber = joinNumber;
    }

    public void setJoinNumber(String joinNumber) {
        this.joinNumber = Integer.parseInt(joinNumber);
    }

    public String getJoinValue() {
        return joinValue;
    }

    public void setJoinValue(String joinValue) {
        this.joinValue = joinValue;
    }


//    public String getSuccesResponse(){
//        DefaultLogger.debug("Ack:"+eType);
//        return "Ack:"+eType;
//    }
//
//    public String getFailResponse(){
//        return "Ack:"+(eType-100);
//    }

    @Override
    public String toString() {
        return "CrestronBean{" +
                "eType=" + eType +
                ", joinNumber=" + joinNumber +
                ", joinValue='" + joinValue + '\'' +
                '}';
    }


    //TODO test sync data
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

    public String getReplyInfo(){
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
