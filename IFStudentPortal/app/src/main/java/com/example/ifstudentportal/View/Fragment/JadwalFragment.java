package com.example.ifstudentportal.View.Fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


import com.example.ifstudentportal.Model.Scrapper;
import com.example.ifstudentportal.R;


import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.chrono.ChronoZonedDateTime;
import org.threeten.bp.temporal.ChronoUnit;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import de.tobiasschuerg.weekview.data.Event;
import de.tobiasschuerg.weekview.view.WeekView;
import id.ac.unpar.siamodels.JadwalKuliah;
import id.ac.unpar.siamodels.Mahasiswa;

import static org.threeten.bp.LocalTime.*;

public class JadwalFragment extends Fragment {
    protected WeekView weekView;
    protected Mahasiswa mahasiswa;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.jadwal_fragment, container, false);
        weekView = view.findViewById(R.id.week_view);
        weekView.addEvent(new Event.Single(13, LocalDate.of(2020,1,7),"AJK 1","AJK 1","CHW", LocalTime.of(13,0).truncatedTo(ChronoUnit.HOURS), LocalTime.of(15,0).truncatedTo(ChronoUnit.HOURS),"AJK 3","AJK 4", Color.WHITE,R.color.grey));
        weekView.addEvent(new Event.Single(13, LocalDate.of(2020,1,6),"Pemodelan Untuk Komputasi","Pemodelan Untuk Komputasi","HUH", LocalTime.of(13,0).truncatedTo(ChronoUnit.HOURS), LocalTime.of(16,0).truncatedTo(ChronoUnit.HOURS),"","", Color.BLACK,R.color.myblue));
        weekView.addEvent(new Event.Single(13, LocalDate.of(2020,1,5),"Basis Data Big Data","Basdat  BD","LV", LocalTime.of(7,0).truncatedTo(ChronoUnit.HOURS), LocalTime.of(11,0).truncatedTo(ChronoUnit.HOURS),"","", Color.BLACK,R.color.creame));

        // calendarView.setEventsForWeek();
       // this.mahasiswa = (Mahasiswa) getArguments().get("mhs");
        //Log.d("aaa",this.mahasiswa.getNama());
        //this.setJadwal();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setJadwal(){
        List<JadwalKuliah> list = this.mahasiswa.getJadwalKuliahList();
        for(int i=0;i<list.size();i++){
            String start = list.get(i).getWaktuMulai().toString();
            Log.d("start",start);
            java.time.LocalTime end = list.get(i).getWaktuSelesai();
            Log.d("end",end.toString());

            String nama = list.get(i).getMataKuliah().getNama();
            //Event.Single event = new Event.Single(i,LocalDate.now(),nama,"","",start,end,"","", Color.RED,Color.WHITE));
            //weekView.addEvent(event);
        }
    }

    public static JadwalFragment newInstance() {
        JadwalFragment fragment = new JadwalFragment();
        return fragment;
    }


}