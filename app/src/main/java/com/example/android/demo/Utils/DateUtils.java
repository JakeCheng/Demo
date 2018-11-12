package com.example.android.demo.Utils;

import java.util.Calendar;

/**
 * Created by android on 2018/10/22.
 */

public class DateUtils {
    /**
     * 获取当天剩余时间
     */
    public static int getRemainingTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) ((cal.getTimeInMillis() - System.currentTimeMillis()) / 1000);
    }
}
