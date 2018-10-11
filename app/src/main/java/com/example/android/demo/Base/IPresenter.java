package com.example.android.demo.Base;

public interface IPresenter<T extends BaseView> {
    void attachView(T view);
    void detachView();
}
