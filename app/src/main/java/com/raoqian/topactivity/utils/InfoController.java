package com.raoqian.topactivity.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.raoqian.topactivity.BaseApplication;
import com.raoqian.topactivity.RecoderHelper;
import com.raoqian.topactivity.bean.UseAppInfoBean;

import java.util.List;

/**
 * Created by raoqian on 2017/7/22.
 */

public class InfoController {

    public static void dataInput(Application context, String pageName) {
        long timeZoom = System.currentTimeMillis();
        save((BaseApplication) context, pageName, timeZoom);
        Log.d("InfoController", "30");
    }

    private static void save(BaseApplication application, String pageName, long timeZoom) {
        RecoderHelper helper = RecoderHelper.instance(application);
        List<UseAppInfoBean> data = DateHelper.instance(application).queryAll();
        if (data.size() > 0) {
            UseAppInfoBean last = data.get(data.size() - 1);
            if (TextUtils.equals(pageName, last.getPageName()) && (last.getEndTime() - timeZoom) < 1000) {
                return;
            }
            last.setEndTime(timeZoom);
            application.getDaoSession().getUseAppInfoBeanDao().update(last);
            helper.stop();
        }
        helper.start();
        application.getDaoSession().getUseAppInfoBeanDao().insert(new UseAppInfoBean(pageName, InfoController.getAppName(application, pageName), timeZoom));
    }


    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context, String pageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(pageName, 0);
            String result = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            Log.d("InfoController", "result = " + result);
            return result;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            Log.d("InfoController", "无法获取" + pageName + "应用名");
        }
        return null;
    }
}
