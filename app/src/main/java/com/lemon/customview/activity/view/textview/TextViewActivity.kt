package com.lemon.customview.activity.view.textview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lemon.customview.R
import com.lemon.customview.activity.view.ExpandableTextViewActivity
import com.lemon.customview.activity.view.MarqueeViewActivity
import kotlinx.android.synthetic.main.activity_text_view.*

class TextViewActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_view)

        btn_slanted.setOnClickListener(this)
        btn_fold_text_view.setOnClickListener(this)
        btn_expand_text_view.setOnClickListener(this)
        btn_number_text_view.setOnClickListener(this)
        btn_alpha_text_view.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_slanted -> startActivity(
                Intent(
                    this@TextViewActivity,
                    SlantedTextViewActivity::class.java
                )
            )
            R.id.btn_fold_text_view -> startActivity(
                Intent(
                    this@TextViewActivity,
                    FoldTextViewActivity::class.java
                )
            )
            R.id.btn_expand_text_view -> startActivity(
                Intent(
                    this@TextViewActivity,
                    ExpandableTextViewActivity::class.java
                )
            )
            R.id.btn_number_text_view -> startActivity(
                Intent(
                    this@TextViewActivity,
                    NumberTextViewActivity::class.java
                )
            )
            R.id.btn_alpha_text_view -> startActivity(
                Intent(
                    this@TextViewActivity,
                    AlphaTextViewActivity::class.java
                )
            )
        }
    }
}