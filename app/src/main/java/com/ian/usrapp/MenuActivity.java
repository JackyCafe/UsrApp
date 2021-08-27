package com.ian.usrapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void doReading(View view) {
            Intent it = new Intent(this, ReadingActivity.class);
            startActivity(it);
    }

    public void doBlog(View view) {
            Intent it = new Intent(this, BlogActivity.class);
            startActivity(it);
        
    }

    public void doCheck(View view) {
        Intent it = new Intent(this,CheckActivity.class);
        startActivity(it);
    }


    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void doFans(View view) {
        Intent it = new Intent(this,FansPageActivity.class);
        startActivity(it);
    }
}