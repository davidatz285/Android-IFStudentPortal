package com.example.ifstudentportal.View;

import android.content.Context;

import id.ac.unpar.siamodels.Mahasiswa;

public interface IHomeActivity {
    Context getContext();
    void displayMahasiswaInfo(Mahasiswa mahasiswa);
    void changePage(int page);
}
