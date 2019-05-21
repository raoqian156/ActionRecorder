package com.raoqian.topactivity.utils;

import android.app.Application;
import android.widget.Toast;

/**
 * Created by raoqian on 2019/4/26.
 */

public class ToastUtil {
    private static Application mContext;

    public static void init(Application context) {
        ToastUtil.mContext = context;
    }

    public static void show(String con) {
        Toast.makeText(mContext, con, Toast.LENGTH_SHORT).show();
    }

}
