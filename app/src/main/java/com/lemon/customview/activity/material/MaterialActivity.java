package com.lemon.customview.activity.material;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.lemon.customview.R;

/**
 * Created by Terminator on 2019/4/7.
 */
public class MaterialActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        findViewById(R.id.btn_text_input_layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_text_input_layout:
                startActivity(new Intent(MaterialActivity.this, TextInputLayoutActivity.class));
                break;
            default:
        }
    }
}
