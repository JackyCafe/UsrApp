package com.ian.usrapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ian.usrapp.Util.UsrGet;
import com.ian.usrapp.Util.UsrPost;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class BlogActivity extends AppCompatActivity {
    AppCompatEditText ed_title,ed_category,ed_content;
    String title,category,content;
    String token;
    private MainApp app;
    int id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        app = (MainApp) getApplication();
        ed_title = findViewById(R.id.title);
        ed_category = findViewById(R.id.category);
        ed_content = findViewById(R.id.content);
        token = app.getToken();
        id = app.getUserId();
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button btn = findViewById(R.id.back_toolbar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Log.i("BlogActivity","id:"+id);

    }

    public void doBack(View view) {
        finish();
    }

    public void doPost(View view) {
        title = ed_title.getText().toString();
        category = ed_category.getText().toString();
        content = ed_content.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UsrPost usrPost = new UsrPost(app.blog_url);
                    usrPost.setToken(token);
                    JSONObject json = new JSONObject();
                    json.put("title", title);//使用URLEncoder.encode對特殊和不可見字元進行編碼
                    json.put("category",category);
                    json.put("content",content);
                    json.put("user", id);
                    usrPost.setToken(token);
                    String response = usrPost.doPost(json);
                    Log.i("BlogActivity","response:"+response);


                } catch (IOException | JSONException e) {
                    Log.i("BlogActivity","exception:"+e.toString());

                    e.printStackTrace();
                }

            }
        }).start();
    }
}