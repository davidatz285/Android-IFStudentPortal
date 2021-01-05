package com.example.ifstudentportal.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.ifstudentportal.R;
import com.example.ifstudentportal.View.IHomeActivity;

public class MenuButtonsFragment extends Fragment implements View.OnClickListener {
    private Button btnJadwal;
    private IMenuFragment ui;
    private Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.menu_buttons_fragment, container, false);
        this.btnJadwal = view.findViewById(R.id.btn_jadwal);
        this.ui = (IMenuFragment) getContext();
        this.btnJadwal.setOnClickListener(this);
//        this.ui = get;
        return view;
    }
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        mContext = context;
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mContext = null;
//    }
    public static MenuButtonsFragment newInstance() {
        MenuButtonsFragment fragment = new MenuButtonsFragment();
        return fragment;
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==btnJadwal.getId()){
            Log.d("btn jadwal","btn jadwal clicked");
            ui.changePage(0);

        }
    }
}
