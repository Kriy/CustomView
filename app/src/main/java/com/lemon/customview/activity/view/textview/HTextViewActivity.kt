package com.lemon.customview.activity.view.textview

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lemon.customview.R

import android.widget.SeekBar
import com.lemon.customview.utils.ResourceUtil
import com.lemon.customview.widget.htextview.HTextView

import kotlinx.android.synthetic.main.activity_h_text_view.*


class HTextViewActivity : AppCompatActivity() {

    var sentences = arrayOf(
        "What is design?",
        "Design",
        "Design is not just",
        "what it looks like",
        "and feels like.",
        "Design is how it works.",
        "- Steve Jobs",
        "'What can I do with it?'.\n- Steve Jobs",
        "Swift",
        "Objective-C",
        "iPhone",
        "iPad",
        "Mac Mini",
        "MacBook Pro",
        "Mac Pro",
        "爱老婆",
        "老婆和女儿"
    )
    private var mCounter = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h_text_view)

        seekbar.max = 20;
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                text2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8.0f + progress);
                text2.reset(text2.text);
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        seekbar.progress = 10
        typeGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.scale -> {
                    text2.setTextColor(Color.BLACK)
                    text2.setBackgroundColor(Color.WHITE)
                    text2.typeface = ResourceUtil.getInstance(assets).getFont("fonts/Lato-Black.ttf")
                    text2.setAnimateType(HTextView.HTextViewType.SCALE)
                }
                R.id.evaporate -> {
                    text2.setTextColor(Color.BLACK);
                    text2.setBackgroundColor(Color.WHITE);
                    text2.typeface = ResourceUtil.getInstance(assets).getFont("fonts/PoiretOne-Regular.ttf");
                    text2.setAnimateType(HTextView.HTextViewType.EVAPORATE);
                }
                R.id.fall -> {
                    text2.setTextColor(Color.BLACK);
                    text2.setBackgroundColor(Color.WHITE);
                    text2.typeface = ResourceUtil.getInstance(assets).getFont("fonts/Mirza-Regular.ttf");
                    text2.setAnimateType(HTextView.HTextViewType.FALL);
                }
                R.id.pixelate -> {
                    text2.setTextColor(Color.BLACK);
                    text2.setBackgroundColor(Color.WHITE);
                    text2.typeface = ResourceUtil.getInstance(assets).getFont("fonts/AmaticaSC-Regular.ttf");
                    text2.setAnimateType(HTextView.HTextViewType.PIXELATE);
                }
                R.id.sparkle -> {
                    text2.setTextColor(Color.WHITE);
                    text2.setBackgroundColor(Color.BLACK);
                    text2.setTypeface(null);
                    text2.setAnimateType(HTextView.HTextViewType.SPARKLE);
                }
                R.id.anvil -> {
                    text2.setTextColor(Color.WHITE);
                    text2.setBackgroundColor(Color.BLACK);
                    text2.setTypeface(null);
                    text2.setAnimateType(HTextView.HTextViewType.ANVIL);
                }
                R.id.line -> {
                    text2.setTextColor(Color.WHITE);
                    text2.setBackgroundColor(Color.BLACK);
                    text2.setTypeface(null);
                    text2.setAnimateType(HTextView.HTextViewType.LINE);
                }
                R.id.typer -> {
                    text2.setTextColor(Color.WHITE);
                    text2.setBackgroundColor(Color.BLACK);
                    text2.setTypeface(null);
                    text2.setAnimateType(HTextView.HTextViewType.TYPER);
                }
                R.id.rainbow -> {
                    text2.setTextColor(Color.WHITE);
                    text2.setBackgroundColor(Color.BLACK);
                    text2.setTypeface(null);
                    text2.setAnimateType(HTextView.HTextViewType.RAINBOW);
                }
            }

            onClick(typeGroup.findViewById(checkedId));
        }
    }

    fun onClick(v: View?) {
        mCounter = if (mCounter >= sentences.size - 1) 0 else mCounter + 1
        text2.animateText(sentences[mCounter])
    }
}