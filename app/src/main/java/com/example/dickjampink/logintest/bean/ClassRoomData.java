package com.example.dickjampink.logintest.bean;

/**
 * Created by Kiboooo on 2017/7/25.
 *
 */

public class ClassRoomData {
    private String ClassRoomName;
    private String ClassRoomFlow;
    private String ClassRoomNuw;
    private String ClassRoomUSERCOUNT;

    public void setClassRoomName(String classRoomName) {
        ClassRoomName = classRoomName;
    }

    public void setClassRoomFlow(String classRoomFlow) {
        ClassRoomFlow = classRoomFlow;
    }

    public void setClassRoomNuw(String classRoomNuw) {
        ClassRoomNuw = classRoomNuw;
    }

    public void setClassRoomUSERCOUNT(String classRoomUSERCOUNT) {
        ClassRoomUSERCOUNT = classRoomUSERCOUNT;
    }

    public String getClassRoomName() {
        return ClassRoomName;
    }

    public String getClassRoomFlow() {
        return "楼层："+ClassRoomFlow;
    }

    public String getClassRoomNum() {
        return ClassRoomNuw;
    }

    public String getClassRoomUSERCOUNT() {
        return "可容纳人数："+ClassRoomUSERCOUNT;
    }
}
