package com.lemon.customview.activity.material;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lemon.customview.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Terminator on 2019/4/7.
 */
public class MaterialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_text_input_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_text_input_layout:
                startActivity(new Intent(MaterialActivity.this, TextInputLayoutActivity.class));
                break;
            default:
        }
    }
}
