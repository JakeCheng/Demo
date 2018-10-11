package com.example.android.demo.Api;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LogInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.e("info",String.format("%1$s->%2$s",request.method(),request.url()));
        if(request.headers()!=null){
            Log.e("info","Headers:" + request.headers());
        }
        if(request.body()!=null){
            Log.e("info","RequestBody:" + bodyToString(request.body()));
        }
        Response response = chain.proceed(chain.request());
        MediaType mediaType = response.body().contentType();
        String responseBody = response.body().string();
        Log.e("info","ResponseBody:" + responseBody);
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, responseBody))
                .build();
    }

    private String bodyToString(final RequestBody request) {
        if(request!=null){
            try {
                final RequestBody copy = request;
                final Buffer buffer = new Buffer();
                copy.writeTo(buffer);
                return buffer.readUtf8();
            } catch (final IOException e) {
                Log.e("info","Did not work.");
            }
        }
        return null;
    }
}
