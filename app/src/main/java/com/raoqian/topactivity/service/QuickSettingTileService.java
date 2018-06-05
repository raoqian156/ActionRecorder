package com.raoqian.topactivity.service;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import com.raoqian.topactivity.NotificationActionReceiver;
import com.raoqian.topactivity.TasksWindow;
import com.raoqian.topactivity.copy.MainActivity;
import com.raoqian.topactivity.utils.SPHelper;

import static com.raoqian.topactivity.BaseApplication.serviceContext;

/**
 * Created by Wen on 5/3/16.
 */
// 允许三方app可以添加自己的快速设定
@TargetApi(Build.VERSION_CODES.N)
public class QuickSettingTileService extends TileService {

    public static final String ACTION_UPDATE_TITLE = "com.willme.topactivity.ACTION.UPDATE_TITLE";
    private UpdateTileReceiver mReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        mReceiver = new UpdateTileReceiver();
    }

    @Override
    public void onTileAdded() {
        SPHelper.setQSTileAdded(this, true);
//        sendBroadcast(new Intent(MainActivity.ACTION_STATE_CHANGED));
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
        SPHelper.setQSTileAdded(this, false);
//        sendBroadcast(new Intent(MainActivity.ACTION_STATE_CHANGED));
    }

    @Override
    public void onStartListening() {
        registerReceiver(mReceiver, new IntentFilter(ACTION_UPDATE_TITLE));
        super.onStartListening();
        updateTile();
    }


    @Override
    public void onStopListening() {
        unregisterReceiver(mReceiver);
        super.onStopListening();
    }

    @Override
    public void onClick() {
        if (serviceContext == null || !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(this, MainActivity.class);
//            intent.putExtra(MainActivity.EXTRA_FROM_QS_TILE, true);
            startActivityAndCollapse(intent);
        } else {
            SPHelper.setIsShowWindow(this, !SPHelper.isShowWindow(this));
            if (SPHelper.isShowWindow(this)) {
                TasksWindow.show(this, null);
                NotificationActionReceiver.showNotification(this, false);
            } else {
                TasksWindow.dismiss(this);
                NotificationActionReceiver.showNotification(this, true);
            }
//            sendBroadcast(new Intent(MainActivity.ACTION_STATE_CHANGED));
        }
    }

    private void updateTile() {
        if (serviceContext == null) {
            getQsTile().setState(Tile.STATE_INACTIVE);
        } else {
            getQsTile().setState(SPHelper.isShowWindow(this) ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        }
        getQsTile().updateTile();
    }


    class UpdateTileReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateTile();
        }
    }

    public static void updateTile(Context context) {
        TileService.requestListeningState(context.getApplicationContext(), new ComponentName(context, QuickSettingTileService.class));
        context.sendBroadcast(new Intent(QuickSettingTileService.ACTION_UPDATE_TITLE));
    }
}
