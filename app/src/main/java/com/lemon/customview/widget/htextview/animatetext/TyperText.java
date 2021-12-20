package com.lemon.customview.widget.htextview.animatetext;

import android.graphics.Canvas;

public class TyperText extends HText {
    private int currentLength;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void animateStart(CharSequence text) {
        currentLength = 0;
        mHTextView.invalidate();
    }

    @Override
    protected void animatePrepare(CharSequence text) {

    }

    @Override
    protected void drawFrame(Canvas canvas) {
        canvas.drawText(mText, 0, currentLength, startX, startY, mPaint);
        if (currentLength < mText.length()) {
            currentLength++;
            mHTextView.postInvalidateDelayed(100);
        }
    }
}
