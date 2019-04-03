package com.lemon.customview.activity.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lemon.customview.R;
import com.lemon.customview.widget.VerifyCodeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerifyCodeViewActivity extends AppCompatActivity {


    @BindView(R.id.verify_code_1)
    VerifyCodeView mVerifyCode1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code_view);
        ButterKnife.bind(this);

        setListener();
    }

    private void setListener() {
        mVerifyCode1.setInputCompleteListener(new VerifyCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                Toast.makeText(VerifyCodeViewActivity.this, "输入完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void invalidContent() {
                // 输入内容更新
            }
        });
    }
}
