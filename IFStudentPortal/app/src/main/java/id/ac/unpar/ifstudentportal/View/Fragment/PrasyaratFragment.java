package id.ac.unpar.ifstudentportal.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.ac.unpar.ifstudentportal.Model.MataKuliahAdapter;
import id.ac.unpar.ifstudentportal.Presenter.Presenter;
import id.ac.unpar.ifstudentportal.View.IHomeActivity;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.TahunSemester;
import com.example.ifstudentportal.R;

public class PrasyaratFragment extends Fragment implements IHomeActivity {
    protected Mahasiswa mahasiswa;
    protected RecyclerView recyclerView;
    protected MataKuliahAdapter mataKuliahAdapter;
    protected Presenter presenter;
    protected IHomeActivity ui;
    private String phpSessId;
    private Bundle savedState;
    protected TextView tvSemester;
    protected TextView tvIPS;
    protected TextView tvIPK;
    protected TextView tvSKSLulus;
    protected TextView tvTOEFL;
    private TahunSemester tahunSemester;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.prasyarat_fragment, container, false);
        this.recyclerView = view.findViewById(R.id.list_matkul);
        this.mahasiswa = (Mahasiswa) this.getActivity().getIntent().getExtras().getSerializable("mhs");
        this.phpSessId = this.getActivity().getIntent().getExtras().getString("phpSessId");
        this.presenter = new Presenter(this);
        this.tvSemester = view.findViewById(R.id.tv_semester);
        this.tvIPS = view.findViewById(R.id.tv_ips);
        this.tvIPK = view.findViewById(R.id.tv_ipk);
        this.tvSKSLulus = view.findViewById(R.id.tv_sks);
        this.tvTOEFL = view.findViewById(R.id.tv_toefl);

        if (this.savedState!=null) {
            this.mahasiswa = (Mahasiswa) this.savedState.getSerializable("mhs_nilai_added");
            this.mataKuliahAdapter = new MataKuliahAdapter(mahasiswa);
            this.recyclerView.setAdapter(mataKuliahAdapter);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            String sem = "SEMESTER "+tahunSemester.getSemester() + " " + tahunSemester.getTahun() + "/"
                    + (tahunSemester.getTahun() + 1);
            this.tvSemester.setText(sem);
            String toefl = this.mahasiswa.getNilaiTOEFL().values().toString();
            this.tvTOEFL.setText("Nilai TOEFL : " + toefl);


            String kode = this.tahunSemester.getKodeDPS();
            TahunSemester smtSebelum;
            if(kode.charAt(2)=='1'){
                String smt = "2";
                String tahun = String.valueOf(Integer.parseInt(kode.substring(0,2))-1);
                smtSebelum = new TahunSemester(tahun.concat(smt));
            }else{
                String smt = "1";
                String tahun = String.valueOf(Integer.parseInt(kode.substring(0,2)));
                smtSebelum = new TahunSemester(tahun.concat(smt));
            }
            if(!this.mahasiswa.getRiwayatNilai().isEmpty()){
                double ipsAngka = this.mahasiswa.calculateIPS(smtSebelum);
                String ips = String.format("%.2f", ipsAngka);
                String lastSem = smtSebelum.getSemester() + " " + smtSebelum.getTahun() + "/"
                        + (smtSebelum.getTahun() + 1);
                String textTvIPS = new StringBuilder().append("IPS ").append(lastSem).append(" : ").append(ips).toString();
                this.tvIPS.setText(textTvIPS);
                double ipkAngka = this.mahasiswa.calculateIPK();
                String ipk = String.format("%.2f", ipkAngka);
                String textTvIPK = "IPK : " + ipk;
                this.tvIPK.setText(textTvIPK);
                int sks = this.mahasiswa.calculateSKSLulus();
                String textTvSKS = "SKS Lulus : " + sks;
                this.tvSKSLulus.setText(textTvSKS);
            }

        } else {
            this.presenter.getNilaiMahasiswa(phpSessId, mahasiswa.getNpm());
            this.presenter.requestTahunSemester(phpSessId, mahasiswa.getNpm());

        }
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

    private Bundle saveState(Mahasiswa mhs) {
        Bundle state = new Bundle();
        state.putSerializable("smt",tahunSemester);
        state.putSerializable("mhs_nilai_added", mhs);
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
        outState.putSerializable("mhs_nilai_added", mahasiswa);
    }

    @Override
    public void setMahasiswaForFragment(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
        this.mataKuliahAdapter = new MataKuliahAdapter(mahasiswa);
        this.recyclerView.setAdapter(mataKuliahAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(!this.mahasiswa.getRiwayatNilai().isEmpty()){
            double ipkAngka = this.mahasiswa.calculateIPK();
            String ipk = String.format("%.2f", ipkAngka);
            String textTvIPK = "IPK : " + ipk;
            this.tvIPK.setText(textTvIPK);
            int sks = this.mahasiswa.calculateSKSLulus();
            String textTvSKS = "SKS Lulus : " + sks;
            this.tvSKSLulus.setText(textTvSKS);
        }
        if(!this.mahasiswa.getNilaiTOEFL().values().isEmpty()){
            String textTvTOEFL = "Nilai TOEFL : " + this.mahasiswa.getNilaiTOEFL().values().toString();
            this.tvTOEFL.setText(textTvTOEFL);
        }

        this.saveState(mahasiswa);
    }

    @Override
    public void setTahunSemesterForFragment(TahunSemester tahunSemester) {
        this.tahunSemester = tahunSemester;
        String sem = "SEMESTER "+tahunSemester.getSemester() + " " + tahunSemester.getTahun() + "/"
                + (tahunSemester.getTahun() + 1);
        this.tvSemester.setText(sem);
        String kode = this.tahunSemester.getKodeDPS();
        TahunSemester smtSebelum;
        if(kode.charAt(2)=='1'){
            String smt = "2";
            String tahun = String.valueOf(Integer.parseInt(kode.substring(0,2))-1);
            smtSebelum = new TahunSemester(tahun.concat(smt));
        }else{
            String smt = "1";
            String tahun = String.valueOf(Integer.parseInt(kode.substring(0,2)));
            smtSebelum = new TahunSemester(tahun.concat(smt));
        }
        if(!this.mahasiswa.getRiwayatNilai().isEmpty()){
            double ipsAngka = this.mahasiswa.calculateIPS(smtSebelum);
            String ips = String.format("%.2f", ipsAngka);
            String lastSem = smtSebelum.getSemester() + " " + smtSebelum.getTahun() + "/"
                    + (smtSebelum.getTahun() + 1);
            String textTvIPS = new StringBuilder().append("IPS ").append(lastSem).append(" : ").append(ips).toString();
            this.tvIPS.setText(textTvIPS);
        }
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

}
