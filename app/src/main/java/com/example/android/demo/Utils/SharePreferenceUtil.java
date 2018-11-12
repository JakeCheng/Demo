package com.example.android.demo.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.text.TextUtils;

import com.example.android.demo.Model.MovieCityModel;
import com.google.gson.Gson;

/**
 * 存储用户基本信息
 */
public class SharePreferenceUtil {
	public static final String SharePre = "Demo";//SharePreference文件名
	private static SharedPreferences mShareP;
	/**
	 * 用户是否登录
	 */
	private static final String is_token = "Token";
	/**
	 * 用户第一次打开
	 */
	private static final String first_open= "first_open";
	/**
	 * 用户邮箱
	 */
	private static final String login_email = "login_email";
	/**
	 * 城市ID
	 */
	private static final String chooseCity = "chooseCity";
	/**
	 * @param context
	 * @return
	 */
	private static SharedPreferences getInstant(final Context context, final String name){
		if(mShareP == null){
			//SharedPreferences将会把这些数据保存在Demo.xml文件中，可以在File Explorer的data/data/相应的包名/test.xml 下导出该文件，并查看。
			mShareP = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		}
		return mShareP;
	}
	/**
	 * 获取是否登录
	 * @param context
	 * @return
	 */
	public static String getToken(Context context){
		return getInstant(context,SharePre).getString(is_token,"");
	}

	/**
	 * 设置是否登录
	 * @param context
	 */
	public static void setToken(Context context, String token){
		getInstant(context,SharePre).edit().putString(is_token,token).apply();
	}

	/**
	 * 获取是否第一次打开App
	 * @param context
	 * @return
	 */
	public static boolean getFirstOpen(Context context){
		return getInstant(context,SharePre).getBoolean(first_open,true);
	}

	/**
	 * 设置是否第一次打开App
	 * @param context
	 * @param isFirstOpen
	 */
	public static void setFirstOpen(Context context, boolean isFirstOpen){
		getInstant(context,SharePre).edit().putBoolean(first_open,isFirstOpen).apply();
	}

	/**
	 * 设置登录邮箱
	 * @param context
	 * @param email
	 */
	public static void setEmail(Context context,String email) {
		getInstant(context,SharePre).edit().putString(login_email,email).apply();
	}

	/**
	 * 获取当前登录邮箱
	 * @param context
	 * @return
	 */
	public static String getEmail(Context context){
		return getInstant(context,SharePre).getString(login_email,"");
	}
	/**
	 * 设置城市
	 * @param context
	 * @param cityId
	 */
	public static void setChooseCity(Context context,String cityId) {
		getInstant(context,SharePre).edit().putString(chooseCity,cityId).apply();
	}

	/**
	 * 获取城市
	 * @param context
	 * @return
	 */
	public static String getChooseCity(Context context){
		return getInstant(context,SharePre).getString(chooseCity,new Gson().toJson(new MovieCityModel.ResultBean(Parcel.obtain())));
	}

	/**
	 * 获取选中城市ID
	 * @param mContext
	 * @return
	 */
	public static String getCityID(Context mContext) {
		return TextUtils.isEmpty(new Gson().fromJson(getChooseCity(mContext), MovieCityModel.ResultBean.class).getId())?
				"1":new Gson().fromJson(getChooseCity(mContext), MovieCityModel.ResultBean.class).getId();
	}
	/**
	 * 获取选中城市ID
	 * @param mContext
	 * @return
	 */
	public static String getCityName(Context mContext) {
		return TextUtils.isEmpty(new Gson().fromJson(getChooseCity(mContext), MovieCityModel.ResultBean.class).getCity_name())?
				"上海":new Gson().fromJson(getChooseCity(mContext), MovieCityModel.ResultBean.class).getCity_name();
	}
}
