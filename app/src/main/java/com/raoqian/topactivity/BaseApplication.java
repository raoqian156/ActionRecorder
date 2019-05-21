package com.raoqian.topactivity;

import android.app.Application;
import android.app.Service;

import com.raoqian.topactivity.bean.DaoMaster;
import com.raoqian.topactivity.bean.DaoSession;
import com.raoqian.topactivity.utils.CrashHandler;
import com.raoqian.topactivity.utils.ToastUtil;

import org.greenrobot.greendao.database.Database;

/**
 * Created by raoqian on 2017/7/23.
 */

public class BaseApplication extends Application {
    public static Service serviceContext;
    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     */
    public static final boolean ENCRYPTED = true;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        ToastUtil.init(this);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
