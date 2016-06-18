package com.example.blureen.ngannganapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.os.Handler;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();
    TextView myListView;
    int count = 1;
    boolean changeAction = false;
    String action;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myListView = (TextView)findViewById(R.id.netResult);
    }


    public void currentStatus(View view)
    {
        handler.post(runnableCode);
    }

    public void changeAction(View view){
        //DO NOTHING
        if(action.equals("off"))
            action = "on";
        else if(action.equals("on"))
            action = "off";
        new MyTask().execute("http://54.183.182.211:8080/hackuci/Binh?action=" + action);
    }

    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            new MyTask().execute("http://54.183.182.211:8080/hackuci/Binh");
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
                s = "Action : " + action + ", Status: " + status;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                s = e.toString();
            }catch (IOException e)
            {
                e.printStackTrace();
                s = e.toString();
            }
            count++;
            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            myListView.setText(s);
            super.onPostExecute(aVoid);
        }
    }
}

