package com.example.android.demo.Interface;

import android.os.Message;

public interface ProgressNotifiable {
    /**
     * 每一阶段的载入开始时均会调用该方法
     */
    void startLoading(boolean hasData, boolean showProgress);

    /**
     * 每一阶段的载入结束时均会调用该方法
     */
    void stopLoading(boolean hasData, boolean hasNext);
    
//    /**
//     * 每一阶段的载入结束且需要标识错误状态时,会调用该方法
//     */
//    public void stopLoading(boolean hasData,NetworkError err);
    void stopHandleMessage(Message msg);
    
    /**
     * 绑定时马上会被调用，用于初始化当前的载入状态
     */
    void init(boolean hasData, boolean isLoading);
}
