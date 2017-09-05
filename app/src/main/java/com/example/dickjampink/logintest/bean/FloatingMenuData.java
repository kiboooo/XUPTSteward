package com.example.dickjampink.logintest.bean;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Kiboooo on 2017/7/22.
 */

public class FloatingMenuData {
    private int SessionNumber;
    private ArrayList<String> SessionYears;
    public FloatingMenuData(){
        SessionYears = new ArrayList<>();
    }

    public void setSessionNumber(int sessionNumber) {
        if (sessionNumber == 1 )
        SessionNumber = 0;
        else {
            Calendar calendar = Calendar.getInstance();
            if (calendar.get(Calendar.MONTH) + 1 > 7) {
                SessionNumber = (sessionNumber - 1) * 2 + 1;
            }
            else if (calendar.get(Calendar.MONTH) + 1 == 7 && calendar.get(Calendar.DAY_OF_MONTH) > 17)
                SessionNumber = (sessionNumber - 1) * 2 + 1;
            else SessionNumber = (sessionNumber - 1) * 2;
        }
    }

    public void AddSessionYears(String sessionYears) {
        SessionYears.add(sessionYears);
        SessionYears.add(sessionYears);
    }

    public String getSessionYears(int i) {
        return SessionYears.get(i);
    }

    public int getSessionNumber() {
        return SessionNumber;
    }

}
