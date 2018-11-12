package com.example.android.demo.Api;

import com.example.android.demo.Model.CookBookModel;
import com.example.android.demo.Model.CookBookRightModel;
import com.example.android.demo.Model.MovieCinameModel;
import com.example.android.demo.Model.MovieCityModel;
import com.example.android.demo.Model.MovieTodayModel;
import com.example.android.demo.Utils.Constants;

public class Api {

    private static ApiService apiService;

    private Api(){
        throw new AssertionError();
    }

    private static ApiService getApiService(String url){
        if(apiService == null){
            apiService = new ApiHttp(url).getRetrofit().create(ApiService.class);
        }
        return apiService;
    }

    public static void initCookBookDateGet(String parentid,String key, SimpleCallback<CookBookModel> simpleCallback) {
        ApiObserver.subscribe(getApiService(Constants.CookBookUrl).initCookBookDateGet(parentid,key),simpleCallback);
    }
    public static void initCookBookRightDateGet(String cid, String key, SimpleCallback<CookBookRightModel> simpleCallback) {
        ApiObserver.subscribe(getApiService(Constants.CookBookUrl).initCookBookRightDateGet(cid,key),simpleCallback);
    }

    public static void initMovieTodayDateGet(String cityId, String key, SimpleCallback<MovieTodayModel> simpleCallback) {
        ApiObserver.subscribe(getApiService(Constants.MovieUrl).initMovieTodayDateGet(cityId,key),simpleCallback);
    }

    public static void initMovieCityDateGet(String key, SimpleCallback<MovieCityModel> simpleCallback) {
        ApiObserver.subscribe(getApiService(Constants.MovieUrl).initMovieCityDateGet(key),simpleCallback);
    }

    public static void initMovieCinameDateGet(String lat, String lon, String radius, String key, SimpleCallback<MovieCinameModel> simpleCallback) {
        ApiObserver.subscribe(getApiService(Constants.MovieUrl).initMovieCinameDateGet(lat,lon,radius,key),simpleCallback);
    }
}
