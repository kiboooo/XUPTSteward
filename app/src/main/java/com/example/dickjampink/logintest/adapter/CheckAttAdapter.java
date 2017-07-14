package com.example.dickjampink.logintest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.bean.CheckAttData;

import java.util.List;

/**
 * Created by Kiboooo on 2017/7/14.
 */

public abstract class CheckAttAdapter extends  RecyclerView.Adapter<CheckAttAdapter.ViewHolder> {
    private List<CheckAttData> mCAtt;//考勤表查询信息列表


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //数据出来后。这个要改 R.layout.att_table_type,
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.att_table_type, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CheckAttData attendance = mCAtt.get(position);
        //需要在这里设置数据到到View
    }

    @Override
    public int getItemCount() {
        return mCAtt.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ClassName;
        TextView Attend;
        TextView Late;
        TextView Absent;

        public ViewHolder(View itemView) {
            super(itemView);
            ClassName = (TextView) itemView.findViewById(R.id.className);
            Attend = (TextView) itemView.findViewById(R.id.chuxi);
            Late = (TextView) itemView.findViewById(R.id.chidao);
            Absent = (TextView) itemView.findViewById(R.id.quexi);
        }
    }

    public CheckAttAdapter(List<CheckAttData> CAtt) {
        mCAtt = CAtt;
    }
}
