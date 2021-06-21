package id.ac.unpar.ifstudentportal.View.Fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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

public class JadwalFragment extends Fragment {
    protected WeekView weekView;
    protected Mahasiswa mahasiswa;
    protected Presenter presenter;
    private List<JadwalKuliah> listJadwal;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jadwal_fragment, container, false);
        this.mahasiswa = (Mahasiswa) this.getActivity().getIntent().getExtras().getSerializable("mhs");
        weekView = view.findViewById(R.id.week_view);
        this.setJadwal();
        return view;
    }

    public void setJadwal(){
        for(int i=0;i<listJadwal.size();i++){
            int start = listJadwal.get(i).getWaktuMulai().getHour();
            LocalTime startTime = LocalTime.of(start,0).truncatedTo(ChronoUnit.HOURS);
            int end = listJadwal.get(i).getWaktuSelesai().getHour();
            LocalTime endTime = LocalTime.of(end,0).truncatedTo(ChronoUnit.HOURS);
            String nama = listJadwal.get(i).getMataKuliah().getNama();
            String dosen = listJadwal.get(i).getPengajar().getNama();
            dosen = getKodeDosen(dosen);
            String lokasi = listJadwal.get(i).getLokasi();
            if(lokasi.contains("Ruang Kuliah")){
                lokasi = "Ruang "+lokasi.substring(13);
            }else if(lokasi.contains("Lab.")){
                lokasi = "Ruang "+lokasi.substring(6,10);
            }
            int year = 2021;
            int month = 3;
            int day = listJadwal.get(i).getHari().getValue();
            String kode = listJadwal.get(i).getMataKuliah().getKode();
            LocalDate date = LocalDate.of(year,month,day);
            Event.Single event = new Event.Single(i,date,nama,kode,"",startTime,endTime,dosen,lokasi, Color.WHITE,getColor(kode));
            weekView.addEvent(event);
        }
    }

    public static JadwalFragment newInstance() {
        JadwalFragment fragment = new JadwalFragment();
        return fragment;
    }

    public String getKodeDosen(String nama){
        String res = "Dosen : ";
        String kode="";
        if(nama.contains("Pascal")){
            kode = "PAN";
        }else if(nama.contains("Vania Natali")){
            kode = "VAN";
        }else if(nama.contains("Mariskha")){
            kode = "MTA";
        }else if(nama.contains("Natalia")){
            kode = "NAT";
        }else if(nama.contains("Elisati")){
            kode = "ELH";
        }else if(nama.contains("Chandra Wijaya")){
            kode = "CHW";
        }else if(nama.contains("Veronica Sri Moertini")){
            kode = "VSM";
        }else if(nama.contains("Maria Veronica")){
            kode = "MVC";
        }else if(nama.contains("Husnul")){
            kode = "HUH";
        }else if(nama.contains("Cecilia Esti")){
            kode = "CEN";
        }else if(nama.contains("Erwin")){
            kode = "IGE";
        }else if(nama.contains("Lionov")){
            kode = "LNV";
        }else if(nama.contains("Raymond")){
            kode = "RCP";
        }else if(nama.contains("Rosa")){
            kode = "RDL";
        }else if(nama.contains("Luciana")){
            kode = "LCA";
        }else{
            res = "kode null";
        }
        return res+kode;
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

    public void setJadwalKuliahList(List<JadwalKuliah> jadwalKuliahs) {
        this.listJadwal = jadwalKuliahs;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
