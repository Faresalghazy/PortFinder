package com.example.fares.portfinder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.ServerSocket;

public class MainActivity extends AppCompatActivity {

    public EditText editText;

    private void changeText(final EditText editText,final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               editText.setText((value));
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

    class MySocket extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            try {


                        Log.d("E", "#fares Creating socket");

                        String s = null;
                        while (s == null) {
                            Log.d("E","in while loop");
                            ServerSocket myserversocket = null;
                            try {
                                myserversocket = new ServerSocket(0);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d("E", "#fares Created socket");

                            s = String.valueOf(myserversocket.getLocalPort());
                            MainActivity.this.changeText(MainActivity.this.editText,s);
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



