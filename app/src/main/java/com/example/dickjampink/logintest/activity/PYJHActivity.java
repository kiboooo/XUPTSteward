package com.example.dickjampink.logintest.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.Request.RequsetZF;
import com.example.dickjampink.logintest.adapter.TestStackAdapter;
import com.example.dickjampink.logintest.bean.PYJHData;
import com.loopeer.cardstack.CardStackView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kiboooo on 2017/7/27.
 */

public class PYJHActivity extends AppCompatActivity implements CardStackView.ItemExpendListener{

    public static Integer[] ItemColor = new Integer[]{
            R.color.item1,
            R.color.item2,
            R.color.item3,
            R.color.item4,
            R.color.item5,
            R.color.item6,
            R.color.item7,
            R.color.item8,
    };

    public static final int PYJH_OVER = 111;
    public static final int PYJH_ADD = 222;

    private CardStackView mStackView;
    private TestStackAdapter mTestStackAdapter;
    public static ProgressDialog PYJHDialog;
    public static List<List<PYJHData>> pList = new ArrayList<>();

    public Handler handler_pyjh = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
           switch (msg.what) {
               case PYJH_ADD:
               {
                   pList.add((List<PYJHData>) msg.obj);
               }
                   break;
                case PYJH_OVER:
                {
                    new Handler().postDelayed(
                            new Runnable() {
                                @Override
                                public void run() {
                                    PYJHDialog.dismiss();
                                    Log.e("GetPYJHList", "进入了Handler");
                                    Log.e("GetPYJHList", "" + pList.size()+"   "+pList.get(0).get(8).getClassName());
                                    mTestStackAdapter = new TestStackAdapter(getBaseContext());
                                    mStackView.setAdapter(mTestStackAdapter);
                                    mStackView.setItemExpendListener(PYJHActivity.this);
                                    mTestStackAdapter.updateData(Arrays.asList(ItemColor));
                                }
                            }, 200);
                }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardstack_pyjh);

        Toolbar toolbar = (Toolbar) findViewById(R.id.PYJHToolBar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mStackView = (CardStackView) findViewById(R.id.pyjh_stackview);

        PYJHDialog = new ProgressDialog(this);
        PYJHDialog.setMessage("培养计划加载中...");
        PYJHDialog.setCancelable(false);
        PYJHDialog.show();

        if (pList.isEmpty()){
            RequsetZF.getPYJHViewState(handler_pyjh, mTestStackAdapter);
        }else{
            mTestStackAdapter = new TestStackAdapter(getBaseContext());
            mStackView.setAdapter(mTestStackAdapter);
            mStackView.setItemExpendListener(this);
            new Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            PYJHDialog.dismiss();
                            mTestStackAdapter.updateData(Arrays.asList(ItemColor));
                        }
                    }, 200);
        }

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

    @Override
    public void onItemExpend(boolean expend) {

    }

}
