package com.example.android.demo.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.android.demo.MyApplication;
import com.example.android.demo.Service.NetWorkChangeEvent;

/**
 * Created by android on 2018/9/20.
 */

public class NetWorkUtils {

    private static final String TAG = "NetWorkUtils";

    public static boolean isConnected(Context context) {
        if (null == context) {
            return false;
        }
        try {
            ConnectivityManager connManager = (ConnectivityManager)
                    context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        } catch (Exception e) {
            Logger.error(TAG, e.toString());
        }
        return false;
    }

    public static boolean isWifiConnected(Context mContext) {
        return NetWorkManager.getInstance().isWifiConnected();
    }

    public static int getNetworkState() {
        if (NetWorkUtils.isMobileConnected(MyApplication.getInstance())) {
            return NetWorkChangeEvent.NET_DATA;
        } else if (NetWorkUtils.isWifiConnected(MyApplication.getInstance())) {
            return NetWorkChangeEvent.NET_WIFI;
        } else {
            return NetWorkChangeEvent.NET_NO;
        }
    }

    public static boolean isMobileConnected(Context context) {
        return NetWorkManager.getInstance().isMobileConnected();
    }
}
