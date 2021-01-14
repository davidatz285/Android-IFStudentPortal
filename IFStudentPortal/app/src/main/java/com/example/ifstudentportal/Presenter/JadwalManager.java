package com.example.ifstudentportal.Presenter;

import com.example.ifstudentportal.Model.Scrapper;
import com.example.ifstudentportal.View.Fragment.IJadwalFragment;

import java.util.List;

import id.ac.unpar.siamodels.JadwalKuliah;

public class JadwalManager {
    private IJadwalFragment iJadwalFragment;
    protected Scrapper scrapper;
    public JadwalManager(IJadwalFragment iJadwalFragment) {
        this.iJadwalFragment =  iJadwalFragment;
        this.scrapper = new Scrapper(iJadwalFragment.getContext(), this);
    }

    public void getListJadwal(String phpSessId){
        this.scrapper.getListJadwal(phpSessId);
    }

    public void setListJadwal(List<JadwalKuliah> jadwalKuliahs) {
        this.iJadwalFragment.setJadwalKuliahList(jadwalKuliahs);
    }
}
