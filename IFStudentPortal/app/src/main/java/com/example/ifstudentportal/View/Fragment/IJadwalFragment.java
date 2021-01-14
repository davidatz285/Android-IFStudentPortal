package com.example.ifstudentportal.View.Fragment;

import android.content.Context;

import java.util.List;

import id.ac.unpar.siamodels.JadwalKuliah;

public interface IJadwalFragment {
    Context getContext();
    void setJadwalKuliahList(List<JadwalKuliah> jadwalKuliahs);
}
