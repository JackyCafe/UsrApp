package com.ian.usrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }
    public void doReading(View view) {
            Intent it = new Intent(this, ReadingActivity.class);
            startActivity(it);
    }

    public void doBlog(View view) {

        
    }

    public void doCheck(View view) {
        Intent it = new Intent(this,CheckActivity.class);
        startActivity(it);
    }
}