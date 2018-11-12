package com.example.android.demo.Base;

import android.support.annotation.UiThread;

import io.reactivex.annotations.NonNull;

public interface IPresenter<T extends IBaseView> {
    /**
     * 将 View 添加到当前 Presenter
     */
    @UiThread
    void attachView(@NonNull T view);

    /**
     * 将 View 从 Presenter 移除
     */
    @UiThread
    void detachView();
}
