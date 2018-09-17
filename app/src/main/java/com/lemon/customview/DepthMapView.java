package com.lemon.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.lemon.customview.bean.DepthDataBean;
import com.lemon.customview.utils.ResourceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class DepthMapView extends View {

    private int mWidth;
    private int mHeight;

    private int mGridWidth;
    private int mRowSpace;
    private int mLineBottom;
    private int lineCount = 4;

    private Paint mTextPaint;
    private Paint mBuyLinePaint;
    private Paint mSellLinePaint;
    private Paint mBuyPathPaint;
    private Paint mSellPathPaint;
    private Paint mRadioPaint;
    private Paint mSelectorBackgroundPaint;

    private Path mBuyPath = new Path();
    private Path mSellPath = new Path();

    private List<DepthDataBean> mBuyData;
    private List<DepthDataBean> mSellData;

    public int mPriceLimit;
    private int mVolumeLimit = 5;

    private HashMap<Integer, DepthDataBean> mapX;
    private HashMap<Integer, Float> mapY;
    private List<Float> mBottomPrice;
    private GestureDetector gestureDetector;

    private boolean mIsHave;
    private boolean mIsLongPress;

    private int mLastPosition;
    private float mMaxVolume;
    private float mMultiple;
    private int mDrawWidth = 0;
    private int mDrawHeight;
    private int mEventX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mEventX = (int) event.getX();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mIsLongPress = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 1) {
//                    mIsLongPress = true;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                mIsLongPress = true;
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                mIsLongPress = false;
                invalidate();
                break;
        }
        gestureDetector.onTouchEvent(event);
        return true;
    }

    public DepthMapView(Context context) {
        this(context, null);
    }

    public DepthMapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DepthMapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mLineBottom = 40;
        mHeight = ResourceUtil.dp2px(getContext(), 200);

        mapX = new HashMap<>();
        mapY = new HashMap<>();

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        mTextPaint.setTextSize(ResourceUtil.getDimension(getContext(), R.dimen.depth_text_size));
        mTextPaint.setColor(ResourceUtil.getColor(getContext(), R.color.chart_text));

        mBuyLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBuyLinePaint.setColor(ResourceUtil.getColor(getContext(), R.color.depth_line_green));
        mBuyLinePaint.setStrokeWidth(ResourceUtil.dp2px(getContext(), 1.5f));
        mBuyLinePaint.setStyle(Paint.Style.STROKE);
        mBuyLinePaint.setTextAlign(Paint.Align.CENTER);

        mSellLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSellLinePaint.setColor(ResourceUtil.getColor(getContext(), R.color.depth_line_red));
        mSellLinePaint.setStrokeWidth(ResourceUtil.dp2px(getContext(), 1.5f));
        mSellLinePaint.setStyle(Paint.Style.STROKE);
        mSellLinePaint.setTextAlign(Paint.Align.CENTER);

        mBuyPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBuyPathPaint.setColor(ResourceUtil.getColor(getContext(), R.color.depth_path_green));

        mSellPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSellPathPaint.setColor(ResourceUtil.getColor(getContext(), R.color.depth_path_red));

        mRadioPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRadioPaint.setTextAlign(Paint.Align.CENTER);

        mSelectorBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectorBackgroundPaint.setColor(ResourceUtil.getColor(getContext(), R.color.depth_selector));

        mBottomPrice = new ArrayList<>();
        mBuyData = new ArrayList<>();
        mSellData = new ArrayList<>();
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                mIsLongPress = true;
                invalidate();
            }
        });
    }

    public void setData(List<DepthDataBean> buyData, List<DepthDataBean> sellData) {
        if (buyData.size() > 0) {
            String volume = String.valueOf(buyData.get(0).getVolume());
            mVolumeLimit = volume.length() - 1 - volume.indexOf(".");
            mBuyData.clear();
            Collections.sort(buyData, new comparePrice());
            float vol = 0;
            DepthDataBean depthDataBean;
            for (int index = buyData.size() - 1; index >= 0; index--) {
                depthDataBean = buyData.get(index);
                vol += depthDataBean.getVolume();
                depthDataBean.setVolume(vol);
                mBuyData.add(0, depthDataBean);
            }
            if (mBottomPrice.size() >= 2) {
                mBottomPrice.add(0, mBuyData.get(0).getPrice());
                if (mBuyData.size() >= 2)
                    mBottomPrice.add(1, mBuyData.get(mBuyData.size() - 1).getPrice());
            } else if (mBottomPrice.size() == 1) {
                mBottomPrice.add(0, mBuyData.get(0).getPrice());
                if (mBuyData.size() >= 2)
                    mBottomPrice.add(mBuyData.get(mBuyData.size() - 1).getPrice());
                else
                    mBottomPrice.add(mBuyData.get(0).getPrice());
            } else {
                mBottomPrice.add(mBuyData.get(0).getPrice());
                if (mBuyData.size() >= 2)
                    mBottomPrice.add(mBuyData.get(mBuyData.size() - 1).getPrice());
                else
                    mBottomPrice.add(mBuyData.get(0).getPrice());
            }
            mMaxVolume = mBuyData.get(0).getVolume();
        }

        if (sellData.size() > 0) {
            mSellData.clear();
            Collections.sort(sellData, new comparePrice());
            float volsell = 0;
            for (DepthDataBean depthDataBean : sellData) {
                volsell += depthDataBean.getVolume();
                depthDataBean.setVolume(volsell);
                mSellData.add(depthDataBean);
            }
            if (mBottomPrice.size() >= 4) {
                mBottomPrice.add(2, mSellData.get(0).getPrice());
                if (mSellData.size() >= 2)
                    mBottomPrice.add(3, mSellData.get(mSellData.size() - 1).getPrice());
            } else if (mBottomPrice.size() == 3) {
                mBottomPrice.add(2, mSellData.get(0).getPrice());
                if (mSellData.size() >= 2)
                    mBottomPrice.add(mSellData.get(mSellData.size() - 1).getPrice());
                else
                    mBottomPrice.add(mSellData.get(0).getPrice());
            } else {
                mBottomPrice.add(mSellData.get(0).getPrice());
                if (mSellData.size() >= 2)
                    mBottomPrice.add(mSellData.get(mSellData.size() - 1).getPrice());
                else
                    mBottomPrice.add(mSellData.get(0).getPrice());
            }
            mMaxVolume = Math.max(mMaxVolume, mSellData.get(mSellData.size() - 1).getVolume());
        }
        mMaxVolume = mMaxVolume * 1.05f;
        mMultiple = mMaxVolume / lineCount;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mDrawWidth = mWidth / 2;
        mDrawHeight = mHeight - mLineBottom;
        mWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        mRowSpace = mDrawHeight / lineCount;
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ResourceUtil.getColor(getContext(), R.color.chart_background));
        canvas.save();
        drawBuy(canvas);
        drawSell(canvas);
        drawText(canvas);
        canvas.restore();
    }

    private void drawBuy(Canvas canvas) {
        mGridWidth = (mDrawWidth / (mBuyData.size() - 1 == 0 ? 1 : mBuyData.size() - 1));
        mBuyPath.reset();
        mapX.clear();
        mapY.clear();
        for (int i = 0; i < mBuyData.size(); i++) {
            if (i == 0) {
                mBuyPath.moveTo(0, (mDrawHeight - (mDrawHeight) * mBuyData.get(0).getVolume() / mMaxVolume));
            }
            if (i >= 1) {
                canvas.drawLine(mGridWidth * (i - 1), mDrawHeight - (mDrawHeight) * mBuyData.get(i - 1).getVolume() / mMaxVolume,
                        mGridWidth * i, mHeight - (mDrawHeight) * mBuyData.get(i).getVolume() / mMaxVolume - mLineBottom,
                        mBuyLinePaint);
            }
            if (i != mBuyData.size() - 1) { //深度圖數組繪製
                mBuyPath.quadTo(mGridWidth * i,
                        mDrawHeight - (mDrawHeight) * mBuyData.get(i).getVolume() / mMaxVolume,
                        mGridWidth * (i + 1),
                        mDrawHeight - (mDrawHeight) * mBuyData.get(i + 1).getVolume() / mMaxVolume);
            }

            float x = mGridWidth * i;
            float y = mDrawHeight - (mDrawHeight) * mBuyData.get(i).getVolume() / mMaxVolume;
            mapX.put((int) x, mBuyData.get(i));
            mapY.put((int) x, y);
            if (i == mBuyData.size() - 1) {
                mBuyPath.quadTo(mGridWidth * i, mDrawHeight - (mDrawHeight) * mBuyData.get(i).getVolume() / mMaxVolume,
                        mGridWidth * i, mDrawHeight);

                mBuyPath.quadTo(mGridWidth * i, mDrawHeight, 0, mDrawHeight);
                mBuyPath.close();
            }
        }
        canvas.drawPath(mBuyPath, mBuyPathPaint);
    }

    private void drawSell(Canvas canvas) {
        mGridWidth = (mDrawWidth / (mSellData.size() - 1 == 0 ? 1 : mSellData.size() - 1));
        mSellPath.reset();
        for (int i = 0; i < mSellData.size(); i++) {
            if (i == 0) {
                mSellPath.moveTo(mDrawWidth, mDrawHeight - mDrawHeight * mSellData.get(0).getVolume() / mMaxVolume);
            }
            if (i >= 1) {
                canvas.drawLine((mGridWidth * (i - 1)) + mDrawWidth, mDrawHeight - (mDrawHeight) * mSellData.get(i - 1).getVolume() / mMaxVolume,
                        (mGridWidth * i) + mDrawWidth, mDrawHeight - (mDrawHeight) * mSellData.get(i).getVolume() / mMaxVolume,
                        mSellLinePaint);
            }

            if (i != mSellData.size() - 1) { //深度圖數組繪製
                mSellPath.quadTo((mGridWidth * i) + mDrawWidth,
                        mDrawHeight - (mDrawHeight) * mSellData.get(i).getVolume() / mMaxVolume,
                        (mGridWidth * (i + 1)) + mDrawWidth,
                        mDrawHeight - (mDrawHeight) * mSellData.get(i + 1).getVolume() / mMaxVolume);
            }

            float x = (mGridWidth * i) + mDrawWidth;
            float y = mDrawHeight - (mDrawHeight) * mSellData.get(i).getVolume() / mMaxVolume;
            mapX.put((int) x, mSellData.get(i));
            mapY.put((int) x, y);
            if (i == mSellData.size() - 1) {
                mSellPath.quadTo(mWidth, mDrawHeight - (mDrawHeight) * mSellData.get(i).getVolume() / mMaxVolume,
                        (mGridWidth * i) + mDrawWidth, mDrawHeight);
                mSellPath.quadTo((mGridWidth * i) + mDrawWidth, mDrawHeight, mDrawWidth, mDrawHeight);
                mSellPath.close();
            }
        }
        canvas.drawPath(mSellPath, mSellPathPaint);
    }

    private void drawText(Canvas canvas) {
        float value;
        String str;
        for (int j = 0; j < lineCount; j++) {
            value = mMaxVolume - mMultiple * j;
            str = getVolumeValue(value);
            canvas.drawText(str, mWidth, mRowSpace * j + 30, mTextPaint);
        }

        int size = mBottomPrice.size();
        int height = mDrawHeight + mLineBottom / 2 + 10;
        if (size > 0) {
            String data = getValue(mBottomPrice.get(0));
            canvas.drawText(data, mTextPaint.measureText(data), height, mTextPaint);
            if (size > 1) {
                data = getValue(mBottomPrice.get(1));
                canvas.drawText(data, mDrawWidth - 10, height, mTextPaint);
                if (size > 2) {
                    data = getValue(mBottomPrice.get(2));
                    canvas.drawText(data, mDrawWidth + mTextPaint.measureText(data) + 10, height, mTextPaint);
                    if (size > 3) {
                        data = getValue(mBottomPrice.get(3));
                        canvas.drawText(data, mWidth, height, mTextPaint);
                    }
                }
            }
        }
        if (mIsLongPress) {
            mIsHave = false;
            for (int key : mapX.keySet()) {
                if (key == mEventX) {
                    mIsHave =true;
                    mLastPosition = mEventX;
                    if (key < mDrawWidth) {
                        canvas.drawCircle(key, mapY.get(key), ResourceUtil.dp2px(getContext(), 8), mBuyLinePaint);
                        mRadioPaint.setColor(ResourceUtil.getColor(getContext(), R.color.depth_line_green));
                    } else {
                        canvas.drawCircle(key, mapY.get(key), ResourceUtil.dp2px(getContext(), 8), mSellLinePaint);
                        mRadioPaint.setColor(ResourceUtil.getColor(getContext(), R.color.depth_line_red));
                    }
                    canvas.drawCircle(key, mapY.get(key), ResourceUtil.dp2px(getContext(), 2), mRadioPaint);

                    String volume = getContext().getString(R.string.trust_quantity) + ": " + getVolumeValue(mapX.get(key).getVolume());
                    String price = getContext().getString(R.string.trust_price) + ": " + getValue(mapX.get(key).getPrice());
                    float width = Math.max(mTextPaint.measureText(volume), mTextPaint.measureText(price));
                    Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
                    float textHeight = metrics.descent - metrics.ascent;
                    canvas.drawRoundRect(new RectF(mDrawWidth - width / 2 - 10, 0, mDrawWidth + width / 2 + 10, textHeight * 2 + 15), 10, 10, mSelectorBackgroundPaint);
                    canvas.drawText(getContext().getString(R.string.trust_quantity) + ": ",
                            mDrawWidth - width / 2 + 5 + mTextPaint.measureText(getContext().getString(R.string.trust_quantity)), textHeight + 2, mTextPaint);
                    canvas.drawText(getVolumeValue(mapX.get(key).getVolume()), mDrawWidth + width / 2 + 5, textHeight + 2, mTextPaint);

                    canvas.drawText(getContext().getString(R.string.trust_price) + ": ",
                            mDrawWidth - width / 2 + 5 + mTextPaint.measureText(getContext().getString(R.string.trust_price)), textHeight * 2 + 5, mTextPaint);
                    canvas.drawText(getValue(mapX.get(key).getPrice()), mDrawWidth + width / 2 + 5, textHeight * 2 + 5, mTextPaint);
                    return;
                }
            }
            if (!mIsHave) {
                mIsHave = true;
                if (mLastPosition < mDrawWidth) {
                    canvas.drawCircle(mLastPosition, mapY.get(mLastPosition), ResourceUtil.dp2px(getContext(), 8), mBuyLinePaint);
                    mRadioPaint.setColor(ResourceUtil.getColor(getContext(), R.color.depth_line_green));
                } else {
                    canvas.drawCircle(mLastPosition, mapY.get(mLastPosition), ResourceUtil.dp2px(getContext(), 8), mSellLinePaint);
                    mRadioPaint.setColor(ResourceUtil.getColor(getContext(), R.color.depth_line_red));
                }
                canvas.drawCircle(mLastPosition, mapY.get(mLastPosition), ResourceUtil.dp2px(getContext(), 2), mRadioPaint);

                String volume = getContext().getString(R.string.trust_quantity) + ": " + getVolumeValue(mapX.get(mLastPosition).getVolume());
                String price = getContext().getString(R.string.trust_price) + ": " + getValue(mapX.get(mLastPosition).getPrice());
                float width = Math.max(mTextPaint.measureText(volume), mTextPaint.measureText(price));
                Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
                float textHeight = metrics.descent - metrics.ascent;
                canvas.drawRoundRect(new RectF(mDrawWidth - width / 2 - 10, 0, mDrawWidth + width / 2 + 10, textHeight * 2 + 15), 10, 10, mSelectorBackgroundPaint);
                canvas.drawText(getContext().getString(R.string.trust_quantity) + ": ",
                        mDrawWidth - width / 2 + 5 + mTextPaint.measureText(getContext().getString(R.string.trust_quantity)), textHeight + 2, mTextPaint);
                canvas.drawText(getVolumeValue(mapX.get(mLastPosition).getVolume()), mDrawWidth + width / 2 + 5, textHeight + 2, mTextPaint);

                canvas.drawText(getContext().getString(R.string.trust_price) + ": ",
                        mDrawWidth - width / 2 + 5 + mTextPaint.measureText(getContext().getString(R.string.trust_price)), textHeight * 2 + 5, mTextPaint);
                canvas.drawText(getValue(mapX.get(mLastPosition).getPrice()), mDrawWidth + width / 2 + 5, textHeight * 2 + 5, mTextPaint);
            }
        }
    }

    public class comparePrice implements Comparator<DepthDataBean> {
        @Override
        public int compare(DepthDataBean o1, DepthDataBean o2) {
            float str1 = o1.getPrice();
            float str2 = o2.getPrice();
            return Float.compare(str1, str2);
        }
    }

    public String getValue(float value) {
//        String value = new BigDecimal(data).toPlainString();
//        return subZeroAndDot(value);
        return String.format("%." + mPriceLimit + "f", value);
    }

    @SuppressLint("DefaultLocale")
    public String getVolumeValue(float value) {
        return String.format("%.4f", value);
    }

}
