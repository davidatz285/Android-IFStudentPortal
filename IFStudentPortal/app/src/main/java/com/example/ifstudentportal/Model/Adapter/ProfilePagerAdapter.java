package com.example.ifstudentportal.Model.Adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class ProfilePagerAdapter extends FragmentPagerAdapter {

    public ProfilePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
//                return new viewAboutFragment();
//            case 1:
//                return new viewSettingFragment();
//            case 2:
//                return new Post();
//            default:
//                return null;
        return new Fragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "About";
            case 1:
                return "Profile";
            case 2:
                return "Post";
            default:
                return null;
        }
    }
}

