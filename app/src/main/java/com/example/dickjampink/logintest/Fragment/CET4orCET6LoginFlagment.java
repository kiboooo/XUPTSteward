package com.example.dickjampink.logintest.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.dickjampink.logintest.R;

/**
 * Created by Kiboooo on 2017/7/28.
 */

public class CET4orCET6LoginFlagment extends DialogFragment {
    private EditText Number;
    private EditText Name;

    public interface Cet4OrCet6LoginInputListener
    {
        void onCLoginInputComplete(
                String username,
                String number
                );

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_check4or6, null);

        Name = (EditText) view.findViewById(R.id.CETname);
        Number = (EditText) view.findViewById(R.id.number);

        builder.setView(view)
                .setPositiveButton("查  询",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                Cet4OrCet6LoginInputListener listener = (Cet4OrCet6LoginInputListener) getActivity();
                                listener.onCLoginInputComplete(
                                        Name.getText().toString(),
                                        Number.getText().toString());
                            }
                        })
                .setNegativeButton("取  消", null);
        return builder.create();
    }
}
