package com.example.android.demo.Utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttp {
    private static final String TAG = "OkHttp";
    /**
     * 静态实例
     */
    private static OkHttp sOkHttpManager;

    /**
     * okhttpclient实例
     */
    private OkHttpClient mClient;

    /**
     * 因为我们请求数据一般都是子线程中请求，在这里我们使用了handler
     */
    private final Handler mHandler;

    /**
     * 构造方法
     */
    private OkHttp() {
        mClient = new OkHttpClient();
        /*
          在这里直接设置连接超时.读取超时，写入超时
         */

        OkHttpClient.Builder builder = mClient.newBuilder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        mClient = builder.build();

        /*
          初始化handler
         */
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 单例模式  获取OkHttp实例
     *
     * @return
     */
    private static OkHttp getInstance() {

        if (sOkHttpManager == null) {
            sOkHttpManager = new OkHttp();
        }
        return sOkHttpManager;
    }
    //-------------------------异步的方式请求数据--------------------------
    public static void getAsync(String url,Map<String, String> params, DataCallBack callBack) {
        getInstance().inner_getAsync(url,params, callBack);
    }
    public static void getAsync(String url, DataCallBack callBack) {
        getInstance().inner_getAsync(url,callBack);
    }
    /**
     * 内部逻辑请求的方法
     *
     * @param url
     * @param callBack
     * @return
     */
    private void inner_getAsync(String url, Map<String, String> params,final DataCallBack callBack) {
        StringBuilder tempParams = new StringBuilder();
        try {
            int pos = 0;
            for (String key : params.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(params.get(key), "utf-8")));
                pos++;
            }
            String requestUrl = String.format("%s%s", url, tempParams.toString());
            Logger.error(TAG, "Get请求URL11111:"+requestUrl);
            final Request request = new Request.Builder().url(requestUrl).get().build();

            mClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    deliverDataFailure(request, e, callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = null;
                    try {
                        result = response.body().string();
                    } catch (IOException e) {
                        deliverDataFailure(request, e, callBack);
                    }
                    deliverDataSuccess(result, callBack);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void inner_getAsync(String url,final DataCallBack callBack) {
        Logger.error(TAG, "Get请求URL2222222:"+url);
        final Request request = new Request.Builder().url(url).get().build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = null;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    deliverDataFailure(request, e, callBack);
                }
                deliverDataSuccess(result, callBack);
            }
        });
    }

    /**
     * 分发失败的时候调用
     *
     * @param request
     * @param e
     * @param callBack
     */
    private void deliverDataFailure(final Request request, final IOException e, final DataCallBack callBack) {
        /*
          在这里使用异步处理
         */
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onFailure(request, e);
                }
            }
        });
    }

    /**
     * 分发成功的时候调用
     *
     * @param result
     * @param callBack
     */
    private void deliverDataSuccess(final String result, final DataCallBack callBack) {
        /*
          在这里使用异步线程处理
         */
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.onSuccess(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 数据回调接口
     */
    public interface DataCallBack {
        void onSuccess(String json);
        void onFailure(Request request, IOException e);
    }

    //-------------------------提交表单--------------------------

    public static void postAsync(String url, Map<String, Object> params, DataCallBack callBack) {
        getInstance().inner_postAsync(url, params, callBack);
    }
    public static void postAsync(String url, DataCallBack callBack) {
        getInstance().inner_postAsync(url,callBack);
    }
    private void inner_postAsync(String url, final DataCallBack callBack) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            RequestBody formBody = builder.build();
            Logger.error(TAG, "网络Post请求: "+url);
            // 请求对象
            final Request request = new Request.Builder().url(url).post(formBody).build();
            mClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    deliverDataFailure(request, e, callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    deliverDataSuccess(result, callBack);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void inner_postAsync(String url, Map<String, Object> params, final DataCallBack callBack) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : params.keySet()) {
                builder.add(key, (String) params.get(key));
            }
            RequestBody formBody = builder.build();
            Logger.error(TAG, "网络Post请求: "+url+formBody.toString());
            // 请求对象
            final Request request = new Request.Builder().url(url).post(formBody).build();
            mClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    deliverDataFailure(request, e, callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    deliverDataSuccess(result, callBack);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //-------------------------文件上传--------------------------
    public static void postFileAsync(String url, Map<String, Object> params, DataCallBack callBack) {
        getInstance().inner_postFileAsync(url, params, callBack);
    }
    private void inner_postFileAsync(String url, Map<String, Object> params, final DataCallBack callBack) {
        try {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            //设置类型
            builder.setType(MultipartBody.FORM);
            //追加参数
            for (String key : params.keySet()) {
                Object object = params.get(key);
                if (!(object instanceof File)) {
                    builder.addFormDataPart(key, object.toString());
                } else {
                    File file = (File) object;
                    builder.addFormDataPart(key, file.getName(), RequestBody.create(null, file));
                }
            }
            RequestBody formBody = builder.build();
            Logger.error(TAG, "网络Post请求: "+url+formBody.toString());
            // 请求对象
            final Request request = new Request.Builder().url(url).post(formBody).build();
            mClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    deliverDataFailure(request, e, callBack);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String result = response.body().string();
                    deliverDataSuccess(result, callBack);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------文件下载--------------------------
    public static void downloadAsync(String url, String desDir,String name, DataCallBack callBack) {
        getInstance().inner_downloadAsync(url, desDir,name, callBack);
    }

    /**
     * 下载文件的内部逻辑处理类
     *
     * @param url      下载地址
     * @param desDir   目标地址
     * @param callBack
     */
    private void inner_downloadAsync(final String url, final String desDir,final String name, final DataCallBack callBack) {
        final Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                /*
                  在这里进行文件的下载处理
                 */
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;
                try {
                    //文件名和目标地址
                    File file = new File(desDir, name);
                    //把请求回来的response对象装换为字节流
                    inputStream = response.body().byteStream();
                    fileOutputStream = new FileOutputStream(file);
                    int len;
                    byte[] bytes = new byte[2048];
                    //循环读取数据
                    while ((len = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, len);
                    }
                    //关闭文件输出流
                    fileOutputStream.flush();
                    //调用分发数据成功的方法
                    deliverDataSuccess(file.getAbsolutePath(), callBack);
                } catch (IOException e) {
                    //如果失败，调用此方法
                    deliverDataFailure(request, e, callBack);
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }

                }
            }

        });
    }
}
