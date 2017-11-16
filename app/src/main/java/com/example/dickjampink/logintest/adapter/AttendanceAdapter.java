package com.example.dickjampink.logintest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.bean.AttendanceData;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DickJampink on 2017/5/23.
 *
 */

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
    private Context Context;
    private List<AttendanceData> mAttendance;//考勤表信息List数组


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.att_table_type, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        AttendanceData attendance = mAttendance.get(position);
//        holder.ClassName.setText(attendance.getClassName());
//        holder.Attend.setText(attendance.getAttendProbability());
//        holder.Absent.setText(attendance.getAbsenceProbability());
//        holder.Late.setText(attendance.getLateProbability());
        holder.pieChart.setCenterText(attendance.getClassName());
        showChart1(holder.pieChart,getPieData(attendance.getAttend(),
                attendance.getShouldAttend()-attendance.getAttend()-attendance.getAbsence()
                ,attendance.getAbsence()));
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
        PieChart pieChart;


        public ViewHolder(View itemView) {
            super(itemView);
//            ClassName = (TextView) itemView.findViewById(R.id.className);
//            Attend = (TextView) itemView.findViewById(R.id.chuxi);
//            Late = (TextView) itemView.findViewById(R.id.chidao);
//            Absent = (TextView) itemView.findViewById(R.id.quexi);
            pieChart = (PieChart) itemView.findViewById(R.id.attendance_start);
        }
    }

    public AttendanceAdapter(Context context,List<AttendanceData> AttendanceList) {
        mAttendance = AttendanceList;
        this.Context = context;
    }

    private void showChart1(PieChart pieChart, PieData pieData) {
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(40f);  //半径
        pieChart.setTransparentCircleRadius(48f); // 半透明圈
        pieChart.setDescription("");
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度
        pieChart.setRotationEnabled(true); // 可以手动旋转

        pieChart.setUsePercentValues(true);  //显示成百分比
        pieChart.setTransparentCircleRadius(10f);
        pieData.setValueTextSize(15);


        //设置数据
        pieChart.setData(pieData);

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
        mLegend.setTextColor(Color.WHITE);
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);

        pieChart.animateXY(1000, 1000);  //设置动画
    }

    private PieData getPieData(int attend,int late,int absence) {

        ArrayList<String> xValues = new ArrayList<>();  //xVals用来表示每个饼块上的内容


        xValues.add("出席" + "(" + attend+ ")");  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
        xValues.add("迟到" + "(" + late+ ")");
        xValues.add("缺席" + "(" + absence + ")");


        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
        /**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         */
        float quarterly1 = (float) ((float) attend * 1.0 / (attend + late + absence) * 100);
        float quarterly2 = (float) ((float) late*1.0/(attend + late + absence)*100);
        float quarterly3 = (float) ((float) absence*1.0/(attend + late + absence)*100);

        yValues.add(new Entry(quarterly1, 0));
        yValues.add(new Entry(quarterly2, 1));
        yValues.add(new Entry(quarterly3, 2));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, ""/*显示在比例图上*/);
        pieDataSet.setSliceSpace(2f); //设置个饼状图之间的距离
        pieDataSet.setColor(Color.WHITE);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = Context.getResources().getDisplayMetrics();
        float px = 3 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        return new PieData(xValues, pieDataSet);
    }

}
