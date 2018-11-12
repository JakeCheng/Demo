package com.example.android.demo.Utils;

import com.example.android.demo.R;

public class Constants {
	public static final int[] ItemName = {R.string.main_tab_cookbook, R.string.main_tab_movie,R.string.main_tab_view,R.string.main_tab_data};
	public static final int[] ItemIconName = {R.drawable.home_tab_new_game, R.drawable.home_tab_video,R.drawable.home_tab_new_game, R.drawable.home_tab_video};
	public static final String CookBookDate = "COOKBOOKDATE";
	public static final String MovieTodayDate = "MovieTodayDate";
	public static final String MovieCityDate = "MovieCityDate";
	public static final String MovieCinemaDate = "MovieCinemaDate";
	public static final String IMAGE_URL="image";
	public static final String IMAGE_URL_ALL="images";

    public static final String PASSWORD = "123";

    public static final int CACHETIME = 60*60*24*365*10;

	public static final String CookBookUrl="http://apis.juhe.cn";
	public static final String CookBookKey = "463f34303af2e185be3085a54b5713a3";
	public static final String MovieUrl = "http://v.juhe.cn";
	public static final String MovieKey = "7e1fd16d444455c7c6a6f0bff9ec2db2";

	public static final String MapUrl = "https://restapi.amap.com/v3/geocode/geo?";
	//定位用到
	public static final String MapKey = "527a1a0031f8d830f1ff260693ba22ee";
	//根据地区获取经纬度
	public static final String MapWebKey = "9652410f18c7959f4e6d6711dc0fc172";
	public static final String View2FragmentToPermissionActivity = "View2FragmentToPermissionActivity";
	public static final String PermissionOK = "PermissionOK";
	public static final String PermissionCancal = "PermissionCancal";
}
