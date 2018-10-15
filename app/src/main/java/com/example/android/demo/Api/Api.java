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

//    public static void initPostLogin(String telephone, String number, String type, String id, String sign, SimpleCallback<LoginBean> callback) {
//        ApiObserver.subscribe(getApiService().initPostLogin(telephone,number,type,id,sign),callback);
//    }
}
