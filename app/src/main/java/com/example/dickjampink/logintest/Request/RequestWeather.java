package com.example.dickjampink.logintest.Request;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Kiboooo on 2017/7/26.
 *
 */

public class RequestWeather {

    public static String GetCityCode(Context context, String DistrictName,String CityName)
            throws IOException, SAXException, ParserConfigurationException {
        String CityCode = "101010100";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Log.e("setWeatherData", DistrictName);
        // 这个Document就是一个XML文件在内存中的镜像
        InputStream in = context.getResources().getAssets().open("weatherid.xml");
        Document doc = db.parse(in);
        Log.e("setWeatherData", DistrictName);

        //获取county节点链
        NodeList nodeList = doc.getElementsByTagName("county");

        for (int i = 0; i < nodeList.getLength(); i++) {

            // 从节点链中取出节点对象（county对象）
            Node fatherNode = nodeList.item(i);
            // 把county节点的属性拿出来,由于已经知道每个节点都是3个
            NamedNodeMap attributes = fatherNode.getAttributes();
            Node attribute = attributes.item(1);
            //与需求地名相比对，相等则输出城市代码。
            if (attribute.getNodeValue().compareTo(CityName) == 0) {
                attribute = attributes.item(2);
                Log.e("setWeatherData", DistrictName + "  " + attribute.getNodeValue());
                CityCode=attribute.getNodeValue();
            }else
            if (attribute.getNodeValue().compareTo(DistrictName) == 0) {
                attribute = attributes.item(2);
                Log.e("setWeatherData", DistrictName + "  " + attribute.getNodeValue());
                CityCode=attribute.getNodeValue();
                break;
            }
        }
        in.close();
        //若找不到返回北京的天气
        return CityCode;
    }

    public static void GetTemperature(final String LatitudeAndLongitude, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .get()
                        .url("https://api.seniverse.com/v3/weather/now.json?key=sfdeqrjxpquxvbii&location="
                                + LatitudeAndLongitude+"&language=zh-Hans&unit=c")
                        .build();

                Call call = new OkHttpClient().newCall(request);
                call.enqueue(callback);
            }
        }).start();
    }
}
