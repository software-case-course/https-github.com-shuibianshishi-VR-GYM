package com.example.jim.sensor;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.RunnableFuture;

public class BlueAcceptInfoActivity extends AppCompatActivity {



    private BluetoothAdapter mBluetoothAdapter;
    private AcceptThread acceptThread;
    private final UUID MY_UUID = UUID
            .fromString("abcd1234-ab12-ab12-ab12-abcdef123456");//和客户端相同的UUID
    private final String NAME = "Bluetooth_Socket";
    private BluetoothServerSocket serverSocket;
    private BluetoothSocket socket;
    private InputStream is;//输入流
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_accept_info);
        message = (TextView) findViewById(R.id.message);
        runAccept();
    }


    private void runAccept(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        acceptThread = new AcceptThread();
        acceptThread.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(final Message msg) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    message.setText(String.valueOf(msg.obj));
                }
            });
//            Toast.makeText(getApplicationContext(), String.valueOf(msg.obj),
//                    Toast.LENGTH_LONG).show();
            super.handleMessage(msg);
        }
    };

    //服务端监听客户端的线程类
    private class AcceptThread extends Thread {
        public AcceptThread() {
            try {
                serverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (Exception e) {
            }
        }
        public void run() {
            try {
                socket = serverSocket.accept();
                is = socket.getInputStream();
                while(true) {
                    byte[] buffer =new byte[1024];
                    int count = is.read(buffer);
                    Message msg = new Message();
                    msg.obj = new String(buffer, 0, count, "utf-8");
                    handler.sendMessage(msg);
                }
            }
            catch (Exception e) {
            }
        }
    }
}
