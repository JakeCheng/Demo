package com.example.android.demo.Api;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ApiObserver<T> implements Observer<T> {

    private final SimpleCallback<T> mCallback;

    public ApiObserver(SimpleCallback<T> callback){
        this.mCallback = callback;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onError(Throwable e) {
        if(mCallback != null){
            mCallback.onError(e);
        }
    }

    @Override
    public void onComplete() {
        if (mCallback != null){
            mCallback.onComplete();
        }
    }

    @Override
    public void onNext(T t) {
        if(mCallback != null){
            mCallback.onNext(t);
        }
    }

    public static <T> ApiObserver<T> getApiObserver(SimpleCallback<T> callback){
        return new ApiObserver<>( callback );
    }

    public static <T> void subscribe(Observable<T> observable, SimpleCallback<T> callback){
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getApiObserver(callback));

    }

}

