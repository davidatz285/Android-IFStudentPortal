package com.example.ifstudentportal.View.Fragment;

import java.util.List;

import id.ac.unpar.siamodels.JadwalKuliah;

public interface IMenuFragment {
    void changePage(int page);
    List<JadwalKuliah> getJadwalKuliahList();
}
