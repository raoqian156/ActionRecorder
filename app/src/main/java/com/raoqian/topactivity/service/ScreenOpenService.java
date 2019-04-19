package com.raoqian.topactivity.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.raoqian.topactivity.app_unlock_clock.UnlockCLockActivity;


/**
 * Created by raoqian on 2017/7/22.
 */

public class ScreenOpenService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ScreenOpenService", "onReceive.startActivity " + intent.getAction());
        if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
            Log.e("ScreenOpenService", "onReceive.startActivity >>> ");
            Intent toMain = new Intent(context, UnlockCLockActivity.class);
            toMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(toMain);
        }
    }

}
