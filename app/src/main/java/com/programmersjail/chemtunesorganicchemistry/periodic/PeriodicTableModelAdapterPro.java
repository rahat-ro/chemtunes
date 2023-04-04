package com.programmersjail.chemtunesorganicchemistry.periodic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.programmersjail.chemtunesorganicchemistry.R;

import java.util.List;

public class PeriodicTableModelAdapterPro extends RecyclerView.Adapter<PeriodicTableModelAdapterPro.ViewHolder> {


    private Context mCtx;
    private List<PeriodicTableModelPro> periodicTableModelProList;

    public PeriodicTableModelAdapterPro(Context mCtx, List<PeriodicTableModelPro> periodicTableModelProList) {
        this.mCtx = mCtx;
        this.periodicTableModelProList = periodicTableModelProList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.periodic_table_model_pro, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final PeriodicTableModelPro model = periodicTableModelProList.get(position);

        Glide.with(mCtx).load(model.getImg()).into(holder.img);


    }

    @Override
    public int getItemCount() {
        return periodicTableModelProList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private CardView c1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.p_img_pro);
            c1 = (CardView) itemView.findViewById(R.id.card1);


        }
    }
}
