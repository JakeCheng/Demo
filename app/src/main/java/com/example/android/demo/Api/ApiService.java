package com.example.android.demo.Api;

import com.example.android.demo.Model.CookBookModel;
import com.example.android.demo.Model.CookBookRightModel;
import com.example.android.demo.Model.MovieCinameModel;
import com.example.android.demo.Model.MovieCityModel;
import com.example.android.demo.Model.MovieTodayModel;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {
    /**
     * 菜单标签信息
     * @param parentid
     * @param key
     * @return
     */
    @FormUrlEncoded
    @POST("/cook/category")
    Observable<CookBookModel> initCookBookDateGet(@Field("parentid") String parentid,@Field("key") String key);
    /**
     * 根据标签查询
     * @param cid
     * @param key
     * @return
     */
    @FormUrlEncoded
    @POST("/cook/index")
    Observable<CookBookRightModel> initCookBookRightDateGet(@Field("cid") String cid,@Field("key") String key);

    /**
     * 影院今日影片
     * @param cityId
     * @param key
     * @return
     */
    @GET("/movie/movies.today")
    Observable<MovieTodayModel> initMovieTodayDateGet(@Query("cityid") String cityId, @Query("key") String key);

    /**
     * 城市数据
     * @param key
     * @return
     */
    @FormUrlEncoded
    @POST("/movie/citys")
    Observable<MovieCityModel> initMovieCityDateGet(@Field("key") String key);

    /**
     * 周边影院
     * @param lat   经度
     * @param lon   维度
     * @param radius  范围
     * @param key
     * @return
     */
    @FormUrlEncoded
    @POST("/movie/cinemas.local")
    Observable<MovieCinameModel> initMovieCinameDateGet(@Field("lat") String lat,@Field("lon") String lon,@Field("radius") String radius,@Field("key") String key);
}
