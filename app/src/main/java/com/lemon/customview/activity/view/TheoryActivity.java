package com.lemon.customview.activity.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.lemon.customview.R;
import com.lemon.customview.activity.WebViewActivity;


public class TheoryActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory);
        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one:
//                https://juejin.im/entry/595f0383f265da6c3a54d6bf
                Intent intent = new Intent(TheoryActivity.this, WebViewActivity.class);
                intent.putExtra("url", "https://juejin.im/entry/595f0383f265da6c3a54d6bf");
                startActivity(intent);
                break;
            case R.id.btn_two:
                break;
        }
    }
}
