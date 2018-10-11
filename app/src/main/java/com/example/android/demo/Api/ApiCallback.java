package com.example.android.demo.Api;

public interface ApiCallback<T> {

    void onNext(T t);

    void onError(Throwable e);

    void onCompleted();

}
