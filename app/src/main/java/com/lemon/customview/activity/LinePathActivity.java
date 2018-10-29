package com.lemon.customview.activity;

import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lemon.customview.R;
import com.lemon.customview.widget.LinePathView;

public class LinePathActivity extends AppCompatActivity {

    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_path);

        mFrameLayout = findViewById(R.id.fl_content);
        //mFrameLayout.removeAllViews();

        mFrameLayout.post(() -> {
            setPath();
            startAnimation();
        });
    }

    private void startAnimation() {
        for (int i = 0; i < mFrameLayout.getChildCount(); i++) {
            View childView = mFrameLayout.getChildAt(i);
            if (childView instanceof LinePathView) {
                ((LinePathView) childView).startAnimation();
            }
        }
    }

    private void setPath() {
        Path path1 = new Path();
        Path path2 = new Path();
        Path path3;
        Path path4;
        Path path5;
        Path path6;

        path1.moveTo(310, 0);
        path2.moveTo(410, 0);

        path1.lineTo(310, 400);
        path1.lineTo(210, 500);
        path1.lineTo(210, 600);
        path1.lineTo(310, 700);
        path1.lineTo(310, 1280);

        path2.lineTo(410, 400);
        path2.lineTo(510, 500);
        path2.lineTo(510, 600);
        path2.lineTo(410, 700);
        path2.lineTo(410, 1280);

        path3 = new Path(path1);
        path3.offset(-100, 0);
        path5 = new Path(path1);
        path5.offset(-200, 0);
        path4 = new Path(path2);
        path4.offset(+100, 0);
        path6 = new Path(path2);
        path6.offset(+200, 0);

        for (int i = 0; i < 6; i++) {
            LinePathView pathView = new LinePathView(LinePathActivity.this);
            switch (i) {
                case 0:
                    pathView.setPath(path1);
                    break;
                case 1:
                    pathView.setPath(path2);
                    break;
                case 2:
                    pathView.setPath(path3);
                    pathView.setMode(LinePathView.TRAIN_MODE);
                    break;
                case 3:
                    pathView.setPath(path4);
                    pathView.setMode(LinePathView.TRAIN_MODE);
                    break;
                case 4:
                    pathView.setPath(path5);
                    break;
                case 5:
                    pathView.setPath(path6);
                    break;
            }
            pathView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFrameLayout.addView(pathView, 0);
        }
    }
}
