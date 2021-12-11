package com.lemon.customview.activity.view.imageview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lemon.customview.R
import com.lemon.customview.activity.view.RoundImageViewActivity
import com.lemon.customview.activity.view.ShapeImageViewActivity
import kotlinx.android.synthetic.main.activity_image_view.*

class ImageViewActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)

        btn_round_image_view.setOnClickListener(this)
        btn_shape_image_view.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_round_image_view -> startActivity(
                Intent(
                    this@ImageViewActivity,
                    RoundImageViewActivity::class.java
                )
            )
            R.id.btn_shape_image_view -> startActivity(
                Intent(
                    this@ImageViewActivity,
                    ShapeImageViewActivity::class.java
                )
            )
        }
    }
}