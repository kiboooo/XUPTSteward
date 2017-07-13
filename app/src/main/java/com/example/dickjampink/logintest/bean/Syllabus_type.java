package com.example.dickjampink.logintest.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by DickJampink on 2017/5/18.
 */

public class Syllabus_type extends DataSupport {

    private String S_Name;
    private String Teach_Name;
    private String JT_NO;
    private String RoomNum;
    private int WeekNum;
    private int Background;

    public String getJT_NO() {
        return JT_NO;
    }

    public int getBackground() {
        return Background;
    }

    public String getRoomNum() {
        return RoomNum;
    }

    public String getS_Name() {
        return S_Name;
    }

    public String getTeach_Name() {
        return Teach_Name;
    }

    public int getWeekNum() {
        return WeekNum;
    }

    public void setJT_NO(String JT_NO) {
        this.JT_NO = JT_NO;
    }

    public void setRoomNum(String roomNum) {
        RoomNum = roomNum;
    }

    public void setS_Name(String s_Name) {
        S_Name = s_Name;
    }

    public void setTeach_Name(String teach_Name) {
        Teach_Name = teach_Name;
    }

    public void setWeekNum(int weekNum) {
        WeekNum = weekNum;
    }

    public void setBackground(int background) {
        Background = background;
    }

}
