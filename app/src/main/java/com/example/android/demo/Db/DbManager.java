package com.example.android.demo.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.demo.GreenDao.DBHelper;
import com.example.android.demo.GreenDao.DaoMaster;
import com.example.android.demo.GreenDao.DaoSession;

public class DbManager {
    // 是否加密
    public static final boolean ENCRYPTED = true;

    private static final String DB_NAME = "KeDouYun.db";
    private static DbManager mDbManager;
    private static DBHelper mDevOpenHelper;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    private Context mContext;

    public DbManager(Context context) {
        this.mContext = context;
        // 初始化数据库信息
        mDevOpenHelper = new DBHelper(context, DB_NAME);
        getDaoMaster(context);
        getDaoSession(context);
    }

    public static DbManager getInstance(Context context) {
        if (null == mDbManager) {
            synchronized (DbManager.class) {
                if (null == mDbManager) {
                    mDbManager = new DbManager(context);
                }
            }
        }
        return mDbManager;
    }

    /**
     * 获取可读数据库
     *
     * @param context
     * @return
     */
    public static SQLiteDatabase getReadableDatabase(Context context) {
        if (null == mDevOpenHelper) {
            getInstance(context);
        }
        return mDevOpenHelper.getReadableDatabase();
    }

    /**
     * 获取可写数据库
     *
     * @param context
     * @return
     */
    public static SQLiteDatabase getWritableDatabase(Context context) {
        if (null == mDevOpenHelper) {
            getInstance(context);
        }
        return mDevOpenHelper.getWritableDatabase();
    }

    /**
     * 获取DaoMaster
     *
     * 判断是否存在数据库，如果没有则创建数据库
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (null == mDaoMaster) {
            synchronized (DbManager.class) {
                if (null == mDaoMaster) {
                    DBHelper helper = new DBHelper(context,DB_NAME);
                    mDaoMaster = new DaoMaster(helper.getWritableDatabase());
                }
            }
        }
        return mDaoMaster;
    }


    /**
     * 获取DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (null == mDaoSession) {
            synchronized (DbManager.class) {
                mDaoSession = getDaoMaster(context).newSession();
            }
        }

        return mDaoSession;
    }
}
