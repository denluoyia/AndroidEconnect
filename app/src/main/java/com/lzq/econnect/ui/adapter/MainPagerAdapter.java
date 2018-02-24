package com.lzq.econnect.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lzq.econnect.ui.fragment.CommunityFragment;
import com.lzq.econnect.ui.fragment.ControlPCFragment;
import com.lzq.econnect.ui.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager / Fragment适配器
 * Created by lzq on 2017/3/19.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments = new ArrayList<>();

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new HomeFragment());
        fragments.add(new ControlPCFragment());
        fragments.add(new CommunityFragment());
    }

    @Override
    public Fragment getItem(int position) {
        if (position < 0 || position >= 3) return null;
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
