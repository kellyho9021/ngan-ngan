package com.example.blureen.mobileapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Handler;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class UserAreaActivity extends AppCompatActivity {

    Handler handler = new Handler();
    String action;
    String status;

    EditText etUserName;
    EditText etDeviceID;
    EditText etStatus;
    TextView tvWelcome;

    String STATUS_URL = "http://54.183.182.211:8080/Ngan-Ngan/Binh?dkey=GiCungDuoc";
    String ACTION_CHANGE_URL = "http://54.183.182.211:8080/Ngan-Ngan/Binh?dkey=GiCungDuoc&action=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        etUserName = (EditText)findViewById(R.id.etUserName);
        etDeviceID = (EditText)findViewById(R.id.etDeviceID);
        etStatus = (EditText)findViewById(R.id.etStatus);
        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        handler.post(runnableCode);
    }

    public void changeAction(View view)
    {
        if(action.equals("off"))
            action = "on";
        else if(action.equals("on"))
            action = "off";
        new MyTask().execute(ACTION_CHANGE_URL + action);
    }

    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            new MyTask().execute(STATUS_URL);
            handler.postDelayed(runnableCode, 2000);
        }
    };

    private class MyTask extends AsyncTask<String, String, String>
    {
        String s;

        @Override
        protected String doInBackground(String... params) {
            URL url;
            try {
                url = new URL(params[0]);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String buff;
                String mess = "";

                while((buff = reader.readLine()) != null)
                {
                    mess += buff;
                }
                String[] words = mess.split(" ");
                action = words[0];
                status = words[1];
                reader.close();
                s = status;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                s = e.toString();
            }catch (IOException e)
            {
                e.printStackTrace();
                s = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            etStatus.setText(s);
            etDeviceID.setText("GiCungDuoc");
            super.onPostExecute(aVoid);
        }
    }
}
