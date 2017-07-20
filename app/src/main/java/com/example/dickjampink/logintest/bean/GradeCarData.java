package com.example.dickjampink.logintest.bean;

import java.util.ArrayList;

/**
 * Created by Kiboooo on 2017/7/17.
 */

public class GradeCarData {
    private double  AllCredit;
    private double  AllGPA;
    private double  AllClass;
    private ArrayList<GradeData> GradeArray ;
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

    public double getAllCredit() {
        if (AllCredit==0)
        for (int i = 0; i <GradeArray.size() ; i++) {
            AllCredit += GradeArray.get(i).getClassCREDIT();

        }
        return AllCredit;
    }

    public double getAllGPA() {
        if (AllGPA==0) {
            for (int i = 0; i < GradeArray.size(); i++) {
                AllGPA += GradeArray.get(i).getGPA();
            }
        }

        return AllGPA;
    }

    public void AddGradeArray(GradeData GD) {
        GradeArray.add(GD);
    }

}
