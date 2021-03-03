package com.example.bluetooth_app;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView status;
    ListView listView;
    Button search;
    BluetoothAdapter bluetoothAdapter;
    ArrayList<String> bluetoothDevice = new ArrayList<String>();
    ArrayList<String> addresses = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
            Log.i("Action",action);

            if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                status.setText("Finished");
                search.setEnabled(true);
            }
            else if (BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String name = device.getName();
                String address = device.getAddress();
                String rssi =   Integer.toString(intent.getShortExtra(BluetoothDevice.EXTRA_RSSI , Short.MIN_VALUE));

                if(!addresses.contains(address)){
                    addresses.add(address);
                    Log.i("Device Found"," name--"+name+ " " +"address---- "+address+ " " +"strenght--- "+rssi+ " " );
                    String temp="";
                    if(name.equals("")){
                        temp=temp+(address + " signal: "+ rssi + "db");
                    }
                    else{
                        temp=temp+(name + "signal strength "+ rssi + "db");
                    }

                    bluetoothDevice.add(temp);
                    arrayAdapter.notifyDataSetChanged();
                }

            }
        }
    };




    public void searchclicked (View view){
        status.setText("Searching.........");
        search.setEnabled(false);
        bluetoothDevice.clear();
        addresses.clear();
        bluetoothAdapter.startDiscovery();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        status = findViewById(R.id.status);
        search = findViewById(R.id.search);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,bluetoothDevice);
        listView.setAdapter(arrayAdapter);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        registerReceiver( broadcastReceiver, intentFilter);

    }
}