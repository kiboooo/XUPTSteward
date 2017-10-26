package com.example.dickjampink.logintest.Request;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.example.dickjampink.logintest.activity.PYJHActivity;
import com.example.dickjampink.logintest.adapter.TestStackAdapter;
import com.example.dickjampink.logintest.bean.LoginData;
import com.example.dickjampink.logintest.bean.PYJHData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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

/**
 * Created by Kiboooo on 2017/7/18.
 *
 */

public class RequsetZF {

    private static OkHttpClient mOkHttpClient;
    private static String auth_Cookie;
    private static String CheckGrad_ViewState = "";
    private static String GradeUrl = "";
    private static LoginData loginData = new LoginData();
//    private static List<List<PYJHData>> PYJHLISTs = new ArrayList<>();

    //說明格式和编码的常量
    private static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    public static void getCheckImag(final Activity activity,final ImageView imageView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String mUrl = "http://222.24.62.120/CheckCode.aspx";
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
                        }

                        //成功的回调
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            //刷新ui，okhttp网络请求后，不是在主线程中，如果要刷新ui，必须的主线程中；
                            if (response.isSuccessful()) {

                                InputStream is = response.body().byteStream();
                                final Bitmap bm = BitmapFactory.decodeStream(is);
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        imageView.setImageBitmap(bm);
                                    }
                                });
                                Headers headers = response.headers();
                                List<String> cookies = headers.values("Set-Cookie");
                                String session = cookies.get(0);
                                auth_Cookie = session.substring(0, session.indexOf(";"));
                                Log.e("auth_Cookie: ", auth_Cookie);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void sendLogin(final String Username, final String Password, final String CheckNumber, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("__VIEWSTATE", "dDwxNTMxMDk5Mzc0Ozs+lYSKnsl/mKGQ7CKkWFJpv0btUa8=")
                            .add("txtUserName", Username)
                            .add("Textbox1", "")
                            .add("TextBox2", Password)
                            .add("txtSecretCode", CheckNumber)
                            .add("RadioButtonList1", "%D1%A7%C9%FA")
                            .add("Button1", "")
                            .add("lbLanguage", "")
                            .add("hidPdrs", "")
                            .add("hidsc", "")
                            .build();

                    final Request request = new Request.Builder()
                            .url("http://222.24.62.120/default2.aspx")
                            .header("Cookie", auth_Cookie)
                            .post(requestBody)
                            .build();

                    Call call = client.newCall(request);
                    call.enqueue(callback);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void getFirstTVGrade(final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                loginData = DataSupport.findLast(LoginData.class);
                String xh = "xh="+loginData.getStudentID();
                String xm = null;
                try {
                    xm = URLEncoder.encode(loginData.getName(),"GBK");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                GradeUrl = "http://222.24.62.120/xscjcx.aspx?" + xh + "&xm=" + xm + "&gnmkdm=N121605";

                OkHttpClient client = new OkHttpClient();
                final Request request = new Request.Builder()
                        .url(GradeUrl)
                        .addHeader("Connection","keep-alive")
                        .addHeader("Upgrade-Insecure-Requests","1")
                        .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                        .addHeader("Referer", GradeUrl)
                        .addHeader("Accept-Encoding","gzip, deflate, sdch")
                        .addHeader("Accept-Language","zh-CN,zh;q=0.8")
                        .addHeader("Cookie",auth_Cookie)
                        .addHeader("Host","222.24.62.120")
                        .build();

                Call call = client.newCall(request);
                call.enqueue(callback);
            }
        }).start();
    }

    public static void CheckGradeRequset(final String ddlXN,final String ddlXQ,final String ButtonSelect, final Callback callback) {
        getFirstTVGrade(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responsebody = response.body().string();
                    Log.e("responsebody   ", responsebody);
                    Document document = Jsoup.parse(responsebody);
                    String viewtate = document.select("input[name=__VIEWSTATE]").val();
                    Log.e("noCode viewstate   ", viewtate.substring(viewtate.length()-5));
                    CheckGrad_ViewState = URLEncoder.encode(viewtate, "GBK");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
//                            Log.e("CheckGrad_ViewState  ", CheckGrad_ViewState.substring(CheckGrad_ViewState.length()-5));
//                            Log.e("GradeUrl  ", GradeUrl);
//                            Log.e("Body :  ", ddlXN + "   " + ddlXQ + "   " + ButtonSelect);
//                            Log.e("Body  ddlXN   :  ", ddlXN.split("=")[0] + "   " + ddlXN.split("=")[1] );
//                            Log.e("Body  ddlXQ   :  ", ddlXQ.split("=")[0] + "   " + ddlXQ.split("=")[1] );
//                            Log.e("Body  ButtonSelec:  ", ButtonSelect.split("=")[0]+ "   " + ButtonSelect.split("=")[1] );

                            String requestbody_String = "__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE="
                                    + CheckGrad_ViewState
                                    + "&hidLanguage=&ddl_kcxz="
                                    + ddlXN + ddlXQ + ButtonSelect;

                            Log.e("ViewState  ", requestbody_String.substring(requestbody_String.length()-100));
                            OkHttpClient client = new OkHttpClient();
                            final Request request = new Request.Builder()
                                    .url(GradeUrl)
                                    .header("Host", "222.24.62.120")
                                    .addHeader("Connection", "keep-alive")
                                    .addHeader("Cache-Control", "max-age=0")
                                    .addHeader("Origin", "http://222.24.62.120")
                                    .addHeader("Upgrade-Insecure-Requests", "1")
                                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                                    .addHeader("Referer", GradeUrl)
                                    .addHeader("Accept-Encoding", "gzip, deflate")
                                    .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
                                    .addHeader("Cookie", auth_Cookie)
                                    .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, requestbody_String))
                                    .build();

                            Call call2 = client.newCall(request);
                            call2.enqueue(callback);
                        }
                    }).start();
                }
            }
        });
    }

    public static void getPYJHViewState( final Handler handler, final TestStackAdapter mTestStackAdapter) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int x = 1; x < 9; x++) {


                    loginData = DataSupport.findLast(LoginData.class);
                    String xh = "xh=" + loginData.getStudentID();
                    String xm = null;
                    try {
                        xm = URLEncoder.encode(loginData.getName(), "GBK");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    GradeUrl = "http://222.24.62.120/pyjh.aspx?" + xh + "&xm=" + xm + "&gnmkdm=N121607";

                    OkHttpClient client = new OkHttpClient();
                    final Request request = new Request.Builder()
                            .url(GradeUrl)
                            .addHeader("Connection", "keep-alive")
                            .addHeader("Upgrade-Insecure-Requests", "1")
                            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                            .addHeader("Referer", GradeUrl)
                            .addHeader("Accept-Encoding", "gzip, deflate, sdch")
                            .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
                            .addHeader("Cookie", auth_Cookie)
                            .addHeader("Host", "222.24.62.120")
                            .build();

                    Call call = client.newCall(request);
                    String responsebody = null;
                    try {
                        responsebody = call.execute().body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Document document = Jsoup.parse(responsebody);
                    String viewtate = document.select("input[name=__VIEWSTATE]").val();
                    try {
                        CheckGrad_ViewState = URLEncoder.encode(viewtate, "GBK");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String requestbody_String = "__EVENTTARGET=xq&__EVENTARGUMENT=&__VIEWSTATE="
                            + CheckGrad_ViewState
                            + "&xq=" + x + "&kcxz=%C8%AB%B2%BF&dpDBGrid%3AtxtChoosePage=1&dpDBGrid%3AtxtPageSize=20";
                    Log.e("GetPYJHList", "&xq=" + x + "&kcxz=%C8%AB%B2%BF&dpDBGrid%3AtxtChoosePage=1&dpDBGrid%3AtxtPageSize=20");
                    final Request request1 = new Request.Builder()
                            .url(GradeUrl)
                            .header("Host", "222.24.62.120")
                            .addHeader("Connection", "keep-alive")
                            .addHeader("Cache-Control", "max-age=0")
                            .addHeader("Origin", "http://222.24.62.120")
                            .addHeader("Upgrade-Insecure-Requests", "1")
                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
                            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                            .addHeader("Referer", GradeUrl)
                            .addHeader("Accept-Encoding", "gzip, deflate")
                            .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
                            .addHeader("Cookie", auth_Cookie)
                            .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, requestbody_String))
                            .build();

                    Call call2 = client.newCall(request1);
                    Document document1 = null;
                    try {
                        document1 = Jsoup.parse(call2.execute().body().string());
                        Elements elements = document1.select("table#DBGrid.datelist > tbody > tr");
                        final List<PYJHData> PYJHList = new ArrayList<>();
//                    Log.e("GetPYJHList", elements.toString());
                        for (int i = 1; i < elements.size(); i++) {
                            PYJHData pyjh = new PYJHData();
                            pyjh.setClassName(elements.get(i).select("td").get(1).text());
                            pyjh.setClassNature(elements.get(i).select("td").get(5).text());
                            pyjh.setPyjh_credit(elements.get(i).select("td").get(2).text());
                            pyjh.setPyjh_leaningWhere(elements.get(i).select("td").get(7).text());
                            pyjh.setPyjh_major(elements.get(i).select("td").get(10).text());
                            pyjh.setPyjh_starAndEnd(elements.get(i).select("td").get(14).text());
                            pyjh.setPyjh_test(elements.get(i).select("td").get(4).text());
                            PYJHList.add(pyjh);
                            if (i == elements.size() - 1) {
                                Message msg = new Message();
                                msg.obj = PYJHList;
                                msg.what = PYJHActivity.PYJH_ADD;
                                handler.sendMessage(msg);
                                if (x == 8) {
                                    handler.sendEmptyMessage(PYJHActivity.PYJH_OVER);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

//    public static void SendPYJHRequest(final String XQ, final Handler handler, final TestStackAdapter mTestStackAdapter) {
//        getPYJHViewState(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                PYJHDialog.dismiss();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String responsebody = response.body().string();
//                    Document document = Jsoup.parse(responsebody);
//                    String viewtate = document.select("input[name=__VIEWSTATE]").val();
//                    CheckGrad_ViewState = URLEncoder.encode(viewtate, "GBK");
////                    new Thread(new Runnable() {
////                        @Override
////                        public void run() {
//                    String requestbody_String = "__EVENTTARGET=xq&__EVENTARGUMENT=&__VIEWSTATE="
//                            + CheckGrad_ViewState
//                            + "&xq=" + XQ + "&kcxz=%C8%AB%B2%BF&dpDBGrid%3AtxtChoosePage=1&dpDBGrid%3AtxtPageSize=20";
//                    OkHttpClient client = new OkHttpClient();
//                    final Request request = new Request.Builder()
//                            .url(GradeUrl)
//                            .header("Host", "222.24.62.120")
//                            .addHeader("Connection", "keep-alive")
//                            .addHeader("Cache-Control", "max-age=0")
//                            .addHeader("Origin", "http://222.24.62.120")
//                            .addHeader("Upgrade-Insecure-Requests", "1")
//                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
//                            .addHeader("Referer", GradeUrl)
//                            .addHeader("Accept-Encoding", "gzip, deflate")
//                            .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
//                            .addHeader("Cookie", auth_Cookie)
//                            .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, requestbody_String))
//                            .build();
//
//                    Call call2 = client.newCall(request);
//                    Document document = null;
//                    try {
//                        document = Jsoup.parse(call2.execute().body().string());
//                        Elements elements = document.select("table#DBGrid.datelist > tbody > tr");
//                        final List<PYJHData> PYJHList = new ArrayList<>();
//                        for (int i = 1; i < elements.size(); i++) {
//                            PYJHData pyjh = new PYJHData();
//                            pyjh.setClassName(elements.get(i).select("td").get(1).text());
//                            pyjh.setClassNature(elements.get(i).select("td").get(5).text());
//                            pyjh.setPyjh_credit(elements.get(i).select("td").get(2).text());
//                            pyjh.setPyjh_leaningWhere(elements.get(i).select("td").get(7).text());
//                            pyjh.setPyjh_major(elements.get(i).select("td").get(10).text());
//                            pyjh.setPyjh_starAndEnd(elements.get(i).select("td").get(14).text());
//                            pyjh.setPyjh_test(elements.get(i).select("td").get(4).text());
//                            PYJHList.add(pyjh);
//                            if (i == elements.size() - 1) {
//                                PYJHLISTs.add(PYJHList);
//                                Log.e("GetPYJHList", PYJHLISTs.get(0).get(0).getClassName() + "   " + PYJHLISTs.size() + "   " + XQ);
//                                if (PYJHLISTs.size() == 8) {
//                                    handler.postDelayed(
//                                            new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    PYJHDialog.dismiss();
//                                                    mTestStackAdapter.updateData(Arrays.asList(ItemColor));
//                                                }
//                                            }, 200);
//                                }
//                            }
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
////                    }).start();
////                }
//            }
//        });
//    }
}
