package com.example.android.demo.Presenter;

import com.example.android.demo.Api.Api;
import com.example.android.demo.Api.SimpleCallback;
import com.example.android.demo.Base.IBasePresenter;
import com.example.android.demo.Model.MovieCinameModel;
import com.example.android.demo.Model.MovieCityModel;
import com.example.android.demo.Model.MovieTodayModel;
import com.example.android.demo.Utils.Constants;
import com.example.android.demo.Utils.Logger;
import com.example.android.demo.Utils.OkHttp;
import com.example.android.demo.View.MovieView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by android on 2018/10/17.
 */

public class MoviePresenter extends IBasePresenter<MovieView> {
    private static final String TAG = "MoviePresenter";

    public void initMovieTodayDateGet(String cityId) {
        Api.initMovieTodayDateGet(cityId, Constants.MovieKey,new SimpleCallback<MovieTodayModel>(mView) {
            @Override
            public void onSuccess(MovieTodayModel bean) {
                mView.onMovieTodayDateGet(bean);
            }

            @Override
            public void onError(String msg) {
                mView.showError(msg);
            }
        });
    }

    public void initMovieCityDateGet() {
        Api.initMovieCityDateGet(Constants.MovieKey,new SimpleCallback<MovieCityModel>(mView) {

            @Override
            public void onSuccess(MovieCityModel bean) {
                mView.onMovieCityDateGet(bean);
            }

            @Override
            public void onError(String msg) {
                mView.showError(msg);
            }
        });
    }
    public void initMovieCinameDateGet(String lat,String lon,String radius) {
        Api.initMovieCinameDateGet(lat,lon,radius,Constants.MovieKey,new SimpleCallback<MovieCinameModel>(mView) {

            @Override
            public void onSuccess(MovieCinameModel bean) {
                mView.onMovieCinameDateGet(bean);
            }

            @Override
            public void onError(String msg) {
                mView.showError(msg);
            }
        });
    }

    public void initLocationDateGet(String cityName) {
        final String[] result = {"0,0"};
        Map<String, String> map = new HashMap<>();
        map.put("key", Constants.MapWebKey);
        map.put("address", cityName);
        OkHttp.getAsync(Constants.MapUrl, map, new OkHttp.DataCallBack() {
            @Override
            public void onSuccess(String json) {
                try {
                    JSONObject object = new JSONObject(json);
                    String status = object.getString("status");
                    Logger.error(TAG,"获取的经纬度信息："+json);
                    if(status.equals("1")){
                        JSONArray geocodes = object.getJSONArray("geocodes");
                        JSONObject trueAddress = geocodes.getJSONObject(0);
                        result[0] = trueAddress.getString("location");
                    }
                    EventBus.getDefault().post(result[0]);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Request request, IOException e) {

            }
        });
    }
}
