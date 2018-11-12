package com.example.android.demo.Utils;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;

public class ScreenUtils {
    public ScreenUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    /**
     * 获取屏幕尺寸
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static Point getScreenSize(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2){
            return new Point(display.getWidth(), display.getHeight());
        }else{
            Point point = new Point();
            display.getSize(point);
            return point;
        }
    }
    /**
     * 获取屏幕尺寸
     * @param context
     * @return
     */
    public static DisplayMetrics getScreenMetrics(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static void showPermission(final Context mContext,String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + mContext.getPackageName())); // 根据包名打开对应的设置界面
                mContext.startActivity(intent);
            }
        });
        builder.create().show();
    }
    /**
     * uri转换为bitmap
     */
    public static Bitmap decodeUriAsBitmap(Context mContext, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 影视接口错误码
     * @param error_code
     * @return
     */
    public static String showErrMovie(int error_code) {
        String errMsg;
        switch (error_code){
            case 204201:
                errMsg = "影片标题不能为空";
                break;
            case 204202:
                errMsg = "查询失败";
                break;
            case 204203:
                errMsg = "查询不到相关记录";
                break;
            case 204204:
                errMsg = "城市ID错误";
                break;
            case 204205:
                errMsg = "错误的经纬度";
                break;
            case 204206:
                errMsg = "错误的电影院ID";
                break;
            case 204207:
                errMsg = "查询的电影院不存在";
                break;
            case 204208:
                errMsg = "错误的电影ID";
                break;
            case 10001:
                errMsg = "错误的请求KEY";
                break;
            case 10002:
                errMsg = "该KEY无请求权限";
                break;
            case 10003:
                errMsg = "KEY过期";
                break;
            case 10004:
                errMsg = "错误的OPENID";
                break;
            case 10005:
                errMsg = "应用未审核超时，请提交认证";
                break;
            case 10007:
                errMsg = "未知的请求源";
                break;
            case 10008:
                errMsg = "被禁止的IP";
                break;
            case 10009:
                errMsg = "被禁止的KEY";
                break;
            case 10011:
                errMsg = "当前IP请求超过限制";
                break;
            case 10012:
                errMsg = "请求超过次数限制";
                break;
            case 10013:
                errMsg = "测试KEY超过请求限制";
                break;
            case 10014:
                errMsg = "系统内部异常";
                break;
            case 10020:
                errMsg = "接口维护";
                break;
            case 10021:
                errMsg = "接口停用";
                break;
            default:
                errMsg = "";
                break;
        }
        return errMsg;
    }
}
