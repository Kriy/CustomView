package com.lemon.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lemon.customview.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TheoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_one, R.id.btn_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
//                https://juejin.im/entry/595f0383f265da6c3a54d6bf
                break;
            case R.id.btn_two:
                break;
        }
    }
}
