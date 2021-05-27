package id.ac.unpar.ifstudentportal.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.unpar.ifstudentportal.Model.MataKuliahAdapter;
import id.ac.unpar.ifstudentportal.Presenter.Presenter;
import id.ac.unpar.ifstudentportal.View.IHomeActivity;
import id.ac.unpar.siamodels.Mahasiswa;

import com.example.ifstudentportal.R;

import java.util.LinkedList;
import java.util.List;


public class PrasyaratFragment extends Fragment implements IHomeActivity {
    protected Mahasiswa mahasiswa;
    protected RecyclerView recyclerView;
    protected MataKuliahAdapter mataKuliahAdapter;
    protected Presenter presenter;
    protected IHomeActivity ui;
    private String phpSessId;
    private List<Mahasiswa.Nilai> listNilai;
    private Bundle savedState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.prasyarat_fragment, container, false);
        this.recyclerView = view.findViewById(R.id.list_matkul);
        this.mahasiswa = (Mahasiswa) this.getActivity().getIntent().getExtras().getSerializable("mhs");
        this.phpSessId = this.getActivity().getIntent().getExtras().getString("phpSessId");
        this.listNilai = new LinkedList<>();
        this.presenter = new Presenter(this);

        if (this.savedState!=null) {
            this.mahasiswa = (Mahasiswa) this.savedState.getSerializable("mhs_nilai_added");
            this.mataKuliahAdapter = new MataKuliahAdapter(mahasiswa);
            this.recyclerView.setAdapter(mataKuliahAdapter);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            this.presenter.getNilaiMahasiswa(phpSessId, mahasiswa.getNpm());
        }
        //Log.d("phpsessid pras mhs",phpSessId);
        Log.d("npm pras mhs", mahasiswa.getRiwayatNilai().size() + "");

        return view;
    }

    @Override
    public void displayMahasiswaInfo(Mahasiswa mhs) {
        this.mahasiswa = mhs;

    }

    public static PrasyaratFragment newInstance() {
        PrasyaratFragment fragment = new PrasyaratFragment();
        return fragment;
    }

    @Override
    public void changePage(int page) {
        this.ui.changePage(page);
    }

    private Bundle saveState(Mahasiswa mhs) { /* called either from onDestroyView() or onSaveInstanceState() */
        Bundle state = new Bundle();
        state.putSerializable("mhs_nilai_added", mhs);
        return state;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        savedState = saveState(this.mahasiswa); /* vstup defined here for sure */
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("mhs_nilai_added", mahasiswa);
    }

    @Override
    public void setMahasiswaForFragment(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
        this.mataKuliahAdapter = new MataKuliahAdapter(mahasiswa);
        this.recyclerView.setAdapter(mataKuliahAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.saveState(mahasiswa);


    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
