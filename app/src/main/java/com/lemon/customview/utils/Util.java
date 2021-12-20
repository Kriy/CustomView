package com.lemon.customview.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.lemon.customview.model.CharacterDiffResult;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Util {

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int dp2px(Context context, float dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    public static void saveProperty(SharedPreferences sharedPreferences, String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void saveProperty(SharedPreferences sharedPreferences, String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void saveProperty(SharedPreferences sharedPreferences, String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void clearProperties(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception ignored) {
            }
        }
    }

    public static List<CharacterDiffResult> diff(CharSequence oldText, CharSequence newText) {

        List<CharacterDiffResult> differentList = new ArrayList<>();
        Set<Integer> skip = new HashSet<>();

        for (int i = 0; i < oldText.length(); i++) {
            char c = oldText.charAt(i);
            for (int j = 0; j < newText.length(); j++) {
                if (!skip.contains(j) && c == newText.charAt(j)) {
                    skip.add(j);
                    CharacterDiffResult different = new CharacterDiffResult();
                    different.c = c;
                    different.fromIndex = i;
                    different.moveIndex = j;
                    differentList.add(different);
                    break;
                }
            }
        }
        return differentList;
    }

    public static int needMove(int index, List<CharacterDiffResult> differentList) {
        for (CharacterDiffResult different : differentList) {
            if (different.fromIndex == index) {
                return different.moveIndex;
            }
        }
        return -1;
    }

    public static boolean stayHere(int index, List<CharacterDiffResult> differentList) {
        for (CharacterDiffResult different : differentList) {
            if (different.moveIndex == index) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return
     */
    public static float getOffset(int from, int move, float progress, float startX, float oldStartX, float[] gaps, float[] oldGaps) {

        float dist = startX;
        for (int i = 0; i < move; i++) {
            dist += gaps[i];
        }

        float cur = oldStartX;
        for (int i = 0; i < from; i++) {
            cur += oldGaps[i];
        }

        return cur + (dist - cur) * progress;

    }

}
