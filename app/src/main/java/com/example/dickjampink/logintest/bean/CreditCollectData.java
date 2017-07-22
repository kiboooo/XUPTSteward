package com.example.dickjampink.logintest.bean;

/**
 * Created by Kiboooo on 2017/7/22.
 */

public class CreditCollectData {
    private String Classname;
    private String CreditRequire;
    private String CreditGet;
    private String CreditNot;

    public void setClassname(String classname) {
        Classname = classname;
    }

    public void setCreditRequire(String creditRequire) {
        CreditRequire = creditRequire;
    }

    public void setCreditGet(String creditGet) {
        CreditGet = creditGet;
    }

    public void setCreditNot(String creditNot) {
        CreditNot = creditNot;
    }

    public void setCreditNeed(String creditNeed) {
        CreditNeed = creditNeed;
    }

    private String CreditNeed;

    public String getClassname() {
        return Classname;
    }

    public String getCreditRequire() {
        return CreditRequire;
    }

    public String getCreditGet() {
        return CreditGet;
    }

    public String getCreditNot() {
        return CreditNot;
    }

    public String getCreditNeed() {
        return CreditNeed;
    }
}
