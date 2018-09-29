package com.lemon.customview.widget;

import android.content.Context;
import android.text.Editable;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lemon.customview.R;
import com.lemon.customview.listener.TextWatcherListener;
import com.lemon.customview.utils.ResourceUtil;

public class VerifyCodeView extends RelativeLayout {

    private int mNumber = 6;
    private TextView[] mTextViews = new TextView[6];
    private String mInputContent = "";
    private EditText mEtInput;

    public VerifyCodeView(Context context) {
        super(context);
    }

    public VerifyCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerifyCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.view_verify_code, this);
        int width = (context.getResources().getDisplayMetrics().widthPixels - ResourceUtil.dp2px(context, 16f) * 7) / 6;
        LinearLayout llVerity = (LinearLayout) findViewById(R.id.ll_verity);
        View child;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0, count = llVerity.getChildCount(); i < count; i++) {
            child = llVerity.getChildAt(i);
            layoutParams = (LinearLayout.LayoutParams) child.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = width;
            child.setLayoutParams(layoutParams);
        }
        layoutParams = (LinearLayout.LayoutParams) findViewById(R.id.et_input).getLayoutParams();
        layoutParams.height = width;
        mEtInput.setLayoutParams(layoutParams);

        mTextViews[0] = (TextView) findViewById(R.id.tv_pay1);
        mTextViews[1] = (TextView) findViewById(R.id.tv_pay2);
        mTextViews[2] = (TextView) findViewById(R.id.tv_pay3);
        mTextViews[3] = (TextView) findViewById(R.id.tv_pay4);
        mTextViews[4] = (TextView) findViewById(R.id.tv_pay5);
        mTextViews[5] = (TextView) findViewById(R.id.tv_pay6);
        mTextViews[0].setSelected(true);
        setEditTextListener();
    }

    private void setEditTextListener() {
        mEtInput.addTextChangedListener(new TextWatcherListener() {
            @Override
            public void afterTextChanged(Editable s) {
                mInputContent = mEtInput.getText().toString();
                if (mInputCompleteListener != null) {
                    if (mInputContent.length() >= mNumber) {
                        mInputCompleteListener.inputComplete();
                    } else {
                        mInputCompleteListener.invalidContent();
                    }
                }
                for (int i = 0; i < mNumber; i++) {
                    mTextViews[0].setSelected(false);
                    if (i < mInputContent.length())
                        mTextViews[i].setText(mInputContent.charAt(i));
                    else
                        mTextViews[i].setText("");
                }
                if ((mNumber - 1) <= mInputContent.length()) {
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
                    if (mInputContent.length() >= mNumber) {
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

    void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.mInputCompleteListener = inputCompleteListener;
    }

    public interface InputCompleteListener {

        void inputComplete();

        void invalidContent();
    }

    void setDigitsKeyListener(DigitsKeyListener digitsKeyListener) {
        mEtInput.setKeyListener(digitsKeyListener);
    }

    public String getEditContent() {
        return mInputContent;
    }
}
