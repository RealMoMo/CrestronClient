package com.hht.crestron.bean;

import com.hht.crestron.utils.DefaultLogger;

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


    public String getSuccesResponse(){
        DefaultLogger.debug("Ack:"+eType);
        return "Ack:"+eType;
    }

    public String getFailResponse(){
        return "Ack:"+(eType-100);
    }

    @Override
    public String toString() {
        return "CrestronBean{" +
                "eType=" + eType +
                ", joinNumber=" + joinNumber +
                ", joinValue='" + joinValue + '\'' +
                '}';
    }
}
