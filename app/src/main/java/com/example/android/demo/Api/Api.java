package com.example.android.demo.Api;

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
}
