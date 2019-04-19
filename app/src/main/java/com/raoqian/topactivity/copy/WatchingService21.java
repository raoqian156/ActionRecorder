package com.raoqian.topactivity.copy;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.raoqian.topactivity.utils.InfoController;

import static com.raoqian.topactivity.BaseApplication.serviceContext;

/**
 * 获取当前屏幕信息
 */
public class WatchingService21 extends AccessibilityService {


    @SuppressLint("NewApi")
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            InfoController.dataInput(getApplication(), event.getPackageName() + "");
            Log.d("WatchingService21", "event.getPackageName() = " + getAppName(WatchingService21.this,
                    event.getPackageName().toString()));

        }
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    protected void onServiceConnected() {
        serviceContext = this;
        Log.d("WatchingService21", "41");
        super.onServiceConnected();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        serviceContext = null;
        Log.d("WatchingService21", "53");
        return super.onUnbind(intent);
    }

    /**
     * 获取应用程序名称
     *
     * @param watchingService
     * @param packageName
     */
    public static String getAppName(WatchingService21 watchingService, String packageName) {
        try {
            PackageManager packageManager = watchingService.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            String result = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            Log.d("InfoController", "result = " + result);
            return result;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            Log.d("InfoController", "无法获取" + packageName + "应用名");
        }
        return null;
    }
}
