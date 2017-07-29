package com.example.dickjampink.logintest.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dickjampink.logintest.Fragment.CET4orCET6LoginFlagment;
import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.Request.RequestCET4orCET6;
import com.example.dickjampink.logintest.bean.CET4orCET6Data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Kiboooo on 2017/7/28.
 */

public class CET4orCET6Activity extends AppCompatActivity implements CET4orCET6LoginFlagment.Cet4OrCet6LoginInputListener {

    public static ProgressDialog cProgressDialog;
    private TextView CheckName,CheckSchool,grade,
            checknumber,checkgrade,hearGrade,
            readGrade,writerGrade,sayNumber, sayGrade;

    @Override
    protected void onStart() {
        super.onStart();
        showLoginDialog();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check4or6);
        Toolbar toolbar = (Toolbar) findViewById(R.id.CETToolBar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        cProgressDialog = new ProgressDialog(this);
        cProgressDialog.setMessage("成绩加载中...");
        cProgressDialog.setCancelable(false);

        CheckName = (TextView) findViewById(R.id.Name);
        CheckSchool = (TextView) findViewById(R.id.school);
        grade = (TextView) findViewById(R.id.grade);
        checknumber = (TextView) findViewById(R.id.checknumber);
        checkgrade = (TextView) findViewById(R.id.checkgrade);
        hearGrade = (TextView) findViewById(R.id.hearGrade);
        readGrade = (TextView) findViewById(R.id.readGrade);
        writerGrade = (TextView) findViewById(R.id.writerGrade);
        sayNumber = (TextView) findViewById(R.id.sayNumber);
        sayGrade = (TextView) findViewById(R.id.sayGrade);

//        showLoginDialog();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //调用登陆的 dialog 对话框
    private void showLoginDialog() {
        CET4orCET6LoginFlagment dialog = new CET4orCET6LoginFlagment();
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), "logindialog");
    }

    //Dialog登录页通过接口 返回给Activity来处理数据
    @Override
    public void onCLoginInputComplete(String username, String number) {
        cProgressDialog.show();
        RequestCET4orCET6.CheckCET4orCET6(username, number, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cProgressDialog.dismiss();
                        Toast.makeText(getBaseContext(),
                                "查询出错，请核对准考证和姓名信息！", Toast.LENGTH_LONG).show();
                        showLoginDialog();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()&& response.code() == 200) {
                    String body = response.body().string();
                    Document document = Jsoup.parse(body);
                    String check = document.select("th.td_title").text();
                    Log.e("CheckCET4orCET6", check+"   "+check.compareTo("笔试成绩 口试成绩"));
                    if (check != "" && check != null && check.equals("笔试成绩 口试成绩")) {
                        final CET4orCET6Data cet4orCET6Data = new CET4orCET6Data();
                        Elements elements = document.select("table.cetTable > tbody > tr");
                        cet4orCET6Data.setCheckName(elements.get(0).select("td").text().replaceAll("\\s*", ""));
                        cet4orCET6Data.setCheckSchool(elements.get(1).select("td").text().replaceAll("\\s*", ""));
                        cet4orCET6Data.setGrade(elements.get(2).select("td").text().replaceAll("\\s*", ""));
                        cet4orCET6Data.setChecknumber(elements.get(4).select("td").text().replaceAll("\\s*", ""));
                        cet4orCET6Data.setCheckgrade(elements.get(5).select("span.colorRed").text().replaceAll("\\s*", ""));
                        cet4orCET6Data.setHearGrade(elements.get(6).select("td").get(1).text().replaceAll("\\s*", ""));
                        cet4orCET6Data.setReadGrade(elements.get(7).select("td").get(1).text().replaceAll("\\s*", ""));
                        cet4orCET6Data.setWriterGrade(elements.get(8).select("td").get(1).text().replaceAll("\\s*", ""));
                        cet4orCET6Data.setSayNumber(elements.get(10).select("td").text().replaceAll("\\s*", ""));
                        cet4orCET6Data.setSayGrade(elements.get(11).select("td").text().replaceAll("\\s*", ""));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initCET4orCET6(cet4orCET6Data);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cProgressDialog.dismiss();
                                Toast.makeText(getBaseContext(),
                                        "查询出错，请核对准考证和姓名信息！", Toast.LENGTH_LONG).show();
                                showLoginDialog();
                            }
                        });

                    }
                }
            }
        });
    }

    private void initCET4orCET6(CET4orCET6Data cet4orCET6Data) {
        cProgressDialog.dismiss();
        CheckName.setText(cet4orCET6Data.getCheckName());
        CheckSchool.setText(cet4orCET6Data.getCheckSchool());
        grade.setText(cet4orCET6Data.getGrade());
        checknumber.setText(cet4orCET6Data.getChecknumber());
        checkgrade.setText(cet4orCET6Data.getCheckgrade());
        hearGrade.setText(cet4orCET6Data.getHearGrade());
        readGrade.setText(cet4orCET6Data.getReadGrade());
        writerGrade.setText(cet4orCET6Data.getWriterGrade());
        sayNumber.setText(cet4orCET6Data.getSayNumber());
        sayGrade.setText(cet4orCET6Data.getSayGrade());
    }
}
