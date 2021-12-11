package com.lemon.customview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import androidx.annotation.NonNull;
import android.text.Editable;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.Gravity;
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
    private int mBackground;
    private int mTextColor;
    private float mTextSize = 26;
    private float mMargin = 16f;

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
        mCount = typedArray.getInteger(R.styleable.VerifyCodeView_verifyCodeCount, mCount);
        mTextColor = typedArray.getColor(R.styleable.VerifyCodeView_verifyCodeTextColor, Color.parseColor("#000000"));
        mTextSize = typedArray.getDimension(R.styleable.VerifyCodeView_verifyCodeTextSize, ResourceUtil.getDimension(context, R.dimen.verify_code_text_size));
        mBackground = typedArray.getResourceId(R.styleable.VerifyCodeView_verifyCodeBackground, R.drawable.cell_edit_bg);
        mMargin = typedArray.getDimension(R.styleable.VerifyCodeView_verifyCodeMargin, ResourceUtil.getDimension(context, R.dimen.verify_code_margin));
        typedArray.recycle();
    }

    private void addView(Context context) {
        View.inflate(context, R.layout.view_verify_code, this);
        int width = ((context.getResources().getDisplayMetrics().widthPixels - (int) mMargin * (mCount + 1))) / mCount;
        LinearLayout llVerity = (LinearLayout) findViewById(R.id.ll_verity);
        TextView child;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < mCount; i++) {
            child = new TextView(context);
            child.setTextSize(mTextSize);
            child.setTextColor(mTextColor);
            child.setBackgroundResource(mBackground);
            child.setGravity(Gravity.CENTER);
            llVerity.addView(child);
            layoutParams = (LinearLayout.LayoutParams) child.getLayoutParams();
            layoutParams.setMarginStart((int) mMargin);
            layoutParams.width = width;
            layoutParams.height = width;
            child.setLayoutParams(layoutParams);
            mTextViews[i] = child;
        }
        mEtInput = (EditText) findViewById(R.id.et_input);
        ViewGroup.LayoutParams etInputLayoutParams = mEtInput.getLayoutParams();
        etInputLayoutParams.height = width;
        mEtInput.setLayoutParams(etInputLayoutParams);

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
                    mTextViews[i].setSelected(false);
                    if (i < mInputContent.length())
                        mTextViews[i].setText(mInputContent.charAt(i) + "");
                    else
                        mTextViews[i].setText("");
                }
                if ((mCount - 1) <= mInputContent.length()) {
                    mTextViews[mCount - 1].setSelected(true);
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
    void setDigitsKeyListener(final String digits) {
        mEtInput.setKeyListener(new DigitsKeyListener() {
            @NonNull
            @Override
            protected char[] getAcceptedChars() {
                return digits.toCharArray();
            }
        });
    }

    public String getEditContent() {
        return mInputContent;
    }
}
