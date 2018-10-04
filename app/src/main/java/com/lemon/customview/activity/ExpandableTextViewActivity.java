package com.lemon.customview.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.text.util.LinkifyCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.util.Linkify;
import android.widget.TextView;
import android.widget.Toast;

import com.lemon.customview.R;
import com.lemon.customview.widget.ExpandableTextView.ExpandableTextView;
import com.lemon.customview.widget.ExpandableTextView.LinkType;

public class ExpandableTextViewActivity extends AppCompatActivity {


    private ExpandableTextView[] views;
    private TextView[] tips;
    private String[] indexs = new String[]{
            "3,5;6,9;10,12",
            "3,5;6,11;12,13;21,22",
            "2,6;7,12;13,14;22,23",
            "3,5;6,9;10,11;19,20",
            "3,5;6,9;10,11;19,21",
            "3,5;6,9;10,11;14,16",
            "3,5;6,9;10,11;14,15;20,21",
            "3,5;6,9;10,11;14,16;21,22",
            "3,5;6,9;10,11;14,15;20,21",
            "4,6;7,10;11,12;15,16;21,22"
    };

    private TextView tvTips00;

    private ExpandableTextView.OnLinkClickListener linkClickListener = (type, content, selfContent) -> {
        if (type.equals(LinkType.LINK_TYPE)) {
            Toast.makeText(ExpandableTextViewActivity.this, "你点击了链接 内容是：" + content, Toast.LENGTH_SHORT).show();
        } else if (type.equals(LinkType.MENTION_TYPE)) {
            Toast.makeText(ExpandableTextViewActivity.this, "你点击了@用户 内容是：" + content, Toast.LENGTH_SHORT).show();
        } else if (type.equals(LinkType.SELF)) {
            Toast.makeText(ExpandableTextViewActivity.this, "你点击了自定义规则 内容是：" + content + " " + selfContent, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_textview);

        initView();

        setTips();
        String yourText = "    我所认识的中国，强大、友好 --习大大。@奥特曼 “一带一路”经济带带动了沿线国家的经济发展，促进我国与他国的友好往来和贸易发展，可谓“双赢”，Github地址。http://www.baidu.com 自古以来，中国以和平、友好的面孔示人。汉武帝派张骞出使西域，开辟丝绸之路，增进与西域各国的友好往来。http://www.baidu.com 胡麻、胡豆、香料等食材也随之传入中国，汇集于中华美食。@RNG 漠漠古道，驼铃阵阵，这条路奠定了“一带一路”的基础，让世界认识了中国。";
//        String yourText = "1、哈哈\n2、哈哈\n3、哈哈\n4、哈哈\n5、哈哈\n6、哈哈\n7、哈哈\n8、哈哈\n9、哈哈\n1、哈哈";
//        String yourText = "1\n2考虑\n3考虑\n4考虑\n5考虑\n6考虑";

        setContent(yourText, true);

        //在RecyclerView中查看效果
        findViewById(R.id.ll_recyclerview).setOnClickListener(v -> {
            startActivity(new Intent(this, ShowInRecyclerViewActivity.class));
        });

    }

    private void initView() {
        views = new ExpandableTextView[10];
        tips = new TextView[10];
        views[0] = findViewById(R.id.ep_01);
        views[1] = findViewById(R.id.ep_02);
        views[2] = findViewById(R.id.ep_03);
        views[3] = findViewById(R.id.ep_04);
        views[4] = findViewById(R.id.ep_05);
        views[5] = findViewById(R.id.ep_06);
        views[6] = findViewById(R.id.ep_07);
        views[7] = findViewById(R.id.ep_08);
        views[8] = findViewById(R.id.ep_09);
        views[9] = findViewById(R.id.ep_10);
        tips[0] = findViewById(R.id.tv_tips01);
        tips[1] = findViewById(R.id.tv_tips02);
        tips[2] = findViewById(R.id.tv_tips03);
        tips[3] = findViewById(R.id.tv_tips04);
        tips[4] = findViewById(R.id.tv_tips05);
        tips[5] = findViewById(R.id.tv_tips06);
        tips[6] = findViewById(R.id.tv_tips07);
        tips[7] = findViewById(R.id.tv_tips08);
        tips[8] = findViewById(R.id.tv_tips09);
        tips[9] = findViewById(R.id.tv_tips10);
        tvTips00 = findViewById(R.id.tv_tips00);
    }

    /**
     * 设置内容
     *
     * @param yourText
     * @param d
     */
    private void setContent(String yourText, boolean d) {
        //1、正常带链接和@用户，没有展开和收回功能
        views[0].setContent(yourText);
        views[0].setLinkClickListener(linkClickListener);

        //2、正常带链接，不带@用户，有展开和收回功能，有切换动画
        views[1].setContent(yourText);
        views[1].setLinkClickListener(linkClickListener);

        //3、正常不带链接，不带@用户，有展开和收回功能，有切换动画
        views[2].setContent(yourText);
        views[2].setLinkClickListener(linkClickListener);

        //4、正常带链接和@用户，有展开和收回功能，有切换动画
        views[3].setContent(yourText);
        views[3].setLinkClickListener(linkClickListener);
        views[3].setNeedSelf(true);

        //5、正常带链接和@用户，有展开和收回功能，没有切换动画
        views[4].setContent(yourText);
        views[4].setLinkClickListener(linkClickListener);

        //6、正常带链接和@用户，有展开，没有收回功能
        views[5].setContent(yourText);
        views[5].setLinkClickListener(linkClickListener);

        //7、正常带链接和@用户，有展开，有收回功能，带附加内容(比如时间)
        views[6].setContent(yourText);
        views[6].setEndExpendContent(" 1小时前");
        views[6].setLinkClickListener(linkClickListener);

        //8、正常带链接和@用户，有展开，没有收回功能，带附加内容(比如时间)
        views[7].setContent(yourText);
        views[7].setEndExpendContent(" 1小时前");
        views[7].setLinkClickListener(linkClickListener);

        //9、正常带链接和@用户，有展开，有收回功能，有'展开'和'收起'始终靠右显示的功能
        views[8].setContent(yourText);
        views[8].setLinkClickListener(linkClickListener);
        //需要先开启始终靠右显示的功能
        views[8].setNeedAlwaysShowRight(true);

        //10、正常带链接和@用户，有展开，有收回功能，带自定义规则（解析[标题](规则)并处理，例如对一些字段进行自定义处理，比如文字中的"--习大大" 和 "Gitbub地址"）
        //如果你需要对一些字段进行自定义处理，比如文字中的"--习大大" 和 "Gitbub地址"，你需要按照下面的形式去组装数据，这样控件就可以自动去解析并展示。
        /**
         * 如需使用此功能，需要先开启
         * app:ep_need_self="true"
         * views[8].setNeedSelf(true);
         */
        if (d) {
            yourText = "    我所认识的中国，强大、友好，[--习大大](schema_jump_userinfo)。@奥特曼 “一带一路”经济带带动了沿线国家的经济发展，促进我国与他国的友好往来和贸易发展，可谓“双赢”，[Github地址](https://github.com/MZCretin/ExpandableTextView)。http://www.baidu.com 自古以来，中国以和平、友好的面孔示人。汉武帝派张骞出使西域，开辟丝绸之路，增进与西域各国的友好往来。http://www.baidu.com 胡麻、胡豆、香料等食材也随之传入中国，汇集于中华美食。@RNG 漠漠古道，驼铃阵阵，这条路奠定了“一带一路”的基础，让世界认识了中国。";
        }else{
            tips[9].setText("10、正常带链接和@用户，有展开，有收回功能，带自定义规则");
            setTips();
        }
        views[9].setContent(yourText);
        views[9].setLinkClickListener(linkClickListener);
        //需要先开启
        views[9].setNeedSelf(true);
    }

    /**
     * 设置tips
     */
    private void setTips() {
        //处理最上边的Tips
        final SpannableString value = SpannableString.valueOf(tvTips00.getText());
        LinkifyCompat.addLinks(value, Linkify.ALL);
        tvTips00.setMovementMethod(LinkMovementMethod.getInstance());
        tvTips00.setText(value);

        //处理剩下的
        for (int i = 0; i < indexs.length; i++) {
            String index = indexs[i];
            TextView view = tips[i];
            String[] split = index.split(";");
            SpannableStringBuilder spannableStringBuilder =
                    new SpannableStringBuilder(view.getText());
            for (String s :
                    split) {
                int x = Integer.parseInt(s.split(",")[0]) + 2;
                int y = Integer.parseInt(s.split(",")[1]) + 2;
                spannableStringBuilder.setSpan(
                        new ForegroundColorSpan(Color.parseColor("#FF6200")),
                        x, y, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            view.setText(spannableStringBuilder);
        }
    }

}
