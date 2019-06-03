package com.hht.crestronserivce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hht.crestronserivce.runnable.LocalServiceSocketRunnable;
import com.hht.crestronserivce.utils.CrestronThreadPool;

public class MainActivity extends AppCompatActivity {

    private LocalServiceSocketRunnable serviceSocketRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceSocketRunnable = new LocalServiceSocketRunnable(this);
        CrestronThreadPool.getInstance().execute(serviceSocketRunnable);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serviceSocketRunnable.close();
    }
}
