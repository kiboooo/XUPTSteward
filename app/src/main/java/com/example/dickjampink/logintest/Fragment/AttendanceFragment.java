package com.example.dickjampink.logintest.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.adapter.CheckAttAdapter;

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
        if (mPage == 1) {
            initCheckAttAdapter();
        }
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
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.check_display);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    public void initCheckAttAdapter() {
        //请求考勤表的数据
    }
}
