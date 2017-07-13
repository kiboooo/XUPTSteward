package com.example.dickjampink.logintest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.bean.AttendanceData;

import java.util.List;

/**
 * Created by DickJampink on 2017/5/23.
 */

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
    private List<AttendanceData> mAttendance;//考勤表信息List数组


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.att_table_type, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AttendanceData attendance = mAttendance.get(position);
        holder.ClassName.setText(attendance.getClassName());
        holder.Attend.setText(attendance.getAttendProbability());
        holder.Absent.setText(attendance.getAbsenceProbability());
        holder.Late.setText(attendance.getLateProbability());

    }

    @Override
    public int getItemCount() {
        return mAttendance.size();
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

    public AttendanceAdapter(List<AttendanceData> AttendanceList) {
        mAttendance = AttendanceList;
    }

}
