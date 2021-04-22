package com.example.ifstudentportal.View.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ifstudentportal.Presenter.Presenter;
import com.example.ifstudentportal.R;
import com.example.ifstudentportal.View.HomeActivity;
import com.example.ifstudentportal.View.IHomeActivity;
import com.mikhaellopez.circularimageview.CircularImageView;

import id.ac.unpar.siamodels.Mahasiswa;

public class HomeFragment extends Fragment implements View.OnClickListener, IHomeActivity {
    protected TextView tvNama;
    protected TextView tvNPM;
    protected CircularImageView photo;
    protected Presenter presenter;
    protected Mahasiswa mahasiswa;
    protected FragmentManager fm;

    protected Button btnJadwal;
    protected Button btnKelulusan;
    protected Button btnLogout;
    protected String phpSessId;
    protected IHomeActivity ui;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        this.tvNama = view.findViewById(R.id.id_fullName_TextView);
        this.tvNPM = view.findViewById(R.id.id_NPM_TextView);
        this.photo = view.findViewById(R.id.id_Profile_Image);
        this.btnJadwal = view.findViewById(R.id.btn_jadwal);
        this.btnJadwal.setOnClickListener(this);
        this.ui = (HomeActivity)getContext();
       // this.mahasiswa = getActivity()
        //Bundle bundle = getActivity().getIntent().getExtras();
        //this.phpSessId = bundle.getString("phpSessId");
        //this.mahasiswa = (Mahasiswa) bundle.getSerializable("mhs");


        //this.fm = getFragmentManager();
       // this.ft = getChildFragmentManager().beginTransaction();
        //this.ft.add(R.id.container_menu, fragmentList[0]).addToBackStack("f0").commit();
//        this.mahasiswa = (Mahasiswa) getArguments().get("mhs");
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("mhs",this.mahasiswa);
//        this.fragmentList[1].setArguments(bundle);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //displayMahasiswaInfo(this.mahasiswa);

    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
        this.tvNama.setText(mahasiswa.getNama());
        this.tvNPM.setText(mahasiswa.getNpm());
    }
    public void onClick(View v) {
        if(v==this.btnJadwal){
            this.changePage(1);
        }
    }

    @Override
    public void displayMahasiswaInfo(Mahasiswa mhs) {
        this.mahasiswa = mhs;
        this.tvNama.setText(mhs.getNama());
        this.tvNPM.setText(mhs.getNpm());
    }

    @Override
    public void changePage(int page) {
        this.ui.changePage(page);
    }
}
