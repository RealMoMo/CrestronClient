package com.hht.crestronserivce.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hht.setting.lib.HHTDeviceManager;
import com.hht.setting.lib.i.HHTDeviceCallBack;
import com.hht.setting.lib.impl.HHTDeviceCallBackImpl;

import java.util.ArrayList;

/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronClient
 * @email momo.weiye@gmail.com
 * @time 2019/6/6 8:54
 * @describe Get device stauts && device status changed
 */
public class CrestronDeviceManager<T extends HHTDeviceCallBack> extends HHTDeviceManager {

    private ArrayList<T> callBackList;
    private static CrestronDeviceManager mInstance;

    private CrestronDeviceManager(@NonNull Context context, @Nullable HHTDeviceCallBack callBack) {
        super(context, callBack);
        callBackList = new ArrayList<T>(2);
    }

    public static CrestronDeviceManager getInstance(Context context) {
        if(mInstance == null){
            synchronized (CrestronDeviceManager.class){
                if(mInstance == null){
                    mInstance = new CrestronDeviceManager(context.getApplicationContext(),new CrestronDeviceCallBack());
                }
            }
        }
        return mInstance;
    }

    public boolean registerDeviceCallBack(T callBack) {
        return callBackList.add(callBack);
    }

    public boolean unregisterDeviceCallBack(T callBack) {
        return callBackList.remove(callBack);
    }


    private static class CrestronDeviceCallBack<T extends HHTDeviceCallBack> extends HHTDeviceCallBackImpl {

        private CrestronDeviceCallBack() {
        }

        @Override
        public void onMuteChange(boolean mute) {
            super.onMuteChange(mute);
            for (int i = 0; i < mInstance.callBackList.size(); i++) {
                if(mInstance.callBackList.get(i) instanceof HHTDeviceCallBack){
                    ((HHTDeviceCallBack) mInstance.callBackList.get(i)).onMuteChange(mute);
                }
            }

        }

        @Override
        public void onVolumeChange(int value) {
            super.onVolumeChange(value);
            for (int i = 0; i < mInstance.callBackList.size(); i++) {
                if(mInstance.callBackList.get(i) instanceof HHTDeviceCallBack){
                    ((HHTDeviceCallBack) mInstance.callBackList.get(i)).onVolumeChange(value);
                }
            }
        }

        @Override
        public void onBrightnessChange(int value) {
            super.onBrightnessChange(value);
            for (int i = 0; i < mInstance.callBackList.size(); i++) {
                if(mInstance.callBackList.get(i) instanceof HHTDeviceCallBack){
                    ((HHTDeviceCallBack) mInstance.callBackList.get(i)).onBrightnessChange(value);
                }
            }
        }
    }

}
