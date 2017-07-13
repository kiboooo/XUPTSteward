package com.example.dickjampink.logintest.bean;

import java.text.DecimalFormat;

/**
 * Created by DickJampink on 2017/5/22.
 */

public class AttendanceData {
    private String ClassName ;
    private int ShouldAttend ;
    private int Attend ;
    private int Absence ;

    public int getAbsence() {
        return Absence;
    }

    public int getAttend() {
        return Attend;
    }

    public int getShouldAttend() {
        return ShouldAttend;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setAbsence(int absence) {
        Absence = absence;
    }

    public void setAttend(int attend) {
        Attend = attend;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public void setShouldAttend(int shouldAttend) {
        ShouldAttend = shouldAttend;
    }

    public String getAttendProbability(){
        double res = (Attend * 1.0) / (ShouldAttend);
        DecimalFormat df = new DecimalFormat("0.00%");
        return "出席：" + df.format(res);
    }

    public String getAbsenceProbability(){
        double res = (Absence * 1.0) / (ShouldAttend);
        DecimalFormat df = new DecimalFormat("0.00%");
        return "缺席：" + df.format(res);
    }

    public String getLateProbability(){
        double res = (ShouldAttend-Absence-Attend) / (ShouldAttend*1.0);
        DecimalFormat df = new DecimalFormat("0.00%");
        return "迟到：" + df.format(res);
    }
}
