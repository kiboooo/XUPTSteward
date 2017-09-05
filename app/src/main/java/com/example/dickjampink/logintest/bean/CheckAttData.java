package com.example.dickjampink.logintest.bean;

/**
 * Created by Kiboooo on 2017/7/14.
 *
 */

public class CheckAttData {
    private String BName;
    private String JT_No;
    private String RoomNum;
    private String S_Name;
    private String Status;
    private String WaterTime;

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

    public String toStirng () {
        return BName+JT_No
        + RoomNum
        + S_Name
        + Status
        + WaterTime;
    }
}

