package com.lab2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;

public class MyAdapter extends FragmentPagerAdapter {

    private int mCurrentPos;

    private JSONArray mData;

    MyAdapter(FragmentManager fm) {
        super(fm);

        mData = new JSONArray();
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        mCurrentPos = position;

        String graphic = "", name = "", helptext = "";

        try {
            graphic = mData.getJSONObject(position).getString("graphic");
            name = mData.getJSONObject(position).getString("name");

            if (mData.getJSONObject(position).has("helptext")) {
                helptext = mData.getJSONObject(position).getString("helptext");
            }
        } catch (JSONException ignored) {
        }

        return PageFragment.newInstance(position, graphic, name, helptext);
    }

    @Override
    public int getCount() {
        return mData.length();
    }

    void setData(JSONArray data) {
        mData = data;
        notifyDataSetChanged();
    }

    int getCurrentPos() {
        return mCurrentPos;
    }
}