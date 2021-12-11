package com.lemon.customview.activity.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.lemon.customview.R;
import com.lemon.customview.widget.KeyboardView;


public class KeyboardActivity extends AppCompatActivity {

    TextView mTvInput;
    KeyboardView mKv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

        mTvInput = findViewById(R.id.tv_input);
        mKv = findViewById(R.id.kv);

        mKv.setOnItemClickListener(position -> {
            if (position == 10) {
                String content = mTvInput.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    mTvInput.setText(content.substring(0, content.length() - 1));
                }
            } else if (position == 11) {
                mTvInput.append("0");
            } else if (position == 12) {
                Toast.makeText(KeyboardActivity.this, "完成", Toast.LENGTH_SHORT).show();
            } else {
                mTvInput.append(position + "");
            }
        });
    }
}
