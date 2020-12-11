package com.example.ifstudentportal.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ifstudentportal.Model.Adapter.ProfilePagerAdapter;
import com.example.ifstudentportal.Presenter.Presenter;
import com.example.ifstudentportal.R;
import com.google.android.material.tabs.TabLayout;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;
import java.io.InputStream;

import id.ac.unpar.siamodels.Mahasiswa;

public class HomeActivity extends AppCompatActivity implements IHomeActivity {
    protected TextView tvNama;
    protected TextView tvNPM;
    protected CircularImageView photo;
    protected Presenter presenter;
    protected Mahasiswa mahasiswa;
    protected TabLayout tabLayout;
    protected ViewPager viewPager;
    protected ProfilePagerAdapter viewPagerAdapter;
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
       // Log.d("mhs lala",this.mahasiswa.getNama());
        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ProfilePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabRippleColor(null);
        this.presenter = new Presenter(this);
//        this.presenter.setHomeActivity(this);
        this.presenter.getMahasiswaInfo(phpSessId,mahasiswa.getNpm());
        //Log.d("foto",this.mahasiswa.getPhotoPath());
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

}
