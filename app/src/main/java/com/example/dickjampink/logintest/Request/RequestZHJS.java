package com.example.dickjampink.logintest.Request;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;

import com.example.dickjampink.logintest.bean.LoginData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.dickjampink.logintest.activity.MainActivity.JSON;

/**
 * Created by Kiboooo on 2017/7/11.
 */

public class RequestZHJS {
    private static final int GETPICTURE = 0;
    private static final int FALL = 2;
    private static final int LOGIN_FALL = 3;
    private static final int LOGIN_SUCCESS = 4;

    private final static String TAG = "MainActivity";
    private static OkHttpClient mOkHttpClient;

    private static String auth_Cookie;

    public static void getPicture(final Handler mHandler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String mUrl = "http://jwkq.xupt.edu.cn:8080/Common/GetValidateCode?time="+System.currentTimeMillis();
                    mOkHttpClient = new OkHttpClient();
                    //创建一个Request
                    final Request request = new Request.Builder()
                            .url(mUrl)
                            .build();
                    //new call
                    Call call = mOkHttpClient.newCall(request);
                    //请求加入调度
                    call.enqueue(new Callback() {
                        //失败的回调
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.i(TAG, "errro");
                        }

                        //成功的回调
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            //刷新ui，okhttp网络请求后，不是在主线程中，如果要刷新ui，必须的主线程中；
                            if (response.isSuccessful()) {

                                InputStream is = response.body().byteStream();
                                Bitmap bm = BitmapFactory.decodeStream(is);
                                mHandler.sendEmptyMessage(GETPICTURE);
                                Message msg = new Message();
                                msg.obj = bm;//可以是基本类型，可以是对象，可以是List、map等；
                                mHandler.sendMessage(msg);
                            }
                            Headers headers = response.headers();
                            Log.e(TAG, "header : " + headers);
                            Log.e(TAG, "header : date  " + headers.get("Date"));
                            List<String> cookies = headers.values("Set-Cookie");
                            String session = cookies.get(0);
//                            Log.e(TAG, "onResponse-size: " + cookies);
                            auth_Cookie = session.substring(0, session.indexOf(";"));
                            Log.i(TAG, "session is  :" + auth_Cookie);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }



    public static void sendRequest(final Handler mHandler, final EditText accountEdit,
                                   final EditText passwordEdit, final EditText authcodeEdit){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String account = accountEdit.getText().toString();
                    final String password = passwordEdit.getText().toString();
                    JSON = MediaType.parse("application/json; charset=utf-8");
                    JSONObject json = new JSONObject();
                    json.put("UserName", accountEdit.getText().toString().trim());
                    json.put("UserPassword", passwordEdit.getText().toString().trim());
                    json.put("ValiCode", authcodeEdit.getText().toString().trim());
                    RequestBody body = RequestBody.create(JSON, json.toString());
                    final Request request = new Request.Builder()
                            .header("Host", "jwkq.xupt.edu.cn:8080")
                            .addHeader("Cookie", auth_Cookie)
                            .url("http://jwkq.xupt.edu.cn:8080/Account/Login")
                            .post(body)
                            .build();


                    Call call2 = mOkHttpClient.newCall(request);
                    //请求加入调度
                    call2.enqueue(new Callback() {
                        //失败的回调
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Message msg = new Message();
                            msg.what = LOGIN_FALL;
                            mHandler.sendMessage(msg);
                            Log.i(TAG, e.getMessage());
                        }

                        //成功的回调
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {


                            //刷新ui，okhttp网络请求后，不是在主线程中，如果要刷新ui，必须的主线程中；
                            if (response.isSuccessful()) {
                                String data = response.body().string();
//                                    final Gson gson = new Gson();
//                                    LData loginData = gson.fromJson(data, LData.class);
                                try {
                                    Log.e(TAG, "jsonArray 前 : "+data);
                                    JSONObject jsonObject = new JSONObject(data);
                                    Log.e(TAG, "COOKIE_HEADERS：：" + response.headers());
                                    Log.e(TAG,"    ++++ "+jsonObject.length());
                                    if (jsonObject.length() >= 0) {

                                        if (jsonObject.getBoolean("IsSucceed")) {
                                            Headers headers = response.headers();
                                            Log.e("Login seccess headers::", headers.toString());
                                            //获取登录成功的Cookie
                                            List<String> cookies = headers.values("Set-Cookie");
                                            String login_success = cookies.get(0);
                                            String login_cookie = auth_Cookie+"; "+login_success.substring(0, login_success.indexOf(";"));
                                            byte[] user_pass = (account+" "+password).getBytes();
                                            LoginData loginD = new LoginData();
                                            JSONObject login_data = jsonObject.getJSONObject("Obj");
                                            loginD.setStudentID(login_data.getString("SNO"));
                                            loginD.setName(login_data.getString("NAME"));
                                            loginD.setXYname(login_data.getString("XYName"));
                                            loginD.setZYName(login_data.getString("ZYName"));
                                            loginD.setBJ(login_data.getString("BJ"));
                                            loginD.setCookie(login_cookie);
                                            loginD.save();
                                            Log.e(TAG, "IsSucceed : "+data);
                                            Log.e(TAG, jsonObject.getJSONObject("Obj").getString("NAME"));

                                            Message message = mHandler.obtainMessage();
                                            message.obj = user_pass;
                                            message.what = LOGIN_SUCCESS;
                                            mHandler.sendMessage(message);
                                        }
                                        else {
                                            mHandler.sendEmptyMessage(LOGIN_FALL);
                                            Log.e(TAG, "FALL : "+data);
                                        }
                                    }
                                    else {
                                        mHandler.sendEmptyMessage(LOGIN_FALL);
                                        Log.e(TAG, "jsonArray.length() < 0 : "+data);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.e(TAG, data);
                                Message msg = new Message();
                                mHandler.sendMessage(msg);

                            }
                            Headers headers = response.headers();
                            Log.d(TAG, "header " + headers);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
