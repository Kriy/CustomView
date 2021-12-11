package com.lemon.customview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lemon.customview.R
import android.content.Intent
import android.view.View
import com.lemon.customview.activity.material.MaterialActivity
import com.lemon.customview.activity.view.TheoryActivity
import com.lemon.customview.activity.view.DepthMapViewActivity
import com.lemon.customview.activity.view.TimeLineMarkerViewActivity
import com.lemon.customview.activity.view.MarqueeViewActivity
import com.lemon.customview.activity.view.textview.SlantedTextViewActivity
import com.lemon.customview.activity.view.KeyboardActivity
import com.lemon.customview.activity.view.VerifyCodeViewActivity
import com.lemon.customview.activity.view.textview.FoldTextViewActivity
import com.lemon.customview.activity.view.ExpandableTextViewActivity
import com.lemon.customview.activity.view.RoundImageViewActivity
import com.lemon.customview.activity.view.ShapeImageViewActivity
import com.lemon.customview.activity.view.Roll3DViewActivity
import com.lemon.customview.activity.view.MeiHeartViewActivity
import com.lemon.customview.activity.view.TextPathViewActivity
import com.lemon.customview.activity.view.LinePathActivity
import com.lemon.customview.activity.view.imageview.ImageViewActivity
import com.lemon.customview.activity.view.textview.TextViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_material_design.setOnClickListener(this)
        btn_view.setOnClickListener(this)
        btn_depth.setOnClickListener(this)
        btn_time.setOnClickListener(this)
        btn_marquee.setOnClickListener(this)
        btn_text_view.setOnClickListener(this)
        btn_image_view.setOnClickListener(this)
        btn_keyboard.setOnClickListener(this)
        btn_verify_code.setOnClickListener(this)
        btn_roll_3d_view.setOnClickListener(this)
        btn_heart_view.setOnClickListener(this)
        btn_path_view.setOnClickListener(this)
        btn_line_path_view.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_material_design -> startActivity(
                Intent(
                    this@MainActivity,
                    MaterialActivity::class.java
                )
            )
            R.id.btn_view -> startActivity(Intent(this@MainActivity, TheoryActivity::class.java))
            R.id.btn_depth -> startActivity(
                Intent(
                    this@MainActivity,
                    DepthMapViewActivity::class.java
                )
            )
            R.id.btn_text_view -> startActivity(
                Intent(
                    this@MainActivity,
                    TextViewActivity::class.java
                )
            )
            R.id.btn_image_view -> startActivity(
                Intent(this@MainActivity, ImageViewActivity::class.java)
            )
            R.id.btn_keyboard -> startActivity(
                Intent(
                    this@MainActivity,
                    KeyboardActivity::class.java
                )
            )
            R.id.btn_marquee -> startActivity(
                Intent(
                    this@MainActivity,
                    MarqueeViewActivity::class.java
                )
            )
            R.id.btn_time -> startActivity(
                Intent(
                    this@MainActivity,
                    TimeLineMarkerViewActivity::class.java
                )
            )
            R.id.btn_verify_code -> startActivity(
                Intent(
                    this@MainActivity,
                    VerifyCodeViewActivity::class.java
                )
            )
            R.id.btn_roll_3d_view -> startActivity(
                Intent(
                    this@MainActivity,
                    Roll3DViewActivity::class.java
                )
            )
            R.id.btn_heart_view -> startActivity(
                Intent(
                    this@MainActivity,
                    MeiHeartViewActivity::class.java
                )
            )
            R.id.btn_path_view -> startActivity(
                Intent(
                    this@MainActivity,
                    TextPathViewActivity::class.java
                )
            )
            R.id.btn_line_path_view -> startActivity(
                Intent(
                    this@MainActivity,
                    LinePathActivity::class.java
                )
            )
            else -> {
            }
        }
    }
}