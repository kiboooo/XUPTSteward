package com.example.dickjampink.logintest.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.bean.LoginData;

import org.litepal.crud.DataSupport;

import static com.example.dickjampink.logintest.R.id.username;
import static com.example.dickjampink.logintest.Request.RequsetZF.getCheckImag;

/**
 * Created by Kiboooo on 2017/7/18.
 */

public class LoginDialogFlagment extends DialogFragment {

    private EditText mUsername;
    private EditText mPassword;
    private EditText mChecknumber;

    private ImageView mCheckImag;
    private LoginData loginData;


    public interface LoginInputListener
    {
        void onLoginInputComplete(
                String username,
                String password,
                String check_number);

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.login_dialog, null);

        loginData = new LoginData();
        loginData = DataSupport.findLast(LoginData.class);

        mUsername = (EditText) view.findViewById(username);
        mPassword = (EditText) view.findViewById(R.id.userpass);
        mChecknumber = (EditText) view.findViewById(R.id.checknum);
        mCheckImag = (ImageView) view.findViewById(R.id.checkimag);

        mUsername.setText(loginData.getStudentID());

        mCheckImag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCheckImag(getActivity(),mCheckImag);
            }
        });

        getCheckImag(getActivity(),mCheckImag);
        builder.setView(view)
                .setPositiveButton("登  录",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                LoginInputListener listener = (LoginInputListener) getActivity();
                                listener.onLoginInputComplete(
                                        mUsername.getText().toString(),
                                        mPassword.getText().toString(),
                                        mChecknumber.getText().toString());
                            }
                        })
                .setNegativeButton("取  消", null);
        return builder.create();
    }
}
