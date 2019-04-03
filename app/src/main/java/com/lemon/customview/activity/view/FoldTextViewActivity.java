package com.lemon.customview.activity.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.lemon.customview.R;
import com.lemon.customview.widget.FoldTextView;
import com.lemon.customview.widget.SpannableFoldTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FoldTextViewActivity extends AppCompatActivity {


    @BindView(R.id.text)
    SpannableFoldTextView mText;
    @BindView(R.id.text1)
    SpannableFoldTextView mText1;
    @BindView(R.id.parent1)
    FrameLayout mParent1;
    @BindView(R.id.text2)
    FoldTextView mText2;
    @BindView(R.id.text3)
    FoldTextView mText3;
    @BindView(R.id.parent3)
    FrameLayout mParent3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fold_textview);
        ButterKnife.bind(this);

        mText.setText("111111123阿斯顿发阿斯顿发送到大。厦法定阿萨【德法师打发斯蒂芬撒地】方阿萨德法师打发斯问问蒂芬撒地方阿萨德法师打发斯蒂。芬撒地方发送到发送到发送到发送到发送到发送，到发送到发送到发送到，发送111111123阿斯顿发阿斯顿发送到大。厦法定阿萨【德法师打发斯蒂芬撒地】方阿萨德法师打发斯问问蒂芬撒地方阿萨德法师打发斯蒂。芬撒地方发送到发送到发送到发送到发送到发送，到发送到发送到发送到，发送");
        mText1.setText("111111123阿斯顿发阿斯顿发送到大。厦法定阿萨【德法师打发斯蒂芬撒地】方阿萨德法师打发斯问问蒂芬撒地方阿萨德法师打发斯蒂。芬撒地方发送到发送到发送到发送到发送到发送，到发送到发送到发送到，发送111111123阿斯顿发阿斯顿发送到大。厦法定阿萨【德法师打发斯蒂芬撒地】方阿萨德法师打发斯问问蒂芬撒地方阿萨德法师打发斯蒂。芬撒地方发送到发送到发送到发送到发送到发送，到发送到发送到发送到，发送");
        mText2.setText("111111123阿斯顿发阿斯顿发送到大。厦法定阿萨【德法师打发斯蒂芬撒地】方阿萨德法师打发斯问问蒂芬撒地方阿萨德法师打发斯蒂。芬撒地方发送到发送到发送到发送到发送到发送，到发送到发送到发送到，发送111111123阿斯顿发阿斯顿发送到大。厦法定阿萨【德法师打发斯蒂芬撒地】方阿萨德法师打发斯问问蒂芬撒地方阿萨德法师打发斯蒂。芬撒地方发送到发送到发送到发送到发送到发送，到发送到发送到发送到，发送");
        mText3.setText("111111123阿斯顿发阿斯顿发送到大。厦法定阿萨【德法师打发斯蒂芬撒地】方阿萨德法师打发斯问问蒂芬撒地方阿萨德法师打发斯蒂。芬撒地方发送到发送到发送到发送到发送到发送，到发送到发送到发送到，发送111111123阿斯顿发阿斯顿发送到大。厦法定阿萨【德法师打发斯蒂芬撒地】方阿萨德法师打发斯问问蒂芬撒地方阿萨德法师打发斯蒂。芬撒地方发送到发送到发送到发送到发送到发送，到发送到发送到发送到，发送");
    }

    @OnClick({R.id.text, R.id.parent1, R.id.text2, R.id.parent3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text:
                Toast.makeText(FoldTextViewActivity.this, "textView点击事件", Toast.LENGTH_SHORT).show();
                break;
            case R.id.parent1:
                Toast.makeText(FoldTextViewActivity.this, "父View点击事件", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text2:
                Toast.makeText(FoldTextViewActivity.this, "textView点击事件", Toast.LENGTH_SHORT).show();
                break;
            case R.id.parent3:
                Toast.makeText(FoldTextViewActivity.this, "父View点击事件", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
