package com.example.dickjampink.logintest.bean;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Kiboooo on 2017/7/17.
 */

public class GradeCarData {
    private double  AllCredit;
    private double  AllGPA;
    private double  AllClass;
    private String CheckTitle;

    public void setCheckTitle(String checkTitle) {
        CheckTitle = checkTitle;
    }

    public String getCheckTitle() {
        return CheckTitle;
    }

    private ArrayList<GradeData> GradeArray ;
    private DecimalFormat df = new DecimalFormat("#.##");

    public GradeCarData()
    {
        AllClass = AllCredit = AllGPA = 0.0;
        GradeArray = new ArrayList<>();
    }

    public ArrayList<GradeData> getGradeArray() {
        return GradeArray;
    }

    public double getAllClass() {
        return GradeArray.size();
    }

    public String getAllCredit() {
        if (AllCredit==0)
        for (int i = 0; i <GradeArray.size() ; i++) {
            AllCredit += GradeArray.get(i).getClassCREDIT();

        }
        return df.format(AllCredit);
    }

    public String getAllGPA() {
        if (AllGPA==0) {
            for (int i = 0; i < GradeArray.size(); i++) {
                AllGPA += GradeArray.get(i).getGPA();
            }
        }

        return df.format(AllGPA);
    }

    public void AddGradeArray(GradeData GD) {
        GradeArray.add(GD);
    }

}
