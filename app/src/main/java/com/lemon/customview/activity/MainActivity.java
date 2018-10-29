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

    @OnClick({R.id.btn_depth, R.id.btn_time, R.id.btn_marquee, R.id.btn_slanted, R.id.btn_keyboard, R.id.btn_verify_code,
            R.id.btn_fold_text_view, R.id.btn_expand_text_view, R.id.btn_round_image_view, R.id.btn_shape_image_view,
            R.id.btn_roll_3d_view, R.id.btn_heart_view, R.id.btn_path_view, R.id.btn_line_path_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_depth:
                startActivity(new Intent(MainActivity.this, DepthMapViewActivity.class));
                break;
            case R.id.btn_time:
                startActivity(new Intent(MainActivity.this, TimeLineMarkerViewActivity.class));
                break;
            case R.id.btn_marquee:
                startActivity(new Intent(MainActivity.this, MarqueeViewActivity.class));
                break;
            case R.id.btn_slanted:
                startActivity(new Intent(MainActivity.this, SlantedTextViewActivity.class));
                break;
            case R.id.btn_keyboard:
                startActivity(new Intent(MainActivity.this, KeyboardActivity.class));
                break;
            case R.id.btn_verify_code:
                startActivity(new Intent(MainActivity.this, VerifyCodeViewActivity.class));
                break;
            case R.id.btn_fold_text_view:
                startActivity(new Intent(MainActivity.this, FoldTextViewActivity.class));
                break;
            case R.id.btn_expand_text_view:
                startActivity(new Intent(MainActivity.this, ExpandableTextViewActivity.class));
                break;
            case R.id.btn_round_image_view:
                startActivity(new Intent(MainActivity.this, RoundImageViewActivity.class));
                break;
            case R.id.btn_shape_image_view:
                startActivity(new Intent(MainActivity.this, ShapeImageViewActivity.class));
                break;
            case R.id.btn_roll_3d_view:
                startActivity(new Intent(MainActivity.this, Roll3DViewActivity.class));
                break;
            case R.id.btn_heart_view:
                startActivity(new Intent(MainActivity.this, MeiHeartViewActivity.class));
                break;
            case R.id.btn_path_view:
                startActivity(new Intent(MainActivity.this, TextPathViewActivity.class));
                break;
            case R.id.btn_line_path_view:
                startActivity(new Intent(MainActivity.this, LinePathActivity.class));
                break;
        }
    }
}
