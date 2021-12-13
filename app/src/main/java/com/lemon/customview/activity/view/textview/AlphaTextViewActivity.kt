package com.lemon.customview.activity.view.textview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lemon.customview.R
import kotlinx.android.synthetic.main.activity_alpha_text_view.*

class AlphaTextViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alpha_text_view)


        tv_alpha.setDuration(3000)
        tv_alpha.setOnClickListener {
            tv_alpha.toggle()
        }
        btn.setOnClickListener {
            tv_alpha.setText("Alpha TextView  Activity TextView", true)
        }
    }
}