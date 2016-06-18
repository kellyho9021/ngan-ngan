package com.example.blureen.mobileapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etFirstName = (EditText)findViewById(R.id.etFirstName);
        final EditText etLastName = (EditText)findViewById(R.id.etLastName);
        final EditText etEmail = (EditText)findViewById(R.id.etEmail);
        final EditText etUserName = (EditText)findViewById(R.id.etUserName);
        final EditText etPassword = (EditText)findViewById(R.id.etPassword);
        final Button btRegister = (Button)findViewById(R.id.btRegister);
        final TextView reset = (TextView) findViewById(R.id.tvReset);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fname = etFirstName.getText().toString();
                final String lname = etLastName.getText().toString();
                final String email = etEmail.getText().toString();
                final String username = etUserName.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success)
                            {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }
                            else
                            {
                                AlertDialog.Builder builder= new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed.").setNegativeButton("Retry", null).create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(fname, lname, email, username, password, listener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}