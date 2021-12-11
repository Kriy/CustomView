package com.lemon.customview.widget.textview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.appcompat.widget.AppCompatTextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberTextView extends AppCompatTextView {

    private String mStartNumber = "0";
    private String mEndNumber = "0";
    private long mAnimDuration = 2500;
    private String mPrefixStr = "";
    private String mPostfixStr = "";
    private boolean mIsEnableAnim = true;
    private boolean isInt;
    private ValueAnimator mAnim;

    public NumberTextView(Context context) {
        super(context, null);
    }

    public NumberTextView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public NumberTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setNumberString(String number) {
        setNumberString("0", number);
    }

    public void setNumberString(String startNumber, String endNumber) {
        mStartNumber = startNumber;
        mEndNumber = endNumber;
        if (checkNumString(startNumber, endNumber)) {
            startAnim();
        } else {
            setText(mPrefixStr + endNumber + mPostfixStr);
        }
    }

    public void setEnableAnim(boolean enableAnim) {
        mIsEnableAnim = enableAnim;
    }

    public void setDuration(long mDuration) {
        this.mAnimDuration = mDuration;
    }

    public void setPrefixString(String mPrefixString) {
        this.mPrefixStr = mPrefixString;
    }

    public void setPostfixString(String mPostfixString) {
        this.mPostfixStr = mPostfixString;
    }

    private boolean checkNumString(String startNumber, String endNumber) {
        String regInt = "-?\\d*";
        isInt = startNumber.matches(regInt) && endNumber.matches(regInt);
        if (isInt) {
            return true;
        }
        String regexDecimal = "-?[1-9]\\d*.\\d*|-?0.\\d*[1-9]\\d*";
        if ("0".equals(startNumber)) {
            if (endNumber.matches(regexDecimal)) {
                return true;
            }
        }
        if (startNumber.matches(regexDecimal) && endNumber.matches(regexDecimal)) {
            return true;
        }
        return false;
    }

    private void startAnim() {
        if (!mIsEnableAnim) {
            setText(mPrefixStr + format(new BigDecimal(mEndNumber)) + mPostfixStr);
            return;
        }
        mAnim = ValueAnimator.ofObject(new BigDecimalEvaluator(), new BigDecimal(mStartNumber), new BigDecimal(mEndNumber));
        mAnim.setDuration(mAnimDuration);
        mAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                BigDecimal value = (BigDecimal) animation.getAnimatedValue();
                setText(mPrefixStr + format(value) + mPostfixStr);
            }
        });
        mAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setText(mPrefixStr + mEndNumber + mPostfixStr);
            }
        });
        mAnim.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAnim != null) {
            mAnim.cancel();
        }
    }

    //数字格式化，保留2为小数字
    private String format(BigDecimal bd) {
        StringBuilder sb = new StringBuilder();
        if (isInt) {
            sb.append("#,###");
        } else {
            int length = 0;
            String[] s1 = mStartNumber.split("\\.");
            String[] s2 = mEndNumber.split("\\.");
            String[] s = s1.length > s2.length ? s1 : s2;
            if (s.length > 1) {
                String decimals = s[1];
                if (decimals != null) {
                    length = decimals.length();
                }
            }
            sb.append("#,##0");
            if (length > 0) {
                sb.append(".");
                for (int i = 0; i < length; i++) {
                    sb.append("0");
                }
            }
        }
        DecimalFormat df = new DecimalFormat(sb.toString());
        return df.format(bd);
    }

    //    TypeEvaluator这个接口，其用来实现自定义动画属性值的计算器。TypeEvaluator接口中只定义了一个方法，
//    evaluate(float fraction, T startValue, T endValue)，evaluate可以接受三个参数fraction,startValue,endValue，
//    其中startValue和endValue都是使用泛型定义的，意味着我们可以自定义传入的计算的动画属性，
//    其返回值也使我们定义的的动画属性类型，以达到在AnimatorUpdateListener监听中，计算出来的属性值都是我们定义的。
//    TypeEvaluator的实现类一般与 setEvaluator(TypeEvaluator)方法或者ofObject(TypeEvaluator, Object… values)配合使用，来定义动画的属性值计算器。
    private static class BigDecimalEvaluator implements TypeEvaluator {
        // fraction : 动画进度   startValue T : 动画属性初始值     endValue T: 动画属性结束值
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            BigDecimal start = (BigDecimal) startValue;
            BigDecimal end = (BigDecimal) endValue;
            BigDecimal result = end.subtract(start);
            return result.multiply(new BigDecimal(fraction)).add(start);
        }
    }
}
