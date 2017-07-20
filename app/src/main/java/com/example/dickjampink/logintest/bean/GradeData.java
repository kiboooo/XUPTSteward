package com.example.dickjampink.logintest.bean;

/**
 * Created by Kiboooo on 2017/7/17.
 */

public class GradeData {
    private String ClassID;
    private String ClassNAME;
    private String ClassNATURE;
    private String Grade;
    private double ClassCREDIT;
    private double GPA;

    public double getClassCREDIT() {
        return ClassCREDIT;
    }

    public double getGPA() {
        return GPA;
    }

    public String getGrade() {
        return Grade;
    }

    public String getClassNAME() {
        return ClassNAME;
    }

    public String getClassNATURE() {
        return ClassNATURE;
    }

    public String getClassID() {
        return ClassID;
    }

    public void setClassCREDIT(double classCREDIT) {
        ClassCREDIT = classCREDIT;
    }

    public void setClassNAME(String classNAME) {
        ClassNAME = classNAME;
    }

    public void setClassNATURE(String classNATURE) {
        ClassNATURE = classNATURE;
    }

    public void setClassID(String classID) {
        ClassID = classID;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }
}
