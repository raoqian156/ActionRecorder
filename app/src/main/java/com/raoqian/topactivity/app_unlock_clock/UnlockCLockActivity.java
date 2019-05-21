package com.raoqian.topactivity.app_unlock_clock;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.raoqian.topactivity.R;
import com.raoqian.topactivity.app_unlock_clock.widget.MyClock;

import static android.view.WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON;
import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;

public class UnlockCLockActivity extends Activity implements MyClock.TimeWatcher {

    private int oldFlags = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_clock);
        Window window = getWindow();
        Log.e("UnlockCLockActivity.flags", "onCreate " + window.getAttributes().flags + "");
        oldFlags = window.getAttributes().flags;
        window.addFlags(FLAG_SHOW_WHEN_LOCKED //锁屏状态下显示
//                | FLAG_DISMISS_KEYGUARD //解锁
//                | FLAG_TURN_SCREEN_ON   //打开屏幕
        );
        MyClock.addTimeWatcher(this);
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
    }

    long showTime = System.currentTimeMillis();

    @Override
    protected void onResume() {
        super.onResume();
        this.showTime = System.currentTimeMillis();
    }

    DevicePolicyManager devicePolicyManager;

    @Override
    public void onTimeChange(long time) {
        Window window = getWindow();
        Log.e("UnlockCLockActivity.flags", window.getAttributes().flags + "");
        window.addFlags(FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
//        if (time - showTime < 5000) return;
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                devicePolicyManager.lockNow();
//                Log.e("UnlockCLockActivity", "lockNow");
//            }
//        });

    }
}
