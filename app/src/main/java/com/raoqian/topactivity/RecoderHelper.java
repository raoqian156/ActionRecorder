package com.raoqian.topactivity;

import android.app.ActivityManager;
import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.raoqian.topactivity.bean.UseAppInfoBean;
import com.raoqian.topactivity.utils.FileHelper;

import java.io.File;
import java.io.IOException;

import static android.content.Context.ACTIVITY_SERVICE;
import static com.raoqian.topactivity.utils.InfoController.getAppName;

/**
 * Created by raoqian on 2017/7/22.
 */

public class RecoderHelper {

    private String pathToot = Environment.getExternalStorageDirectory().getPath() + "/topactivity/";

    private static RecoderHelper mHelper;
    private FileHelper mFileHelper;
    Application mContext;

    private RecoderHelper(Application context) {
        mFileHelper = new FileHelper(context);
        mContext = context;
    }

    public static RecoderHelper instance(Application context) {
        if (mHelper == null) {
            mHelper = new RecoderHelper(context);
        }
        return mHelper;
    }

    public void start() {
        long timeZoom = System.currentTimeMillis();
        String pageName = ((ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE)).getRunningTasks(1).get(0).topActivity.getPackageName();
        try {
            Log.e("InfoController", "40");
            saveText(new UseAppInfoBean(pageName, getAppName(mContext, pageName), timeZoom).toString() + "\n---start---\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unlock() {
        long timeZoom = System.currentTimeMillis();
        String pageName = ((ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE)).getRunningTasks(1).get(0).topActivity.getPackageName();
        try {
            Log.e("InfoController", "52");
            saveText(new UseAppInfoBean(pageName, getAppName(mContext, pageName), timeZoom).toString() + "\n---unlock---\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        long timeZoom = System.currentTimeMillis();
        String pageName = ((ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE)).getRunningTasks(1).get(0).topActivity.getPackageName();
        try {
            Log.e("InfoController", "64");
            saveText(new UseAppInfoBean(pageName, getAppName(mContext, pageName), 0, timeZoom).toString() + "\n---stop----\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //写入信心到txt
    private void saveText(String str) throws IOException {
        String savePath = pathToot;
        File fileDirectory = new File(savePath);
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }
        File files = new File(savePath + "haha.txt");
        if (files.createNewFile()) {
            Log.e("FileHelper", "记录文件已存在");
        } else {
            Log.e("FileHelper", "记录文件已创建");
        }
        mFileHelper.writeFileSdcardFile(savePath + "haha.txt", str);
    }
}
