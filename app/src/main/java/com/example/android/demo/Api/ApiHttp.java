package com.example.android.demo.Api;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHttp {

    /**
     *  默认超时时间 单位/秒
     */
    public static final int DEFAULT_TIME_OUT = 20;

    private int mTimeout = DEFAULT_TIME_OUT;

    public String mBaseUrl;

    private OkHttpClient mOkHttpClient;

    private Retrofit mRetrofit;



    public ApiHttp(String baseUrl){
        this(baseUrl,DEFAULT_TIME_OUT);
    }

    /**
     *
     * @param baseUrl
     * @param timeout  超时时间 单位/秒
     */
    public ApiHttp(String baseUrl, int timeout){
        this.mBaseUrl = baseUrl;
        this.mTimeout = timeout;
    }

    public Retrofit getRetrofit(){
        if(mRetrofit == null){

            mRetrofit = new Retrofit.Builder()
                    .baseUrl("")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory( RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }
        return mRetrofit;
    }

    public OkHttpClient getOkHttpClient(){
        if(mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(mTimeout, TimeUnit.SECONDS)
                    .readTimeout(mTimeout, TimeUnit.SECONDS)
                    .writeTimeout(mTimeout, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(new LogInterceptor())
                    .build();
        }
        return mOkHttpClient;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.mOkHttpClient = okHttpClient;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.mRetrofit = retrofit;
    }
}
