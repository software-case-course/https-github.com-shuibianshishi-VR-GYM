package soup.mushroom.lux.bluetoothzengdiaomodel;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BlueSendInfoActivity extends AppCompatActivity {


    private int moveCount;

    private BluetoothAdapter adapter;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private ListView mMatchedList;//已匹配设备
    private ListView mNearList;//可匹配设备
    private List<String> Device_name = new ArrayList<String>();//放可用设备的名字
    private List<BluetoothDevice> Device = new ArrayList<BluetoothDevice>();//放可用设备
    private List<String> Device_name2 = new ArrayList<String>();//放已配对设备的名字
    private Set<BluetoothDevice> Devices;//放已配对设备
    private DeviceAdapter mDeviceadapter;
    private DeviceAdapter mDeviceadapter2;
    private Handler mHandler;

    private final UUID MY_UUID = UUID
            .fromString("abcd1234-ab12-ab12-ab12-abcdef123456");//随便定义一个
    private BluetoothSocket clientSocket;
    private OutputStream os;//输出流

    private static  final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION  = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        initView();
        initData();
        initListener();

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 101:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = BluetoothAdapter.getDefaultAdapter();
                                Devices=adapter.getBondedDevices();
                                if (Devices.size()>0){

                                    for (BluetoothDevice bluetoothDevice:Devices){
                                        if (!Device_name2.contains(bluetoothDevice.getName()))
                                            Device_name2.add(bluetoothDevice.getName()+ ":" + device.getAddress());
                                    }
                                            mDeviceadapter2 = new DeviceAdapter(BlueSendInfoActivity.this,R.layout.device_item, Device_name2);
                                            mMatchedList.setAdapter(mDeviceadapter2);
                                }
                            }
                        });
                        break;
                }
            }
        };
    }




    private BroadcastReceiver receiver  = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();//获得已经搜索到的蓝牙设备
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //搜索到的蓝牙设备加入到一个list中
                if(device.getName()!=null){
                    Device_name.add(device.getName()+ ":" + device.getAddress());
                    Device.add(device);
                    mDeviceadapter.notifyDataSetChanged();
                }
            }
        }
    };

    private void initView(){
        mMatchedList=(ListView)findViewById(R.id.matched_list);
        mNearList=(ListView)findViewById(R.id.near_list);
    }


    private void initData(){
        moveCount = 0;
        adapter = BluetoothAdapter.getDefaultAdapter();

        mDeviceadapter = new DeviceAdapter(BlueSendInfoActivity.this, R.layout.device_item, Device_name);
        mNearList.setAdapter(mDeviceadapter);

        Devices=adapter.getBondedDevices();

        if (Devices.size()>0){

            for (BluetoothDevice bluetoothDevice:Devices){
                if (!Device_name2.contains(bluetoothDevice.getName()))
                    Device_name2.add(bluetoothDevice.getName()+ ":" + bluetoothDevice.getAddress());

            }
            mDeviceadapter2 = new DeviceAdapter(BlueSendInfoActivity.this,R.layout.device_item, Device_name2);
            mMatchedList.setAdapter(mDeviceadapter2);
        }


    }

    private void initListener(){
        mNearList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.cancelDiscovery();
                        device = Device.get(position);
                        int connetTime = 0;
                        boolean connected = false;
                        initSocket();
                        while (!connected && connetTime <= 5) {
                            try {

                                if(socket!=null) {
                                    socket.connect();
                                    connected = true;
                                }else{
                                    connetTime++;
                                    connected = false;
                                    mHandler.sendEmptyMessage(101);
                                    // return;
                                }

                            } catch (Exception e1) {

                                // 关闭 socket
                                try {
                                    socket.close();
                                    socket = null;

                                } catch (Exception e2) {
                                    //TODO: handle exception

                                }
                            } finally {

                            }
                        }
                    }
                }).start();

            }

        });





        mMatchedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = Device_name2.get(position);
                String address = s.substring(s.indexOf(":") + 1).trim();//把地址解析出来
                //主动连接蓝牙服务端
                try {
                    //判断当前是否正在搜索
                    if (adapter.isDiscovering()) {
                        adapter.cancelDiscovery();
                    }
                    try {
                        if (device == null) {
                            //获得远程设备
                            device = adapter.getRemoteDevice(address);
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
                        moveCount++;
                        String str = "运动次数"+String.valueOf(moveCount);
                        os.write(str.getBytes("utf-8"));
                    }
                } catch (Exception e) {
                }
            }
        });
    }




    private void initSocket() {
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


    //点击配对发生的事件
    public void pair() {
        if (adapter == null)
        {
        }
        // 打开蓝牙
        if (!adapter.isEnabled())
        {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            // 设置蓝牙可见性，最多300秒
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(intent);

        }
        if(adapter.isEnabled()){
            IntentFilter mFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND );
            mFilter.addAction(BluetoothDevice.ACTION_FOUND);
            mFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
            mFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
            mFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
            mFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            // 注册广播接收器，接收并处理搜索结果

            if (Build.VERSION.SDK_INT >= 23) {
                //校验是否已具有模糊定位权限
                if (ContextCompat.checkSelfPermission(BlueSendInfoActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(BlueSendInfoActivity.this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                } else {
                    //具有权限
                    adapter.startDiscovery();
                }
            } else {
                //系统不高于6.0直接执行
                adapter.startDiscovery();
            }
            Device_name.clear();
            registerReceiver(receiver,mFilter);
            //查找已经配对的设备，加载到Matched列表中
            Devices=adapter.getBondedDevices();
            if (Devices.size()>0){
                for (BluetoothDevice bluetoothDevice:Devices){
                    if (!Device_name2.contains(bluetoothDevice.getName()))
                        Device_name2.add(bluetoothDevice.getName()+ ":" + device.getAddress());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mDeviceadapter2.notifyDataSetChanged();
                        }
                    });

                }
            }
        }

    }


    public void pairThread(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                pair();
                mHandler.sendEmptyMessage(102);
            }
        }).start();
    }




    //点击连接按钮发生的事件
    public void connect(View view) {
        Intent intent = new Intent(BlueSendInfoActivity.this,BlueAcceptInfoActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
