package com.programmersjail.chemtunesorganicchemistry.liveclass;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.programmersjail.chemtunesorganicchemistry.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LiveClassModelAdapter extends RecyclerView.Adapter<LiveClassModelAdapter.ViewHolder> {

    private Context mCtx;
    private List<LiveClassModel> liveClassModelList;

    public LiveClassModelAdapter(Context mCtx, List<LiveClassModel> liveClassModelList) {
        this.mCtx = mCtx;
        this.liveClassModelList = liveClassModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_class_model, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final LiveClassModel model = liveClassModelList.get(position);
        Glide.with(mCtx).load(model.getIcon()).into(holder.icon);
        holder.name.setText(model.getName());
        holder.text.setText(model.getText());
        holder.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(model.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return liveClassModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name,text;
        private CircleImageView icon;
        private CardView c;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);

           name = (TextView) itemView.findViewById(R.id.name);
           text = (TextView) itemView.findViewById(R.id.text);
           icon = (CircleImageView) itemView.findViewById(R.id.profile_image);
           c = (CardView) itemView.findViewById(R.id.c1);

       }
   }
}
