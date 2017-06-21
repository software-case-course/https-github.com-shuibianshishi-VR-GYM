package com.example.jim.sensor;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by saihor on 2017/6/21.
 */

public class SenserUtil {


    public static int gobalCount;
    public static String bluetoothAddress;
    private static BluetoothDevice device;
    private static BluetoothAdapter adapter;
    private static BluetoothSocket clientSocket;
    private static OutputStream os;//输出流
    private static BluetoothSocket socket;
    private static final UUID MY_UUID = UUID
            .fromString("abcd1234-ab12-ab12-ab12-abcdef123456");//随便定义一个
    public static void WriteSportMessage(String message){
       // initSocket();
        adapter = BluetoothAdapter.getDefaultAdapter();
        try {
            try {
                if (device == null) {
                    //获得远程设备
                    device = adapter.getRemoteDevice(bluetoothAddress);
                }
                if (clientSocket == null) {
                    //创建客户端蓝牙Socket
                    clientSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
                    //开始连接蓝牙，如果没有配对则弹出对话框提示我们进行配对
                    clientSocket.connect();
                    //获得输出流（客户端指向服务端输出文本）
                    os = clientSocket.getOutputStream();
                }
            } catch (Exception e) {
            }
            if (os != null) {
                //往服务端写信息
                gobalCount++;
                String str = message+":"+String.valueOf(gobalCount);
                os.write(str.getBytes("utf-8"));
            }else{
                Log.v("xxx","error2");
            }
        } catch (Exception e) {
        }
    }

    private static void initSocket() {
        BluetoothSocket temp = null;
        try {
            Method m = device.getClass().getMethod(
                    "createRfcommSocket", new Class[] { int.class });
            temp = (BluetoothSocket) m.invoke(device, 29);//这里端口为29
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        socket = temp;


    }

}
