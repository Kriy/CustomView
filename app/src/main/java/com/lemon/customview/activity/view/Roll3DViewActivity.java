package com.lemon.customview.activity.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lemon.customview.R;
import com.lemon.customview.widget.Roll3DLinearLayout;
import com.lemon.customview.widget.Roll3DView;


public class Roll3DViewActivity extends AppCompatActivity {

    private Roll3DLinearLayout mItem1;
    private Roll3DLinearLayout mItem2;
    private Roll3DLinearLayout mItem3;
    private Roll3DLinearLayout mItem4;
    private Roll3DLinearLayout mItem5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_3d_view);
        initView();
    }

    private void initView() {
        mItem1 = findViewById(R.id.item1);
        mItem2 = findViewById(R.id.item2);
        mItem3 = findViewById(R.id.item3);
        mItem4 = findViewById(R.id.item4);
        mItem5 = findViewById(R.id.item5);
        Roll3DView roll3DView1 = mItem1.getRoll3DView();
        Roll3DView roll3DView2 = mItem2.getRoll3DView();
        Roll3DView roll3DView3 = mItem3.getRoll3DView();
        Roll3DView roll3DView4 = mItem4.getRoll3DView();
        Roll3DView roll3DView5 = mItem5.getRoll3DView();

        roll3DView1.setRollMode(Roll3DView.RollMode.Roll2D);
        mItem1.setTitleText("2D平移");

        roll3DView2.setRollMode(Roll3DView.RollMode.Whole3D);
        mItem2.setTitleText("3D翻转");

        roll3DView3.setRollMode(Roll3DView.RollMode.SepartConbine);
        roll3DView3.setPartNumber(3);
        mItem3.setTitleText("开合效果");

        roll3DView4.setRollMode(Roll3DView.RollMode.Jalousie);
        roll3DView4.setPartNumber(8);
        mItem4.setTitleText("百叶窗");

        roll3DView5.setRollMode(Roll3DView.RollMode.RollInTurn);
        roll3DView5.setPartNumber(9);
        mItem5.setTitleText("轮转效果");
    }
}
