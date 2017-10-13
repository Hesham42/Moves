package com.example.hesham.moves.page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hesham on 10/13/2017.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<Fragment>();
        fragments.add(new Popular());
        fragments.add(new Favourit());
        fragments.add(new TopRate());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
