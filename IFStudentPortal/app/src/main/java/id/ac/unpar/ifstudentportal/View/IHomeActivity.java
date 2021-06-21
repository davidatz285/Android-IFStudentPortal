package id.ac.unpar.ifstudentportal.View;

import android.content.Context;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.TahunSemester;


public interface IHomeActivity {
    Context getContext();
    void displayMahasiswaInfo(Mahasiswa mahasiswa);
    void changePage(int page);
    void setMahasiswaForFragment(Mahasiswa mahasiswa);
    void setTahunSemesterForFragment(TahunSemester tahunSemester);
}
