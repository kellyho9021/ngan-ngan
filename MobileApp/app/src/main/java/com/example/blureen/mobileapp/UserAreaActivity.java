package com.example.blureen.mobileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final EditText etUserName = (EditText)findViewById(R.id.etUserName);
        final EditText etDeviceID = (EditText)findViewById(R.id.etDeviceID);
        final EditText etStatus = (EditText)findViewById(R.id.etStatus);
        final Button changeAction = (Button)findViewById(R.id.btChangeAction);
        final TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
    }
}
