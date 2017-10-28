package com.example.fares.portfinder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.ServerSocket;

public class MainActivity extends AppCompatActivity {

    static EditText editText;

    public static void ChangeEditText(String s){
        editText.setText(s);
    }
    class MySocket extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... strings) {
            Log.d("E", "#fares in background");
            try {
                Log.d("E", "#fares Creating socket");
                ServerSocket myserversocket = new ServerSocket(0);
                Log.d("E", "#fares Created socket");

                String s = String.valueOf(myserversocket.getLocalPort());
                myserversocket.close();
                MainActivity.ChangeEditText(s);
                Log.d("E", "Port = " + strings[0]);
            } catch (Exception e) {
                Log.d("E", e.getStackTrace().toString());
            }
            return null;
        }


    }
    private boolean isConnectedtoInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void FindPort(View v) {
        if (isConnectedtoInternet(MainActivity.this)) {
            editText = (EditText) findViewById(R.id.editText);
            
            MySocket mysocket = new MySocket();
            mysocket.execute();

        } else {
            Toast.makeText(MainActivity.this, "Not connected to internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}



