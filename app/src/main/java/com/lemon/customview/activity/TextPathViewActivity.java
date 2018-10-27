package com.lemon.customview.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lemon.customview.R;
import com.lemon.customview.widget.new_path.MoBikeView;

public class TextPathViewActivity extends AppCompatActivity implements SensorEventListener{

    private MoBikeView mMobikeView;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private int[] mImgs = {
            R.mipmap.ic_share_fb,
            R.mipmap.ic_share_kongjian,
            R.mipmap.ic_share_pyq,
            R.mipmap.ic_share_qq,
            R.mipmap.ic_share_tw,
            R.mipmap.ic_share_wechat,
            R.mipmap.ic_share_weibo
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_path_view);

        mMobikeView = findViewById(R.id.mo_bike);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        addViews();

        mMobikeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMobikeView.onRandomChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    private void addViews() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        for (int mImg : mImgs) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(mImg);
            iv.setTag(R.id.wd_view_circle_tag, true);
            mMobikeView.addView(iv, lp);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1] * 2.0f;
            mMobikeView.onSensorChanged(-x, y);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
