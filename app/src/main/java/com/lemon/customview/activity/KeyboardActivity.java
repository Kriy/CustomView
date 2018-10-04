package com.lemon.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.lemon.customview.R;
import com.lemon.customview.widget.KeyboardView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KeyboardActivity extends AppCompatActivity {

    @BindView(R.id.tv_input)
    TextView mTvInput;
    @BindView(R.id.kv)
    KeyboardView mKv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        ButterKnife.bind(this);

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
