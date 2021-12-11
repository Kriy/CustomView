package com.lemon.customview.activity.view.textview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lemon.customview.R
import kotlinx.android.synthetic.main.activity_number_text_view.*

class NumberTextViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_text_view)

        ntv_1.setNumberString("非数字测试没有效果")


        btn_1.setOnClickListener {
            ntv_2.setPrefixString("$")
            ntv_2.setNumberString("100")
        }
        btn_2.setOnClickListener {
            ntv_3.setPostfixString("%")
            ntv_3.setNumberString("100")
        }
        btn_3.setOnClickListener {
            ntv_4.setNumberString("100")
        }
        ntv_5.setDuration(5000)
        ntv_5.setNumberString("1", "100")
    }

}