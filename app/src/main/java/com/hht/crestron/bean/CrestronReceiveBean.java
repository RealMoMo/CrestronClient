package com.hht.crestron.bean;

/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronClient
 * @email momo.weiye@gmail.com
 * @time 2019/4/18 13:49
 * @describe
 */
public class CrestronReceiveBean {

    /**
     * data format:
        eType:100,joinNumber:5,joinValue:0
        eType:102,joinNumber:5075,joinValue:HDMI 6eType:100,joinNumber:5075,joinValue:0
     */
    private int eType;
    private int joinNumber;
    private String joinValue;


    public CrestronReceiveBean() {
    }

    public CrestronReceiveBean(int eType, int joinNumber, String joinValue) {
        this.eType = eType;
        this.joinNumber = joinNumber;
        this.joinValue = joinValue;
    }

    public CrestronReceiveBean(String[] data){
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

    @Override
    public String toString() {
        return "CrestronReceiveBean{" +
                "eType=" + eType +
                ", joinNumber=" + joinNumber +
                ", joinValue='" + joinValue + '\'' +
                '}';
    }
}
