package com.example.dickjampink.logintest.bean;

/**
 * Created by DickJampink on 2017/5/9.
 */

public class LData {
    private boolean IsSuccess;
    private String Msg;
    private int Obj;

    public String getMsg() {
        return Msg;
    }

    public int getObj() {
        return Obj;
    }

    public void setObj(int obj) {
        Obj = obj;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }

    public boolean getIsSuccess() {
        return IsSuccess;
    }
}
