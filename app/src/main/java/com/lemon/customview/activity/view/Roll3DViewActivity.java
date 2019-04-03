package com.lemon.customview.activity.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lemon.customview.R;
import com.lemon.customview.widget.Roll3DLinearLayout;
import com.lemon.customview.widget.Roll3DView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Roll3DViewActivity extends AppCompatActivity {

    @BindView(R.id.item1)
    Roll3DLinearLayout mItem1;
    @BindView(R.id.item2)
    Roll3DLinearLayout mItem2;
    @BindView(R.id.item3)
    Roll3DLinearLayout mItem3;
    @BindView(R.id.item4)
    Roll3DLinearLayout mItem4;
    @BindView(R.id.item5)
    Roll3DLinearLayout mItem5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_3d_view);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
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
