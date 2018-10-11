package com.example.android.demo.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.example.android.demo.Utils.Logger;
import com.example.android.demo.Utils.NetWorkManager;

import org.greenrobot.eventbus.EventBus;

public class NetworkReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkReceiver";

    /**
     * 枚举网络状态
     * NET_NO：没有网络 ,  NET_2G:2g网络 , NET_3G：3g网络 ,NET_4G：4g网络 ,NET_WIFI：wifi , NET_UNKNOWN：未知网络
     */
    public enum NetState {
        NET_NO, NET_2G, NET_3G, NET_4G, NET_WIFI, NET_UNKNOWN
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NetState netState = getCurrentNetStateCode(context);
        if (null != netState) {
            String networkId = netState == NetState.NET_WIFI ? getNetworkID(context) : null;
            Logger.debug(TAG, netState + " " + networkId);
            NetWorkManager.getInstance().setWifiConnected(netState == NetState.NET_WIFI);
            EventBus.getDefault().post(new NetWorkChangeEvent(netState, networkId));

        }
    }

    public void register(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(this, filter);
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }

    public static NetState getCurrentNetStateCode(Context context) {
        NetState stateCode = NetState.NET_NO;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnectedOrConnecting()) {
            switch (ni.getType()) {
                //wifi
                case ConnectivityManager.TYPE_WIFI:
                    stateCode = NetState.NET_WIFI;
                    break;
                //mobile 网络
                case ConnectivityManager.TYPE_MOBILE:
                    switch (ni.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_GPRS: //联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: //电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: //移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            stateCode = NetState.NET_2G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: //电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            stateCode = NetState.NET_3G;
                            break;
                        default:
                        case TelephonyManager.NETWORK_TYPE_LTE://4g
                        case 19: //4g+
                            stateCode = NetState.NET_4G;
                            break;
                    }
                    break;
                default:
                    stateCode = NetState.NET_UNKNOWN;
            }
        }

        return stateCode;
    }

    public String getNetworkID(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager == null ? null : wifiManager.getConnectionInfo();
        if (wifiInfo != null) {
            return String.valueOf(wifiInfo.getBSSID());
        }
        return null;
    }
}