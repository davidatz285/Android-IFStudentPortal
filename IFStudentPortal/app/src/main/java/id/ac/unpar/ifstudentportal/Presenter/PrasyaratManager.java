package id.ac.unpar.ifstudentportal.Presenter;

import id.ac.unpar.ifstudentportal.Model.Scrapper;
import id.ac.unpar.ifstudentportal.View.Fragment.IPrasyaratFragment;
import id.ac.unpar.siamodels.Mahasiswa;

public class PrasyaratManager {
    private IPrasyaratFragment iPrasyaratFragment;
    protected Scrapper scrapper;

    public PrasyaratManager(IPrasyaratFragment iPrasyaratFragment) {
        this.iPrasyaratFragment = iPrasyaratFragment;
        this.scrapper = new Scrapper(iPrasyaratFragment.getContext(), this);
    }



}
