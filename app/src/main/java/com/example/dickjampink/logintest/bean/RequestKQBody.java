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

    /*获取所需要请求的日期*/
    public void setWaterDate(int Status) {
        Calendar c = Calendar.getInstance();
        String Month = null, Day = null;

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);

        //控制个位数格式
        if (month < 10) {
            Month = "0" + month;
        } else Month = "" + month;
        if (day < 10) {
            Day = "0" + day;
        } else Day = "" + day;

        setStatus(Status+"");

        if (Status == 1) {
            WaterDate = year + "-" + Month + "-" + Day + "a" + year + "-" + Month + "-" + Day;
        } else if (Status == 2) {
            int nowDay = c.get(Calendar.DAY_OF_MONTH);
            c.set(Calendar.DATE, nowDay - 7);
            if (c.get(Calendar.MONTH) + 1 < 10){
                if (c.get(Calendar.DATE) < 10) {
                    WaterDate = c.get(Calendar.YEAR) + "-0" + (c.get(Calendar.MONTH) + 1) + "-0" + c.get(Calendar.DATE) + "a" + year + "-" + Month + "-" + Day;
                }else
                WaterDate = c.get(Calendar.YEAR) + "-0" +( c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DATE) + "a" + year + "-" + Month + "-" + Day;
            }else if (c.get(Calendar.DATE) < 10)
            WaterDate = c.get(Calendar.YEAR) + "-" +( c.get(Calendar.MONTH)+1) + "-0" + c.get(Calendar.DATE) + "a" + year + "-" + Month + "-" + Day;
            else
                WaterDate = c.get(Calendar.YEAR) + "-" +( c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DATE) + "a" + year + "-" + Month + "-" + Day;
        } else if (Status == 3) {
            int nowMonth = c.get(Calendar.MONTH);
            c.set(Calendar.MONTH, nowMonth - 1);
            if (c.get(Calendar.MONTH) + 1 < 10){
                if (c.get(Calendar.DATE) < 10) {
                    WaterDate = c.get(Calendar.YEAR) + "-0" + (c.get(Calendar.MONTH) + 1) + "-0" + c.get(Calendar.DATE) + "a" + year + "-" + Month + "-" + Day;
                }else
                    WaterDate = c.get(Calendar.YEAR) + "-0" +( c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DATE) + "a" + year + "-" + Month + "-" + Day;
            }else if (c.get(Calendar.DATE) < 10)
                WaterDate = c.get(Calendar.YEAR) + "-" +( c.get(Calendar.MONTH)+1) + "-0" + c.get(Calendar.DATE) + "a" + year + "-" + Month + "-" + Day;
            else
                WaterDate = c.get(Calendar.YEAR) + "-" +( c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DATE) + "a" + year + "-" + Month + "-" + Day;
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
