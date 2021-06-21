package id.ac.unpar.ifstudentportal.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ifstudentportal.R;
import java.util.ArrayList;
import java.util.List;
import id.ac.unpar.ifstudentportal.Presenter.Presenter;
import id.ac.unpar.siamodels.Mahasiswa;
import id.ac.unpar.siamodels.prodi.teknikinformatika.Kelulusan;

public class KelulusanAdapter extends RecyclerView.Adapter<KelulusanAdapter.KelulusanViewHolder> {
    protected List<String> alasan;
    protected Mahasiswa mahasiswa;
    protected Presenter presenter;
    protected List<Mahasiswa.Nilai> nilaiList;
    protected boolean bisaLulus;

    public KelulusanAdapter(Mahasiswa mahasiswa){
        this.mahasiswa = mahasiswa;
        this.nilaiList = mahasiswa.getRiwayatNilai();
        this.alasan = new ArrayList<>();
        Kelulusan str = new Kelulusan();
        this.bisaLulus = str.checkPrasyarat(mahasiswa, alasan);
    }
    @NonNull
    @Override
    public KelulusanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alasan_kelulusan, parent, false);
        KelulusanAdapter.KelulusanViewHolder vh = new KelulusanAdapter.KelulusanViewHolder(view);
        return vh;
    }

    public boolean bisaLulus() {
        return bisaLulus;
    }

    @Override
    public void onBindViewHolder(@NonNull KelulusanViewHolder holder, int position) {
        holder.tvAlasan.setText(alasan.get(position));
    }

    @Override
    public int getItemCount() {
        return alasan.size();
    }

    public List<String> getAlasan() {
        return alasan;
    }

    class KelulusanViewHolder extends RecyclerView.ViewHolder {
        TextView tvAlasan;

        KelulusanViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvAlasan = itemView.findViewById(R.id.tv_alasan);
        }
    }

}
