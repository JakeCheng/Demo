package com.example.android.demo.GreenDao;

import android.content.Context;
import android.util.Log;

import com.example.android.demo.Db.Teacher;

import org.greenrobot.greendao.database.Database;

public class DBHelper extends DaoMaster.OpenHelper{
    public DBHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        if (oldVersion < newVersion) {
            Log.i("version", oldVersion + "---先前和更新之后的版本---" + newVersion);
            MigrationHelper.getInstance().migrate(db, StudentDao.class,TeacherDao.class);
            //更改过的实体类(新增的不用加)   更新UserDao文件 可以添加多个  XXDao.class 文件
//             MigrationHelper.getInstance().migrate(db, UserDao.class,XXDao.class);
        }

    }
}
