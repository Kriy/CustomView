package com.lemon.customview.widget.htextview.animatetext;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;

import com.lemon.customview.utils.ResourceUtil;

public class RainBowText extends HText {
    private int mTextWidth;
    private LinearGradient mLinearGradient;
    private Matrix mMatrix;
    private float mTranslate;
    private int dx;
    private int[] colors =
            new int[]{0xFFFF2B22, 0xFFFF7F22, 0xFFEDFF22, 0xFF22FF22, 0xFF22F4FF, 0xFF2239FF, 0xFF5400F7};

    @Override
    protected void initVariables() {
        mMatrix = new Matrix();
        dx = ResourceUtil.dp2Px(7);
    }

    public void setColors(int colors[]) {
        this.colors = colors;
    }

    @Override
    protected void animateStart(CharSequence text) {

        mHTextView.invalidate();
    }

    @Override
    protected void animatePrepare(CharSequence text) {
        mTextWidth = (int) mPaint.measureText(mText, 0, mText.length());
        mTextWidth = Math.max(ResourceUtil.dp2Px(100), mTextWidth);
        if (mTextWidth > 0) {
            mLinearGradient = new LinearGradient(0, 0, mTextWidth, 0,
                    colors, null, Shader.TileMode.MIRROR);
            mPaint.setShader(mLinearGradient);
        }
    }

    @Override
    protected void drawFrame(Canvas canvas) {
        if (mMatrix != null && mLinearGradient != null) {
            mTranslate += dx;
            mMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mMatrix);
            canvas.drawText(mText, 0, mText.length(), startX, startY, mPaint);
            mHTextView.postInvalidateDelayed(100);
        }
    }
}
