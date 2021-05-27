package id.ac.unpar.ifstudentportal.View.Fragment;

import android.content.Context;

import id.ac.unpar.siamodels.Mahasiswa;

public interface IPrasyaratFragment {
    Context getContext();
    void changePage(int page);
    void displayNilaiMahasiswa(Mahasiswa mahasiswa);
}
