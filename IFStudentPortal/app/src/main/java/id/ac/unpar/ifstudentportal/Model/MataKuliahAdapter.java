package id.ac.unpar.ifstudentportal.Model;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.ac.unpar.ifstudentportal.Presenter.Presenter;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.MataKuliah;
import id.ac.unpar.siamodels.MataKuliahFactory;
import id.ac.unpar.siamodels.matakuliah.interfaces.HasPrasyarat;
import com.example.ifstudentportal.R;
import java.util.ArrayList;
import java.util.List;


public class MataKuliahAdapter extends RecyclerView.Adapter<MataKuliahAdapter.MyViewHolder> {
    protected Mahasiswa mahasiswa;
    protected Presenter presenter;
    protected List<Mahasiswa.Nilai> nilaiList;
    protected List<PrasyaratDisplay> itemDisplayList;
    protected List<MataKuliah> mkList;

    public MataKuliahAdapter(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
        this.nilaiList = mahasiswa.getRiwayatNilai();
        mkList = this.requestAvailableKuliah();
        this.itemDisplayList = checkPrasyarat();
    }

    @NonNull
    @Override
    public MataKuliahAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_matakuliah, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MataKuliahAdapter.MyViewHolder holder, int position) {
        holder.tvKode.setText(itemDisplayList.get(position).mataKuliah.getKode());
        holder.tvNama.setText(itemDisplayList.get(position).mataKuliah.getNama());
        String status = itemDisplayList.get(position).status[0];
        holder.tvStatus.setText(status);
        if(status.contains("sudah lulus")){
            holder.tvStatus.setTextColor(Color.BLUE);
        }else if(status.contains("tidak memiliki prasyarat") || status.contains("memenuhi syarat")){
            holder.tvStatus.setTextColor(Color.parseColor("#FF5DC062"));
        }else{
            holder.tvStatus.setTextColor(Color.RED);
        }
    }


    @Override
    public int getItemCount() {
        return itemDisplayList.size();
    }

    public List<MataKuliah> requestAvailableKuliah() {
        // TODO
        MataKuliahFactory mkFactory = MataKuliahFactory.getInstance();
        String[] kodeMataKuliahKurikulum2018 = {
                "AIF181100","AIF181101","AIF181103","AIF181104","AIF181105","AIF181106","AIF181107",
                "AIF182007","AIF182100","AIF182101","AIF182103","AIF182105","AIF182106","AIF182109",
                "AIF182111","AIF182112","AIF182204","AIF182210","AIF182302","AIF182308","AIF183002",
                "AIF183010","AIF183013","AIF183015","AIF183106","AIF183107","AIF183111","AIF183112",
                "AIF183114","AIF183116","AIF183117","AIF183118","AIF183119","AIF183120","AIF183121",
                "AIF183122","AIF183123","AIF183124","AIF183128","AIF183141","AIF183143","AIF183145",
                "AIF183147","AIF183149","AIF183153","AIF183155","AIF183201","AIF183204","AIF183209",
                "AIF183225","AIF183227","AIF183229","AIF183232","AIF183236","AIF183238","AIF183250",
                "AIF183300","AIF183303","AIF183305","AIF183308","AIF183331","AIF183333","AIF183337",
                "AIF183339","AIF183340","AIF183342","AIF183346","AIF183348","AIF184000","AIF184001",
                "AIF184002","AIF184004","AIF184005","AIF184006","AIF184007","AIF184104","AIF184106",
                "AIF184108","AIF184109","AIF184110","AIF184114","AIF184115","AIF184116","AIF184119",
                "AIF184120","AIF184121","AIF184123","AIF184125","AIF184127","AIF184129","AIF184222",
                "AIF184224","AIF184228","AIF184230","AIF184231","AIF184232","AIF184233","AIF184235",
                "AIF184237","AIF184247","AIF184303","AIF184334","AIF184336","AIF184338","AIF184339",
                "AIF184340","AIF184341","AIF184342","AIF184343","AIF184344","AIF184345","MKU180110",
                "MKU180120","MKU180130","MKU180240","MKU180250","MKU180370","MKU180380"
        };
        List<MataKuliah> mkList;
        mkList = new ArrayList<>(kodeMataKuliahKurikulum2018.length);
        for (int i=0; i < kodeMataKuliahKurikulum2018.length; i++){
            MataKuliah curr = mkFactory.createMataKuliah(kodeMataKuliahKurikulum2018[i]);
            mkList.add(curr);
        }
        return mkList;
    }

    private List<PrasyaratDisplay> checkPrasyarat() {
        List<PrasyaratDisplay> table = new ArrayList<>();
        for (MataKuliah mk : mkList) {
            if (mahasiswa.hasLulusKuliah(mk.getKode())) {
                table.add(new PrasyaratDisplay(mk, new String[] { "sudah lulus" }));
            } else {
                if (mk instanceof HasPrasyarat) {
                    List<String> reasons = new ArrayList<>();
                    ((HasPrasyarat) mk).checkPrasyarat(mahasiswa, reasons);
                    if (!reasons.isEmpty()) {
                        table.add(new PrasyaratDisplay(mk, reasons.toArray(new String[reasons.size()])));
                    } else {
                        if (mahasiswa.hasLulusKuliah(mk.getKode())) {
                            table.add(new PrasyaratDisplay(mk, new String[] { "sudah lulus" }));
                        } else {
                            table.add(new PrasyaratDisplay(mk, new String[] { "memenuhi syarat" }));
                        }
                    }
                } else {
                    table.add(new PrasyaratDisplay(mk, new String[] { "tidak memiliki prasyarat" }));
                }

            }
        }
        return table;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvKode;
        TextView tvNama;
        TextView tvStatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.tvKode = itemView.findViewById(R.id.kode_mk);
            this.tvNama = itemView.findViewById(R.id.nama_mk);
            this.tvStatus = itemView.findViewById(R.id.status_mk);
        }

    }
    private class PrasyaratDisplay {
        MataKuliah mataKuliah;
        String[] status;

        PrasyaratDisplay(MataKuliah mataKuliah, String[] status) {
            this.mataKuliah = mataKuliah;
            this.status = status;
        }
    }
}
