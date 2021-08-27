package com.ian.usrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;

import com.ian.usrapp.Adapter.CheckAdapter;
import com.ian.usrapp.Obj.Check;
import com.ian.usrapp.Util.UsrGet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class CheckActivity extends AppCompatActivity {
    RecyclerView view ;
    MainApp app;
    URL url;
    HttpURLConnection conn;
    MyHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        view = findViewById(R.id.checkview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        view.setLayoutManager(manager);
        view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button btn = findViewById(R.id.back_toolbar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        app = (MainApp) getApplication();
        handler = new MyHandler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UsrGet<Check> usrGet = new UsrGet<>(app.activity_url);
                    usrGet.setToken(app.getToken());
                    String response = usrGet.doGet();
                    Type type = new TypeToken<List<Check>>(){}.getType();
                    List<Check> checks = usrGet.parseJsonList(response, type);
                    handler.setData(checks);
                    handler.sendEmptyMessage(0);

                } catch (IOException e) {
                    for (StackTraceElement se:e.getStackTrace())
                    {app.Log(se.toString());}
                }

            }
        }).start();
    }

    class MyHandler extends Handler{
        List<Check> datas;
        public void setData(List<Check> datas)
        {
            this.datas = datas;
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            view.setAdapter(new CheckAdapter(CheckActivity.this,datas));
//            view.notifyAll();
        }
    }
}