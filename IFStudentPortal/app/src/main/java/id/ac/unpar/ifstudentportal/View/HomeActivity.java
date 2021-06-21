package id.ac.unpar.ifstudentportal.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import id.ac.unpar.ifstudentportal.Presenter.Presenter;
import com.example.ifstudentportal.R;
import id.ac.unpar.ifstudentportal.View.Fragment.HomeFragment;
import id.ac.unpar.ifstudentportal.View.Fragment.JadwalFragment;
import id.ac.unpar.ifstudentportal.View.Fragment.KelulusanFragment;
import id.ac.unpar.ifstudentportal.View.Fragment.PrasyaratFragment;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.TahunSemester;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class HomeActivity extends AppCompatActivity implements IHomeActivity {
    protected FragmentManager fm;
    protected Fragment[] fragments;
    protected Presenter presenter;
    protected Mahasiswa mahasiswa;
    protected HomeFragment homeFragment;
    protected JadwalFragment jadwalFragment;
    protected PrasyaratFragment prasyaratFragment;
    protected KelulusanFragment kelulusanFragment;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        this.presenter = new Presenter(this);
        this.fragments = new Fragment[4];
        this.homeFragment = HomeFragment.newInstance();
        this.homeFragment.setPresenter(presenter);
        this.jadwalFragment = JadwalFragment.newInstance();
        this.bundle = getIntent().getExtras();
        String phpSessId = bundle.getString("phpSessId");
        this.jadwalFragment.setPresenter(presenter);
        Mahasiswa mahasiswa = (Mahasiswa) bundle.getSerializable("mhs");
        this.presenter.getMahasiswaInfo(phpSessId,mahasiswa.getNpm());
        this.prasyaratFragment = PrasyaratFragment.newInstance();
        this.prasyaratFragment.setPresenter(presenter);
        this.kelulusanFragment = KelulusanFragment.newInstance();
        this.kelulusanFragment.setPresenter(presenter);
        this.fragments[0] = homeFragment;
        this.fragments[1] = jadwalFragment;
        this.fragments[2] = prasyaratFragment;
        this.fragments[3] = kelulusanFragment;
        this.fm = getSupportFragmentManager();
        FragmentTransaction ft = this.fm.beginTransaction();
        ft.add(R.id.fragment_container, this.fragments[0]).commit();
        AndroidThreeTen.init(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void displayMahasiswaInfo(Mahasiswa mahasiswa){
        this.mahasiswa = mahasiswa;
        this.homeFragment.setMahasiswa(mahasiswa);
        this.homeFragment.displayMahasiswaInfo(mahasiswa);
        this.jadwalFragment.setJadwalKuliahList(mahasiswa.getJadwalKuliahList());
    }

    public void changePage(int page){
        fm = getSupportFragmentManager();
        FragmentTransaction ft = this.fm.beginTransaction();
        if(page==1){
            ft.remove(fragments[0]);
            ft.add(R.id.fragment_container,fragments[1]);
            ft.show(fragments[1]);
            ft.addToBackStack(null);
            ft.commit();
        }else if(page==2){
            ft.remove(fragments[0]);
            ft.add(R.id.fragment_container,fragments[2]);
            ft.show(fragments[2]);
            ft.addToBackStack(null);
            ft.commit();
        }else if(page==3){
            ft.remove(fragments[0]);
            ft.add(R.id.fragment_container,fragments[3]);
            ft.show(fragments[3]);
            ft.addToBackStack(null);
            ft.commit();
        }else{
            this.presenter.logout();
            this.bundle.clear();
            Intent i = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void setMahasiswaForFragment(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
        this.prasyaratFragment.setMahasiswaForFragment(mahasiswa);
        this.kelulusanFragment.setMahasiswaForFragment(mahasiswa);
    }

    @Override
    public void setTahunSemesterForFragment(TahunSemester tahunSemester) {

    }



}
