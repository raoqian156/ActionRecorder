package com.raoqian.topactivity.copy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.raoqian.topactivity.R;
import com.raoqian.topactivity.TongJiActivity;

import static com.raoqian.topactivity.utils.CrashHandler.TAG;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_copy);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isAccessibilitySettingsOn(MainActivity.this)) {
            ((TextView) findViewById(R.id.start_click)).setText("记录已打开");
        } else {
            ((TextView) findViewById(R.id.start_click)).setText("打开记录");
        }
    }

    public void onStart(View view) {
        if (isAccessibilitySettingsOn(MainActivity.this)) {
            Intent intent = new Intent();
            intent.setAction("android.settings.ACCESSIBILITY_SETTINGS");
            startActivity(intent);
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            new AlertDialog.Builder(this)
                    .setMessage("安卓5.0以后需要获取权限")
                    .setPositiveButton("前去设置"
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setAction("android.settings.ACCESSIBILITY_SETTINGS");
                                    startActivity(intent);
                                }
                            })
                    .setNegativeButton(android.R.string.cancel
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                        }
                    })
                    .create()
                    .show();
        } else {
            startService(new Intent(this, WatchingService.class));
        }
    }

    private boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = "com.raoqian.topactivity/com.raoqian.topactivity.copy.WatchingService21";
        boolean accessibilityFound = false;
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Log.v(TAG, "***ACCESSIBILIY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
                splitter.setString(settingValue);
                while (splitter.hasNext()) {
                    String accessabilityService = splitter.next();

                    Log.v(TAG, "-------------- > accessabilityService :: " + accessabilityService);
                    if (accessabilityService.equalsIgnoreCase(service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Log.v(TAG, "***ACCESSIBILIY IS DISABLED***");
        }

        return accessibilityFound;
    }

    public void onStop(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            stopService(new Intent(this, WatchingService21.class));
        } else {
            stopService(new Intent(this, WatchingService.class));
        }
    }

    public void onShow(View view) {
        startActivity(new Intent(this, TongJiActivity.class));
    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(mReceiver);
//    }

//    UpdateSwitchReceiver mReceiver;
//
//    class UpdateSwitchReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//        }
//    }
}
