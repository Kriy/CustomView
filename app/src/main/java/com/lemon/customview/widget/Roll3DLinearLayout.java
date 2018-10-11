package com.lemon.customview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lemon.customview.R;

public class Roll3DLinearLayout extends LinearLayout implements View.OnClickListener{

    private Context context;
    private Roll3DView roll3DView;
    private TextView titleTv;

    public Roll3DLinearLayout(Context context) {
        this(context, null);
    }

    public Roll3DLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Roll3DLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        View.inflate(context, R.layout.demo_item, this);

        Button toLeft = findViewById(R.id.left_btn);
        Button toRight = findViewById(R.id.right_btn);
        Button toUp = findViewById(R.id.roll_up_btn);
        Button toDown = findViewById(R.id.roll_down_btn);
        roll3DView = findViewById(R.id.three_d_view);
        titleTv = findViewById(R.id.title_tv);


        toLeft.setOnClickListener(this);
        toRight.setOnClickListener(this);
        toUp.setOnClickListener(this);
        toDown.setOnClickListener(this);

        BitmapDrawable bgDrawable1 = (BitmapDrawable) getResources().getDrawable(R.drawable.img1);
        BitmapDrawable bgDrawable2 = (BitmapDrawable) getResources().getDrawable(R.drawable.img2);
        BitmapDrawable bgDrawable3 = (BitmapDrawable) getResources().getDrawable(R.drawable.img3);
        BitmapDrawable bgDrawable4 = (BitmapDrawable) getResources().getDrawable(R.drawable.img4);
        BitmapDrawable bgDrawable5 = (BitmapDrawable) getResources().getDrawable(R.drawable.img5);


        Bitmap bitmap1 = bgDrawable1.getBitmap();
        Bitmap bitmap2 = bgDrawable2.getBitmap();
        Bitmap bitmap3 = bgDrawable3.getBitmap();
        Bitmap bitmap4 = bgDrawable4.getBitmap();
        Bitmap bitmap5 = bgDrawable5.getBitmap();

        roll3DView.addImageBitmap(bitmap1);
        roll3DView.addImageBitmap(bitmap2);
        roll3DView.addImageBitmap(bitmap3);
        roll3DView.addImageBitmap(bitmap4);
        roll3DView.addImageBitmap(bitmap5);

        roll3DView.setRollMode(Roll3DView.RollMode.Whole3D);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                roll3DView.setRollDirection(0);
                roll3DView.toPre();
                break;
            case R.id.right_btn:
                roll3DView.setRollDirection(0);
                roll3DView.toNext();
                break;
            case R.id.roll_up_btn:
                roll3DView.setRollDirection(1);
                roll3DView.toPre();
                break;
            case R.id.roll_down_btn:
                roll3DView.setRollDirection(1);
                roll3DView.toNext();
                break;
        }
    }

    public Roll3DView getRoll3DView() {
        return roll3DView;
    }

    public void setTitleText(String titleText) {
        titleTv.setText(titleText);
    }

}
