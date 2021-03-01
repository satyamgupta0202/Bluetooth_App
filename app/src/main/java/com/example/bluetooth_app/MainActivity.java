package com.example.bluetooth_app;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView status;
    ListView listView;
    Button search;
    BluetoothAdapter bluetooth;
    public void searchclicked (View view){
        status.setText("Searching.........");
        search.setEnabled(false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        status = findViewById(R.id.status);
        search = findViewById(R.id.search);
    }
}