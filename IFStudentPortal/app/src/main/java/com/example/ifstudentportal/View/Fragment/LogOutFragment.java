package com.example.ifstudentportal.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.ifstudentportal.R;

public class LogOutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.logout_fragment, container, false);
        return view;
    }

    public static LogOutFragment newInstance() {
        LogOutFragment fragment = new LogOutFragment();
        return fragment;
    }
}
