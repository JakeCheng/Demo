package com.example.android.demo.GreenDao;

import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.internal.DaoConfig;

/**
 * 区分哪些表需要更新结构
 */
public class MyDaoMaster extends DaoMaster {
    public MyDaoMaster(SQLiteDatabase db) {
        super(db);
    }
    public static void createTables(Database db, boolean ifNotExists, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
            String tableName = daoConfig.tablename;
            switch (tableName){
                case "STUDENT":
                    StudentDao.createTable(db, ifNotExists);
                    break;
                case "TEACHER":
                    TeacherDao.createTable(db, ifNotExists);
                    break;
                default:
                    StudentDao.createTable(db, ifNotExists);
                    TeacherDao.createTable(db, ifNotExists);
                    break;
            }
        }
    }
    public static void dropTables(Database db, boolean ifExists, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
            String tableName = daoConfig.tablename;
            switch (tableName){
                case "STUDENT":
                    StudentDao.dropTable(db, ifExists);
                    break;
                case "TEACHER":
                    TeacherDao.dropTable(db, ifExists);
                    break;
                default:
                    StudentDao.dropTable(db, ifExists);
                    TeacherDao.dropTable(db, ifExists);
                    break;
            }
        }
    }
}
