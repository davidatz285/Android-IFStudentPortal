package com.example.ifstudentportal.View;

import android.content.Context;

import com.example.ifstudentportal.Model.Wrapper;

import id.ac.unpar.siamodels.Mahasiswa;

public interface ILoginActivity {
    void login(String email,String npm);
    void switchToHomeActivity(Wrapper wrapper);
    Context getContext();
    void displayMahasiswaInfo(Mahasiswa mahasiswa);
}
