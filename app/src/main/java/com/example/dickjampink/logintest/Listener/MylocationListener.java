package com.example.dickjampink.logintest.Listener;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

/**
 * Created by Kiboooo on 2017/7/26.
 *
 */

public class MylocationListener implements BDLocationListener {

    public static String LocationName = "";
    public static String LocationCityName = "";
    //温度请求
    public static String LocationRequest = "";

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        LocationName = bdLocation.getDistrict().substring(0,bdLocation.getCity().length()-1);
        LocationCityName = bdLocation.getCity().substring(0,bdLocation.getCity().length()-1);
        double locationLatitude = bdLocation.getLatitude();
        double locationLongitude = bdLocation.getLongitude();
        LocationRequest = locationLatitude + ":" + locationLongitude;

        Log.e("onReceiveLocation", LocationName );
        Log.e("onReceiveLocation", LocationRequest);
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }
}
