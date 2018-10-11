
package com.example.android.demo.Utils;


import android.util.Log;

import com.example.android.demo.BuildConfig;

public final class Logger {
    private final static String DEFAULT_TAG = "Logger";

    private Logger() {
        throw new UnsupportedOperationException("L cannot instantiated!");
    }

    public static void verbose(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.v(tag == null ? DEFAULT_TAG : tag, msg == null ? "" : msg);
    }

    public static void debug(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            if (msg.length() > 3000) {
                for (int i = 0; i < msg.length(); i += 3000) {
                    if (i + 3000 < msg.length())
                        Log.d(tag == null ? DEFAULT_TAG : tag, msg == null ? "" : msg.substring(i, i + 3000));
                    else
                        Log.d(tag == null ? DEFAULT_TAG : tag, msg == null ? "" : msg.substring(i, msg.length()));
                }
            } else {
                Log.d(tag == null ? DEFAULT_TAG : tag, msg == null ? "" : msg);
            }

        }

    }

    public static void info(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.i(tag == null ? DEFAULT_TAG : tag, msg == null ? "" : msg);
    }

    public static void warn(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.w(tag == null ? DEFAULT_TAG : tag, msg == null ? "" : msg);
    }

    public static void error(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            if (msg.length() > 3000) {
                for (int i = 0; i < msg.length(); i += 3000) {
                    if (i + 3000 < msg.length())
                        Log.e(tag == null ? DEFAULT_TAG : tag, msg == null ? "" : msg.substring(i, i + 3000));
                    else
                        Log.e(tag == null ? DEFAULT_TAG : tag, msg == null ? "" : msg.substring(i, msg.length()));
                }
            } else {
                Log.e(tag == null ? DEFAULT_TAG : tag, msg == null ? "" : msg);
            }

        }
    }

}
