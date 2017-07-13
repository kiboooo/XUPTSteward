package com.example.dickjampink.logintest.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by DickJampink on 2017/5/18.
 */

public class LoginData extends DataSupport {
    private String studentID;
    private String Name;
    private String Cookie;
    private String XYname;
    private String ZYName;
    private String BJ;

    public String getName() {
        return Name;
    }

    public String getBJ() {
        return BJ;
    }

    public String getCookie() {
        return Cookie;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getXYname() {
        return XYname;
    }

    public String getZYName() {
        return ZYName;
    }

    public void setBJ(String BJ) {
        this.BJ = BJ;
    }

    public void setCookie(String cookie) {
        Cookie = cookie;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setXYname(String XYname) {
        this.XYname = XYname;
    }

    public void setZYName(String ZYName) {
        this.ZYName = ZYName;
    }

    public void setName(String name) {
        Name = name;
    }
}
