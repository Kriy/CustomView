package com.lemon.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
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
    //圆点半径
    private int mDotRadius = 2;
    //圆圈半径
    private int mCircleRadius = 8;
    private float mGridWidth;
    //底部价格区域高度
    private int mBottomPriceHeight;
    //右侧委托量绘制个数
    private int mLineCount;
    //背景颜色
    private int mBackgroundColor;

    private boolean mIsHave;
    //是否是长按
    private boolean mIsLongPress;

    //最大的委托量
    private float mMaxVolume;
    private float mMultiple;
    private int mLastPosition;
    private int mDrawWidth = 0;
    private int mDrawHeight;
    //触摸点的X轴值
    private int mEventX;

    //文案绘制画笔
    private Paint mTextPaint;
    //买入区域边线绘制画笔
    private Paint mBuyLinePaint;
    //卖出区域边线绘制画笔
    private Paint mSellLinePaint;
    //买入区域绘制画笔
    private Paint mBuyPathPaint;
    //卖出取悦绘制画笔
    private Paint mSellPathPaint;
    //选中时圆点绘制画笔
    private Paint mRadioPaint;
    //选中时中间文案背景画笔
    private Paint mSelectorBackgroundPaint;

    private Path mBuyPath = new Path();
    private Path mSellPath = new Path();

    private List<DepthDataBean> mBuyData;
    private List<DepthDataBean> mSellData;

    //    价格显示精度限制
    public int mPriceLimit = 4;
//    private int mVolumeLimit = 5;

    private HashMap<Integer, DepthDataBean> mMapX;
    private HashMap<Integer, Float> mMapY;
    private Float[] mBottomPrice;
    private GestureDetector mGestureDetector;

    public DepthMapView(Context context) {
        this(context, null);
    }

    public DepthMapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DepthMapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @SuppressLint("UseSparseArrays")
    private void init(AttributeSet attrs) {
        mBottomPriceHeight = 40;
        mMapX = new HashMap<>();
        mMapY = new HashMap<>();
        mBottomPrice = new Float[4];
        mBuyData = new ArrayList<>();
        mSellData = new ArrayList<>();
        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                mIsLongPress = true;
                invalidate();
            }
        });

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);

        mBuyLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBuyLinePaint.setStyle(Paint.Style.STROKE);
        mBuyLinePaint.setTextAlign(Paint.Align.CENTER);
        mBuyPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mSellLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSellLinePaint.setStyle(Paint.Style.STROKE);
        mSellLinePaint.setTextAlign(Paint.Align.CENTER);
        mSellPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mRadioPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRadioPaint.setTextAlign(Paint.Align.CENTER);
        mSelectorBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DepthMapView);
        if (typedArray != null) {
            try {
                mLineCount = typedArray.getInt(R.styleable.DepthMapView_line_count, 4);
                mDotRadius = typedArray.getDimensionPixelSize(R.styleable.DepthMapView_dot_radius, ResourceUtil.dp2px(getContext(), mDotRadius));
                mCircleRadius = typedArray.getDimensionPixelSize(R.styleable.DepthMapView_circle_radius, ResourceUtil.dp2px(getContext(), mCircleRadius));
                mBackgroundColor = typedArray.getColor(R.styleable.DepthMapView_background_color, ResourceUtil.getColor(getContext(), R.color.depth_background));
                mBuyLinePaint.setStrokeWidth(typedArray.getDimensionPixelSize(R.styleable.DepthMapView_line_width, ResourceUtil.dp2px(getContext(), 1.5f)));
                mSellLinePaint.setStrokeWidth(typedArray.getDimensionPixelSize(R.styleable.DepthMapView_line_width, ResourceUtil.dp2px(getContext(), 1.5f)));
                mTextPaint.setColor(typedArray.getColor(R.styleable.DepthMapView_text_color, ResourceUtil.getColor(getContext(), R.color.depth_text_color)));
                mTextPaint.setTextSize(typedArray.getDimension(R.styleable.DepthMapView_text_size, ResourceUtil.getDimension(getContext(), R.dimen.depth_text_size)));
                mBuyLinePaint.setColor(typedArray.getColor(R.styleable.DepthMapView_buy_line_color, ResourceUtil.getColor(getContext(), R.color.depth_buy_line)));
                mSellLinePaint.setColor(typedArray.getColor(R.styleable.DepthMapView_sell_line_color, ResourceUtil.getColor(getContext(), R.color.depth_sell_line)));
                mBuyPathPaint.setColor(typedArray.getColor(R.styleable.DepthMapView_buy_path_color, ResourceUtil.getColor(getContext(), R.color.depth_buy_path)));
                mSellPathPaint.setColor(typedArray.getColor(R.styleable.DepthMapView_sell_path_color, ResourceUtil.getColor(getContext(), R.color.depth_sell_path)));
                mSelectorBackgroundPaint.setColor(typedArray.getColor(R.styleable.DepthMapView_selector_background_color, ResourceUtil.getColor(getContext(), R.color.depth_selector)));
            } finally {
                typedArray.recycle();
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        mDrawWidth = mWidth / 2 - 1;
        mDrawHeight = h - mBottomPriceHeight;
    }

    public void setData(List<DepthDataBean> buyData, List<DepthDataBean> sellData) {
        float vol = 0;
        if (buyData.size() > 0) {
            mBuyData.clear();
            Collections.sort(buyData, new comparePrice());
            DepthDataBean depthDataBean;
            for (int index = buyData.size() - 1; index >= 0; index--) {
                depthDataBean = buyData.get(index);
                vol += depthDataBean.getVolume();
                depthDataBean.setVolume(vol);
                mBuyData.add(0, depthDataBean);
            }
            mBottomPrice[0] = mBuyData.get(0).getPrice();
            mBottomPrice[1] = mBuyData.get(mBuyData.size() > 1 ? mBuyData.size() - 1 : 0).getPrice();
            mMaxVolume = mBuyData.get(0).getVolume();
        }

        if (sellData.size() > 0) {
            mSellData.clear();
            vol = 0;
            Collections.sort(sellData, new comparePrice());
            for (DepthDataBean depthDataBean : sellData) {
                vol += depthDataBean.getVolume();
                depthDataBean.setVolume(vol);
                mSellData.add(depthDataBean);
            }
            mBottomPrice[2] = mSellData.get(0).getPrice();
            mBottomPrice[3] = mSellData.get(mSellData.size() > 1 ? mSellData.size() - 1 : 0).getPrice();
            mMaxVolume = Math.max(mMaxVolume, mSellData.get(mSellData.size() - 1).getVolume());
        }
        mMaxVolume = mMaxVolume * 1.05f;
        mMultiple = mMaxVolume / mLineCount;
        invalidate();
    }

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
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mBackgroundColor);
        canvas.save();
        drawBuy(canvas);
        drawSell(canvas);
        drawText(canvas);
        canvas.restore();
    }

    private void drawBuy(Canvas canvas) {
        mGridWidth = (mDrawWidth * 1.0f / (mBuyData.size() - 1 == 0 ? 1 : mBuyData.size() - 1));
        mBuyPath.reset();
        mMapX.clear();
        mMapY.clear();
        float x;
        float y;
        for (int i = 0; i < mBuyData.size(); i++) {
            if (i == 0) {
                mBuyPath.moveTo(0, getY(mBuyData.get(0).getVolume()));
            }
            y = getY(mBuyData.get(i).getVolume());
            if (i >= 1) {
                canvas.drawLine(mGridWidth * (i - 1), getY(mBuyData.get(i - 1).getVolume()), mGridWidth * i, y, mBuyLinePaint);
            }
            if (i != mBuyData.size() - 1) {
                mBuyPath.quadTo(mGridWidth * i, y, mGridWidth * (i + 1), getY(mBuyData.get(i + 1).getVolume()));
            }

            x = mGridWidth * i;
            mMapX.put((int) x, mBuyData.get(i));
            mMapY.put((int) x, y);
            if (i == mBuyData.size() - 1) {
                mBuyPath.quadTo(mGridWidth * i, y, mGridWidth * i, mDrawHeight);
                mBuyPath.quadTo(mGridWidth * i, mDrawHeight, 0, mDrawHeight);
                mBuyPath.close();
            }
        }
        canvas.drawPath(mBuyPath, mBuyPathPaint);
    }

    private void drawSell(Canvas canvas) {
        mGridWidth = (mDrawWidth * 1.0f / (mSellData.size() - 1 == 0 ? 1 : mSellData.size() - 1));
        mSellPath.reset();
        float x;
        float y;
        for (int i = 0; i < mSellData.size(); i++) {
            if (i == 0) {
                mSellPath.moveTo(mDrawWidth, getY(mSellData.get(0).getVolume()));
            }
            y = getY(mSellData.get(i).getVolume());
            if (i >= 1) {
                canvas.drawLine((mGridWidth * (i - 1)) + mDrawWidth, getY(mSellData.get(i - 1).getVolume()),
                        (mGridWidth * i) + mDrawWidth, y, mSellLinePaint);
            }
            if (i != mSellData.size() - 1) {
                mSellPath.quadTo((mGridWidth * i) + mDrawWidth, y,
                        (mGridWidth * (i + 1)) + mDrawWidth, getY(mSellData.get(i + 1).getVolume()));
            }
            x = (mGridWidth * i) + mDrawWidth;
            mMapX.put((int) x, mSellData.get(i));
            mMapY.put((int) x, y);
            if (i == mSellData.size() - 1) {
                mSellPath.quadTo(mWidth, y, (mGridWidth * i) + mDrawWidth, mDrawHeight);
                mSellPath.quadTo((mGridWidth * i) + mDrawWidth, mDrawHeight, mDrawWidth, mDrawHeight);
                mSellPath.close();
            }
        }
        canvas.drawPath(mSellPath, mSellPathPaint);
    }

    private void drawText(Canvas canvas) {
        float value;
        String str;
        for (int j = 0; j < mLineCount; j++) {
            value = mMaxVolume - mMultiple * j;
            str = getVolumeValue(value);
            canvas.drawText(str, mWidth, mDrawHeight / mLineCount * j + 30, mTextPaint);
        }
        int size = mBottomPrice.length;
        int height = mDrawHeight + mBottomPriceHeight / 2 + 10;
        if (size > 0 && mBottomPrice[0] != null) {
            String data = getValue(mBottomPrice[0]);
            canvas.drawText(data, mTextPaint.measureText(data), height, mTextPaint);
            data = getValue(mBottomPrice[1]);
            canvas.drawText(data, mDrawWidth - 10, height, mTextPaint);
            data = getValue(mBottomPrice[2]);
            canvas.drawText(data, mDrawWidth + mTextPaint.measureText(data) + 10, height, mTextPaint);
            data = getValue(mBottomPrice[3]);
            canvas.drawText(data, mWidth, height, mTextPaint);
        }
        if (mIsLongPress) {
            mIsHave = false;
            for (int key : mMapX.keySet()) {
                if (key == mEventX) {
                    mLastPosition = mEventX;
                    drawSelectorView(canvas, key);
                    break;
                }
            }
            if (!mIsHave) {
                drawSelectorView(canvas, mLastPosition);
            }
        }
    }

    private void drawSelectorView(Canvas canvas, int position) {
        mIsHave = true;
        if (position < mDrawWidth) {
            canvas.drawCircle(position, mMapY.get(position), mCircleRadius, mBuyLinePaint);
            mRadioPaint.setColor(ResourceUtil.getColor(getContext(), R.color.depth_buy_line));
        } else {
            canvas.drawCircle(position, mMapY.get(position), mCircleRadius, mSellLinePaint);
            mRadioPaint.setColor(ResourceUtil.getColor(getContext(), R.color.depth_sell_line));
        }
        canvas.drawCircle(position, mMapY.get(position), mDotRadius, mRadioPaint);

        String volume = getContext().getString(R.string.trust_quantity) + ": " + getVolumeValue(mMapX.get(position).getVolume());
        String price = getContext().getString(R.string.trust_price) + ": " + getValue(mMapX.get(position).getPrice());
        float width = Math.max(mTextPaint.measureText(volume), mTextPaint.measureText(price));
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        float textHeight = metrics.descent - metrics.ascent;

        int padding = ResourceUtil.dp2px(getContext(), 5);
        canvas.drawRoundRect(new RectF(mDrawWidth - width / 2 - padding, 0, mDrawWidth + width / 2 + padding * 2, textHeight * 2 + padding * 2), 10, 10, mSelectorBackgroundPaint);
        canvas.drawText(getContext().getString(R.string.trust_quantity) + ": ",
                mDrawWidth - width / 2 + padding + mTextPaint.measureText(getContext().getString(R.string.trust_quantity)), textHeight + 2, mTextPaint);
        canvas.drawText(getVolumeValue(mMapX.get(position).getVolume()), mDrawWidth + width / 2 + padding, textHeight + 2, mTextPaint);
        canvas.drawText(getContext().getString(R.string.trust_price) + ": ",
                mDrawWidth - width / 2 + padding + mTextPaint.measureText(getContext().getString(R.string.trust_price)), textHeight * 2 + padding, mTextPaint);
        canvas.drawText(getValue(mMapX.get(position).getPrice()), mDrawWidth + width / 2 + padding, textHeight * 2 + padding, mTextPaint);
    }

    public class comparePrice implements Comparator<DepthDataBean> {
        @Override
        public int compare(DepthDataBean o1, DepthDataBean o2) {
            float str1 = o1.getPrice();
            float str2 = o2.getPrice();
            return Float.compare(str1, str2);
        }
    }

    private float getY(float volume) {
        return mDrawHeight - (mDrawHeight) * volume / mMaxVolume;
    }

    private String getValue(float value) {
//        String value = new BigDecimal(data).toPlainString();
//        return subZeroAndDot(value);
        return String.format("%." + mPriceLimit + "f", value);
    }

    @SuppressLint("DefaultLocale")
    private String getVolumeValue(float value) {
        return String.format("%.4f", value);
    }

}
