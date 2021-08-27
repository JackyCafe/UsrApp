package com.ian.usrapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ian.usrapp.Obj.ReadClass;

public class ReadingContentActivity extends AppCompatActivity {
    TextView tv;
    Bundle bundle;
    ReadClass rc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_content);
        tv = findViewById(R.id.tv_content);
        bundle = getIntent().getBundleExtra("content");
        rc = (ReadClass) bundle.getSerializable("data");
        tv.setText(HtmlCompat.fromHtml(rc.getContent(),HtmlCompat.FROM_HTML_MODE_LEGACY));
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Button btn = findViewById(R.id.back_toolbar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void doBack(View view) {
        finish();
    }
}