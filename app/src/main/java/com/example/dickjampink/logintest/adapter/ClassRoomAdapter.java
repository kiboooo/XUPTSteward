package com.example.dickjampink.logintest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.bean.ClassRoomData;

import java.util.ArrayList;

/**
 * Created by Kiboooo on 2017/7/25.
 *
 */

public class ClassRoomAdapter extends RecyclerView.Adapter<ClassRoomAdapter.ViewHolder> {

    private ArrayList<ClassRoomData> classRoomDatas;

    public ClassRoomAdapter(ArrayList<ClassRoomData> classRoomDatas) {
        this.classRoomDatas = classRoomDatas;
    }

    @Override
    public ClassRoomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.classroom, parent, false));
    }

    @Override
    public void onBindViewHolder(ClassRoomAdapter.ViewHolder holder, int position) {
        ClassRoomData classRoomData = classRoomDatas.get(position);
        holder.ClassRoomNumber.setText(classRoomData.getClassRoomNum());
        holder.ClassRoomName.setText(classRoomData.getClassRoomName());
        holder.ClassRoomFlow.setText(classRoomData.getClassRoomFlow());
        holder.ClassRoomUSERCOUNT.setText(classRoomData.getClassRoomUSERCOUNT());
    }

    @Override
    public int getItemCount() {
        return classRoomDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ClassRoomNumber;
        TextView ClassRoomName;
        TextView ClassRoomFlow;
        TextView ClassRoomUSERCOUNT;

        public ViewHolder(View itemView) {
            super(itemView);
            ClassRoomNumber = (TextView) itemView.findViewById(R.id.ClassRoomNumber);
            ClassRoomName = (TextView) itemView.findViewById(R.id.ClassRoomName);
            ClassRoomFlow = (TextView) itemView.findViewById(R.id.ClassRoomFlow);
            ClassRoomUSERCOUNT = (TextView) itemView.findViewById(R.id.ClassRoomUSERCOUNT);
        }
    }
}
