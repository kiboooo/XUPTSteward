package com.example.dickjampink.logintest.bean;

import java.io.Serializable;

/**
 * Created by Kiboooo on 2017/7/14.
 *
 */

public class CheckAttData implements Serializable {
    private String Class_No;
    private String BName;
    private String JT_No;
    private String RoomNum;
    private String S_Name;
    private String Status;
    private String WaterTime;
    private String RBH;
    private String SBH;
    private String WaterDate;
    private String Term_No;

    public String getClass_No() {
        return Class_No;
    }

    public String getRBH() {
        return RBH;
    }

    public String getSBH() {
        return SBH;
    }

    public String getWaterDate() {
        return WaterDate;
    }

    public String getTerm_No() {
        return Term_No;
    }

    public String getBName() {
        return BName;
    }

    public String getJT_No() {
        return JT_No;
    }

    public String getRoomNum() {
        return RoomNum;
    }

    public String getS_Name() {
        return S_Name;
    }

    public String getStatus() {
        return Status;
    }

    public String getWaterTime() {
        return WaterTime;
    }

    public void setBName(String BName) {
        this.BName = BName;
    }

    public void setJT_No(String JT_No) {
        this.JT_No = JT_No;
    }

    public void setRoomNum(String roomNum) {
        RoomNum = roomNum;
    }

    public void setS_Name(String s_Name) {
        S_Name = s_Name;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setWaterTime(String waterTime) {
        WaterTime = waterTime;
    }

    public void setRBH(String RBH) {
        this.RBH = RBH;
    }

    public void setSBH(String SBH) {
        this.SBH = SBH;
    }

    public void setWaterDate(String waterDate) {
        WaterDate = waterDate;
    }

    public void setTerm_No(String term_No) {
        Term_No = term_No;
    }

    public void setClass_No(String class_No) {
        Class_No = class_No;
    }

    public String toStirng () {
        return BName+JT_No
        + RoomNum
        + S_Name
        + Status
        + WaterTime;
    }
}

