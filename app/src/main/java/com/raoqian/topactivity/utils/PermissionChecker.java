package com.raoqian.topactivity.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by raoqian on 2019/4/18
 * 权限检查类
 */

public class PermissionChecker {

    public interface CheckResultListener {
        void onResult(boolean isAllGet, @NonNull List<String> failList);
    }

    private static volatile CheckResultListener listener;

    public static void check(Activity context, CheckResultListener listener, String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            listener.onResult(true, new ArrayList<String>());
            return;
        }
        if (permissions == null || permissions.length == 0) {
            listener.onResult(true, new ArrayList<String>());
            return;
        }
        PermissionChecker.listener = listener;
        List<String> needApply = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                needApply.add(permissions[i]);
            }
        }
        if (needApply.isEmpty()) {//未授予的权限为空，表示都授予了
            listener.onResult(true, new ArrayList<String>());
        } else {//请求权限方法
            String[] per = needApply.toArray(new String[needApply.size()]);//将List转为数组
            ActivityCompat.requestPermissions(context, per, 666);
        }

    }

    public static void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        if (requestCode == 666) {
            int failCount = 0;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("TEST", permissions[i] + "权限已申请");
                } else {
                    Log.e("TEST", permissions[i] + "权限已拒绝");
                    failCount++;
                }
            }
            if (failCount == 0) {
                listener.onResult(true, new ArrayList<String>());
            } else {
                List<String> res = Arrays.asList(permissions);
                listener.onResult(false, res);
            }

        }
    }
}
