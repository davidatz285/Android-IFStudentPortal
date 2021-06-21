package id.ac.unpar.ifstudentportal.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ifstudentportal.R;
import id.ac.unpar.ifstudentportal.Model.KelulusanAdapter;
import id.ac.unpar.ifstudentportal.Presenter.Presenter;
import id.ac.unpar.ifstudentportal.View.IHomeActivity;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.TahunSemester;

public class KelulusanFragment extends Fragment implements IHomeActivity {
    protected TextView tvStatus;
    protected RecyclerView recyclerView;
    protected KelulusanAdapter kelulusanAdapter;
    protected Mahasiswa mahasiswa;
    protected Presenter presenter;
    protected IHomeActivity ui;
    private String phpSessId;
    private Bundle savedState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kelulusan_fragment, container, false);
        this.recyclerView = view.findViewById(R.id.rv_alasan);
        this.tvStatus = view.findViewById(R.id.tv_status);
        this.mahasiswa = (Mahasiswa) this.getActivity().getIntent().getExtras().getSerializable("mhs");
        this.phpSessId = this.getActivity().getIntent().getExtras().getString("phpSessId");
        this.presenter = new Presenter(this);
        if (this.savedState!=null) {
            this.mahasiswa = (Mahasiswa) this.savedState.getSerializable("mhslulus_nilai_added");
            this.kelulusanAdapter = new KelulusanAdapter(mahasiswa);
            this.recyclerView.setAdapter(kelulusanAdapter);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            String status = "";
            if(kelulusanAdapter.bisaLulus()){
                status = "Anda sudah memenuhi syarat untuk lulus.";
            }else if(kelulusanAdapter.getAlasan().size() != 0){
                status = "Anda belum memenuhi syarat untuk lulus:";
            }
            this.tvStatus.setText(status);
        } else {
            this.presenter.getNilaiMahasiswa(phpSessId, mahasiswa.getNpm());
        }
        return view;
    }

    public static KelulusanFragment newInstance(){
        return new KelulusanFragment();
    }

    @Override
    public void displayMahasiswaInfo(Mahasiswa mahasiswa) {

    }

    @Override
    public void changePage(int page) {
        this.ui.changePage(page);
    }

    @Override
    public void setMahasiswaForFragment(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
        this.kelulusanAdapter = new KelulusanAdapter(mahasiswa);
        this.recyclerView.setAdapter(kelulusanAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.saveState(mahasiswa);
        String status = "";
        if(kelulusanAdapter.bisaLulus()){
            status = "Anda sudah memenuhi syarat untuk lulus.";
        }else if(kelulusanAdapter.getAlasan().size() != 0){
            status = "Anda belum memenuhi syarat untuk lulus:";
        }
        this.tvStatus.setText(status);
    }

    @Override
    public void setTahunSemesterForFragment(TahunSemester tahunSemester) {
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
    private Bundle saveState(Mahasiswa mhs) {
        Bundle state = new Bundle();
        state.putSerializable("mhslulus_nilai_added", mhs);
        return state;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        savedState = saveState(this.mahasiswa);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("mhslulus_nilai_added", mahasiswa);
    }
}
