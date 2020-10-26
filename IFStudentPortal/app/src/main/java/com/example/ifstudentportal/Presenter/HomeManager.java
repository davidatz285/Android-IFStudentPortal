package com.example.ifstudentportal.Presenter;

import com.example.ifstudentportal.Model.Scrapper;
import com.example.ifstudentportal.View.IHomeActivity;
import id.ac.unpar.siamodels.Mahasiswa;

public class HomeManager {
    protected IHomeActivity iHomeActivity;
    protected Scrapper scrapper;

    public HomeManager(IHomeActivity iHomeActivity) {
        this.iHomeActivity = iHomeActivity;
        this.scrapper = new Scrapper(iHomeActivity.getContext(),this);
    }

    public void getMahasiswaInfo(String phpSessId, String npm) {
        this.scrapper.getMahasiswaInfo(phpSessId, npm);
    }
    public void displayMahasiswaInfo(Mahasiswa mahasiswa) {
        this.iHomeActivity.displayMahasiswaInfo(mahasiswa);
    }
    public HomeManager() {
    }
}
