package com.example.android.demo.Base;

public class BasePresenter<T extends BaseView> implements IPresenter<T> {
    protected T mView;
    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
