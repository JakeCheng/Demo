package com.example.android.demo.Utils;

import android.content.Context;
import android.content.SharedPreferences;

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
	 * @param context
	 * @return
	 */
	private static SharedPreferences getInstant(Context context,String name){
		if(mShareP == null){
			//SharedPreferences将会把这些数据保存在CareerAnswer.xml文件中，可以在File Explorer的data/data/相应的包名/test.xml 下导出该文件，并查看。
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
		getInstant(context,SharePre).edit().putString(is_token,token).commit();
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
		getInstant(context,SharePre).edit().putBoolean(first_open,isFirstOpen).commit();
	}

	/**
	 * 设置登录邮箱
	 * @param context
	 * @param email
	 */
	public static void setEmail(Context context,String email) {
		getInstant(context,SharePre).edit().putString(login_email,email).commit();
	}

	/**
	 * 获取当前登录邮箱
	 * @param context
	 * @return
	 */
	public static String getEmail(Context context){
		return getInstant(context,SharePre).getString(login_email,"");
	}
}
