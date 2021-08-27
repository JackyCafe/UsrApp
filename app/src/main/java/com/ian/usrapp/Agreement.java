package com.ian.usrapp;



import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Agreement extends AppCompatActivity {
    TextView tv ;
    private MainApp app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        tv = findViewById(R.id.tv_agree);
        app = (MainApp) getApplication();
        AssetManager am = getAssets();
        StringBuilder sb = new StringBuilder();

        try (InputStream is = am.open("readme.txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = null;
            while ((str=br.readLine())!=null){
                sb.append(str+"\n");
            }

        }catch (Exception e){

        }
        tv.setText(sb.toString());


        setSupportActionBar(toolbar);
        Button btn = findViewById(R.id.back_toolbar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void doAgree(View view) {

        Intent it  = new Intent(Agreement.this, MenuActivity.class);
        app.setIsAgreed(true);
        startActivity(it);
    }
}