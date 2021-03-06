package com.raoqian.topactivity.copy;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.raoqian.topactivity.utils.InfoController;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.raoqian.topactivity.BaseApplication.serviceContext;

public class WatchingService extends Service {

    private ActivityManager mActivityManager;
    private String text = null;
    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceContext = this;
        Log.e("WatchingService", "46");
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    class RefreshTask extends TimerTask {

        @Override
        public void run() {
            List<RunningTaskInfo> rtis = mActivityManager.getRunningTasks(1);
            String pageName = rtis.get(0).topActivity.getPackageName();
            if (!pageName.equals(text)) {
                text = pageName;
                InfoController.dataInput(getApplication(), pageName);
                Log.e("WatchingService", "event.getPackageName() = " + getAppName(pageName));
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e("FLAGX : ", ServiceInfo.FLAG_STOP_WITH_TASK + "");
        Intent restartServiceIntent = new Intent(getApplicationContext(),
                this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(
                getApplicationContext(), 1, restartServiceIntent,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext()
                .getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 500,
                restartServicePendingIntent);
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        serviceContext = null;
        return super.onUnbind(intent);
    }

    /**
     * 获取应用程序名称
     *
     * @param packageName
     */
    public String getAppName(String packageName) {
        try {
            PackageManager packageManager = getApplicationContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            String result = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            Log.e("InfoController", "result = " + result);
            return result;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("InfoController", "无法获取" + packageName + "应用名  --> " + e.toString());
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            Log.e("InfoController", "无法获取" + packageName + "应用名");
        }
        return null;
    }
}
