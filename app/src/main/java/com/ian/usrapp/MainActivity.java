package com.ian.usrapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.google.gson.Gson;
import com.ian.usrapp.Obj.Token;
import com.ian.usrapp.Util.UsrPost;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private AppCompatEditText ed_user, ed_pwd;
    private String usr,pwd;
    private MainApp app;
    private JWT jwt;
    private Token token;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_user = findViewById(R.id.user);
        ed_pwd = findViewById(R.id.pwd);
        app = (MainApp) getApplication();
       // app.setIsAgreed(false);
        gson = new Gson();

        if (!app.getUserName().equals(""))    {
            ed_user.setText(app.getUserName());
        }
        if (!app.getPwdName().equals(""))    {
            ed_pwd.setText(app.getPwdName());
        } 
    }

    public void doLogin(View view) {
        if (!ed_user.getText().equals("")){
            usr = ed_user.getText().toString();
            app.setUser(usr);
        }
        if (!ed_pwd.getText().equals("")){
            pwd = ed_pwd.getText().toString();
            app.setPwd(pwd);
        }
        doAuthentication();
    }

    private void doAuthentication() {
        Log.i("MainActivity","doAuthentication");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = new JSONObject();//建立json物件
                    json.put("username", usr);//使用URLEncoder.encode對特殊和不可見字元進行編碼
                    json.put("password",pwd);
                    UsrPost usrPost = new UsrPost(app.token_url);
                    String response = usrPost.doPost(json);
                    token = gson.fromJson(response,Token.class);
                    app.setToken(token.access);
                    jwt = new JWT(token.access);
                    Log.i("MainActivity",jwt.toString());
                    String user = jwt.getClaim("user_id").asString();
                    app.setUserId(Integer.parseInt(user));
                    if (token!=null){
                        boolean isAgreed = app.getAgree();
                        if (isAgreed == true){
                            Intent it  = new Intent(MainActivity.this, MenuActivity.class);
                            startActivity(it);
                            app.Log("doLogin");
                        }else{
                            Intent it  = new Intent(MainActivity.this, Agreement.class);
                            startActivity(it);                        }
                    }
                } catch (Exception e) {
                    for(StackTraceElement se:e.getStackTrace())
                    {  Log.i("MainActivity",e.toString());
                    }
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!app.getUserName().equals(""))    {
            ed_user.setText(app.getUserName());
        }
        if (!app.getPwdName().equals(""))    {
            ed_pwd.setText(app.getPwdName());
        }
    }

    public void doBack(View view) {
        finish();
    }

    public void doRegister(View view) {
        Intent it = new Intent(this, RegisterActivity.class);
        startActivity(it);
    }
}