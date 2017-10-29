package com.example.fares.portfinder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public EditText editText;
    private ArrayList<String> ports = new ArrayList<>();
    private ListView listView;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        adapter = new PortListAdapter(MainActivity.this, ports);

    }

    private void updateListView(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(value)) {
                    ports.add(0, value);
                    listView.setAdapter(adapter);
                }
            }
        });
    }

    private boolean isConnectedtoInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void FindPort(View v) {
        if (isConnectedtoInternet(MainActivity.this)) {
            MySocket mysocket = new MySocket();
            mysocket.execute();
        } else {
            Toast.makeText(MainActivity.this, "Not connected to internet", Toast.LENGTH_SHORT).show();
        }
    }

    class MySocket extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Log.d("E", "#fares Creating socket");

                String s = null;
                while (s == null) {
                    Log.d("E", "in while loop");
                    ServerSocket myserversocket = null;
                    try {
                        myserversocket = new ServerSocket(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("E", "#fares Created socket");

                    s = String.valueOf(myserversocket.getLocalPort());
                    MainActivity.this.updateListView(s);
                    try {
                        myserversocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            } catch (Exception e) {
                Log.d("E", e.getMessage());

                Log.d("E", "doinbackground exception");
            }
            return null;
        }
    }
}



