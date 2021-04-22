package com.example.ifstudentportal.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ifstudentportal.Presenter.Presenter;
import com.example.ifstudentportal.R;
import com.example.ifstudentportal.View.Fragment.HomeFragment;
import com.example.ifstudentportal.View.Fragment.JadwalFragment;
import com.jakewharton.threetenabp.AndroidThreeTen;


import id.ac.unpar.siamodels.Mahasiswa;

public class HomeActivity extends AppCompatActivity implements IHomeActivity {

    protected FragmentManager fm;
    protected Fragment[] fragments;
    protected Button btnPrasyarat;
    protected Button btnJadwal;
    protected Presenter presenter;
    protected Mahasiswa mahasiswa;
    protected HomeFragment homeFragment;
    protected JadwalFragment jadwalFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        this.presenter = new Presenter(this);
        this.fragments = new Fragment[4];
        homeFragment = HomeFragment.newInstance();
        homeFragment.setPresenter(presenter);
        jadwalFragment = JadwalFragment.newInstance();
        Bundle bundle = getIntent().getExtras();
        String phpSessId = bundle.getString("phpSessId");
        jadwalFragment.setPresenter(presenter,phpSessId);
        Mahasiswa mahasiswa = (Mahasiswa) bundle.getSerializable("mhs");
        presenter.getMahasiswaInfo(phpSessId,mahasiswa.getNpm());

        //this.displayMahasiswaInfo(mahasiswa);
        this.fragments[0] = homeFragment;
        this.fragments[1] = jadwalFragment;
        //Log.d("mhs npm",this.mahasiswa.getNpm());

//        this.presenter.setHomeActivity(this);
        //this.fragments = viewPagerAdapter.getFragments();

        this.fm = getSupportFragmentManager();
        FragmentTransaction ft = this.fm.beginTransaction();
        ft.add(R.id.fragment_container, this.fragments[0]).addToBackStack(null).commit();
        AndroidThreeTen.init(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void displayMahasiswaInfo(Mahasiswa mahasiswa){
        this.mahasiswa = mahasiswa;
        this.homeFragment.setMahasiswa(mahasiswa);
        this.homeFragment.displayMahasiswaInfo(mahasiswa);

    }
    public void changePage(int page){
        fm = getSupportFragmentManager();
        FragmentTransaction ft = this.fm.beginTransaction();
        if(page==1){
            ft.remove(fragments[0]);
            ft.add(R.id.fragment_container,fragments[1]);
            ft.show(fragments[1]);
            ft.addToBackStack(null);
            ft.commit();
            Log.d("click","clickedddd");
        }

    }


//    @Override
//    public void onBackPressed() {
//        // if there is a fragment and the back stack of this fragment is not empty,
//        // then emulate 'onBackPressed' behaviour, because in default, it is not working
//        FragmentManager fm = getSupportFragmentManager();
//        for (Fragment frag : fm.getFragments()) {
//            if (frag.isVisible()) {
//                FragmentManager childFm = frag.getChildFragmentManager();
//                if (childFm.getBackStackEntryCount() > 0) {
//                    childFm.popBackStack();
//                    return;
//                }
//            }
//        }
//        super.onBackPressed();
//    }


}
