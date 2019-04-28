package com.hht.crestron;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hht.crestron.LocalSocketRunnable;
import com.hht.crestron.R;
import com.hht.crestron.utils.CrestronThreadPool;
import com.hht.sdk.client.APIManager;


/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronClientActivity
 * @email momo.weiye@gmail.com
 * @time 2019/4/9 19:16
 * @describe
 */
public class CrestronClientActivity extends Activity implements View.OnClickListener {

    LocalSocketRunnable client;
    EditText et_clientSend;
    TextView tv_showReceiveDataClient;
    Button btn_send;

    //    ClientLastly client;
    StringBuffer receiveData=new StringBuffer();

    Handler handler=new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.arg1==0x12) {
                receiveData.append((String)msg.obj);
                tv_showReceiveDataClient.setText(receiveData);
                receiveData.append("\r\n");
            }

            return false;
        }
    });

    private CrestronThreadPool threadPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        APIManager.connectionService(getApplicationContext());

        et_clientSend=(EditText) findViewById(R.id.et_clientSend);
        tv_showReceiveDataClient=(TextView) findViewById(R.id.tv_showReceiveDataClient);
        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        client=new LocalSocketRunnable(handler,this);
        //new Thread(client).start();

        threadPool = CrestronThreadPool.getInstance();
        threadPool.execute(client);
    }


    public void btn_clientSend(){
        client.send(et_clientSend.getText().toString()+"");
        et_clientSend.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.close();
        handler.removeCallbacksAndMessages(null);
        handler = null;


        APIManager.disconnectService(this.getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:{
              btn_clientSend();
            }break;
        }
    }
}