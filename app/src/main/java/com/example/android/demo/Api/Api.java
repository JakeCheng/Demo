package com.example.android.demo.Api;

import com.example.android.demo.Model.CookBookModel;
import com.example.android.demo.Model.CookBookRightModel;

public class Api {

    private static ApiService apiService;

    private Api(){
        throw new AssertionError();
    }

    private static ApiService getApiService(){
        if(apiService == null){
            apiService = ApiManager.getInstance().getApiService(ApiService.class);
        }
        return apiService;
    }

    public static void initCookBookDateGet(String parentid,String key, SimpleCallback<CookBookModel> simpleCallback) {
        ApiObserver.subscribe(getApiService().initCookBookDateGet(parentid,key),simpleCallback);
    }
    public static void initCookBookRightDateGet(String cid, String key, SimpleCallback<CookBookRightModel> simpleCallback) {
        ApiObserver.subscribe(getApiService().initCookBookRightDateGet(cid,key),simpleCallback);
    }
}
