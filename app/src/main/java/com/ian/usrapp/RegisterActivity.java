package com.ian.usrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.ian.usrapp.Util.UsrPost;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {
    AppCompatEditText ed_user,ed_email,ed_password,ed_password2;
    String user,email,password,password2;
    JSONObject json;
    MainApp app;
    boolean isRegister = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ed_user = findViewById(R.id.ed_username);
        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        ed_password2 = findViewById(R.id.ed_password2);

        json = new JSONObject();
        app = (MainApp) getApplication();
    }

    public void Register(View view) {

        user = ed_user.getText().toString();
        email = ed_email.getText().toString();
        password = ed_password.getText().toString();
        password2 = ed_password2.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    json.put("username", user);
                    json.put("email", email);
                    json.put("password", password);
                    json.put("password2", password2);
                    app.Log(json.toString());
                    UsrPost usrPost = new UsrPost(app.register_url);
                    String response = usrPost.doPost(json);
                    JSONObject jsonObject = new JSONObject(response);
                    app.Log(jsonObject.get("response").toString());
                    if (jsonObject.get("response").toString().equals("successfully ")){
                        app.setUser(user);
                        app.setPwd(password);
                        finish();
//                        MyHandler handler = new MyHandler();
//                        handler.sendEmptyMessage(0);
                     }

                    app.Log(response);
                } catch (IOException|JSONException e) {
                    app.Log(e.toString());
                    e.printStackTrace();
                }
            }
        }).start();

    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            app.Log("Here");
            RegisterActivity.this.finish();
        }
    }
}