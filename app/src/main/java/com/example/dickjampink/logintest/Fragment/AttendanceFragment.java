package com.example.dickjampink.logintest.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.adapter.CheckAttAdapter;
import com.example.dickjampink.logintest.bean.RequestKQBody;

/**
 * Created by Kiboooo on 2017/7/14.
 *
 */

public class AttendanceFragment extends Fragment {

    public static final String TAB_PAGE = "tab_page";

    private CheckAttAdapter adapter;

    private int mPage;
    public static AttendanceFragment newInstance(int type){
        Bundle args = new Bundle();
        args.putInt(TAB_PAGE, type);
        AttendanceFragment fragment = new AttendanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(TAB_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        if (mPage == 2){
            view = inflater.inflate(R.layout.content_checking, container, false);

        }else if (mPage == 1)
        {
            view = inflater.inflate(R.layout.content_checking_detail, container, false);
            Button checkButton = (Button) view.findViewById(R.id.checkButton);
            final RequestKQBody requestKQBody = new RequestKQBody();
            final Spinner spinner = (Spinner) view.findViewById(R.id.CheckSpinner);
            final CheckBox normalCB = (CheckBox) view.findViewById(R.id.normal);
            final CheckBox abnormalCB = (CheckBox) view.findViewById(R.id.abnormal);
            final CheckBox lateCB = (CheckBox) view.findViewById(R.id.late);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    requestKQBody.setWaterDate(i+1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            checkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    initCheckBoxControl(spinner, normalCB, lateCB, abnormalCB,requestKQBody);
                    requestKQBody.setPage("1");
                    requestKQBody.setRows("10");
                    /*未完成*/
//                    initCheckAttAdapter(view, requestKQBody);
                }
            });
        }
        return view;
    }

    //获取请求的Body
    private void initCheckBoxControl(Spinner spinner,CheckBox normal, CheckBox late, CheckBox abnormal,RequestKQBody RKQB) {
        if (normal.isChecked()) {
            RKQB.setFlag("1");
        }
        if (late.isChecked())
        {
            if (RKQB.getFlag().length() > 0) {
                RKQB.setFlag("1a2");
            } else RKQB.setFlag("2");
        }
        if (abnormal.isChecked()) {
            if (RKQB.getFlag().length() == 0) {
                RKQB.setFlag("3");
            } else
            if (normal.isChecked()|| late.isChecked()) {
                if (!late.isChecked()) {
                    RKQB.setFlag("1a3");
                } else if (!normal.isChecked()) {
                    RKQB.setFlag("2a3");
                } else RKQB.setFlag("1a2a3");
            }
        }
        Log.e("WaterDate", RKQB.getWaterDate());
        Log.e("Flag", RKQB.getFlag());
        Log.e("Status", RKQB.getStatus());
    }

    public void initCheckAttAdapter(final View view, final RequestKQBody RKQB ){
        //请求考勤表的数据,数据请求完毕后，利用runOnUiThread在UI线程中进行UI加载。
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //未实例化Adapter
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.check_display);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);
            }
        });
    }

}
