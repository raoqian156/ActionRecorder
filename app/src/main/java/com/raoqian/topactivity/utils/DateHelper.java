package com.raoqian.topactivity.utils;

import com.raoqian.topactivity.BaseApplication;
import com.raoqian.topactivity.bean.DaoSession;
import com.raoqian.topactivity.bean.UseAppInfoBean;
import com.raoqian.topactivity.bean.UseAppInfoBeanDao.Properties;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import static com.raoqian.topactivity.bean.UseAppInfoBeanDao.Properties.StartTime;

/**
 * Created by raoqian on 2017/7/23.
 */

public class DateHelper {
    private static DateHelper mHelper;
    private BaseApplication application;

    private DateHelper(BaseApplication application) {
        this.application = application;
    }

    public static DateHelper instance(BaseApplication application) {
        if (mHelper == null) {
            mHelper = new DateHelper(application);
        }
        return mHelper;
    }

    private DaoSession daoSession() {
        return application.getDaoSession();
    }

    public List<UseAppInfoBean> queryById(Long id) {
        List<UseAppInfoBean> list = daoSession().getUseAppInfoBeanDao().queryBuilder()
                .where(Properties.Id.eq(id))
                .orderAsc(StartTime).list();
        return list;
    }

    public List<UseAppInfoBean> queryByPageName(String pageName) {
        List<UseAppInfoBean> list = daoSession().getUseAppInfoBeanDao().queryBuilder()
                .where(Properties.PageName.eq(pageName))
                .orderAsc(StartTime).list();
        return list;
    }

    public List<UseAppInfoBean> queryAll() {
        List<UseAppInfoBean> list = daoSession().getUseAppInfoBeanDao().queryBuilder()
                .orderAsc(StartTime).list();
        return list;
    }

    long oneDay = 1000 * 60 * 60 ;

    public List<UseAppInfoBean> queryNearData() {
        WhereCondition limit = Properties.StartTime.between(System.currentTimeMillis() - oneDay, System.currentTimeMillis());
        WhereCondition limit2 = Properties.StartTime.ge(10);
        List<UseAppInfoBean> list = daoSession().getUseAppInfoBeanDao().queryBuilder()
                .orderAsc(Properties.StartTime).where(limit,limit2).list();
        return list;
    }


}
