package com.example.dickjampink.logintest.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dickjampink.logintest.Fragment.AppealDialog;
import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.bean.CheckAttData;

import java.util.List;

/**
 * Created by Kiboooo on 2017/7/14.
 *
 */

public class CheckAttAdapter extends  RecyclerView.Adapter<CheckAttAdapter.ViewHolder> {
    private List<CheckAttData> mCAtt;//考勤表查询信息列表
    private Activity mActivity;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //数据出来后。这个要改 R.layout.att_table_type,
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CheckAttData attendance = mCAtt.get(position);
        //需要在这里设置数据到到View
        holder.WaterTime.setText(attendance.getWaterTime());
        holder.S_Name.setText(attendance.getS_Name());
        holder.JT_No.setText(attendance.getJT_No());
        holder.RoomNum.setText(attendance.getRoomNum());
        holder.BNanme.setText(attendance.getBName());
        holder.CheckAppeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.Check_appeal) {
                    /*开启申诉信息界面的Dialog*/
                    Appeal(position);
                }
            }
        });
        switch (Integer.parseInt(attendance.getStatus())) {
            case 1:
                holder.Status.setText("正常签到");
                holder.Status.setTextColor(Color.GREEN);
                break;
            case 2:
                holder.Status.setText("迟到");
                holder.Status.setTextColor(Color.parseColor("#FFC408"));
                holder.CheckAppeal.setVisibility(View.VISIBLE);

                break;
            case 3:
                holder.Status.setText("缺勤");
                holder.Status.setTextColor(Color.RED);
                holder.CheckAppeal.setVisibility(View.VISIBLE);
                break;
            default:
                Log.e("initCheckAttAdapter", Integer.parseInt(attendance.getStatus())+"");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mCAtt.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView WaterTime,S_Name,JT_No,RoomNum,Status,BNanme;
        Button CheckAppeal;
        public ViewHolder(View itemView) {
            super(itemView);
            WaterTime = (TextView) itemView.findViewById(R.id.WaterTime);
            S_Name = (TextView) itemView.findViewById(R.id.S_Name);
            JT_No = (TextView) itemView.findViewById(R.id.JT_No);
            RoomNum = (TextView) itemView.findViewById(R.id.RoomNum);
            Status = (TextView) itemView.findViewById(R.id.Status);
            BNanme = (TextView) itemView.findViewById(R.id.BNanme);
            CheckAppeal = (Button) itemView.findViewById(R.id.Check_appeal);
        }
    }

    public CheckAttAdapter(List<CheckAttData> CAtt, Activity activity) {
        mCAtt = CAtt;
        mActivity = activity;
    }

    /*弹出一个填写申请的Dialog*/
    private void Appeal(int position) {
        Log.e("Appeal", "position = " + position + "  " + mCAtt.get(position).toStirng());
        AppealDialog.newInstance(mCAtt.get(position)).show(mActivity.getFragmentManager(),"appeal");
    }

}
