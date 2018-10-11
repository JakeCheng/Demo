package com.example.android.demo.Service;


import com.example.android.demo.Utils.Logger;

/**
 * 网络变化的事件
 */

public class NetWorkChangeEvent {

    private static final String TAG = "NetWorkChangeEvent";

    public static final int NET_NO = 0;
    public static final int NET_DATA = 1;
    public static final int NET_WIFI = 2;


    public int net;
    public NetworkReceiver.NetState netState;
    private String netId;

    public NetWorkChangeEvent(NetworkReceiver.NetState netState, String netId) {
        this.netState = netState;
        switch (netState) {
            default:
            case NET_NO:
                this.net = NET_NO;
                break;
            case NET_2G:
            case NET_3G:
            case NET_4G:
                this.net = NET_DATA;
                break;
            case NET_WIFI:
                this.net = NET_WIFI;
                break;
        }
        this.netId = netId;
        Logger.debug(TAG, toString());
    }

    @Override
    public String toString() {
        return "NetWorkChangeEvent{" +
                "net=" + net +
                ", netId='" + netId + '\'' +
                '}';
    }
}
