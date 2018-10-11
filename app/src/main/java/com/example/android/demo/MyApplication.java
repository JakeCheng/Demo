package com.example.android.demo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;

import com.example.android.demo.Db.DbManager;
import com.squareup.leakcanary.LeakCanary;

public class MyApplication extends Application {
    private static final String SystemConfigFile = "systemConfig.xml";
    private static final String TAG = "MyApplication";
    private static MyApplication mInstance = null;
    private static SharedPreferences mSharedPreferences;

    public MyApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        createSharedPreferencesFile();
        DbManager.getInstance(getApplicationContext());
    }
    /**
     * @描述：创建存放系统所有配置文件的 sharedpreferences文件
     */
    private void createSharedPreferencesFile() {
        mSharedPreferences = getSharedPreferences(SystemConfigFile, MODE_PRIVATE);
    }

    /**
     * @return sharedPreferences文件实例
     * @描述: 返回系统配置信息的sharedPreferences文件实例
     */
    public static SharedPreferences getSystemConfigSharedFile() {
        return mSharedPreferences;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MyApplication getInstance() {
        if (mInstance == null){
            synchronized (mInstance.getClass()){
                if (mInstance == null){
                    mInstance = new MyApplication();
                }
            }
        }
        return mInstance;
    }
}
