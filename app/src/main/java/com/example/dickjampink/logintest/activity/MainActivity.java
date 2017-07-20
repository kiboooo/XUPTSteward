package com.example.dickjampink.logintest.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.Request.RequestZHJS;
import com.example.dickjampink.logintest.Syllabus.Syllabus;
import com.example.dickjampink.logintest.bean.LoginData;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import okhttp3.MediaType;

import static com.example.dickjampink.logintest.R.id.account;
import static com.example.dickjampink.logintest.R.id.password;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int  GETPICTURE = 0;
    private static final int  FALL = 2;
    private static final int LOGIN_FALL = 3;
    private static final int LOGIN_SUCCESS = 4;

    private SharedPreferences pref;


    private EditText accountEdit;
    private EditText passwordEdit;
    private EditText authcodeEdit;

    TextInputLayout accountLayout = null;
    TextInputLayout passwordLayout = null ;
    TextInputLayout authcodeLayout= null;

    private CheckBox rememberPass;
    private ImageView image_auth;

    public static MediaType JSON ;

    public  Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GETPICTURE:
                    //完成主界面更新,拿到数据
                    Bitmap bm = (Bitmap) msg.obj;
                    image_auth.setImageBitmap(bm);
                    break;
                case LOGIN_SUCCESS:
                    //若登录成功，则加载下一个页
                    byte[] u_p = (byte[])msg.obj;
                    String base = new String(u_p);
                    int i = base.indexOf(' ');
                    final String Account = base.substring(0, i);
                    final String Password = base.substring(i+1, base.length());
                    Intent intent = new Intent(MainActivity.this, Syllabus.class);
                    //利用 SharedPreferences 储存方式，存放用户名和密码。
                    SharedPreferences.Editor editor = pref.edit();
                    //判断 CheakBox是否有确认。
                    if (rememberPass.isChecked()) {
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", Account);
                        editor.putString("password", Password);

                    } else {
                        //若是没有打钩，就把之前压入editor的数据清空
                        editor.clear();
                    }
                    editor.apply();
                    startActivity(intent);
                    break;
                case FALL:
                    Toast.makeText(MainActivity.this, "网络出现了问题", Toast.LENGTH_SHORT).show();
                    RequestZHJS.getPicture(mHandler);
                    break;
                case LOGIN_FALL:
                    if (accountEdit.length()==0)
                        accountLayout.setError("学号不能为空！");
                    else if(accountEdit.length()<8 || accountEdit.length()>8)
                        accountLayout.setError("学号输入有误！");
                    if(passwordEdit.length()==0)
                        passwordLayout.setError("密码不能为空！");
                    if(authcodeEdit.length() == 0)
                        authcodeLayout.setError("验证码不能为空！");
                    Toast.makeText(MainActivity.this, "输入信息错误，请核对", Toast.LENGTH_SHORT).show();
                    RequestZHJS.getPicture(mHandler);
                    break;
                default:
                    break;
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("llll: ", "返回后调用的是这个！！");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /**
         * 得到SharedPreferences对象的一种方法：
         *  PreferenceManager类中的getDefaultSharedPreferences（）方法
         *          这是一个静态方法，他接收一个Context参数，并自动使用当前应用程序的包名作为前缀来命名SharedPreferences文件
         *          得到了SharedPreferences对象之后，就可以开始向SharedPreferences文件中储存数据了。
         *          分三步：
         *          1.调用SharedPreferences对象的edit（）方法来获取一个 SharedPreferences.Editor对象。
         *          2.向 SharedPreferences.Editor 对象中添加数据，比如添加一个布尔型数据就使用 putBoolean（）方法，
         *            添加一个字符串则使用putString（）等；
         *          3. 调用apply（）方法将添加的数据提交，从而完成数据的存储操作。
         */
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        /**
         * 这里使用了SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION，表示会让应用的主体内容占用系统导航栏的空间，
         * 然后又调用了setNavigationBarColor()方法将导航栏设置成透明色。
         *
         * * 在布局文件中设置完毕后，还需要在onCreate()里面加上如下的代码：
         * 设置状态栏颜色为透明：getWindow().setStatusBarColor(Color.TRANSPARENT);
         * 设置状态栏和APP的位置关系：
         * getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
         * | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
         * setSystemUiVisibility(int visibility)传入的实参类型如下：

            SYSTEM_UI_FLAG_FULLSCREEN： 隐藏状态栏
            SYSTEM_UI_FLAG_HIDE_NAVIGATION： 隐藏导航栏
            SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN： Activity全屏显示，但是状态栏不会被覆盖掉，让状态栏上浮在Activity上
            SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION： 让导航栏悬浮在Activity上
            SYSTEM_UI_FLAG_LAYOUT_STABLE： 保持整个View的稳定，使其不会随着SystemUI的变化而变化；
            SYSTEM_UI_FLAG_IMMERSIVE：沉浸模式；
            SYSTEM_UI_FLAG_IMMERSIVE_STICKY：沉浸模式且状态栏和导航栏出现片刻后会自动隐藏；
         */
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        getSupportActionBar().hide();

        accountLayout = (TextInputLayout) findViewById(R.id.input_user);
        passwordLayout = (TextInputLayout) findViewById(R.id.input_password);
        authcodeLayout = (TextInputLayout) findViewById(R.id.auth_code);


        accountEdit = (EditText) findViewById(account);

        passwordEdit = (EditText) findViewById(password);

        authcodeEdit = (EditText) findViewById(R.id.authCode);


        image_auth = (ImageView) findViewById(R.id.authImage);

        rememberPass = (CheckBox) findViewById(R.id.remenber_password);

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        image_auth.setOnClickListener(this);

        boolean isRemember = pref.getBoolean("remember_password", false);

        if (isRemember) {
            //用于将账号和密码都设置到文本框中：
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");

            accountEdit.setText(account);
            passwordEdit.setText(password);

            rememberPass.setChecked(true);
        }

        //利用 LitePal.getDatabase() 数据库来储存数据，下面是数据库的创建
        LitePal.getDatabase();
        }

        //设置点击事件：
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                RequestZHJS.sendRequest(mHandler,accountEdit,passwordEdit,authcodeEdit);
                break;

            case R.id.authImage:
                RequestZHJS.getPicture(mHandler);
                break;

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //返回登录界面后，清除数据库的信息
        DataSupport.deleteAll(LoginData.class);
        RequestZHJS.getPicture(mHandler);
    }

}
