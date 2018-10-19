package com.example.android.demo.Api;

import com.example.android.demo.Model.CookBookModel;
import com.example.android.demo.Model.CookBookRightModel;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
}
