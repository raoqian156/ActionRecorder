package com.raoqian.topactivity.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.raoqian.topactivity.RecoderHelper;

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
                helper.stop();
                break;
            case Intent.ACTION_SCREEN_ON:
                helper.start();
                break;
            case Intent.ACTION_USER_PRESENT:
                helper.unlock();
                break;
        }
    }

}
