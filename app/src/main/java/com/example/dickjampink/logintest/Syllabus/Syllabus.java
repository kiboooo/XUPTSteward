package com.example.dickjampink.logintest.Syllabus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dickjampink.logintest.Fragment.AppealDialog;
import com.example.dickjampink.logintest.Fragment.LoginDialogFlagment;
import com.example.dickjampink.logintest.Listener.MylocationListener;
import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.Request.RequestWeather;
import com.example.dickjampink.logintest.Request.RequestZHJS;
import com.example.dickjampink.logintest.Request.RequsetZF;
import com.example.dickjampink.logintest.activity.CET4orCET6Activity;
import com.example.dickjampink.logintest.activity.MainActivity;
import com.example.dickjampink.logintest.activity.PYJHActivity;
import com.example.dickjampink.logintest.activity.WeatherActivity;
import com.example.dickjampink.logintest.adapter.CheckAdapter;
import com.example.dickjampink.logintest.adapter.CreditCollectAdapter;
import com.example.dickjampink.logintest.adapter.GradeAdapter;
import com.example.dickjampink.logintest.bean.CheckAttData;
import com.example.dickjampink.logintest.bean.CreditCollectData;
import com.example.dickjampink.logintest.bean.FloatingMenuData;
import com.example.dickjampink.logintest.bean.GradeCarData;
import com.example.dickjampink.logintest.bean.GradeData;
import com.example.dickjampink.logintest.bean.LoginData;
import com.example.dickjampink.logintest.bean.Syllabus_type;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.dickjampink.logintest.R.menu.syllabus;

public class Syllabus extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        LoginDialogFlagment.LoginInputListener,
        AppealDialog.AppealDialogInputListener
{


    public static final int SUCCESSFUL = 4;
    public static final int GETPICTURE = 3;
    public static final int FALL = 5;

    private final int APPEAL_SUCCESS = 1024;
    private final int APPEAL_FALL = 1023;

    private boolean LoginFlage = false;
    private GradeCarData gcd;
    private FloatingActionMenu ActionMenu;
    private ArrayList<FloatingActionButton> floatingActionButtons = new ArrayList<>();

    private Toolbar toolbar;
    private View contentSyllabus,contentChecking,contentPersonMsg,contentWebView,contentGradeView;
    private WebView wv;
    private LoginData logindata;
    private CircleImageView personImage_big;
    private CircleImageView personImage_small;
    private static ProgressDialog progressDialog;

    //两次Back退出的判断时间差的条件
    private long exitTime = 0;

    private final int w[][] = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, R.id.w11, R.id.w21, R.id.w31, R.id.w41, R.id.w51, R.id.w61, R.id.w71},
            {0, R.id.w12, R.id.w22, R.id.w32, R.id.w42, R.id.w52, R.id.w62, R.id.w72},
            {0, R.id.w13, R.id.w23, R.id.w33, R.id.w43, R.id.w53, R.id.w63, R.id.w73},
            {0, R.id.w14, R.id.w24, R.id.w34, R.id.w44, R.id.w54, R.id.w64, R.id.w74},
            {0, R.id.w15, R.id.w25, R.id.w35, R.id.w45, R.id.w55, R.id.w65, R.id.w75},
            {0, R.id.w16, R.id.w26, R.id.w36, R.id.w46, R.id.w56, R.id.w66, R.id.w76},
            {0, R.id.w17, R.id.w27, R.id.w37, R.id.w47, R.id.w57, R.id.w67, R.id.w77},
            {0, R.id.w18, R.id.w28, R.id.w38, R.id.w48, R.id.w58, R.id.w68, R.id.w78},
            {0, R.id.w19, R.id.w29, R.id.w39, R.id.w49, R.id.w59, R.id.w69, R.id.w79},
            {0, R.id.w110, R.id.w210, R.id.w310, R.id.w410, R.id.w510, R.id.w610, R.id.w710},
    };

    private final int Background[] = {
            R.drawable.syllabus_grid_type01, R.drawable.syllabus_grid_type02,
            R.drawable.syllabus_grid_type03, R.drawable.syllabus_grid_type04,
            R.drawable.syllabus_grid_type05, R.drawable.syllabus_grid_type06,
            R.drawable.syllabus_grid_type07, R.drawable.syllabus_grid_type08,
            R.drawable.syllabus_grid_type09, R.drawable.syllabus_grid_type10
    };

    private final int Week_back[] = {
            0, R.id.week7, R.id.week1, R.id.week2, R.id.week3, R.id.week4, R.id.week5, R.id.week6
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
                    TextView NOMsg = (TextView) findViewById(R.id.Lesson_NotMsg);
                    NOMsg.setVisibility(View.GONE);
                    setSyllabus();
                    break;
                case FALL:
                    break;
                /*申诉成功*/
                case APPEAL_SUCCESS:
                    Toast.makeText(Syllabus.this,"申请成功",Toast.LENGTH_SHORT).show();
                    break;
                /*申诉失败*/
                case APPEAL_FALL:
                    Toast.makeText(Syllabus.this,"该记录已被申请过，请等待审核!",Toast.LENGTH_SHORT).show();
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
        //获取数据表实例
        LitePal.getDatabase();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        logindata = new LoginData();
        logindata = DataSupport.findLast(LoginData.class);

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
        contentGradeView = findViewById(R.id.gradeview);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("成绩加载中...");
        progressDialog.setCancelable(false);

        /*
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

        //设置NavigationVime 的 menu 中的名字和学号。
        String temp = "学号：" + logindata.getStudentID();
        XH.setText(temp);
        Name.setText(logindata.getName());

        //加载天气状况
        setWeatherData(headerView);
        //加载今天是周几
        setWeek();
        //加载课程表信息,每次开学请输入开学日期格式：yyyy-MM-dd
        try {
            long HowManyWeeks = getWeak("2017-09-03");
            Log.e("HowManyWeeks", HowManyWeeks+"");
            RequestZHJS.getSyllabus(HowManyWeeks,mHandler);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //关闭Dialog
        MainActivity.LoginDialog.dismiss();

        //加载学生照片
        getPicture_person();
        //加载个人信息页
        setPersonMsg(logindata);
        //初始化 Floating Action Button AND Menu
        initFloatingActionButton();

    }

    public static long getWeak(String startDate) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date = sdf.parse(startDate);
        long start = date.getTime();
        long now = System.currentTimeMillis();
        long weakMS = 1000*60*60*24*7;
        long gone = now-start;
        int weeks =(int)(gone/weakMS);
        float weeksAsFloat = (float)gone/(float)weakMS;
        Log.e("getWeak", weeks + "   " + weeksAsFloat);
        if(weeksAsFloat>weeks)
        {
            weeks=weeks+1;
        }
        return weeks;
    }


    //实现了，需要两次点击Back
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_DOWN) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            if (System.currentTimeMillis() - exitTime > 2000) {
                    Snackbar.make(contentSyllabus, "再点一次退出哦...", Snackbar.LENGTH_SHORT)
                            .show();

                exitTime = System.currentTimeMillis();
            }else
            {
                finish();
                System.exit(0);
            }
            return true;

        } else {
            return super.onKeyDown(keyCode, event);
        }
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
        if (id == R.id.action_Change) {
            Snackbar.make(contentSyllabus,"温馨提示：确认注销当前账号吗？",Snackbar.LENGTH_SHORT)
                    .setAction("确认", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Syllabus.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        TextView webViewNotMsg = (TextView) findViewById(R.id.Webview_NotMsg);

        if (id == R.id.nav_syllabus) {

            toolbar.setTitle("课程表");
            contentChecking.setVisibility(View.GONE);
            contentSyllabus.setVisibility(View.VISIBLE);
            contentPersonMsg.setVisibility(View.GONE);
            contentWebView.setVisibility(View.GONE);
            contentGradeView.setVisibility(View.GONE);

        } else if (id == R.id.nav_checking) {

            toolbar.setTitle("考勤表");
            contentChecking.setVisibility(View.VISIBLE);
            contentSyllabus.setVisibility(View.GONE);
            contentPersonMsg.setVisibility(View.GONE);
            contentWebView.setVisibility(View.GONE);
            contentGradeView.setVisibility(View.GONE);
            initCheckingTable();

        } else if (id == R.id.nav_personmsg) {

            toolbar.setTitle("个人信息");
            contentChecking.setVisibility(View.GONE);
            contentSyllabus.setVisibility(View.GONE);
            contentPersonMsg.setVisibility(View.VISIBLE);
            contentWebView.setVisibility(View.GONE);
            contentGradeView.setVisibility(View.GONE);

        } else if (id == R.id.nav_library) {
            toolbar.setTitle("图书馆");
            contentChecking.setVisibility(View.GONE);
            contentSyllabus.setVisibility(View.GONE);
            contentPersonMsg.setVisibility(View.GONE);
            contentWebView.setVisibility(View.VISIBLE);
            contentGradeView.setVisibility(View.GONE);
            searchUrl(webViewNotMsg,"http://lib.xupt.edu.cn/wap/index.html#xylib_search_1");

        } else if (id == R.id.nav_PE) {
            toolbar.setTitle("体育部");
            contentChecking.setVisibility(View.GONE);
            contentSyllabus.setVisibility(View.GONE);
            contentPersonMsg.setVisibility(View.GONE);
            contentWebView.setVisibility(View.VISIBLE);
            contentGradeView.setVisibility(View.GONE);
            searchUrl(webViewNotMsg,"http://yddx.boxkj.com/wx/loginout");

        } else if (id == R.id.nav_checkscore) {
            toolbar.setTitle("查成绩");
            contentChecking.setVisibility(View.GONE);
            contentSyllabus.setVisibility(View.GONE);
            contentPersonMsg.setVisibility(View.GONE);
            contentWebView.setVisibility(View.GONE);
            contentGradeView.setVisibility(View.VISIBLE);
            if (!LoginFlage)
                showLoginDialog();
            else
                setGradeDisplay(gcd);
        } else if (id == R.id.nav_aboutour) {
            /*关于页*/
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            setAboutDialog();

        }else if (id == R.id.nav_check4or6) {
            /*查4-6级成绩*/
            Intent intent = new Intent(Syllabus.this, CET4orCET6Activity.class);
            startActivity(intent);

        } else if (id == R.id.nav_cancel) {
            /*注销页*/
            Snackbar.make(contentSyllabus,"温馨提示：确认注销当前账号吗？",Snackbar.LENGTH_SHORT)
                    .setAction("确认", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Syllabus.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).show();
        }

        //手动关闭抽屉
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //考勤表的ViewPager和Tablayout的联动显示界面
    private void initCheckingTable() {
        ViewPager viewPager = (ViewPager) contentChecking.findViewById(R.id.CheckMain);
        CheckAdapter adapter = new CheckAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) contentChecking.findViewById(R.id.CheckTitle);
        tabLayout.setupWithViewPager(viewPager);
    }

    //获取个人信息页的
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

    //利用数据库的数据，建立课程格子
    private void setSyllabus() {
        List<Syllabus_type> texts = DataSupport.findAll(Syllabus_type.class);
        String ui_control;
        int rand_background = 0;
        for (Syllabus_type text : texts) {
            int[] time_base = syllabus_time(text.getJT_NO());
            if (text.getBackground() == -1) {
                text.setBackground(rand_background);
                for (Syllabus_type up : texts) {
                    if (up.getS_Name().compareTo(text.getS_Name()) == 0) {
                        up.setBackground(rand_background);
                    }
                }
                rand_background++;
                Log.e("The table is ：：：", text.getS_Name() + "   " + text.getBackground() + "  " + rand_background);
            }
            for (int aTime_base : time_base) {
                if (aTime_base % 2 == 0)
                    ui_control = text.getTeach_Name() + "\n" + text.getRoomNum();
                else
                    ui_control = text.getS_Name();
                TextView w_view = (TextView) findViewById(w[aTime_base][text.getWeekNum()]);
                w_view.setTextColor(Color.WHITE);
                w_view.setText(ui_control);
                w_view.setBackgroundResource(Background[text.getBackground()]);
            }
        }
    }

    //设置个人信息页数据
    private void setPersonMsg(LoginData ld) {
        TextView ps_name = (TextView) findViewById(R.id.person_name);
        TextView ps_xuehao = (TextView) findViewById(R.id.person_xuehao);
        TextView ps_xueyuan = (TextView) findViewById(R.id.person_xueyuan);
        TextView ps_banji = (TextView) findViewById(R.id.person_banji);
        TextView ps_zhuanye = (TextView) findViewById(R.id.person_zhuanye);
        ps_name.setText(ld.getName());
        ps_banji.setText(ld.getBJ());
        ps_xuehao.setText(ld.getStudentID());
        ps_xueyuan.setText(ld.getXYname());
        ps_zhuanye.setText(ld.getZYName());
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

    //设置今天是周几的指示器
    private void setWeek() {
        Calendar c = Calendar.getInstance();
        TextView week_back = (TextView) findViewById(Week_back[c.get(Calendar.DAY_OF_WEEK)]);
        week_back.setBackgroundResource(R.drawable.syllabus_grid_type07);
    }

    @Override
    public void finish() {
        super.finish();
        DataSupport.deleteAll(Syllabus_type.class);
        wv.clearCache(true);
        wv.clearHistory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataSupport.deleteAll(Syllabus_type.class);

    }

    //初始化 FloatingActionButton 和 Menu
    private void initFloatingActionButton()  {
        ActionMenu = (FloatingActionMenu) findViewById(R.id.ActionMenu);
        ActionMenu.setClosedOnTouchOutside(true);
        ActionMenu.showMenu(true);
        floatingActionButtons.add((FloatingActionButton) contentGradeView.findViewById(R.id.XFControl));
        floatingActionButtons.add((FloatingActionButton) contentGradeView.findViewById(R.id.PYJH));
        floatingActionButtons.add((FloatingActionButton) contentGradeView.findViewById(R.id.session1));
        floatingActionButtons.add((FloatingActionButton) contentGradeView.findViewById(R.id.session2));
        floatingActionButtons.add((FloatingActionButton) contentGradeView.findViewById(R.id.session3));
        floatingActionButtons.add((FloatingActionButton) contentGradeView.findViewById(R.id.session4));
        floatingActionButtons.add((FloatingActionButton) contentGradeView.findViewById(R.id.session5));
        floatingActionButtons.add((FloatingActionButton) contentGradeView.findViewById(R.id.session6));
        floatingActionButtons.add((FloatingActionButton) contentGradeView.findViewById(R.id.session7));
        floatingActionButtons.add((FloatingActionButton) contentGradeView.findViewById(R.id.session8));
        for (int i = 0; i < floatingActionButtons.size(); i++) {
            floatingActionButtons.get(i).hideButtonInMenu(true);
        }
    }

    //建立Grade所需要的数据
    private GradeCarData initGrade(String GradeBody) {
        Document document = Jsoup.parse(GradeBody);
        Elements elements = document.select("table#Datagrid1.datelist > tbody > tr");
        String title = document.select("font").text();
        Log.e("initGrade.lenght ", elements.size() + "");
        Log.e("initGrade ", elements.toString());
        Log.e("initGrade ", title);
        gcd = new GradeCarData();
        gcd.setCheckTitle(title);
        for (int i = 1; i < elements.size(); i++) {
            GradeData gd = new GradeData();
            gd.setClassID(elements.get(i).select("td").get(2).text());
            gd.setClassNAME(elements.get(i).select("td").get(3).text());
            gd.setClassCREDIT(Double.parseDouble(elements.get(i).select("td").get(6).text()));
            gd.setClassNATURE(elements.get(i).select("td").get(4).text());
            gd.setGrade(elements.get(i).select("td").get(8).text());
            gd.setGPA(Double.parseDouble(elements.get(i).select("td").get(7).text()));
            gcd.AddGradeArray(gd);
        }
        return gcd;
    }

    //建立成绩显示
    private void setGradeDisplay(GradeCarData GCD) {

        TextView credit_num = (TextView) findViewById(R.id.credit_num);
        TextView GPA_num = (TextView) findViewById(R.id.GPA_num);
        TextView Class_num = (TextView) findViewById(R.id.Class_num);

        credit_num.setText(GCD.getAllCredit());
        GPA_num.setText(GCD.getAllGPA());
        Class_num.setText(String.valueOf(GCD.getAllClass()));
        toolbar.setTitle(GCD.getCheckTitle());


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.GradeRecyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        GradeAdapter adapter = new GradeAdapter(GCD.getGradeArray());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                /*
                dy > 0 时为手指向上滚动,列表滚动显示下面的内容
                dy < 0 时为手指向下滚动,列表滚动显示上面的内容
                */
                if (dy > 0) {
                    //隐藏菜单按钮
//                    ActionMenu.hideMenu(true);
                    ActionMenu.hideMenuButton(true);
                } else {
                    //显示菜单按钮
//                    ActionMenu.showMenu(true);
                    ActionMenu.showMenuButton(true);

                }
            }
        });
        LoginFlage = true;
        progressDialog.dismiss();
        ActionMenu.showMenu(true);

    }

    private ArrayList<CreditCollectData> initCreditCollect(String CreditBody) {
        ArrayList<CreditCollectData> CCD = new ArrayList<>();
        Document document = Jsoup.parse(CreditBody);
        Elements elements = document.select("table#Datagrid2.datelist > tbody > tr");
        Log.e("initCreditCollect", elements.toString() + "   " + elements.size());
        for (int i = 1; i < elements.size(); i++) {
            CreditCollectData ccd = new CreditCollectData();
            ccd.setClassname(elements.get(i).select("td").get(0).text());
            ccd.setCreditRequire(elements.get(i).select("td").get(1).text());
            ccd.setCreditGet(elements.get(i).select("td").get(2).text());
            ccd.setCreditNot(elements.get(i).select("td").get(3).text());
            ccd.setCreditNeed(elements.get(i).select("td").get(4).text());
            CCD.add(ccd);
        }
        return CCD;
    }

    //显示学分统计的内容
    private void setCreditCollectDisplay(ArrayList<CreditCollectData> CCD) {

        TextView credit_num = (TextView) findViewById(R.id.credit_num);
        TextView GPA_num = (TextView) findViewById(R.id.GPA_num);
        TextView Class_num = (TextView) findViewById(R.id.Class_num);

        credit_num.setText("");
        GPA_num.setText("");
        Class_num.setText("");
        toolbar.setTitle("学分统计");


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.GradeRecyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        CreditCollectAdapter adapter = new CreditCollectAdapter(CCD);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                /*
                dy > 0 时为手指向上滚动,列表滚动显示下面的内容
                dy < 0 时为手指向下滚动,列表滚动显示上面的内容
                */
                if (dy > 0) {
                    //隐藏菜单按钮
                    ActionMenu.hideMenuButton(true);
                } else {
                    //显示菜单按钮
                    ActionMenu.showMenuButton(true);

                }
            }
        });
        LoginFlage = true;
        progressDialog.dismiss();
        ActionMenu.showMenu(true);

    }

    //设定FloatingMenu的数据
    private FloatingMenuData initFMD(String MenuBody) {
        Document document = Jsoup.parse(MenuBody);
        Elements elements = document.select("select#ddlXN > option");
        FloatingMenuData fmd = new FloatingMenuData();
        fmd.setSessionNumber(elements.size());
        for (int i = 1; i < elements.size(); i++) {
            fmd.AddSessionYears(elements.get(i).val());
        }
        return fmd;
    }

    //设置按钮的显示以及，为按钮设置点击事件。
    private void setFloatingActionButton(final FloatingMenuData FMD) {
        Log.e("getSessionNumber", "" + FMD.getSessionNumber());
        for (int i = 0; i <FMD.getSessionNumber()+1 ; i++) {
            floatingActionButtons.get(i).setVisibility(View.VISIBLE);
            if (i == 0) {
                //成绩统计
                floatingActionButtons.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActionMenu.hideMenu(true);
                        progressDialog.show();
                        RequsetZF.CheckGradeRequset("&ddlXN=", "&ddlXQ=",
                                "&Button1=%B3%C9%BC%A8%CD%B3%BC%C6", new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setGradeDisplay(gcd);
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String Body = response.body().string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setCreditCollectDisplay(initCreditCollect(Body));
                                    }
                                });
                            }
                        });
                    }
                });
            } else if (i == 1) {
                floatingActionButtons.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Syllabus.this, PYJHActivity.class);
                        startActivity(intent);
                    }
                });

            } else {
                final int finalI = FMD.getSessionNumber() - i+1;
                floatingActionButtons.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActionMenu.hideMenu(true);
                        progressDialog.show();
                        Log.e("CheckGradeRequset", FMD.getSessionYears(finalI - 1) + "   " + (finalI % 2 + 1));
                        RequsetZF.CheckGradeRequset("&ddlXN=" + FMD.getSessionYears(finalI - 1), "&ddlXQ=" + (finalI % 2 + 1),
                                "&btn_xq=%D1%A7%C6%DA%B3%C9%BC%A8", new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e("floatingActionButtons", "onFailure");
                                                setGradeDisplay(gcd);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        final String Body = response.body().string();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e("floatingActionButtons", Body.substring(Body.length() - 3000));
                                                setGradeDisplay(initGrade(Body));
                                            }
                                        });
                                    }
                                });
                    }
                });
            }
        }
    }

    //关于的Dialog显示内容
    private void setAboutDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater
                .inflate(R.layout.about_dialog, (ViewGroup) findViewById(R.id.dialog));
        TextView Github = (TextView) dialog.findViewById(R.id.github);
        TextView Email = (TextView) dialog.findViewById(R.id.email);
        AlertDialog.Builder builder = new AlertDialog.Builder(Syllabus.this);
        String git = "GitHub：" + "https://github.com/kiboooo";
        String email = "Email ： " + "www.kiboooo78@gmail.com";
        builder.setView(dialog);
        Github.setText(git);
        Email.setText(email);
        builder.show();
    }

    //调用登陆的 dialog 对话框
    private void showLoginDialog() {
        LoginDialogFlagment dialog = new LoginDialogFlagment();
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), "logindialog");
    }

    //Dialog登录页通过接口 返回给Activity来处理数据
    @Override
    public void onLoginInputComplete(String username, String password, String check_number) {
        RequsetZF.sendLogin(username, password, check_number, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(getBaseContext(),
                                "登录出错，请核对登录信息！", Toast.LENGTH_LONG).show();
                        showLoginDialog();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (response.headers().size() == 10 && response.code() == 200) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.show();
                                Calendar calendar = Calendar.getInstance();
                                //初始申请的成绩，为上一个学期的成绩。
                                String ddlXQ;
                                String ddlXN = "&ddlXN=" + (calendar.get(Calendar.YEAR) - 1)
                                        + "-" + (calendar.get(Calendar.YEAR));
                                Log.e("calendar", calendar.get(Calendar.MONTH) + "--" + calendar.get(Calendar.DAY_OF_MONTH));
                                if(calendar.get(Calendar.MONTH) + 1 > 7)
                                    ddlXQ = "&ddlXQ=2";
                                else if (calendar.get(Calendar.MONTH) + 1 == 7 && calendar.get(Calendar.DAY_OF_MONTH) > 17) {
                                    ddlXQ = "&ddlXQ=2";
                                } else {
                                    ddlXQ = "&ddlXQ=1";
                                }
                                String select = "&btn_xq=%D1%A7%C6%DA%B3%C9%BC%A8";
                                RequsetZF.CheckGradeRequset(ddlXN, ddlXQ, select, new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onResponse(Call call, final Response response)
                                            throws IOException {
                                        if (response.isSuccessful()) {
                                            final String Gradebody = response.body().string();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    setFloatingActionButton(initFMD(Gradebody));
                                                    setGradeDisplay(initGrade(Gradebody));
                                                }
                                            });

                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        Log.e("loginzf false  ", response.body().string());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(getBaseContext(),
                                        "登录出错，请核对登录信息！", Toast.LENGTH_LONG).show();
                                showLoginDialog();
                            }
                        });

                    }
                }
            }
        });

    }
    //检测URl是否有效。
    private void searchUrl(final TextView webViewNotMsg, final String Url)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Request request = new Request.Builder()
                        .url(Url)
                        .build();

                Call call = new OkHttpClient().newCall(request);
                try {
                    int returnNumber = call.execute().code();
                    Log.e("searchUrl", "   " + returnNumber);
                    if (returnNumber==200){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                webViewNotMsg.setVisibility(View.GONE);
                                wv.getSettings().setJavaScriptEnabled(true);
                                wv.setWebViewClient(new WebViewClient());
                                wv.loadUrl(Url);
                            }
                        });
                    }else runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webViewNotMsg.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //设置天气的显示，以及点击温度页的跳转。
    private void setWeatherData(View headerView) {
        try {
            final TextView Temperature = (TextView) headerView.findViewById(R.id.WenduDisplay);
            TextView TemperatureLocation = (TextView) headerView.findViewById(R.id.WenduLocal);
            TemperatureLocation.setText(MylocationListener.LocationName);
            final String CSDM = RequestWeather.GetCityCode(getApplicationContext(),
                    MylocationListener.LocationName,MylocationListener.LocationCityName);
            final String url = "http://wx.weather.com.cn/mweather/"
                    + CSDM + ".shtml#1";

            Log.e("setWeatherData", url);
            Log.e("setWeatherData", MylocationListener.LocationName);
            Log.e("setWeatherData", MylocationListener.LocationCityName);
            Log.e("setWeatherData", MylocationListener.LocationRequest);
            //天气按钮的监听
            Temperature.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Syllabus.this, WeatherActivity.class);
                    intent.putExtra("URL", url);
                    startActivity(intent);
                }
            });
            RequestWeather.GetTemperature(MylocationListener.LocationRequest, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                            JSONArray jsonArray = new JSONObject(response.body().string())
                                    .getJSONArray("results");
                            Log.e("GetTemperature", jsonArray.get(0).toString());
                            final String temperature = jsonArray.getJSONObject(0).getJSONObject("now")
                                    .getString("temperature")+"º";
                            Log.e("GetTemperature", temperature);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Temperature.setText(temperature);
                                    Log.e("setWeatherData", temperature);
                                }
                            });
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
            Log.e("setWeatherData", e.toString());
        }

    }


    /*考勤表申述逻辑处理*/
    @Override
    public void onAppealDialogInputComplete(String RemarkMSG, CheckAttData checkAttData) {
//        Toast.makeText(this,
//                "RemarkMSG= "+RemarkMSG+"checkAttData= "+checkAttData.toStirng(),
//                Toast.LENGTH_SHORT).show();
        RequestZHJS.AppealRequset(RemarkMSG, checkAttData, mHandler, APPEAL_SUCCESS, APPEAL_FALL);
    }
}
