package com.example.dickjampink.logintest.Request;

import android.util.Log;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Kiboooo on 2017/7/28.
 */

public class RequestCET4orCET6 {
    public static void CheckCET4orCET6(final String Name , final String Number, final Callback callback)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request_cookie = new Request.Builder()
                            .url("http://www.chsi.com.cn/cet/")
                            .build();
                    Call call = new OkHttpClient().newCall(request_cookie);
                    String Cookie = call.execute().headers().get("Set-Cookie");
                    String Url = "http://www.chsi.com.cn/cet/query?zkzh=" + Number + "&xm=" + URLEncoder.encode(Name, "UTF-8");
                    Log.e("CheckCET4orCET6", Url);
                    Request request = new Request.Builder()
                            .url(Url)
                            .header("Cookie", Cookie)
                            .addHeader("Referer", "http://www.chsi.com.cn/cet/")
                            .build();

                    Call call2 = new OkHttpClient().newCall(request);
                    call2.enqueue(callback);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
