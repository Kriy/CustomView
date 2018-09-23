package com.lemon.customview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lemon.customview.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_depth, R.id.btn_time, R.id.btn_marquee})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_depth:
                startActivity(new Intent(MainActivity.this, DepthMapViewActivity.class));
                break;
            case R.id.btn_time:
                startActivity(new Intent(MainActivity.this, TimeLineMarkerViewActivity.class));
                break;
            case R.id.btn_marquee:
                startActivity(new Intent(MainActivity.this, TimeLineMarkerViewActivity.class));
                break;
        }
    }
}
