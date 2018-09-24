package com.lemon.customview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.lemon.customview.R;
import com.lemon.customview.utils.ResourceUtil;

import java.util.List;

public class MarqueeView extends View implements Runnable {

    private static final String TAG = "MarqueeView";

    public static final int REPEAT_ONETIME = 0;//一次结束
    public static final int REPEAT_INTERVAL = 1;//一次结束以后，再继续第二次
    public static final int REPEAT_CONTINUOUS = 2;//紧接着 滚动第二次

    private String mContent;
    private float mSpeed = 1;
    private int mTextColor = Color.BLACK;
    private float mTextSize = 12;
    private int mTextDistance;
    private int mTextDistance1 = 10;
    private String mBlackCount = "";

    private int mRepeatType = REPEAT_INTERVAL;
    private float mStartLocationDistance = 1.0f;
    private boolean mIsClickStop = false;
    private boolean mIsResetLocation = true;
    private float mXlocation = 0;
    private int mContentWidth;
    private boolean mIsRoll = false;
    private float mOneBlackWidth;
    private TextPaint mPaint;
    private Rect mRect;
    private int mRepeatCount = 0;
    private boolean mResetInit = true;
    private Thread mThread;
    private String mStringContent = "";
    private float mTextHeight;

    public MarqueeView(Context context) {
        this(context, null);
    }

    public MarqueeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initPaint();
        setClick();
    }

    @SuppressLint("RestrictedApi")
    private void initAttrs(AttributeSet attrs) {
        TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(getContext(), attrs, R.styleable.MarqueeView);
        mTextColor = tintTypedArray.getColor(R.styleable.MarqueeView_marqueeview_text_color, mTextColor);
        mIsClickStop = tintTypedArray.getBoolean(R.styleable.MarqueeView_marqueeview_isclickalbe_stop, mIsClickStop);
        mSpeed = tintTypedArray.getFloat(R.styleable.MarqueeView_marqueeview_text_speed, mSpeed);
        mTextSize = tintTypedArray.getFloat(R.styleable.MarqueeView_marqueeview_text_size, mTextSize);
        mTextDistance1 = tintTypedArray.getInteger(R.styleable.MarqueeView_marqueeview_text_distance, mTextDistance1);
        mStartLocationDistance = tintTypedArray.getFloat(R.styleable.MarqueeView_marqueeview_text_startlocationdistance, mStartLocationDistance);
        mRepeatType = tintTypedArray.getInt(R.styleable.MarqueeView_marqueeview_repeat_type, mRepeatType);
        tintTypedArray.recycle();
    }

    private void initPaint() {
        mRect = new Rect();
        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(ResourceUtil.dp2px(getContext(), mTextSize));
    }

    private void setClick() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsClickStop) {
                    if (mIsRoll) {
                        stopRoll();
                    } else {
                        continueRoll();
                    }
                }
            }
        });
    }

    public void stopRoll() {
        mIsRoll = false;
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }
    }

    public void continueRoll() {
        if (!mIsRoll) {
            if (mThread != null) {
                mThread.interrupt();
                mThread = null;
            }
            mIsRoll = true;
            mThread = new Thread(this);
            mThread.start();
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mResetInit) {
            setTextDirection(mTextDistance1);
            if (mStartLocationDistance < 0) {
                mStartLocationDistance = 0;
            } else if (mStartLocationDistance > 1) {
                mStartLocationDistance = 1;
            }
            mXlocation = getWidth() * mStartLocationDistance;
            mResetInit = false;
        }

        switch (mRepeatType) {
            case REPEAT_ONETIME:
                if (mContentWidth < (-mXlocation))
                    stopRoll();
                break;
            case REPEAT_INTERVAL:
                if (mXlocation <= -mContentWidth)
                    mXlocation = getWidth();
                break;
            case REPEAT_CONTINUOUS:
                if (mXlocation < 0) {
                    int beAppend = (int) ((-mXlocation) / mContentWidth);
                    if (beAppend >= mRepeatCount) {
                        mRepeatCount++;
                        mContent += mStringContent;
                    }
                }
                break;
            default:
                if (mContentWidth < (-mXlocation))
                    stopRoll();
                break;
        }
        if (mContent != null) {
            canvas.drawText(mContent, mXlocation, getHeight() / 2 + mTextHeight / 2, mPaint);
        }
    }

    public void setRepetType(int repetType) {
        this.mRepeatType = repetType;
        mResetInit = true;
        setContent(mStringContent);
    }

    @Override
    public void run() {
        while (mIsRoll && !TextUtils.isEmpty(mStringContent)) {
            try {
                Thread.sleep(10);
                mXlocation = mXlocation - mSpeed;
                postInvalidate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setTextDistance(int textDistance) {
        String black = " ";
        mOneBlackWidth = getBlackWidth();
        textDistance = ResourceUtil.dp2px(getContext(), textDistance);
        int count = (int) (textDistance / mOneBlackWidth);
        if (count == 0)
            count = 1;
        mTextDistance = (int) (mOneBlackWidth * count);
        mBlackCount = "";
        for (int i = 0; i <= count; i++) {
            mBlackCount += black;
        }
        setContent(mStringContent);
    }

    private float getBlackWidth() {
        String text1 = "en en";
        String text2 = "enen";
        return getContentWidth(text1) - getContentWidth(text2);
    }

    private float getContentWidth(String text) {
        if (TextUtils.isEmpty(text)) {
            return 0;
        }
        if (mRect == null)
            mRect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), mRect);
        mTextHeight = getContentHeight();
        return mRect.width();
    }

    private float getContentHeight() {
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        return Math.abs((fontMetrics.bottom - fontMetrics.top)) / 2;
    }

    public void setTextColor(int textColor) {
        if (textColor != 0) {
            this.mTextColor = textColor;
            mPaint.setColor(getResources().getColor(textColor));//文字颜色值,可以不设定
        }
    }

    public void setTextSize(float textSize) {
        if (textSize > 0) {
            this.mTextSize = textSize;
            mPaint.setTextSize(ResourceUtil.dp2px(getContext(), textSize));//文字颜色值,可以不设定
            mContentWidth = (int) (getContentWidth(mStringContent) + mTextDistance);//大小改变，需要重新计算宽高
        }
    }

    public void setTextSpeed(float speed) {
        this.mSpeed = speed;
    }

    public void setClickStop(boolean isClickStop) {
        this.mIsClickStop = isClickStop;
    }

    public void setContinueAble(int isContinueAble) {
        this.mRepeatType = isContinueAble;
    }

    private void setResetLocation(boolean isReset) {
        mIsResetLocation = isReset;
    }

    public void setContent(List<String> strings) {
        setTextDistance(mTextDistance1);
        StringBuilder temString = new StringBuilder();
        if (strings != null && strings.size() != 0) {
            for (int i = 0; i < strings.size(); i++) {
                temString.append(strings.get(i)).append(mBlackCount);
            }
        }
        setContent(temString.toString());
    }

    public void setContent(String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        if (mIsResetLocation) {//控制重新设置文本内容的时候，是否初始化xLocation。
            mXlocation = getWidth() * mStartLocationDistance;
        }
        if (!content.endsWith(mBlackCount)) {
            content = content + mBlackCount;//避免没有后缀
        }
        this.mStringContent = content;
        //这里需要计算宽度啦，当然要根据模式来搞
        if (mRepeatType == REPEAT_CONTINUOUS) {
            //如果说是循环的话，则需要计算 文本的宽度 ，然后再根据屏幕宽度 ， 看能一个屏幕能盛得下几个文本
            mContentWidth = (int) (getContentWidth(mStringContent) + mTextDistance);//可以理解为一个单元内容的长度
            //从0 开始计算重复次数了， 否则到最后 会跨不过这个坎而消失。
            mRepeatCount = 0;
            int contentCount = (getWidth() / mContentWidth) + 2;
            this.mContent = "";
            for (int i = 0; i <= contentCount; i++) {
                this.mContent += this.mStringContent;//根据重复次数去叠加。
            }
        } else {
            if (mXlocation < 0 && mRepeatType == REPEAT_ONETIME) {
                if (-mXlocation > mContentWidth) {
                    mXlocation = getWidth() * mStartLocationDistance;
                }
            }
            mContentWidth = (int) getContentWidth(mStringContent);
            this.mContent = content;
        }
        if (!mIsRoll) {//如果没有在滚动的话，重新开启线程滚动
            continueRoll();
        }
    }
}
