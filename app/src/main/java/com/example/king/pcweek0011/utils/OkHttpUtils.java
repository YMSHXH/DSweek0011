package com.example.king.pcweek0011.utils;

import android.os.Handler;
import android.text.TextUtils;

import com.example.king.pcweek0011.contract.ProductContact;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtils {

    /**
     * 单例模式
     */
    private static OkHttpUtils instance;
    private final OkHttpClient okHttpClient;
    private final Handler handler;

    private OkHttpUtils() {
        handler = new Handler();
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5,TimeUnit.SECONDS)
                .readTimeout(5,TimeUnit.SECONDS)
                .writeTimeout(5,TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpUtils getInstance(){
        if (instance == null){
            synchronized (OkHttpUtils.class){
                if (instance == null){
                    instance = new OkHttpUtils();
                }
            }
        }
        return instance;
    }

    /**
     * post请求
     * @param api
     * @param params
     * @param ipresenter
     */
    public void toPost(String api, Map<String, String> params, final ProductContact.Ipresenter ipresenter){

        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> p : params.entrySet()) {
            builder.add(p.getKey(),p.getValue());
        }

        final Request request = new Request.Builder()
                .post(builder.build())
                .url(api)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ipresenter.onFail("网络错误");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                if (!TextUtils.isEmpty(res)) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ipresenter.onSuccess(res);
                        }
                    });
                }
            }
        });
    }
}
