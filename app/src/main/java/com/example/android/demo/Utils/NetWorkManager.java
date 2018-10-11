package com.example.android.demo.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.android.demo.MyApplication;
import com.example.android.demo.Service.NetWorkChangeEvent;

/**
 * Created by sw on 2018/6/18.
 */

public class NetWorkManager {
    private static final String TAG = "NetWorkManager";
    private static boolean isWifiConnected;
    private static volatile NetWorkManager sIntance;



    public static NetWorkManager getInstance(){
        if(sIntance == null){
            synchronized (NetWorkManager.class){
                if(sIntance == null){
                    sIntance = new NetWorkManager();
                }
            }
        }
        return sIntance;
    }

    public static boolean isWifiConnected() {
        return isWifiConnected;
    }

    public void setWifiConnected(boolean wifiConnected) {
        isWifiConnected = wifiConnected;
    }

    public void checkWifiConnected(){
        ConnectivityManager connManager = (ConnectivityManager)
                MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        isWifiConnected = (networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI);
    }

    public static boolean isMobileConnected(){
        return !isWifiConnected;
    }
    public static int getNetworkState() {
        if (isMobileConnected()) {
            return NetWorkChangeEvent.NET_DATA;
        } else if (isWifiConnected()) {
            return NetWorkChangeEvent.NET_WIFI;
        } else {
            return NetWorkChangeEvent.NET_NO;
        }
    }

    public boolean isConnected(){
        try {
            ConnectivityManager connManager = (ConnectivityManager)
                    MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        } catch (Exception e) {
            Logger.warn(TAG, e.toString());
        }
        return false;
    }


}
