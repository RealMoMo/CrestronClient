package com.hht.crestronserivce.global;

import android.content.Context;

import com.hht.crestronserivce.utils.CrestronCommandManager;
import com.hht.setting.lib.HHTDeviceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronClient
 * @email momo.weiye@gmail.com
 * @time 2019/7/23 20:47
 * @describe
 */
public class HHTDeviceBean {

    private static HHTDeviceBean mInstance;

    private List<String> sourceTypeList;

    private HHTDeviceBean() {
        sourceTypeList = new ArrayList<>(8);
    }

    public static HHTDeviceBean getInstance() {
        if (mInstance == null) {
            synchronized (CrestronCommandManager.class){
                if(mInstance == null){
                    mInstance = new HHTDeviceBean();
                }
            }
        }
        return mInstance;
    }


    public List<String> getSourceTypeList() {
        return sourceTypeList;
    }

    public void setSourceTypeList(List<String> sourceTypeList) {
        this.sourceTypeList = sourceTypeList;
    }
}
