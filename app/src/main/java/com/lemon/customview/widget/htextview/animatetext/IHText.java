package com.lemon.customview.widget.htextview.animatetext;

import android.graphics.Canvas;
import android.util.AttributeSet;

import com.lemon.customview.widget.htextview.HTextView;

public interface IHText {
    void init(HTextView hTextView, AttributeSet attrs, int defStyle);

    void animateText(CharSequence text);

    void onDraw(Canvas canvas);

    void reset(CharSequence text);
}
