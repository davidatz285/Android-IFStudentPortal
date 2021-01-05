package com.example.ifstudentportal.Model.Adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ifstudentportal.View.Fragment.LogOutFragment;
import com.example.ifstudentportal.View.Fragment.MenuFragment;
import com.example.ifstudentportal.View.Fragment.ProfileFragment;


public class ProfilePagerAdapter extends FragmentStateAdapter {
    protected Fragment[] fragments;

    public Fragment[] getFragments() {
        return fragments;
    }

    public ProfilePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.fragments = new Fragment[3];
        this.fragments[0] = MenuFragment.newInstance();
        this.fragments[1] = ProfileFragment.newInstance();
        this.fragments[2] = LogOutFragment.newInstance();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return this.fragments[position];
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

