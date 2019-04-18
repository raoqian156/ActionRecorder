package com.raoqian.topactivity.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.raoqian.topactivity.RecoderHelper;
import com.raoqian.topactivity.app_unlock_clock.ScreenObserver;
import com.raoqian.topactivity.app_unlock_clock.UnlockCLockActivity;

import static com.raoqian.topactivity.BaseApplication.serviceContext;


/**
 * Created by raoqian on 2017/7/22.
 */

public class ScreenOpenService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        RecoderHelper helper = RecoderHelper.instance(serviceContext.getApplication());
        switch (intent.getAction()) {
            case Intent.ACTION_SCREEN_OFF:
                Intent toMain = new Intent(context, UnlockCLockActivity.class);
                toMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(toMain);
                helper.stop();
                ScreenObserver.screenChange(false);
                break;
            case Intent.ACTION_SCREEN_ON:
                helper.start();
                ScreenObserver.screenChange(true);
                break;
            case Intent.ACTION_USER_PRESENT:
                helper.unlock();
                break;
        }
    }

}
