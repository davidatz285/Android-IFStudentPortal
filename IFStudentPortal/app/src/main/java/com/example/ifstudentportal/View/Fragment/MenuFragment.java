package com.example.ifstudentportal.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ifstudentportal.R;
import com.example.ifstudentportal.View.HomeActivity;
import com.example.ifstudentportal.View.IHomeActivity;

import id.ac.unpar.siamodels.Mahasiswa;

public class MenuFragment extends Fragment implements IMenuFragment {
    private IMenuFragment ui;
    private Fragment[] fragmentList;
    private FragmentManager fm;
    FragmentTransaction ft;
    protected Mahasiswa mahasiswa;
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.menu_fragment, container, false);

        this.ui = (HomeActivity) getContext();
        Log.d("uiaaaaaaa", ui.toString());
        this.fragmentList = new Fragment[3];
        this.fragmentList[0] = MenuButtonsFragment.newInstance();
        this.fragmentList[1] = JadwalFragment.newInstance();
        //this.fm = getFragmentManager();
        this.ft = getChildFragmentManager().beginTransaction();
        this.ft.add(R.id.container_menu, fragmentList[0]).addToBackStack("f0").commit();
//        this.mahasiswa = (Mahasiswa) getArguments().get("mhs");
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("mhs",this.mahasiswa);
//        this.fragmentList[1].setArguments(bundle);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IMenuFragment) {
            ui = (IMenuFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ui = null;
    }


    @Override
    public void changePage(int page) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        if (page == 0) {
            if (fragmentList[1].isAdded()) {
                ft.show(fragmentList[1]).addToBackStack(null);
            } else {
                ft.add(R.id.container_menu, fragmentList[1]).addToBackStack("fragment 1");
            }
            if (fragmentList[0].isAdded()) {
                ft.hide(fragmentList[0]);
            }

//                ft.hide(fragmentList[0]);
//                ft.add(R.id.container_menu,fragmentList[1]);
//                ft.show(fragmentList[1]);
//                ft.addToBackStack(null);
            Log.d("click lala", "lalalalala");
        }
        ft.commit();
    }

}

