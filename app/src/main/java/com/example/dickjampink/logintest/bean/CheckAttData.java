package com.example.dickjampink.logintest.bean;

/**
 * Created by Kiboooo on 2017/7/14.
 */

public class CheckAttData {
    private String ClassName;
    private String ClassLocation;
    private String ClassTime;

    public String getClassLocation() {
        return ClassLocation;
    }

    public String getClassName() {
        return ClassName;
    }

    public String getClassTime() {
        return ClassTime;
    }

    public void setClassLocation(String classLocation) {
        ClassLocation = classLocation;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public void setClassTime(String classTime) {
        ClassTime = classTime;
    }
}

