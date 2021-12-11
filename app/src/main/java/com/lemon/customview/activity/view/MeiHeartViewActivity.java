package com.lemon.customview.activity.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.SparseArray;
import android.view.View;

import com.lemon.customview.R;
import com.lemon.customview.widget.Heart.HeartType;
import com.lemon.customview.widget.Heart.MeiHeartView;

public class MeiHeartViewActivity extends AppCompatActivity {
    private MeiHeartView mHeartView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mei_heart_view);

        mHeartView = findViewById(R.id.heart_view);
        setHeartBitmap();

        mHeartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeartView.addHeart();
            }
        });

        intervalAddHeart();
    }

    private void intervalAddHeart() {
        mHeartView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHeartView.performClick();
                intervalAddHeart();
            }
        }, 1000);
    }

    public void setHeartBitmap() {
        SparseArray<Bitmap> bitmapArray = new SparseArray<>();
        Bitmap bitmap1 = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_heart_0);
        Bitmap bitmap2 = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_heart_1);
        Bitmap bitmap3 = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_heart_2);
        Bitmap bitmap4 = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_heart_3);
        Bitmap bitmap5 = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_heart_4);
        Bitmap bitmap6 = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_heart_5);
        Bitmap bitmap7 = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_heart_6);
        bitmapArray.put(HeartType.BLUE, bitmap1);
        bitmapArray.put(HeartType.GREEN, bitmap2);
        bitmapArray.put(HeartType.YELLOW, bitmap3);
        bitmapArray.put(HeartType.PINK, bitmap4);
        bitmapArray.put(HeartType.BROWN, bitmap5);
        bitmapArray.put(HeartType.PURPLE, bitmap6);
        bitmapArray.put(HeartType.RED, bitmap7);
        mHeartView.setHeartBitmap(bitmapArray);
    }


}
