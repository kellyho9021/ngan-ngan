package com.example.blureen.mobileapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BluReen on 6/17/2016.
 */
public class LoginRequest extends StringRequest{
    private static final String LOGIN_URL = "view-source:http://54.183.182.211:8080/Ngan-Ngan/Login.html";
    private Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener){
        super(Method.POST, LOGIN_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
