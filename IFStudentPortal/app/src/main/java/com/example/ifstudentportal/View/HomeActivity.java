package com.example.ifstudentportal.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ifstudentportal.Presenter.Presenter;
import com.example.ifstudentportal.R;

import id.ac.unpar.siamodels.Mahasiswa;

public class HomeActivity extends AppCompatActivity implements IHomeActivity {
    protected TextView tvNama;
    protected TextView tvEmail;
    protected TextView tvNPM;
    protected Presenter presenter;
    protected Mahasiswa mahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        this.tvNama = findViewById(R.id.tv_nama);
        this.tvEmail = findViewById(R.id.tv_email);
        this.tvNPM = findViewById(R.id.tv_npm);
        Bundle bundle = getIntent().getExtras();
        String phpSessId = bundle.getString("phpSessId");
        this.mahasiswa = (Mahasiswa) bundle.getSerializable("mhs");
        Log.d("mhs npm",this.mahasiswa.getNpm());
       // Log.d("mhs lala",this.mahasiswa.getNama());
        this.presenter = new Presenter(this);
//        this.presenter.setHomeActivity(this);
        this.presenter.getMahasiswaInfo(phpSessId,mahasiswa.getNpm());
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void displayMahasiswaInfo(Mahasiswa mahasiswa) {
        //Log.d("homesiswa", this.mahasiswa.getNama());
        this.tvNama.setText(tvNama.getText() + " " + mahasiswa.getNama());
        this.tvEmail.setText(tvEmail.getText() + " " + mahasiswa.getEmailAddress());
        this.tvNPM.setText(tvNPM.getText() + " " + mahasiswa.getNpm());
    }

}
