package com.hht.crestron;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hht.crestron.LocalSocketRunnable;
import com.hht.crestron.R;


/**
 * @author Realmo
 * @version 1.0.0
 * @name CrestronClientActivity
 * @email momo.weiye@gmail.com
 * @time 2019/4/9 19:16
 * @describe
 */
public class CrestronClientActivity extends Activity {

    LocalSocketRunnable client;
    EditText et_clientSend;
    TextView tv_showReceiveDataClient;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        et_clientSend=(EditText) findViewById(R.id.et_clientSend);
        tv_showReceiveDataClient=(TextView) findViewById(R.id.tv_showReceiveDataClient);

        client=new LocalSocketRunnable(handler);
        new Thread(client).start();


    }


    public void btn_clientSend(View view){
        client.send(et_clientSend.getText().toString()+"");
        et_clientSend.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.close();
    }
}