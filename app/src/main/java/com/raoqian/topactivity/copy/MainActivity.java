package com.raoqian.topactivity.copy;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.raoqian.topactivity.IntentStartActivity;
import com.raoqian.topactivity.R;
import com.raoqian.topactivity.TongJiActivity;
import com.raoqian.topactivity.service.ScreenOpenService;
import com.raoqian.topactivity.utils.PermissionChecker;
import com.raoqian.topactivity.utils.ToastUtil;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.raoqian.topactivity.utils.CrashHandler.TAG;

public class MainActivity extends Activity implements View.OnLongClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_copy);
        openClock();
        findViewById(R.id.test).setOnLongClickListener(this);
        new Thread(() -> okhttpTest()).start();
    }

    private void okhttpTest() {
        // 启动客户端类，主要有两种方法进行创建，new对象和Builder内部类实现实例化
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build();

// get请求
// 通过Builder模式创建一个Request对象（即请求报文）
// 这里可以设置请求基本参数：url地址，get请求，POST请求，请求头，cookie参数等
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
//                .header("User-Agent", "xxx.java")
//                .addHeader("token", "xxx")
                .get()
                .build();
//        // POST请求
//// 表单形式上传
//        RequestBody body = new FormBody.Builder().add("xxx","xxx").build();
//// JSON参数形式，File对象上传
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
//        RequestBody body = RequestBody.create(MediaType.parse("File/*"), file);
//
//        Request request = new Request.Builder()
//                .post(body)
//                .url(url)
//                .header("User-Agent", "xxx.java")
//                .addHeader("token", "xxx")
//                .build();


// 创建Call对象（Http请求） ，即连接Request和Response的桥梁
// newCall方法将request封装成Call对象
        Call call = client.newCall(request);
        try {
// Response即响应报文信息，包含返回状态码，响应头，响应体等
            Response response = call.execute();
// 这里深入一点，Call其实是一个接口，调用Call的execute()发送同步请求其实是调用了Realcall实现类的方法，Realcall从源码可以看出示一个Runable
            Log.e("MainActivity", response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionChecker.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onStart(View view) {
        if (isAccessibilitySettingsOn(MainActivity.this)) {
            Intent intent = new Intent();

            intent.setAction(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            new AlertDialog.Builder(this)
                    .setMessage("安卓5.0以后需要获取权限\n请手动打开应用 " + getString(R.string.app_name) + " 无障碍服务")
                    .setPositiveButton("前去设置"
                            , (dialog, which) -> PermissionChecker.check(MainActivity.this,
                                    (isAllGet, failList) -> {
                                        if (isAllGet) {
                                            Intent intent = new Intent();
                                            intent.setAction("android.settings.ACCESSIBILITY_SETTINGS");
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(MainActivity.this, "必须获取文件读写", Toast.LENGTH_LONG).show();
                                        }
                                    },
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    .create()
                    .show();
        } else {
            startService(new Intent(this, WatchingService.class));
        }
    }

    private boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = "com.raoqian.topactivity/com.raoqian.topactivity.copy.WatchingService21";
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

        return false;
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

    public void onOpenUnlock(View view) {
        toSet();
    }

    private void toSet() {
        String packagename = getPackageName();
        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }
        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);
        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);
        Log.e("PermissionPageManager", "resolveinfoList" + resolveinfoList.size());
        for (int i = 0; i < resolveinfoList.size(); i++) {
            Log.e("PermissionPageManager", resolveinfoList.get(i).activityInfo.packageName + resolveinfoList.get(i).activityInfo.name);
        }
        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packageName参数2 = 参数 packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packageName参数2.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            // 设置ComponentName参数1:packageName参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
            try {
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    ScreenOpenService screenBroadcastReceiver = new ScreenOpenService();

    private void openClock() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        getApplicationContext().registerReceiver(screenBroadcastReceiver, filter);
        ToastUtil.show("开启成功");
    }

    public void onCloseUnlock(View view) {
//        try {
        getApplicationContext().unregisterReceiver(screenBroadcastReceiver);
        ToastUtil.show("锁屏闹钟已关闭");
//        } catch (IllegalStateException e) {
//            ToastUtil.show("锁屏闹钟尚未开启");
//        }
    }


    int index = 0;

    public void testClick(View passer) {
        startActivity(new Intent(MainActivity.this, IntentStartActivity.class));
    }

    @Override
    public boolean onLongClick(View v) {
        TextView view = (TextView) v;
        try {
            String con = view.getText().toString().trim();
            con = con.substring(con.lastIndexOf("-") + 1);
            Intent intent = new Intent();
            intent.setAction(con);
            startActivity(intent);
            Log.e("MainActivity", con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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
