package com.hht.crestronserivce.bean.event;

/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronClient
 * @email momo.weiye@gmail.com
 * @time 2019/6/24 11:41
 * @describe
 */
public class EmergencyMessageEvent {

    private String message;

    public EmergencyMessageEvent(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
