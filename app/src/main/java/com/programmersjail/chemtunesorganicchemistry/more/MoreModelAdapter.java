package com.programmersjail.chemtunesorganicchemistry.more;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.programmersjail.chemtunesorganicchemistry.R;

import java.util.List;

public class MoreModelAdapter extends RecyclerView.Adapter<MoreModelAdapter.ViewHolder>{

    private Context context;
    private List<MoreModel> moreModelList;

    public MoreModelAdapter(Context context, List<MoreModel> moreModelList) {
        this.context = context;
        this.moreModelList = moreModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_model, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MoreModel moreModel = moreModelList.get(position);
        holder.title.setText(moreModel.getTitle());
        holder.description.setText(moreModel.getDescription());
        holder.titleTwo.setText(moreModel.getTitleTwo());
        holder.descriptionTwo.setText(moreModel.getDescriptionTwo());

    }

    @Override
    public int getItemCount() {
        return moreModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title,description,titleTwo,descriptionTwo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.t1);
            description = (TextView) itemView.findViewById(R.id.t2);
            titleTwo = (TextView) itemView.findViewById(R.id.t3);
            descriptionTwo = (TextView) itemView.findViewById(R.id.t4);

        }
    }
}
