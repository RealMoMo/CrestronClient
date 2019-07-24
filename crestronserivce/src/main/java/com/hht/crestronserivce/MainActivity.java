package com.hht.crestronserivce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.hht.crestronserivce.bean.event.EmergencyMessageEvent;
import com.hht.crestronserivce.runnable.LocalServiceSocketRunnable;
import com.hht.crestronserivce.utils.CrestronThreadPool;
import com.hht.crestronserivce.utils.ToastUtils;
import com.hht.sdk.client.APIManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private LocalServiceSocketRunnable serviceSocketRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        APIManager.connectionService(this);
        serviceSocketRunnable = new LocalServiceSocketRunnable(this);
        CrestronThreadPool.getInstance().execute(serviceSocketRunnable);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serviceSocketRunnable.close();
        APIManager.disconnectService(getApplicationContext());
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEmergencyMessageEvent(EmergencyMessageEvent event){
        ToastUtils.toast(this,event.getMessage(), Toast.LENGTH_SHORT);
    }
}
