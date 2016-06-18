package com.example.blureen.mobileapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BluReen on 6/17/2016.
 */
public class RegisterRequest extends StringRequest {
    private static final String REGISTER_URL = "http://54.183.182.211:8080/Ngan-Ngan/register.html";
    private Map<String, String> params;

    public RegisterRequest(String fName, String lName, String email, String username, String password, Response.Listener<String> listener){
        super(Method.POST, REGISTER_URL, listener, null);
        params = new HashMap<>();
        params.put("fname", fName);
        params.put("lname", lName);
        params.put("email", email);
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
