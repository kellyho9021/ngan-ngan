package com.example.blureen.ngannganapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView myListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myListView = (TextView)findViewById(R.id.netResult);
        new MyTask().execute();
    }

    private class MyTask extends AsyncTask<Void, Void, Void>
    {
        String s;

        @Override
        protected Void doInBackground(Void... params) {
            URL url;
            try {
                url = new URL("http://54.183.182.211:8080/hackuci/update.jsp");
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String buff;
                String mess = "";
                while((buff = reader.readLine()) != null)
                {
                    mess += buff;
                }
                reader.close();
                s = mess;
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
        protected void onPostExecute(Void aVoid) {
            myListView.setText(s);
            super.onPostExecute(aVoid);
        }
    }
}

