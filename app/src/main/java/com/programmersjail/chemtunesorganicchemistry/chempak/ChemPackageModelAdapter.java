package com.programmersjail.chemtunesorganicchemistry.chempak;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.programmersjail.chemtunesorganicchemistry.R;
import com.programmersjail.chemtunesorganicchemistry.flashcard.FlashPlayerActivity;
import com.programmersjail.chemtunesorganicchemistry.payment.PaymentActivity;

import java.util.List;

public class ChemPackageModelAdapter extends RecyclerView.Adapter<ChemPackageModelAdapter.ViewHolder> {


    private Context mCtx;
    private List<ChemPackageModel> chemPackageModelList;

    public ChemPackageModelAdapter(Context mCtx, List<ChemPackageModel> chemPackageModelList) {
        this.mCtx = mCtx;
        this.chemPackageModelList = chemPackageModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chem_package_model, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ChemPackageModel model = chemPackageModelList.get(position);
        Glide.with(mCtx).load(model.getPackage_img()).into(holder.icon);

        holder.pakTitle.setText(model.getPackage_title());
        holder.pakFee.setText(model.getPackage_fee());
        holder.pakDuration.setText(model.getPackage_duration());
        holder.pakSDis.setText(model.getPackage_s_dis());
        holder.pakDIS.setText(model.getPackage_dis());

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, PaymentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name", model.getPackage_title());
                intent.putExtra("limit", model.getPackage_limit());
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return chemPackageModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView icon;
        private TextView pakTitle,pakFee,pakDuration,pakSDis,pakDIS;
        private Button btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.pak_icon);
            pakTitle = (TextView) itemView.findViewById(R.id.pak_title);
            pakFee = (TextView) itemView.findViewById(R.id.pak_fee);
            pakDuration = (TextView) itemView.findViewById(R.id.pak_duration);
            pakSDis = (TextView) itemView.findViewById(R.id.pak_s_dis);
            pakDIS = (TextView) itemView.findViewById(R.id.pak_dis);

            btn = (Button) itemView.findViewById(R.id.btnSubscribe);
            


        }
    }
}
