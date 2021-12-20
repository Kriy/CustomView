package com.lemon.customview.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DF on 2017/9/6.
 */

public class ResourceUtil {

    private static final float  DENSITY = Resources.getSystem().getDisplayMetrics().density;

    public static int dp2Px(int dp) {
        return Math.round(dp * DENSITY);
    }

    /**
     * dp转化成px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转成dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px转成sp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    public static int getColor(Context context, @ColorRes int resId) {
        return ContextCompat.getColor(context, resId);
    }

    public static float getDimension(Context context, @DimenRes int resId) {
        return context.getResources().getDimension(resId);
    }



    private static ResourceUtil instance;

    private AssetManager assetManager;

    private Map<String, Typeface> fonts;

    private ResourceUtil(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.fonts = new HashMap<>();
    }

    public static ResourceUtil getInstance(AssetManager assetManager) {
        if (instance == null) {
            instance = new ResourceUtil(assetManager);
        }
        return instance;
    }

    public Typeface getFont(String asset) {
        if (fonts.containsKey(asset))
            return fonts.get(asset);

        Typeface font = null;

        try {
            font = Typeface.createFromAsset(assetManager, asset);
            fonts.put(asset, font);
        } catch (RuntimeException e) {
            Log.e("TAG", "getFont: Can't create font from asset.", e);
        }

        return font;
    }

}
