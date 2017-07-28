package com.example.dickjampink.logintest.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dickjampink.logintest.Fragment.AttendanceFragment;

/**
 * Created by Kiboooo on 2017/7/14.
 *
 */

public class CheckAdapter extends FragmentPagerAdapter {
    private String[] cTitle = {"教室使用情况","考勤查询", "考勤总结"};
    private Context mContext;

    public CheckAdapter(FragmentManager fm,Context c ) {
        super(fm);
        mContext = c;
    }

    @Override
    public Fragment getItem(int position) {
        return AttendanceFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return cTitle.length;
    }

    public Context getmContext() {
        return mContext;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return cTitle[position];
    }
}
