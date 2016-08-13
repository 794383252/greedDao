package com.example.myapplication.dbmanager;

import android.content.Context;

import com.student.dao.DaoMaster;
import com.student.dao.DaoSession;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Administrator on 2016/8/12.
 * <p>
 * 1：创建数据库
 * 2：创建数据库的表
 * 3：包含数据库的增删改查
 * 4：对数据库的升级
 */
public class daomanager {
    private static final String TAG = "ln";
    private static final String DB_NAME = "mydb.sqlite";//数据库名称
    private volatile static daomanager manager;//多线程访问，使用单例模式
    private static DaoMaster.DevOpenHelper helper;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private Context context;

    /**
     * 使用单例模式获得操作数据库的对象
     *
     * @return
     */
    public static daomanager getInstance() {
        daomanager instance = null;
        if (instance == null) {
            synchronized (daomanager.class) {
                instance = new daomanager();
                manager = instance;
            }
        }
        return instance;
    }

    //判断是否存在数据库，如果没有就创建一个数据库
    public DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    //传递上下文context
    public void init(Context context) {
        this.context = context;
    }

    /**
     * 完成对数据库的增删改查的操作，仅仅是一个接口
     *
     * @return
     */
    public DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    /**
     * 打开输出日志的操作，默认是关闭的
     */
    public void setDebug() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /**
     * 关闭所有的操作，数据库开启的时候，使用完毕了必须要关闭
     */
    public void closeConnection() {
        closeDaosession();
        closeHelper();
    }

    public void closeHelper() {
        if (helper != null) {
            helper.close();
            helper = null;
        }
    }

    /**
     * 关闭数据库
     */
    public void closeDaosession() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }
}
