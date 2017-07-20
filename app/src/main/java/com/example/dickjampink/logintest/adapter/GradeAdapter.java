package com.example.dickjampink.logintest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.bean.GradeData;

import java.util.ArrayList;

/**
 * Created by Kiboooo on 2017/7/17.
 *
 */

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.ViewHolder> {

    private ArrayList<GradeData> GD;

    public GradeAdapter (ArrayList<GradeData> GD) {
        this.GD = GD;
    }

    @Override
    public GradeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GradeAdapter.ViewHolder holder, int position) {
        GradeData gradeData = GD.get(position);
        holder.ClassID.setText(gradeData.getClassID());
        holder.ID_GPA.setText(String.valueOf(gradeData.getGPA()));
        holder.Class_name.setText(gradeData.getClassNAME());
        holder.Grade.setText(String.valueOf(gradeData.getGrade()));
        holder.ID_Credit.setText(String.valueOf(gradeData.getClassCREDIT()));
        holder.ID_Nature.setText(gradeData.getClassNATURE());
    }

    @Override
    public int getItemCount() {
        return GD.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ClassID;
        TextView ID_GPA;
        TextView Class_name;
        TextView Grade;
        TextView ID_Nature;
        TextView ID_Credit;

        public ViewHolder(View itemView) {
            super(itemView);
            ClassID = (TextView) itemView.findViewById(R.id.ClassID);
            ID_GPA = (TextView) itemView.findViewById(R.id.ID_GPA);
            Class_name = (TextView) itemView.findViewById(R.id.Class_name);
            Grade = (TextView) itemView.findViewById(R.id.Grade);
            ID_Nature = (TextView) itemView.findViewById(R.id.ID_Nature);
            ID_Credit = (TextView) itemView.findViewById(R.id.ID_Credit);
        }
    }
}
