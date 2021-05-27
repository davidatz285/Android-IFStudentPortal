package id.ac.unpar.ifstudentportal.Presenter;

import id.ac.unpar.ifstudentportal.Model.Scrapper;
import id.ac.unpar.ifstudentportal.View.IHomeActivity;
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

    public void getNilaiMahasiswa(String phpSessId, String npm){
        this.scrapper.getNilaiMahasiswa(phpSessId,npm);
    }

    public HomeManager() {
    }

    public void setMahasiswaForFragment(Mahasiswa mahasiswa) {
        this.iHomeActivity.setMahasiswaForFragment(mahasiswa);
    }
}
