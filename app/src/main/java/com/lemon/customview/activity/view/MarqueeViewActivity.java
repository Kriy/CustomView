package com.lemon.customview.activity.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.lemon.customview.R;
import com.lemon.customview.widget.MarqueeView;

import java.util.ArrayList;
import java.util.List;


public class MarqueeViewActivity extends AppCompatActivity implements View.OnClickListener {

    private MarqueeView mMvMain1;
    private MarqueeView mMvMain2;
    private MarqueeView mMvMain3;
    private String content2;
    private String content3;
    private List<String> list1 = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee_view);

        initData();
    }

    private void initData() {
        mMvMain1 = findViewById(R.id.mv_main1);
        mMvMain2 = findViewById(R.id.mv_main2);
        mMvMain3 = findViewById(R.id.mv_main3);
        findViewById(R.id.bt_control0).setOnClickListener(this);
        findViewById(R.id.bt_control4).setOnClickListener(this);
        findViewById(R.id.bt_control24).setOnClickListener(this);
        findViewById(R.id.bt_control23).setOnClickListener(this);
        findViewById(R.id.bt_control).setOnClickListener(this);
        findViewById(R.id.bt_control2).setOnClickListener(this);
        findViewById(R.id.bt_control00).setOnClickListener(this);
        list1.add("北冥有鱼   其名为鲲");
        list1.add("鲲之大  不知其几千里也；  化而为鸟    其名为鹏");
        list1.add("故夫知效一官，  行比一乡，  德合一君，");
        content2 = "要么孤独,要么庸俗 ";
        content3 = "你在桥上看风景 看风景的人 在楼上看你";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_control0:
                mMvMain1.setContent(list1);
                mMvMain2.setContent(content2);
                mMvMain3.setContent(content3);
                break;
            case R.id.bt_control4:
                mMvMain2.setTextColor(R.color.colorAccent);
                break;
            case R.id.bt_control24:
                mMvMain2.setTextSize(17);
                break;
            case R.id.bt_control23:
                mMvMain2.setTextSpeed(5);
                break;
            case R.id.bt_control:
                mMvMain3.continueRoll();
                break;
            case R.id.bt_control2:
                mMvMain3.stopRoll();
                break;
            case R.id.bt_control00:
                mMvMain3.setTextDistance(50);//设置3的间距
                break;
        }
    }
}
