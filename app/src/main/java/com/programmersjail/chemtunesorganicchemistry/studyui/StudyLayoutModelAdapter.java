package com.programmersjail.chemtunesorganicchemistry.studyui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.programmersjail.chemtunesorganicchemistry.notes.NoteActivity;
import com.programmersjail.chemtunesorganicchemistry.R;
import com.programmersjail.chemtunesorganicchemistry.flashcard.FlashCardActivity;

import java.util.List;

public class StudyLayoutModelAdapter extends RecyclerView.Adapter<StudyLayoutModelAdapter.ViewHolder> {

    private List<StudyLayoutModel> studyLayoutModelList;

    public StudyLayoutModelAdapter(List<StudyLayoutModel> studyLayoutModelList) {
        this.studyLayoutModelList = studyLayoutModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.study_layout_model, parent, false);
        return new StudyLayoutModelAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final StudyLayoutModel model = studyLayoutModelList.get(position);
        holder.sTitle.setText(model.getStudyTitle());
        holder.sImg.setImageResource(model.getStudyIcon());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (position){

                    case 0:

                        Intent i2 = new Intent(v.getContext(), FlashCardActivity.class);
                        i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i2.putExtra("title",studyLayoutModelList.get(holder.getAdapterPosition()).getStudyTitle());
                        v.getContext().startActivity(i2);

                        break;

                    case 1:

                        Intent i3 = new Intent(v.getContext(), NoteActivity.class);
                        i3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i3.putExtra("title",studyLayoutModelList.get(holder.getAdapterPosition()).getStudyTitle());
                        v.getContext().startActivity(i3);

                        break;

                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return studyLayoutModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView sTitle;
        private ImageView sImg;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sTitle = (TextView) itemView.findViewById(R.id.s_title);
            sImg = (ImageView) itemView.findViewById(R.id.s_img);
            cardView = (CardView) itemView.findViewById(R.id.ccc);

        }
    }
}
