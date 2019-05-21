package com.raoqian.topactivity.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SPHelper {
    /**
     * @return 是否显示悬浮穿
     */
    public static boolean isShowWindow(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean("is_show_window", true);
    }

    public static void setIsShowWindow(Context context, boolean isShow) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean("is_show_window", isShow).commit();
//        if (isShow) {
//            setJustRecord(context, false);
//        }
    }

    private final static String SAVE_TAG = "save.tag6";

    public static boolean saveSelectPackageId(Context context, String packName) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Log.e("SPHelper", "in = " + packName);
        List<String> saves = new ArrayList<>();
        String strs = sp.getString(SAVE_TAG, "");
        if (!TextUtils.isEmpty(strs)) {
            String[] s = str2StrArray(strs);
            saves.addAll(Arrays.asList(s));
            if (saves.remove(packName)) {
                sp.edit().putString(SAVE_TAG, saves.toString()).apply();
                return true;
            }
        }
        saves.add(packName);
        sp.edit().putString(SAVE_TAG, saves.toString()).apply();
        Log.e("SPHelper.LINE", "saves.toString() = " + saves.toString());
        return false;
    }

    private static String[] str2StrArray(String strs) {//针对 List<String>.toString()
        if (TextUtils.isEmpty(strs)) {
            return new String[]{};
        } else {
            strs = strs.replaceAll(" ", "");
            strs = strs.substring(strs.lastIndexOf("[") + 1, strs.lastIndexOf("]"));
            return strs.split(",");
        }
    }

    public static List<String> getSelectPackageId(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        List<String> saves = new ArrayList<>();
        String strs = sp.getString(SAVE_TAG, "");
        String[] s = str2StrArray(strs);
        saves.addAll(Arrays.asList(s));
        return saves;
    }


    public static void saveLastPackageId(Context context, String packName) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString("LastPackageId", packName).apply();
    }

    public static String getLastPackageId(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString("LastPackageId", "");
    }


    public static boolean hasQSTileAdded(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean("has_qs_tile_added", false);
    }

    public static void setQSTileAdded(Context context, boolean added) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean("has_qs_tile_added", added).commit();
    }

    public static boolean isNotificationToggleEnabled(Context context) {
        if (!hasQSTileAdded(context)) {
            return true;
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean("is_noti_toggle_enabled", true);
    }

    public static void setNotificationToggleEnabled(Context context, boolean isEnabled) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean("is_noti_toggle_enabled", isEnabled).commit();
    }

//    public static boolean isJustRecord(Context context) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        return sp.getBoolean("is_just_record", true);
//    }
//
//    public static void setJustRecord(Context context, boolean isJustRecord) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        sp.edit().putBoolean("is_just_record", isJustRecord).commit();
//        if (isJustRecord) {
//            setIsShowWindow(context, false);
//        }
//    }
}
