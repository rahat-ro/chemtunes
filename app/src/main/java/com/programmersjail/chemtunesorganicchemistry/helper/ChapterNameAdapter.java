package com.programmersjail.chemtunesorganicchemistry.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.programmersjail.chemtunesorganicchemistry.R;

import java.util.List;

public class ChapterNameAdapter extends RecyclerView.Adapter<ChapterNameAdapter.ViewHolder> {

    private Context mCtx;
    private List<ChapterName> chapterNameList;

    public ChapterNameAdapter(Context mCtx, List<ChapterName> chapterNameList) {
        this.mCtx = mCtx;
        this.chapterNameList = chapterNameList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_name, parent, false);
        return new ChapterNameAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final ChapterName model = chapterNameList.get(position);
        holder.sTitle.setText(model.getChapterName());
       // holder.sImg.setImageResource(model.getStudyIcon());



    }

    @Override
    public int getItemCount() {
        return chapterNameList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView sTitle;
        private ImageView sImg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sTitle = (TextView) itemView.findViewById(R.id.s_title);
            //sImg = (ImageView) itemView.findViewById(R.id.s_img);


        }
    }
}
