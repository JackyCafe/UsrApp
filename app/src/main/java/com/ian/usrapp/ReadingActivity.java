package com.ian.usrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ian.usrapp.Adapter.ReaderAdapter;
import com.ian.usrapp.Obj.ReadClass;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ReadingActivity extends AppCompatActivity {
    RecyclerView viewer;
    MainApp app;
    MyHandler handler=new MyHandler();
    ReaderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading2);
        app = (MainApp) getApplication();
        viewer=findViewById(R.id.reader_show);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getUrl();
            }

        }).start();
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        viewer.setLayoutManager(manager);
        viewer.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }
    private void getUrl() {
        try {
            URL url = new URL(app.reading_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization","Bearer " + app.getToken());

            conn.connect();
            InputStream in = conn.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            String line;
            StringBuffer sr = new StringBuffer();
            while ((line = br.readLine())!=null){
                sr.append(line);
            }

            Gson gson =new Gson();
            List<ReadClass> rs=gson.fromJson(sr.toString(),new TypeToken<List<ReadClass>>(){}.getType()
            );
            handler.setData(rs);
            handler.sendEmptyMessage(0);
            Log.v("Jacky",rs.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    class MyHandler extends Handler {
        List<ReadClass> data;

        public void setData(List<ReadClass> data) {
            this.data = data;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            adapter = new ReaderAdapter(data);
            handler.setData(data);
            viewer.setAdapter(adapter);
        }
    }
}