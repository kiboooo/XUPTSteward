package com.example.dickjampink.logintest.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.bean.CheckAttData;


/**
 * Created by Kiboooo on 2017/10/23.
 */

public class AppealDialog extends DialogFragment {

    private EditText Remark;
    private TextView ClassName,ClassDate;
    private CheckAttData mCheckAttData;

    public static AppealDialog newInstance(CheckAttData CheckAttData) {

        AppealDialog dialog = new AppealDialog();
        Bundle msg = new Bundle();
        Log.e("AppealDialog", CheckAttData.toStirng());
        msg.putSerializable("data", CheckAttData);
        dialog.setArguments(msg);
        return dialog;

    }


    public interface AppealDialogInputListener
    {
        void onAppealDialogInputComplete(
                String RemarkMSG,
                CheckAttData checkAttData
        );
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_appeal, null);

        /*获取传入的序列化好的data数据*/
        mCheckAttData = (CheckAttData) getArguments().getSerializable("data");

        Remark = (EditText) view.findViewById(R.id.appeal_message);
        ClassName = (TextView) view.findViewById(R.id.appeal_class_name);
        ClassDate = (TextView) view.findViewById(R.id.appeal_date);

        if (mCheckAttData != null) {
            ClassName.setText(mCheckAttData.getS_Name());
            ClassDate.setText(mCheckAttData.getWaterDate());
        }else
            Log.e("mCheckAttData", "is null");

        builder.setView(view)
                .setPositiveButton("申  诉",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                AppealDialogInputListener listener = (AppealDialogInputListener) getActivity();
                                listener.onAppealDialogInputComplete(
                                        Remark.getText().toString(),
                                        mCheckAttData
                                );
                            }
                        })
                .setNegativeButton("取  消", null);
        return builder.create();
    }
}
