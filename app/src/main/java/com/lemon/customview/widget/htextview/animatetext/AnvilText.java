package com.lemon.customview.widget.htextview.animatetext;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.animation.BounceInterpolator;

import com.lemon.customview.utils.Util;

public class AnvilText extends HText {

    private Paint bitmapPaint;
    private Bitmap[] smokes = new Bitmap[50];
    private float ANIMA_DURATION = 800;
    private int mTextHeight = 0;
    private int mTextWidth;
    private float progress;
    private Matrix mMatrix;
    private float dstWidth;

    @Override
    protected void initVariables() {
        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setColor(Color.WHITE);
        bitmapPaint.setStyle(Paint.Style.FILL);
        // load drawable
        try {
            for (int j = 0; j < 50; j++) {
                String drawable;
                if (j < 10) {
                    drawable = "wenzi000" + j;
                } else {
                    drawable = "wenzi00" + j;
                }
                Resources resources = mHTextView.getResources();
                int imgId = resources.getIdentifier(drawable, "drawable", mHTextView.getContext().getPackageName());
                smokes[j] = BitmapFactory.decodeResource(resources, imgId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMatrix = new Matrix();
    }

    @Override
    protected void animateStart(CharSequence text) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1)
                .setDuration((long) ANIMA_DURATION);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue();
                mHTextView.invalidate();
            }
        });
        valueAnimator.start();
        if (smokes.length > 0) {
            mMatrix.reset();
            dstWidth = mTextWidth * 1.2f;
            if (dstWidth < 404f) dstWidth = 404f;
            mMatrix.postScale(dstWidth / (float) smokes[0].getWidth(), 1f);
        }
    }

    @Override
    protected void animatePrepare(CharSequence text) {
        Rect bounds = new Rect();
        mPaint.getTextBounds(mText.toString(), 0, mText.length(), bounds);
        mTextHeight = bounds.height();
        mTextWidth = bounds.width();
    }

    @Override
    protected void drawFrame(Canvas canvas) {
        float offset = startX;
        float oldOffset = oldStartX;
        int maxLength = Math.max(mText.length(), mOldText.length());
        float percent = progress;
        boolean showSmoke = false;
        for (int i = 0; i < maxLength; i++) {
            // draw old text
            if (i < mOldText.length()) {
                mOldPaint.setTextSize(mTextSize);
                int move = Util.needMove(i, differentList);
                if (move != -1) {
                    mOldPaint.setAlpha(255);
                    float p = percent * 2f;
                    p = p > 1 ? 1 : p;
                    float distX = Util.getOffset(i, move, p, startX, oldStartX, gaps, oldGaps);
                    canvas.drawText(mOldText.charAt(i) + "", 0, 1, distX, startY, mOldPaint);
                } else {
                    float p = percent * 2f;
                    p = p > 1 ? 1 : p;
                    mOldPaint.setAlpha((int) ((1 - p) * 255));
                    canvas.drawText(mOldText.charAt(i) + "", 0, 1, oldOffset, startY, mOldPaint);
                }
                oldOffset += oldGaps[i];
            }
            // draw new text
            if (i < mText.length()) {
                if (!Util.stayHere(i, differentList)) {
                    showSmoke = true;
                    float interpolation = new BounceInterpolator().getInterpolation(percent);
                    mPaint.setAlpha(255);
                    mPaint.setTextSize(mTextSize);
                    float y = startY - (1 - interpolation) * mTextHeight * 2;
                    float width = mPaint.measureText(mText.charAt(i) + "");
                    canvas.drawText(mText.charAt(i) + "", 0, 1, offset + (gaps[i] - width) / 2, y, mPaint);
                }
                offset += gaps[i];
            }
        }
        if (percent > 0.3 && percent < 1) {
            if (showSmoke) {
                drawSmokes(canvas, startX + (offset - startX) / 2f, startY - 50, percent);
            }
        }
    }

    private void drawSmokes(Canvas canvas, float x, float y, float percent) {
        Bitmap b = smokes[0];
        try {
            int index = (int) (50 * percent);
            if (index < 0) index = 0;
            if (index >= 50) index = 49;
            b = smokes[index];
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (b != null) {
            float dx = (mHTextView.getWidth() - dstWidth) / 2 > 0 ? (mHTextView.getWidth() - dstWidth) / 2 : 0;
            canvas.translate(dx, (mHTextView.getHeight() - b.getHeight()) / 2);
            canvas.drawBitmap(b, mMatrix, bitmapPaint);
        }
    }
}
