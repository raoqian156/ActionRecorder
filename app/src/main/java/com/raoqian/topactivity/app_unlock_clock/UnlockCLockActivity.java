package com.raoqian.topactivity.app_unlock_clock;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.raoqian.topactivity.R;
import com.raoqian.topactivity.app_unlock_clock.widget.MyClock;

public class UnlockCLockActivity extends Activity implements MyClock.TimeWatcher {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_clock);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED //锁屏状态下显示
//                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD //解锁
//                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON   //打开屏幕
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
