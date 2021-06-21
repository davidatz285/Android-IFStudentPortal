package id.ac.unpar.ifstudentportal.View.Fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import id.ac.unpar.ifstudentportal.Presenter.Presenter;
import id.ac.unpar.ifstudentportal.View.HomeActivity;
import id.ac.unpar.ifstudentportal.View.IHomeActivity;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.TahunSemester;
import com.example.ifstudentportal.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.io.IOException;


public class HomeFragment extends Fragment implements View.OnClickListener, IHomeActivity {
    protected TextView tvNama;
    protected TextView tvNPM;
    protected CircularImageView photo;
    protected Presenter presenter;
    protected Mahasiswa mahasiswa;
    protected FragmentManager fm;
	protected Button btnPrasyarat;
    protected Button btnJadwal;
    protected Button btnKelulusan;
    protected Button btnLogout;
    protected String phpSessId;
    protected IHomeActivity ui;
    protected Bundle savedState;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        this.tvNama = view.findViewById(R.id.id_fullName_TextView);
        this.tvNPM = view.findViewById(R.id.id_NPM_TextView);
        this.photo = view.findViewById(R.id.id_Profile_Image);
        this.btnJadwal = view.findViewById(R.id.btn_jadwal);
        this.btnPrasyarat = view.findViewById(R.id.btn_prasyarat);
        this.btnKelulusan = view.findViewById(R.id.btn_ringkasan);
        this.btnLogout = view.findViewById(R.id.btn_logout);
        this.ui = (HomeActivity)getContext();
        if (savedState != null) {
            this.tvNama.setText(savedState.getSerializable("nama").toString());
            this.tvNPM.setText(savedState.getSerializable("npm").toString());
        }
        this.btnJadwal.setOnClickListener(this);
        this.btnPrasyarat.setOnClickListener(this);
        this.btnKelulusan.setOnClickListener(this);
        this.btnLogout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedState != null) {
            this.tvNama.setText(savedState.getSerializable("nama").toString());
            this.tvNPM.setText(savedState.getSerializable("npm").toString());
        }
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
        this.tvNama.setText(mahasiswa.getNama());
        this.tvNPM.setText(mahasiswa.getNpm());
        this.savedState = saveState();
    }

    private Bundle saveState() { /* called either from onDestroyView() or onSaveInstanceState() */
        Bundle state = new Bundle();
        state.putSerializable("nama", mahasiswa.getNama());
        state.putSerializable("npm", mahasiswa.getNpm());
        return state;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        savedState = saveState(); /* vstup defined here for sure */
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("nama", mahasiswa.getNama());
        outState.putSerializable("npm", mahasiswa.getNpm());

    }

    public void onClick(View v) {
        if(v==this.btnJadwal){
            this.changePage(1);
        }else if(v==this.btnPrasyarat){
            this.changePage(2);
        }
        else if(v==this.btnKelulusan){
            this.changePage(3);
        }else{
            this.changePage(4);
        }
    }

    @Override
    public void displayMahasiswaInfo(Mahasiswa mhs) {
        this.mahasiswa = mhs;
        this.tvNama.setText(mhs.getNama());
        this.tvNPM.setText(mhs.getNpm());
        try{
            if(mhs.getPhotoPath().length()>30){
                byte[] bytePhoto = mhs.getPhotoImage();
                this.photo.setImageBitmap(BitmapFactory.decodeByteArray(bytePhoto,0,bytePhoto.length));
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changePage(int page) {
        this.ui.changePage(page);
    }

    @Override
    public void setMahasiswaForFragment(Mahasiswa mahasiswa) {
    }

    @Override
    public void setTahunSemesterForFragment(TahunSemester tahunSemester) {
    }

}
