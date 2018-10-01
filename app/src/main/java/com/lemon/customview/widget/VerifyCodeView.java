package com.lemon.customview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lemon.customview.R;
import com.lemon.customview.listener.TextWatcherListener;
import com.lemon.customview.utils.ResourceUtil;

public class VerifyCodeView extends RelativeLayout {

    private int mCount = 6;
    private int mTextColor;
    private float mTextSize;

    private TextView[] mTextViews = new TextView[mCount];
    private String mInputContent = "";
    private EditText mEtInput;

    public VerifyCodeView(Context context) {
        this(context, null);
    }

    public VerifyCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(context, attrs);
        addView(context);
        setEditTextListener();
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerifyCodeView);
        mCount = typedArray.getInteger(R.styleable.VerifyCodeView_count, 6);
        mTextColor = typedArray.getColor(R.styleable.VerifyCodeView_textColor, Color.parseColor("#CED1F3"));
        mTextSize = typedArray.getFloat(R.styleable.VerifyCodeView_textSize, 26f);

        typedArray.recycle();
    }

    private void addView(Context context) {
//        mPaint.setTextSize(ResourceUtil.dp2px(getContext(), mTextSize));

        View.inflate(context, R.layout.view_verify_code, this);
        int width = (context.getResources().getDisplayMetrics().widthPixels - ResourceUtil.dp2px(context, 16f) * 7) / mCount;
        LinearLayout llVerity = (LinearLayout) findViewById(R.id.ll_verity);
        View child;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < mCount; i++) {
            child = llVerity.getChildAt(i);
            layoutParams = (LinearLayout.LayoutParams) child.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = width;
            child.setLayoutParams(layoutParams);
            mTextViews[i] = (TextView) llVerity.getChildAt(i);
        }
        mEtInput = (EditText) findViewById(R.id.et_input);
        ViewGroup.LayoutParams layoutParams1 = mEtInput.getLayoutParams();
        layoutParams1.height = width;
        mEtInput.setLayoutParams(layoutParams1);

        mTextViews[0].setSelected(true);
    }

    private void setEditTextListener() {
        mEtInput.addTextChangedListener(new TextWatcherListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                mInputContent = mEtInput.getText().toString();
                if (mInputCompleteListener != null) {
                    if (mInputContent.length() >= mCount) {
                        mInputCompleteListener.inputComplete();
                    } else {
                        mInputCompleteListener.invalidContent();
                    }
                }
                for (int i = 0; i < mCount; i++) {
                    mTextViews[0].setSelected(false);
                    if (i < mInputContent.length())
                        mTextViews[i].setText(mInputContent.charAt(i) + "");
                    else
                        mTextViews[i].setText("");
                }
                if ((mCount - 1) <= mInputContent.length()) {
                    mTextViews[5].setSelected(true);
                } else if (mInputContent.isEmpty()) {
                    mTextViews[0].setSelected(true);
                } else
                    mTextViews[mInputContent.length()].setSelected(true);
            }
        });

        mEtInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    mInputContent = mEtInput.getText().toString();
                    if (mInputContent.length() >= mCount) {
                        if (mInputCompleteListener != null)
                            mInputCompleteListener.inputComplete();
                    }
                    return true;
                }
                return false;
            }
        });
    }


    private InputCompleteListener mInputCompleteListener;

    public void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.mInputCompleteListener = inputCompleteListener;
    }

    public interface InputCompleteListener {

        void inputComplete();

        void invalidContent();
    }

    //动态限制输入的内容
    void setDigitsKeyListener(DigitsKeyListener digitsKeyListener) {
        mEtInput.setKeyListener(digitsKeyListener);
    }

    public String getEditContent() {
        return mInputContent;
    }
}
