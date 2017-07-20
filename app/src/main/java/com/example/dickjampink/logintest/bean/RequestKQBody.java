package com.example.dickjampink.logintest.bean;

import java.util.Calendar;

/**
 * Created by Kiboooo on 2017/7/17.
 */

public class RequestKQBody {

    private String WaterDate = "";
    private String Status = "";
    private String Flag = "";
    private String page = "";
    private String rows = "";

    public String getFlag() {
        return Flag;
    }

    public String getPage() {
        return page;
    }

    public String getRows() {
        return rows;
    }

    public String getStatus() {
        return Status;
    }

    public String getWaterDate() {
        return WaterDate;
    }

    public void setWaterDate(int Status) {
        Calendar c = Calendar.getInstance();
        int  year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DATE);
        setStatus(Status+"");
        if (Status == 1) {
            WaterDate = year + "-" + month + "-" + day + "a" + year + "-" + month + "-" + day;
        } else if (Status == 2) {
            int nowDaty = c.get(Calendar.DAY_OF_MONTH);
            c.set(Calendar.DATE, nowDaty - 7);
            WaterDate = c.get(Calendar.YEAR) + "-" +( c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DATE) + "a" + year + "-" + month + "-" + day;
        } else if (Status == 3) {
            int nowMonth = c.get(Calendar.MONTH);
            c.set(Calendar.MONTH, nowMonth - 1);
            WaterDate = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DATE) + "a" + year + "-" + month + "-" + day;
        }
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

}
