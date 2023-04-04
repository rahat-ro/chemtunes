package com.programmersjail.chemtunesorganicchemistry.periodic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.programmersjail.chemtunesorganicchemistry.R;
import com.programmersjail.chemtunesorganicchemistry.videolec.VideoLectureModelAdapter;

import java.util.List;

public class PeriodicTableModelAdapter extends RecyclerView.Adapter<PeriodicTableModelAdapter.ViewHolder> {


    private Context mCtx;
    private List<PeriodicTableModel> periodicTableModelList;

    public PeriodicTableModelAdapter(Context mCtx, List<PeriodicTableModel> periodicTableModelList) {
        this.mCtx = mCtx;
        this.periodicTableModelList = periodicTableModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.periodic_table_model, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PeriodicTableModel model = periodicTableModelList.get(position);

        holder.name.setText(model.getName());
        holder.Symbol.setText(model.getSymbol());
        holder.atomicNumber.setText(model.getAtomic_number());
        holder.atomicMass.setText(model.getRelative_atomic_mass());
    }

    @Override
    public int getItemCount() {
        return periodicTableModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Symbol,name,atomicNumber,atomicMass;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Symbol = (TextView) itemView.findViewById(R.id.p_symbol);
            name = (TextView) itemView.findViewById(R.id.p_name);
            atomicNumber = (TextView) itemView.findViewById(R.id.p_atomic_number);
            atomicMass = (TextView) itemView.findViewById(R.id.p_atomic_mass);

        }
    }
}
