package com.example.dickjampink.logintest.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.Request.RequestZHJS;
import com.example.dickjampink.logintest.adapter.AttendanceAdapter;
import com.example.dickjampink.logintest.adapter.CheckAttAdapter;
import com.example.dickjampink.logintest.adapter.ClassRoomAdapter;
import com.example.dickjampink.logintest.bean.AttendanceData;
import com.example.dickjampink.logintest.bean.CheckAttData;
import com.example.dickjampink.logintest.bean.ClassRoomData;
import com.example.dickjampink.logintest.bean.RequestKQBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Kiboooo on 2017/7/14.
 *
 */

public class AttendanceFragment extends Fragment {

    public static final String TAB_PAGE = "tab_page";

    private CheckAttAdapter adapter;
    private ProgressDialog progressDialog;
    private ArrayList<ClassRoomData> CRDs = new ArrayList<>();
    private ClassRoomAdapter classRoomAdapter;
    private String ClassRoomTitle = "长安校区东区逸夫楼";
    private List<AttendanceData> att_data = new ArrayList<>();

    private int mPage;
    public static AttendanceFragment newInstance(int type){
        Bundle args = new Bundle();
        args.putInt(TAB_PAGE, type);
        AttendanceFragment fragment = new AttendanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(TAB_PAGE);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("数据加载中...");
        progressDialog.setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = null;
        if (mPage == 1){
            view = inflater.inflate(R.layout.content_classroom, container, false);
            final android.support.v7.widget.Toolbar toolbar =
                    (android.support.v7.widget.Toolbar) view.findViewById(R.id.ClassRoomToolBar);
            toolbar.inflateMenu(R.menu.classroom_select);
            final View finalView = view;
            toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    ClassRoomTitle = item.getTitle().toString();
                    toolbar.setTitle(ClassRoomTitle);
                    String RequestID = "163";
                    switch (item.getItemId()) {
                        case R.id.Building1:
                            Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                            RequestID = "164";
                        break;
                        case R.id.Building2:
                            Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                            RequestID = "166";
                            break;
                        case R.id.Building3:
                            Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                            RequestID = "167";
                            break;
                        case R.id.Building4:
                            Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                            RequestID = "163";
                            break;
                        case R.id.Building5:
                            Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                            RequestID = "165";
                            break;
                        case R.id.Building6:
                            Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                            RequestID = "162";
                            break;
                        case R.id.Building7:
                            Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                            RequestID = "181";
                            break;
                    }
                    progressDialog.show();
                    RequestZHJS.GetClassRoomMSGRequest(RequestID, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "请求失败，请稍后重试",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                String body = response.body().string();
                                Log.e("GetClassRoomMSGRequest", body);
                                try {
                                    JSONObject jsonObject = new JSONObject(body);
                                    if (jsonObject.getBoolean("IsSucceed")) {
                                        JSONArray jsonArray = jsonObject.getJSONArray("Obj");
                                        CRDs = new ArrayList<>();
                                        for (int i = 0; i < jsonArray.length() ; i++) {
                                            ClassRoomData crd = new ClassRoomData();
                                            crd.setClassRoomName(jsonArray.getJSONObject(i)
                                                    .getJSONObject("Build").getString("NAME"));
                                            crd.setClassRoomNuw(jsonArray.getJSONObject(i)
                                                    .getString("ROOMNUM"));
                                            crd.setClassRoomFlow(jsonArray.getJSONObject(i)
                                                    .getString("ROOMFLOW"));
                                            crd.setClassRoomUSERCOUNT(jsonArray.getJSONObject(i)
                                                    .getString("USERCOUNT"));
                                            CRDs.add(crd);
                                        }
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();
                                                classRoomAdapter = new ClassRoomAdapter(CRDs);
                                                DisplayClassRoomCar(finalView, classRoomAdapter);
                                            }
                                        });
                                    }else {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();
                                                Toast.makeText(getContext(),
                                                        "请求失败，请稍后重试", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                } catch (JSONException e) {
                                    Log.e("JSONException", "请求失败，请稍后重试");
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    return true;
                }
            });
            if (CRDs.size()>0) {
                toolbar.setTitle(ClassRoomTitle);
                 DisplayClassRoomCar(view, classRoomAdapter);
            }else
            {
                toolbar.setTitle(ClassRoomTitle);
                RequestZHJS.GetClassRoomMSGRequest("163", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String body = response.body().string();
                            Log.e("GetClassRoomMSGRequest", body);
                            try {
                                JSONObject jsonObject = new JSONObject(body);
                                if (jsonObject.getBoolean("IsSucceed")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("Obj");
                                    CRDs = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length() ; i++) {
                                        ClassRoomData crd = new ClassRoomData();
                                        crd.setClassRoomName(jsonArray.getJSONObject(i)
                                                .getJSONObject("Build").getString("NAME"));
                                        crd.setClassRoomNuw(jsonArray.getJSONObject(i)
                                                .getString("ROOMNUM"));
                                        crd.setClassRoomFlow(jsonArray.getJSONObject(i)
                                                .getString("ROOMFLOW"));
                                        crd.setClassRoomUSERCOUNT(jsonArray.getJSONObject(i)
                                                .getString("USERCOUNT"));
                                        CRDs.add(crd);
                                    }
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            classRoomAdapter = new ClassRoomAdapter(CRDs);
                                            DisplayClassRoomCar(finalView, classRoomAdapter);
                                        }
                                    });
                                }else {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(),
                                                    "请求失败，请稍后重试", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            } catch (JSONException e) {
                                Log.e("JSONException", "请求失败，请稍后重试");
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }

        }else if (mPage == 2)
        {
            view = inflater.inflate(R.layout.content_checking_detail, container, false);
            Button checkButton = (Button) view.findViewById(R.id.checkButton);
            final RequestKQBody requestKQBody = new RequestKQBody();
            final Spinner spinner = (Spinner) view.findViewById(R.id.CheckSpinner);
            final CheckBox normalCB = (CheckBox) view.findViewById(R.id.normal);
            final CheckBox abnormalCB = (CheckBox) view.findViewById(R.id.abnormal);
            final CheckBox lateCB = (CheckBox) view.findViewById(R.id.late);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    requestKQBody.setWaterDate(i+1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            checkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    initCheckBoxControl(normalCB, lateCB, abnormalCB,requestKQBody);
                    requestKQBody.setPage("1");
                    requestKQBody.setRows("10");
                    initCheckAttAdapter(requestKQBody);
                }
            });
        } else if (mPage == 3) {
            view = inflater.inflate(R.layout.content_checking, container, false);
            if (att_data.isEmpty()) {
                initAttendance(view);
            }
            else
            {
                setAttendance(view, att_data);
            }
            progressDialog.dismiss();
        }
        return view;
    }

    //获取请求的Body
    private void initCheckBoxControl(CheckBox normal, CheckBox late,
                                     CheckBox abnormal,RequestKQBody RKQB) {
        if (normal.isChecked()) {
            RKQB.setFlag("1");
        }
        if (late.isChecked())
        {
            Log.e("late.isChecked()", "" + RKQB.getFlag().length());
            if (normal.isChecked()) {
                RKQB.setFlag("1a2");
            } else RKQB.setFlag("2");
        }
        if (abnormal.isChecked()) {
            if (!normal.isChecked() && !late.isChecked()) {
                RKQB.setFlag("3");
            } else
            if (normal.isChecked()|| late.isChecked()) {
                if (!late.isChecked()) {
                    RKQB.setFlag("1a3");
                } else if (!normal.isChecked()) {
                    RKQB.setFlag("2a3");
                } else RKQB.setFlag("1a2a3");
            }
        }
        Log.e("WaterDate", RKQB.getWaterDate());
        Log.e("Flag", RKQB.getFlag());
        Log.e("Status", RKQB.getStatus());
    }


    public void initCheckAttAdapter(final RequestKQBody RKQB ){
        //请求考勤表的数据,数据请求完毕后，利用runOnUiThread在UI线程中进行UI加载。
        Log.e("initCheckAttAdapter", "请求的数据--》" +RKQB.getWaterDate()+" "
                +RKQB.getStatus()+" "+RKQB.getFlag()+" "+RKQB.getPage()+" "
                +RKQB.getRows() );
        RequestZHJS.CheckMsgRequest(RKQB.getWaterDate(), RKQB.getStatus(),
                RKQB.getFlag(), RKQB.getPage(), RKQB.getRows(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("initCheckAttAdapter", "请求数据失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){

                    try {
                        final List<CheckAttData> CADs= new ArrayList<>();
                        String b = response.body().string();
                        Log.e("initCheckAttAdapter", b);
                        JSONObject jsonObject = new JSONObject(b);
                        int Total = jsonObject.getInt("total");
                        Total = Total > 10 ? 10 : Total;
                        Log.e("initCheckAttAdapter", Total + "");
                        JSONArray jsonArray = jsonObject.getJSONArray("rows");
                        for (int i = 0; i < Total; i++) {
                            CheckAttData CAD  = new CheckAttData();
                            CAD.setBName(jsonArray.getJSONObject(i).getString("BName"));
                            CAD.setJT_No(jsonArray.getJSONObject(i).getString("JT_No"));
                            CAD.setRoomNum(jsonArray.getJSONObject(i).getString("RoomNum"));
                            CAD.setS_Name(jsonArray.getJSONObject(i).getString("S_Name"));
                            CAD.setWaterTime(jsonArray.getJSONObject(i).getString("WaterTime"));
                            CAD.setStatus(jsonArray.getJSONObject(i).getString("Status"));
                            CADs.add(CAD);
                            Log.e("initCheckAttAdapter", CAD.toStirng());
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("jinlaile", "kjdshfkjsdhgjlkshlkj");
                                TextView Detail_NotMsg = (TextView) getActivity().findViewById(R.id.Detail_NotMsg);
                                Detail_NotMsg.setText("");
                                RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.check_display);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                CheckAttAdapter adapter = new CheckAttAdapter(CADs);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    private void initAttendance(final View view) {
        progressDialog.show();
        RequestZHJS.getAttendance(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONObject(body).getJSONArray("Obj");
                        att_data = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            AttendanceData a_data = new AttendanceData();
                            JSONObject att_D = jsonArray.getJSONObject(i);
                            a_data.setClassName(att_D.getString("CourseName"));
                            a_data.setAbsence(att_D.getInt("Absence"));
                            a_data.setAttend(att_D.getInt("Attend"));
                            a_data.setShouldAttend(att_D.getInt("ShouldAttend"));
                            att_data.add(a_data);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setAttendance(view, att_data);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void DisplayClassRoomCar(View view,ClassRoomAdapter adapter) {
        TextView NotMsg = (TextView) view.findViewById(R.id.ClassRoomNot_Msg);
        NotMsg.setText("");
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.ClassRoomRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setAttendance(View view,List<AttendanceData> att_data) {
        if (!att_data.isEmpty()) {
            TextView NotMsg_C = (TextView) view.findViewById(R.id.Checking_NotMsg);
            NotMsg_C.setText("");
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.att_table_display);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        AttendanceAdapter adapter = new AttendanceAdapter(att_data);
        recyclerView.setAdapter(adapter);
        progressDialog.dismiss();
    }

}
