package com.lemon.customview.activity.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import com.lemon.customview.R;
import com.lemon.customview.widget.VerifyCodeView;

public class VerifyCodeViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code_view);

        ((VerifyCodeView) findViewById(R.id.verify_code_1)).setInputCompleteListener(new VerifyCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                Toast.makeText(VerifyCodeViewActivity.this, "输入完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void invalidContent() {

            }
        });
    }
}
