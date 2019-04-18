package com.hht.crestron.bean;

/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronClient
 * @email momo.weiye@gmail.com
 * @time 2019/4/18 14:57
 * @describe
 */
public class CrestronResponseBean {

    private int eType;

    public CrestronResponseBean() {
    }

    public CrestronResponseBean(int eType) {
        this.eType = eType;
    }


    @Override
    public String toString() {
        return "CrestronResponseBean{" +
                "eType=" + eType +
                '}';
    }
}
