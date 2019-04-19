package com.raoqian.topactivity.app_unlock_clock;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by raoqian on 2019/4/11
 */

public class ScreenOnListener extends BroadcastReceiver {
    public static final String TAG = "ScreenOnListener";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            if (UnlockCLockActivity.class.getName().equals(getTopActivity(context))) {
                loge(context, "未启用  -> " + getTopActivity(context));
                Toast.makeText(context, "未启用", Toast.LENGTH_SHORT).show();
                return;
            }

            loge(context, "锁屏 " + getTopActivity(context) + "  " + UnlockCLockActivity.class.getName());
            Intent toMain = new Intent(context, UnlockCLockActivity.class);
            toMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            toMain.putExtra("from", TAG);
            context.startActivity(toMain);
        }
    }

    private void loge(Context s, String con) {
        Log.e("ScreenOnListener", con);
    }


    /**
     * 得到栈顶的activity
     * 路径+类名
     * 需要在清单文件中添加权限 <uses-permission android:name = “android.permission.GET_TASKS”/>
     */
    public static String getTopActivity(Context context) {

        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        ComponentName componentName;
        if (runningTaskInfos != null) {
            componentName = runningTaskInfos.get(0).topActivity;
            String result = componentName.getPackageName() + "." + componentName.getClassName();
            return result;
        } else {
            return null;
        }
    }
}
