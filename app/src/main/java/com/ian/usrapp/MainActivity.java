package com.ian.usrapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
                    String user = jwt.getClaim("user_id").asString();
                    app.setUserId(Integer.parseInt(user));
                    if (token!=null){
                        Intent it = new Intent(MainActivity.this, Menu.class);
                        startActivity(it);
                        app.Log("doLogin");
                    }
                } catch (Exception e) {
                    for(StackTraceElement se:e.getStackTrace())
                    {app.Log(se.toString());}
                    e.printStackTrace();
                }
            }
        }).start();

    }


}