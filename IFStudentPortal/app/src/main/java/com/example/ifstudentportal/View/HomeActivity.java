package com.example.ifstudentportal.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ifstudentportal.Model.Adapter.ProfilePagerAdapter;
import com.example.ifstudentportal.Presenter.Presenter;
import com.example.ifstudentportal.R;
import com.example.ifstudentportal.View.Fragment.IMenuFragment;
import com.example.ifstudentportal.View.Fragment.JadwalFragment;
import com.example.ifstudentportal.View.Fragment.MenuFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.mikhaellopez.circularimageview.CircularImageView;


import java.util.List;

import id.ac.unpar.siamodels.JadwalKuliah;
import id.ac.unpar.siamodels.Mahasiswa;

public class HomeActivity extends AppCompatActivity implements IHomeActivity, IMenuFragment {
    protected TextView tvNama;
    protected TextView tvNPM;
    protected CircularImageView photo;
    protected Presenter presenter;
    protected Mahasiswa mahasiswa;
    protected TabLayout tabLayout;
    protected ViewPager2 viewPager;
    protected ProfilePagerAdapter viewPagerAdapter;
    protected FragmentManager fm;
    protected Fragment[] fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        this.tvNama = findViewById(R.id.id_fullName_TextView);
       // this.tvEmail = findViewById(R.id.tv_email);
        this.tvNPM = findViewById(R.id.id_NPM_TextView);
        this.photo = findViewById(R.id.id_Profile_Image);
        Bundle bundle = getIntent().getExtras();
        String phpSessId = bundle.getString("phpSessId");
        this.mahasiswa = (Mahasiswa) bundle.getSerializable("mhs");
        Log.d("mhs npm",this.mahasiswa.getNpm());

        //Log.d("mhs lala",this.mahasiswa.getNama());
        viewPager = findViewById(R.id.viewPager);
        this.viewPagerAdapter = new ProfilePagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = findViewById(R.id.sliding_tabs);
        new TabLayoutMediator(tabLayout,viewPager, (tab, position) -> {
            if(position == 0)
                tab.setText("Menu");
            else if(position == 1)
                tab.setText("Profile");
            else tab.setText("Log Out");
        }).attach();
        tabLayout.setTabRippleColor(null);
        this.presenter = new Presenter(this);
//        this.presenter.setHomeActivity(this);
        this.presenter.getMahasiswaInfo(phpSessId,mahasiswa.getNpm());
        this.fragments = viewPagerAdapter.getFragments();

//        this.fragments[0] = MenuFragment.newInstance();
//        this.fragments[1] = JadwalFragment.newInstance();
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.root_container, fragments[0]);
//        ft.commit();
        //Log.d("foto",this.mahasiswa.getPhotoPath());
        AndroidThreeTen.init(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void displayMahasiswaInfo(Mahasiswa mahasiswa){
        //Log.d("homesiswa", this.mahasiswa.getNama());
        this.tvNama.setText(mahasiswa.getNama());
        //this.tvEmail.setText(tvEmail.getText() + " " + mahasiswa.getEmailAddress());
        this.tvNPM.setText(mahasiswa.getNpm());
//        this.mahasiswa.setJadwalKuliahList(mahasiswa.getJadwalKuliahList());
//        List<JadwalKuliah> list = this.mahasiswa.getJadwalKuliahList();
//        for(int i=0;i<list.size();i++){
//            Log.d("jad",list.get(i).getMataKuliah().getNama());
//        }
//        Bundle aa = new Bundle();
//        aa.putSerializable("mhs",this.mahasiswa);
//        fragments[0].setArguments(aa);
        /*
        saat photo  di student  portal  suda normal, uncomment bagian ini
        try{
            byte[] arrayPhotoByte  =  this.mahasiswa.getPhotoImage();
            this.photo.setImageBitmap(BitmapFactory.decodeByteArray(arrayPhotoByte,0,arrayPhotoByte.length));
        }catch (IOException e){
            e.printStackTrace();
        }
        */



    }
    public void changePage(int page){
//        fm = getSupportFragmentManager();
//        FragmentTransaction ft = this.fm.beginTransaction();
//        if(page==0){
//            //ft.replace(R.id.container_menu,JadwalFragment.newInstance());
//            ft.remove(fragments[0]);
//            ft.hide(fragments[0]);
//            ft.add(R.id.container_menu,fragments[1]);
//            ft.show(fragments[1]);
//            ft.addToBackStack(null);
//            ft.commit();
//            Log.d("click","clickedddd");
//        }
        MenuFragment mf = (MenuFragment) fragments[0];
        mf.changePage(0);
    }

    @Override
    public List<JadwalKuliah> getJadwalKuliahList() {
        return null;
    }

    @Override
    public void onBackPressed() {
        // if there is a fragment and the back stack of this fragment is not empty,
        // then emulate 'onBackPressed' behaviour, because in default, it is not working
        FragmentManager fm = getSupportFragmentManager();
        for (Fragment frag : fm.getFragments()) {
            if (frag.isVisible()) {
                FragmentManager childFm = frag.getChildFragmentManager();
                if (childFm.getBackStackEntryCount() > 0) {
                    childFm.popBackStack();
                    return;
                }
            }
        }
        super.onBackPressed();
    }
}
