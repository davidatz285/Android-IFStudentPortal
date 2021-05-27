package id.ac.unpar.ifstudentportal.View.Fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


import id.ac.unpar.ifstudentportal.Presenter.Presenter;

import com.example.ifstudentportal.R;


import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.List;

import de.tobiasschuerg.weekview.data.Event;
import de.tobiasschuerg.weekview.view.WeekView;
import id.ac.unpar.siamodels.JadwalKuliah;
import id.ac.unpar.siamodels.Mahasiswa;

public class JadwalFragment extends Fragment implements IJadwalFragment{
    protected WeekView weekView;
    protected Mahasiswa mahasiswa;
    protected Presenter presenter;
    private List<JadwalKuliah> listJadwal;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.jadwal_fragment, container, false);
        this.mahasiswa = (Mahasiswa) this.getActivity().getIntent().getExtras().getSerializable("mhs");
        Log.d("jad mhs",this.mahasiswa.getNpm());
        String phpSessId = this.getActivity().getIntent().getExtras().getString("phpSessId");
        weekView = view.findViewById(R.id.week_view);
        //this.listJadwal = this.mahasiswa.getJadwalKuliahList();
        //weekView.setLessonClickListener(Log.d("aa"," "));
        this.setJadwal();

        return view;
    }


    public void setJadwal(){
        for(int i=0;i<listJadwal.size();i++){
            int start = listJadwal.get(i).getWaktuMulai().getHour();
            Log.d("start",String.valueOf(start));
            LocalTime startTime = LocalTime.of(start,0).truncatedTo(ChronoUnit.HOURS);
            int end = listJadwal.get(i).getWaktuSelesai().getHour();
            LocalTime endTime = LocalTime.of(end,0).truncatedTo(ChronoUnit.HOURS);
            //Log.d("end",end.toString());
            String nama = listJadwal.get(i).getMataKuliah().getNama();
            Log.d("matkul",nama);

            int year = LocalDate.now().getYear();
            int month = LocalDate.now().getMonthValue();
            int day = listJadwal.get(i).getHari().getValue();
            String kode = listJadwal.get(i).getMataKuliah().getKode();
            Event.Single event = new Event.Single(i,LocalDate.of(year,month,day+1),nama,kode,"",startTime,endTime,"","", Color.WHITE,getColor(kode));
            weekView.addEvent(event);
        }
    }

    public static JadwalFragment newInstance() {
        JadwalFragment fragment = new JadwalFragment();
        return fragment;
    }


    public int getColor(String kode){
        int[] warna = new int[9];
        warna[0] = Color.argb(255,95,212,126);
        warna[1] = Color.argb(255,95, 187, 212);
        warna[2] = Color.argb(255,245, 105, 105);
        warna[3] = Color.argb(255,247, 124, 186);
        warna[4] = Color.argb(255,217, 134, 213);
        warna[5] = Color.argb(255,158, 134, 217);
        warna[6] = Color.argb(255,78, 139, 252);
        warna[7] = Color.argb(255,117, 209, 204);
        warna[8] = Color.argb(255,159, 207, 153);

        int no = Integer.parseInt(kode.substring(3))%9;
        return warna[no];
    }
    @Override
    public void setJadwalKuliahList(List<JadwalKuliah> jadwalKuliahs) {
        this.listJadwal = jadwalKuliahs;
    }

    public void setPresenter(Presenter presenter,String phpSessId) {
        this.presenter = presenter;
        //this.presenter.getListJadwal(phpSessId);
    }
}
