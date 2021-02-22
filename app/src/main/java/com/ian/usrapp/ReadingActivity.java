package com.ian.usrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.GridLayout;

import com.google.gson.reflect.TypeToken;
import com.ian.usrapp.Adapter.ReadAdapter;
import com.ian.usrapp.Obj.ReadClass;
import com.ian.usrapp.Util.UsrGet;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ReadingActivity extends AppCompatActivity {
    RecyclerView viwer ;
    MyHandler handler =new MyHandler();
    ReadAdapter adapter;
    MainApp app;
    private static final int MAX = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        viwer = findViewById(R.id.reader_show);
//        LinearLayoutManager manager = new LinearLayoutManager(this);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setOrientation(GridLayoutManager.HORIZONTAL);

        app = (MainApp) getApplication();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UsrGet<ReadClass> usrGet = new UsrGet<>(app.reading_url);
                    usrGet.setToken(app.getToken());
                    String response = usrGet.doGet();
                    Type type = new TypeToken<List<ReadClass>>(){}.getType();

                    List<ReadClass> datas = (List<ReadClass>) usrGet.parseJsonList(response,type);
                    manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            return setSpanSize(position,datas);
                        }
                    });
                    handler.setData(datas);
                    handler.sendEmptyMessage(0);
                } catch (IOException e) {
                    for (StackTraceElement se :e.getStackTrace())
                    app.Log(se.toString());
                    e.printStackTrace();
                }
            }
        }).start();
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        viwer.setLayoutManager(manager);
        viwer.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    private int setSpanSize(int position, List<ReadClass> listEntities) {
        int count;
        if (listEntities.get(position).getTitle().length() > MAX) {
            count = 2;
        } else {
            count = 1;
        }

        return count;
    }




    class MyHandler extends Handler {
        List<ReadClass> data;

        public void setData(List<ReadClass> data) {
            this.data = data;
        }


        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            adapter = new ReadAdapter(data);
            handler.setData(data);
            viwer.setAdapter(adapter);
        }
    }
}