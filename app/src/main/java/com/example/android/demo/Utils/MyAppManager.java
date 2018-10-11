package com.example.android.demo.Utils;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

public class MyAppManager {
    private final List<Activity> activityList = new LinkedList<>();
    private final List<Activity> activityAllList = new LinkedList<>();
    private static MyAppManager instance;

    public static MyAppManager getInstance() {
        if (null == instance) {
            instance = new MyAppManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void addAllActivity(Activity activity) {
        activityAllList.add(activity);
    }

    public void deleteLastActivity() {
        if (!activityAllList.isEmpty())
            activityAllList.remove(activityAllList.size() - 1);
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    public void exitAll() {
        for (Activity activity : activityAllList) {
            activity.finish();
        }
    }

    public Activity getTopAct() {
        if (!activityAllList.isEmpty()) {
            Activity activity = activityAllList.get(activityAllList.size() - 1);
            if (!activity.isFinishing()) {
                return activity;
            }
        }
        return null;
    }
}
