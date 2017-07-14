package com.example.dickjampink.logintest.Syllabus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.adapter.AttendanceAdapter;
import com.example.dickjampink.logintest.adapter.CheckAdapter;
import com.example.dickjampink.logintest.bean.AttendanceData;
import com.example.dickjampink.logintest.bean.LoginData;
import com.example.dickjampink.logintest.bean.Syllabus_type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.dickjampink.logintest.R.menu.syllabus;

public class Syllabus extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final int SUCCESSFUL = 4;
    private static final int GETPICTURE = 3;
    private static final int ATTENDANCE = 7;
    private static final int FALL = 5;


    private Toolbar toolbar;
    private View contentSyllabus, contentChecking, contentPersonMsg,contentWebView;
    private WebView wv;
    LoginData logindata;
    private String Cookie;
    private CircleImageView personImage_big;
    private CircleImageView personImage_small;
    AlertDialog.Builder builder;

    private final int w[][] = {
            {0,0,0,0,0,0,0,0},
            {0, R.id.w11,R.id.w21,R.id.w31,R.id.w41,R.id.w51,R.id.w61,R.id.w71},
            {0,R.id.w12,R.id.w22,R.id.w32,R.id.w42,R.id.w52,R.id.w62,R.id.w72},
            {0,R.id.w13,R.id.w23,R.id.w33,R.id.w43,R.id.w53,R.id.w63,R.id.w73},
            {0,R.id.w14,R.id.w24,R.id.w34,R.id.w44,R.id.w54,R.id.w64,R.id.w74},
            {0,R.id.w15,R.id.w25,R.id.w35,R.id.w45,R.id.w55,R.id.w65,R.id.w75},
            {0,R.id.w16,R.id.w26,R.id.w36,R.id.w46,R.id.w56,R.id.w66,R.id.w76},
            {0,R.id.w17,R.id.w27,R.id.w37,R.id.w47,R.id.w57,R.id.w67,R.id.w77},
            {0,R.id.w18,R.id.w28,R.id.w38,R.id.w48,R.id.w58,R.id.w68,R.id.w78},
            {0,R.id.w19,R.id.w29,R.id.w39,R.id.w49,R.id.w59,R.id.w69,R.id.w79},
            {0,R.id.w110,R.id.w210,R.id.w310,R.id.w410,R.id.w510,R.id.w610,R.id.w710},
    };

    private final int Background[] = {
            R.drawable.syllabus_grid_type01,R.drawable.syllabus_grid_type02,
            R.drawable.syllabus_grid_type03,R.drawable.syllabus_grid_type04,
            R.drawable.syllabus_grid_type05,R.drawable.syllabus_grid_type06,
            R.drawable.syllabus_grid_type07
    };

    private final int Week_back[] = {
            0,R.id.week7,R.id.week1,R.id.week2,R.id.week3,R.id.week4,R.id.week5,R.id.week6
    };



    public Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GETPICTURE:
                    //完成主界面更新,拿到数据
                    Bitmap ui_bm = (Bitmap) msg.obj;
                    personImage_big.setImageBitmap(ui_bm);
                    personImage_small.setImageBitmap(ui_bm);
                    break;
                case SUCCESSFUL:
                    setSyllabus();
                    break;
                case ATTENDANCE:
                    String data = msg.obj.toString();
                    try {
                        JSONArray obj = new JSONObject(data).getJSONArray("Obj");
                        ArrayList<AttendanceData> att_data = new ArrayList<>();
                        for (int i = 0; i <obj.length(); i++) {
                            AttendanceData a_data = new AttendanceData();
                            JSONObject att_D = obj.getJSONObject(i);
                            a_data.setClassName(att_D.getString("CourseName"));
                            a_data.setAbsence(att_D.getInt("Absence"));
                            a_data.setAttend(att_D.getInt("Attend"));
                            a_data.setShouldAttend(att_D.getInt("ShouldAttend"));
                            att_data.add(a_data);
                        }
//                        setAttendance(att_data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case FALL:
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        ViewPager viewPager = (ViewPager) findViewById(R.id.CheckMain);
        CheckAdapter adapter = new CheckAdapter(getSupportFragmentManager(), getBaseContext());
        viewPager.setAdapter(adapter);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.CheckTitle);
        tabLayout.setupWithViewPager(viewPager);


        //获取数据表实例
        LitePal.getDatabase();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        logindata = new LoginData();
        logindata = DataSupport.findLast(LoginData.class);
        Cookie = logindata.getCookie();

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //菜单按钮的监听 在下面的方法中完成
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        wv = (WebView) findViewById(R.id.web);
        contentSyllabus = findViewById(R.id.syllabus);
        contentChecking = findViewById(R.id.checking);
        contentPersonMsg = findViewById(R.id.personal);
        contentWebView = findViewById(R.id.webview);

        /**
         * 在NavigationView中，有时候我们的业务逻辑可能需要获取到Head中的一些控件，
         * 比如，在这里，我们我们希望获取到我们Head中的Text，这时候如果我们像往常一样直接findViewById的话，
         * 就会遇到空指针的错误，因为在这里我们获取到的控件为Null。那我们怎么获取到Head中的空间呢？
         * 其实很简单，我们需要先通过NavigationView获取到Head的View，然后通过这个View获取到Head的控件：
         */
        View headerView = navigationView.getHeaderView(0);
        TextView XH = (TextView) headerView.findViewById(R.id.XH);
        TextView Name = (TextView) headerView.findViewById(R.id.StudenName);
        personImage_big = (CircleImageView) headerView.findViewById(R.id.userimage);
        personImage_small = (CircleImageView) findViewById(R.id.personal_msg_image);


        String temp = "学号：" + logindata.getStudentID();
        XH.setText(temp);
        Name.setText(logindata.getName());

        //加载今天是周几
        setWeek();
        //加载课程表信息
        getSyllabus();
        //加载学生照片
        getPicture_person();
        //加载个人信息页
        setPersonMsg(logindata);
        //加载考勤表
        getAttendance();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //按返回键（BACK）时 回调的方法需要进行的操作
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //当客户点击MENU按钮的时候，调用该方法
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(syllabus, menu);
        return true;
    }

    //当客户点击菜单当中的某一个选项时，会调用该方法
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //no inspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_syllabus) {

            toolbar.setTitle("课程表");
            contentChecking.setVisibility(View.GONE);
            contentSyllabus.setVisibility(View.VISIBLE);
            contentPersonMsg.setVisibility(View.GONE);
            contentWebView.setVisibility(View.GONE);

        } else if (id == R.id.nav_checking) {

            toolbar.setTitle("考勤表");
            contentChecking.setVisibility(View.VISIBLE);
            contentSyllabus.setVisibility(View.GONE);
            contentPersonMsg.setVisibility(View.GONE);
            contentWebView.setVisibility(View.GONE);

        } else if (id == R.id.nav_personmsg) {

            toolbar.setTitle("个人信息");
            contentChecking.setVisibility(View.GONE);
            contentSyllabus.setVisibility(View.GONE);
            contentPersonMsg.setVisibility(View.VISIBLE);
            contentWebView.setVisibility(View.GONE);

        } else if (id == R.id.nav_library) {
            toolbar.setTitle("图书馆");
            contentChecking.setVisibility(View.GONE);
            contentSyllabus.setVisibility(View.GONE);
            contentPersonMsg.setVisibility(View.GONE);
            contentWebView.setVisibility(View.VISIBLE);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.setWebViewClient(new WebViewClient());
            wv.loadUrl("http://lib.xupt.edu.cn/");

        } else if (id == R.id.nav_checkscore) {
            toolbar.setTitle("查成绩");
            contentChecking.setVisibility(View.GONE);
            contentSyllabus.setVisibility(View.GONE);
            contentPersonMsg.setVisibility(View.GONE);
            contentWebView.setVisibility(View.VISIBLE);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.setWebViewClient(new WebViewClient());
            wv.loadUrl("http://222.24.62.120/");

        } else if (id == R.id.nav_aboutour) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            setAboutDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getPicture_person() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String Murl = "http://jwkq.xupt.edu.cn:8080/Common/GetPhotoByBH?xh=" + logindata.getStudentID();
                    OkHttpClient mOkHttpClient = new OkHttpClient();
                    //创建一个Request
                    final Request request = new Request.Builder()
                            .url(Murl)
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
                                //利用BitmapFactory工厂类的decode（解析）数据流对象的形式，获取到图片的位示图
                                InputStream is = response.body().byteStream();
                                Bitmap bm = BitmapFactory.decodeStream(is);
                                Message msg = new Message();
                                msg.obj = bm;//可以是基本类型，可以是对象，可以是List、map等；
                                msg.what = GETPICTURE;
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

    private void getSyllabus(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient mOkHttpClient = new OkHttpClient();
                    String session = getSession();
                    Log.e("Session is :", session);
                    RequestBody body = new FormBody.Builder()
                            .add("term_no", session)
                            .add("json", "true")
                            .build();
                    Request request = new Request.Builder()
//                            .header("Host", "jwkq.xupt.edu.cn:8080")
//                            .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                            .url("http://jwkq.xupt.edu.cn:8080/User/GetStuClass")
                            .addHeader("Cookie", Cookie)
                            .post(body)
                            .build();
                    Call call2 = mOkHttpClient.newCall(request);
                    //请求加入调度
                    call2.enqueue(new Callback() {
                        //失败的回调
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("课表请求失败咯", "++++++++++");
                            Message msg = new Message();
                            msg.what = FALL;
                            mHandler.sendMessage(msg);
                        }

                        //成功的回调
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {


                            //刷新ui，okhttp网络请求后，不是在主线程中，如果要刷新ui，必须的主线程中；
                            if (response.isSuccessful()) {
                                Log.e("Cookie :", response.headers().toString());
                                String data = response.body().string();

                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getBoolean("IsSucceed")) {
                                        Log.e("返回的数据如下：：  ", data);
                                        Log.e("OBJ :: ", new JSONObject(data).getJSONArray("Obj").toString());
                                        JSONArray jsonArray = new JSONObject(data).getJSONArray("Obj");
                                        if (jsonArray.length() >= 0) {
                                            save_syllabus_data(jsonArray);
                                            mHandler.sendEmptyMessage(SUCCESSFUL);
                                        } else {
                                            mHandler.sendEmptyMessage(FALL);
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

    private void getAttendance(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient att_Okhttp = new OkHttpClient();
                    Log.e("考勤表的Cookie：", Cookie);
                    RequestBody body = new FormBody.Builder()
                            .add("json", "true")
                            .build();
                    final Request request = new Request.Builder()
                            .url("http://jwkq.xupt.edu.cn:8080/User/GetAttendRepList")
                            .header("Cookie",Cookie)
                            .post(body)
                            .build();
                    Call call = att_Okhttp.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Message msg = new Message();
                            msg.what = FALL;
                            mHandler.sendMessage(msg);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                if (response.isSuccessful()) {
                                    Log.e("考勤表获取成功", "22222");
                                    String data = response.body().string();
                                    Log.e("考勤表获取成功", new JSONObject(data).toString());
                                    JSONArray obj = new JSONObject(data).getJSONArray("Obj");
                                    if (obj.length() >= 0) {
                                        Log.e("考勤表获取成功", "1111111111");
                                        Message msg = new Message();
                                        msg.obj = data;
                                        msg.what = ATTENDANCE;
                                        mHandler.sendMessage(msg);
                                    }
                                } else {
                                    Log.e("考勤表获取失败", "999999999999");
                                    mHandler.sendEmptyMessage(FALL);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
    private String getSession() {
        Calendar c = Calendar.getInstance();
        if (c.get(Calendar.MONTH) > 8) {
            return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.YEAR) + 1) + "-1";
        }
        return (c.get(Calendar.YEAR)-1)+ "-" + (c.get(Calendar.YEAR)) + "-2";
    }

    private void save_syllabus_data(JSONArray data){
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

    //利用数据库的数据，建立课程格子
    private void setSyllabus(){
        List<Syllabus_type> texts = DataSupport.findAll(Syllabus_type.class);
        String ui_control;
        int rand_background;

        for (Syllabus_type text : texts) {
            int[] time_base = syllabus_time(text.getJT_NO());
            if (text.getBackground() == -1) {
                Log.e("S_name：：：", text.getS_Name());
                rand_background = (int)(Math.random()*7);
                text.setBackground(rand_background);
                for (Syllabus_type up : texts) {
                    if (up.getS_Name().compareTo( text.getS_Name())==0) {
                        up.setBackground(rand_background);
                    }
                }
                    Log.e("The table is ：：：", text.getS_Name()+"   "+text.getBackground()+"  "+rand_background);
            }else
                rand_background = text.getBackground();
            for (int aTime_base : time_base) {
                if (aTime_base%2==0)
                    ui_control = text.getTeach_Name() + "\n" + text.getRoomNum();
                else
                    ui_control = text.getS_Name() ;
                TextView w_view = (TextView) findViewById(w[aTime_base][text.getWeekNum()]);
                w_view.setText(ui_control);
                w_view.setBackgroundResource(Background[rand_background]);
            }
        }
    }

    private void setPersonMsg(LoginData ld){
        TextView ps_name = (TextView) findViewById(R.id.person_name);
        TextView ps_xuehao = (TextView) findViewById(R.id.person_xuehao);
        TextView ps_xueyuan = (TextView) findViewById(R.id.person_xueyuan);
        TextView ps_banji = (TextView) findViewById(R.id.person_banji);
        TextView ps_zhuanye = (TextView) findViewById(R.id.person_zhuanye);
        Log.e("personmsg", ld.getName());
        Log.e("personmsg", ld.getBJ());
        Log.e("personmsg", ld.getStudentID());
        Log.e("personmsg", ld.getXYname());
        Log.e("personmsg", ld.getZYName());
        ps_name.setText(ld.getName());
        ps_banji.setText(ld.getBJ());
        ps_xuehao.setText(ld.getStudentID());
        ps_xueyuan.setText(ld.getXYname());
        ps_zhuanye.setText(ld.getZYName());
    }

    @Override
    public void finish() {
        super.finish();
        DataSupport.deleteAll(Syllabus_type.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataSupport.deleteAll(Syllabus_type.class);

    }

    //将上课时间进行分离成int数组
    private int[] syllabus_time(String T) {
        String[] time = T.split("-");
        int[] S_time = new int[time.length];
        for (int i = 0; i < time.length; i++) {
            S_time[i] = Integer.parseInt(time[i]);
        }
        return S_time;
    }

    private void setWeek() {
        Calendar c = Calendar.getInstance();
        TextView week_back = (TextView) findViewById(Week_back[c.get(Calendar.DAY_OF_WEEK)]);
        week_back.setBackgroundResource(R.drawable.syllabus_grid_type07);
    }

    //建立考勤表
    private void setAttendance(ArrayList<AttendanceData> ATT){

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.att_table_display);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        AttendanceAdapter adapter = new AttendanceAdapter(ATT);
        recyclerView.setAdapter(adapter);
    }

    private void  setAboutDialog() {
            LayoutInflater inflater = getLayoutInflater();
            View dialog = inflater.inflate(R.layout.about_dialog, (ViewGroup) findViewById(R.id.dialog));
            TextView Github = (TextView) dialog.findViewById(R.id.github);
            TextView Email = (TextView) dialog.findViewById(R.id.email);
            builder = new AlertDialog.Builder(Syllabus.this);
            String git = "GitHub：" + "https://github.com/kiboooo";
            String email = "Email ： " + "www.kiboooo78@gmail.com";
            builder.setView(dialog);
            Github.setText(git);
            Email.setText(email);
            builder.show();
    }

}
