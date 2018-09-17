package com.lemon.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lemon.customview.R;
import com.lemon.customview.utils.ResourceUtil;

/**
 * Created on 2017/9/6.
 */

public class TimeLineMarkerView extends View {

    private int mMarkerSize = 16; //圆点大小,单位为dp
    private int mLineSize   = 2; //线段粗细
    private Drawable mUpLine; //上面线段颜色或者图片
    private Drawable mDownLine; //下面线段颜色或者图片
    private Drawable mMarkerDrawable;//圆点颜色或者图片
    private boolean  mOrientation; //竖向还是横向  false竖向,true横向

    public TimeLineMarkerView(Context context) {
        this(context, null);
    }

    public TimeLineMarkerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeLineMarkerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimeLineMarker);
        mMarkerSize = typedArray.getDimensionPixelSize(R.styleable.TimeLineMarker_markerSize, mMarkerSize);
        mLineSize = typedArray.getDimensionPixelSize(R.styleable.TimeLineMarker_lineSize, mLineSize);
        mUpLine = typedArray.getDrawable(R.styleable.TimeLineMarker_upLine);
        mDownLine = typedArray.getDrawable(R.styleable.TimeLineMarker_downLine);
        mMarkerDrawable = typedArray.getDrawable(R.styleable.TimeLineMarker_marker);
        mOrientation = typedArray.getBoolean(R.styleable.TimeLineMarker_orientation, false);
        typedArray.recycle();

        if (mUpLine != null) {
            mUpLine.setCallback(this);
        }

        if (mDownLine != null) {
            mDownLine.setCallback(this);
        }

        if (mMarkerDrawable != null) {
            mMarkerDrawable.setCallback(this);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (mOrientation) {
            if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(120, 80);
            } else if (widthSpecMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(120, heightSpecSize);
            } else if (heightMeasureSpec == MeasureSpec.AT_MOST) {
                setMeasuredDimension(widthMeasureSpec, 80);
            }
        } else {
            if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(80, 120);
            } else if (widthSpecMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(80, heightMeasureSpec);
            } else if (heightSpecMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(widthSpecSize, 120);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mUpLine != null) {
            mUpLine.draw(canvas);
        }
        if (mDownLine != null) {
            mDownLine.draw(canvas);
        }
        if (mMarkerDrawable != null) {
            mMarkerDrawable.draw(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initDrawableSize();
    }

    private void initDrawableSize() {
        int pLeft = getPaddingLeft();
        int pRight = getPaddingRight();
        int pTop = getPaddingTop();
        int pBottom = getPaddingBottom();
        int width = getWidth();
        int height = getHeight();
        int cWidth = width - pLeft - pRight;
        int cHeight = height - pTop - pBottom;
        Rect bounds;
        int mMarkerSizepx = ResourceUtil.dp2px(getContext(), mMarkerSize);
        int mLineSizepx = ResourceUtil.dp2px(getContext(), mLineSize);

        if (mOrientation) {
            if (mMarkerDrawable != null) {
                int markSize = Math.min(Math.min(cWidth, cHeight), mMarkerSizepx);
                mMarkerDrawable.setBounds(pLeft + width / 2 - markSize / 2, height / 2 - markSize / 2, pLeft + width / 2 - markSize / 2 + markSize, height / 2 + markSize / 2);
                bounds = mMarkerDrawable.getBounds();
            } else {
                bounds = new Rect(pLeft + width / 2, pTop + height, pLeft + width / 2, pTop);
            }
            int halfLine = mLineSizepx >> 1;
            int linetop = bounds.centerY() - halfLine;
            if (mUpLine != null) {
                mUpLine.setBounds(0, linetop, bounds.left, linetop + mLineSizepx);
            }
            if (mDownLine != null) {
                mDownLine.setBounds(bounds.right, linetop, width, linetop + mLineSizepx);
            }
        } else {
            if (mMarkerDrawable != null) {
                int markSize = Math.min(Math.min(cWidth, cHeight), mMarkerSizepx);
                mMarkerDrawable.setBounds(pLeft + width / 2 - markSize / 2, pTop + height / 2 - markSize / 2, pLeft + markSize / 2 + width / 2, pTop + height / 2 - markSize / 2 + markSize);
                bounds = mMarkerDrawable.getBounds();
            } else {
                bounds = new Rect(pLeft + width - mLineSizepx / 2, pTop + height / 2, pLeft + mLineSizepx / 2, pTop + height / 2);
            }
            int halfLine = mLineSizepx >> 1;
            int lineLeft = bounds.centerX() - halfLine;
            if (mUpLine != null) {
                mUpLine.setBounds(lineLeft, 0, lineLeft + mLineSizepx, bounds.top);
            }
            if (mDownLine != null) {
                mDownLine.setBounds(lineLeft, bounds.bottom, lineLeft + mLineSizepx, height);
            }
        }
    }

    //下来提供几个方法。以供代码动态设置
    public void setLineSize(int linesize) {
        if (this.mLineSize != linesize) {
            mLineSize = linesize;
            initDrawableSize();
            invalidate();
        }
    }

    public void setMarkerSize(int markerSize) {
        if (this.mMarkerSize != markerSize) {
            mMarkerSize = markerSize;
            initDrawableSize();
            invalidate();
        }
    }

    public void setBeginLine(Drawable beginLine) {
        if (this.mUpLine != beginLine) {
            this.mUpLine = beginLine;
            if (mUpLine != null) {
                mUpLine.setCallback(this);
            }
            initDrawableSize();
            invalidate();
        }
    }

    public void setEndLine(Drawable endLine) {
        if (this.mDownLine != endLine) {
            this.mDownLine = endLine;
            if (mDownLine != null) {
                mDownLine.setCallback(this);
            }
            initDrawableSize();
            invalidate();
        }
    }

    public void setMarkerDrawable(Drawable markerDrawable) {
        if (this.mMarkerDrawable != markerDrawable) {
            this.mMarkerDrawable = markerDrawable;
            if (mMarkerDrawable != null) {
                mMarkerDrawable.setCallback(this);
            }
            initDrawableSize();
            invalidate();
        }
    }
}
