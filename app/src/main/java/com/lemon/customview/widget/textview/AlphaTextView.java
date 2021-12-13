package com.lemon.customview.widget.textview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

public class AlphaTextView extends AppCompatTextView {

    private String mTextString;
    private SpannableString mSpannableString;

    private double[] mAlphas;
    private int mDuration = 2000;
    private boolean mIsVisible = true;
    private boolean mIsTextResetting = false;
    private ForegroundColorSpan[] mSpans;
    private ValueAnimator mAnimator;

    public AlphaTextView(Context context) {
        this(context, null);
    }

    public AlphaTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlphaTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mAnimator = ValueAnimator.ofFloat(0.0f, 2.0f);
        mAnimator.addUpdateListener(valueAnimator -> {
            Float alpha = (Float) valueAnimator.getAnimatedValue();
            resetSpannableString(mIsVisible ? alpha : 2.0f - alpha);
        });
        mAnimator.setDuration(mDuration);
    }

    public void toggle() {
        if (this.mIsVisible) {
            hide();
        } else {
            show();
        }
    }

    public void show() {
        this.mIsVisible = true;
        this.mAnimator.start();
    }

    public void hide() {
        this.mIsVisible = false;
        this.mAnimator.start();
    }

    public void setIsVisible(boolean isVisible) {
        this.mIsVisible = isVisible;
        resetSpannableString(isVisible ? 2.0f : 0.0f);
    }

    public void setText(String content, boolean isVisible) {
        setText(content);
        this.mIsVisible = isVisible;
        this.mAnimator.start();
    }

    public void setText(String text) {
        super.setText(text);
        reset();
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        super.setText(text, type);
        reset();
    }

    private void reset() {
        if (!mIsTextResetting) {
            this.mTextString = getText().toString();
            this.mSpannableString = new SpannableString(this.mTextString);
            this.mSpans = new ForegroundColorSpan[this.mTextString.length()];
            for (int i = 0, length = this.mTextString.length(); i < length; i++) {
                this.mSpans[i] = new ForegroundColorSpan();
                this.mSpannableString.setSpan(this.mSpans[i], i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            resetAlphas();
            resetSpannableString(mIsVisible ? 2.0f : 0);
        }
    }

    private void resetAlphas() {
        this.mAlphas = new double[mTextString.length()];
        for (int i = 0; i < mTextString.length(); i++) {
            this.mAlphas[i] = Math.random() - 1;
        }
    }

    private void resetSpannableString(double alpha) {
        this.mIsTextResetting = true;
        int color = getCurrentTextColor();
        for (int i = 0, length = this.mTextString.length(); i < length; i++) {
            this.mSpans[i].setColor(Color.argb(clamp(this.mAlphas[i] + alpha), Color.red(color), Color.green(color), Color.blue(color)));
        }
        setText(this.mSpannableString);
        this.mIsTextResetting = false;
    }

    private int clamp(double f) {
        return (int) (255 * Math.min(Math.max(f, 0), 1));
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
        this.mAnimator.setDuration(duration);
    }

    public static class ForegroundColorSpan extends CharacterStyle
            implements UpdateAppearance {

        private int mColor;

        @Override
        public void updateDrawState(TextPaint tp) {
            tp.setColor(mColor);
        }

        public int getColor() {
            return mColor;
        }

        public void setColor(int color) {
            this.mColor = color;
        }
    }
}
