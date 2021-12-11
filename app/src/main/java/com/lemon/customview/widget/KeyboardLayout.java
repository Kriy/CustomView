package com.lemon.customview.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class KeyboardLayout extends ViewGroup implements View.OnClickListener{

    private String[] keys = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", "-1"};

//    int keyViewHeight

    public KeyboardLayout(Context context) {
        super(context);
    }

    public KeyboardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private Drawable getDrawable(int resId){
        return ContextCompat.getDrawable(getContext(), resId);
    }

    private int getColor(int id){
        return ContextCompat.getColor(getContext(), id);
    }

    private int getMeasure(int size){
        return MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
    }

}
