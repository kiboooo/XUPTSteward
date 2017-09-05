package com.example.dickjampink.logintest.Request;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;

import com.example.dickjampink.logintest.Syllabus.Syllabus;
import com.example.dickjampink.logintest.bean.LoginData;
import com.example.dickjampink.logintest.bean.Syllabus_type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.dickjampink.logintest.Syllabus.Syllabus.SUCCESSFUL;
import static com.example.dickjampink.logintest.activity.MainActivity.JSON;

/**
 * Created by Kiboooo on 2017/7/11.
 *
 */

public class RequestZHJS {
    private static final int GETPICTURE = 0;
    private static final int FALL = 2;
    private static final int LOGIN_FALL = 3;
    private static final int LOGIN_SUCCESS = 44;

    private final static String TAG = "MainActivity";
    private static OkHttpClient mOkHttpClient;

    private static String auth_Cookie;
    private static String seccessfulCookie;

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
                            Log.e(TAG, "onResponse-size: " + cookies);
                            auth_Cookie = session.substring(0, session.indexOf(";"));
                            Log.e(TAG, "session is  :" + auth_Cookie);
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
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.length() >= 0) {

                                        if (jsonObject.getBoolean("IsSucceed")) {
                                            Headers headers = response.headers();
                                            //获取登录成功的Cookie
                                            List<String> cookies = headers.values("Set-Cookie");
                                            String login_cookie = cookies.get(0);
                                            Log.e("Cookie login_cookie", login_cookie);
                                            seccessfulCookie = auth_Cookie+"; "+login_cookie.substring(0, login_cookie.indexOf(";"));
                                            Log.e("Cookie seccessfulCookie",seccessfulCookie );
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

    public static void CheckMsgRequest(final String WaterDate, final String Status,
                                       final String Flag, final String Page,
                                       final String Rows, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody requestBody = new FormBody.Builder()
                        .add("WaterDate", WaterDate)
                        .add("Status", Status)
                        .add("Flag", Flag)
                        .add("page", Page)
                        .add("rows", Rows)
                        .build();

                Log.e("initCheckAttAdapter", seccessfulCookie);
                Request request = new Request.Builder()
                        .url("http://jwkq.xupt.edu.cn:8080/User/GetAttendList")
                        .addHeader("Cookie",seccessfulCookie)
                        .addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8")
                        .post(requestBody)
                        .build();

                Call call = mOkHttpClient.newCall(request);
                call.enqueue(callback);
            }
        }).start();
    }

    public static void GetClassRoomMSGRequest(final String ID, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody requestBody = new FormBody.Builder()
                        .add("id", ID)
                        .add("json", "true")
                        .build();

                Request request = new Request.Builder()
                        .url("http://jwkq.xupt.edu.cn:8080/Room/GetListByBuild")
                        .header("Host", "jwkq.xupt.edu.cn:8080")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
                        .addHeader("Origin", "http://jwkq.xupt.edu.cn:8080")
                        .addHeader("X-Requested-With", "XMLHttpRequest")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .addHeader("Referer", "http://jwkq.xupt.edu.cn:8080/")
                        .addHeader("Accept-Encoding", "gzip, deflate")
                        .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
                        .post(requestBody)
                        .build();

                Call call = mOkHttpClient.newCall(request);
                call.enqueue(callback);
            }
        }).start();
    }

    //获取考勤表信息
    public static void getAttendance(final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                    OkHttpClient att_Okhttp = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("json", "true")
                            .build();
                    final Request request = new Request.Builder()
                            .url("http://jwkq.xupt.edu.cn:8080/User/GetAttendRepList")
                            .header("Cookie", seccessfulCookie)
                            .post(body)
                            .build();
                    Call call = att_Okhttp.newCall(request);
                    call.enqueue(callback);
            }
        }).start();
    }
   public static void getSyllabus(final Handler mHandler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LoginData logindata = DataSupport.findLast(LoginData.class);
                    OkHttpClient mOkHttpClient = new OkHttpClient();
                    String session = getSession();
                    RequestBody body = new FormBody.Builder()
                            .add("term_no", session)
                            .add("json", "true")
                            .build();
                    Request request = new Request.Builder()
                            .url("http://jwkq.xupt.edu.cn:8080/User/GetStuClass")
                            .addHeader("Cookie", seccessfulCookie)
                            .post(body)
                            .build();
                    Call call2 = mOkHttpClient.newCall(request);
                    //请求加入调度
                    call2.enqueue(new Callback() {
                        //失败的回调
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Message msg = new Message();
                            msg.what = Syllabus.FALL;
                            mHandler.sendMessage(msg);
                        }

                        //成功的回调
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {


                            //刷新ui，okhttp网络请求后，不是在主线程中，如果要刷新ui，必须的主线程中；
                            if (response.isSuccessful()) {
                                String data = response.body().string();
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getBoolean("IsSucceed")) {
                                        Log.e("78返回的数据如下", data);
                                        Log.e("78OBJ :: ", new JSONObject(data).getJSONArray("Obj").toString());
                                        JSONArray jsonArray = new JSONObject(data).getJSONArray("Obj");
                                        if (jsonArray.length() >= 0) {
                                            save_syllabus_data(jsonArray);
                                            mHandler.sendEmptyMessage(SUCCESSFUL);
                                        } else {
                                            mHandler.sendEmptyMessage(Syllabus.FALL);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Message msg = new Message();
                                mHandler.sendMessage(msg);

                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //利用日期类 求出 查询的时间点为哪一个学期；
    private static String getSession() {
        Calendar c = Calendar.getInstance();
        if (c.get(Calendar.MONTH)+1 > 8) {
            return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.YEAR) + 1) + "-1";
        }
        return (c.get(Calendar.YEAR) - 1) + "-" + (c.get(Calendar.YEAR)) + "-2";
    }

    //储存课表信息
    private static void save_syllabus_data(JSONArray data) {
        try {
            int data_size = data.length();
            for (int i = 0; i < data_size; i++) {
                Syllabus_type syllabus_type = new Syllabus_type();
                JSONObject jsonObject = data.getJSONObject(i);
                syllabus_type.setWeekNum(jsonObject.getInt("WEEKNUM"));
                syllabus_type.setS_Name(jsonObject.getString("S_Name"));
                syllabus_type.setTeach_Name(jsonObject.getString("Teach_Name"));
                syllabus_type.setJT_NO(jsonObject.getString("JT_NO"));
                syllabus_type.setRoomNum(jsonObject.getString("RoomNum"));
                syllabus_type.setBackground(-1);
                syllabus_type.save();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
