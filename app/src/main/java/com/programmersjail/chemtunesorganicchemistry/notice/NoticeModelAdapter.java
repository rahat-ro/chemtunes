package com.programmersjail.chemtunesorganicchemistry.notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.programmersjail.chemtunesorganicchemistry.R;
import com.programmersjail.chemtunesorganicchemistry.helper.ChapterNameAdapter;
import com.programmersjail.chemtunesorganicchemistry.notes.NoteModel;
import com.programmersjail.chemtunesorganicchemistry.notes.NoteModelAdapter;

import java.util.List;

public class NoticeModelAdapter extends RecyclerView.Adapter<NoticeModelAdapter.ViewHolder> {

    private Context context;
    private List<NoticeModel> noticeModelList;

    public NoticeModelAdapter(Context context, List<NoticeModel> noticeModelList) {
        this.context = context;
        this.noticeModelList = noticeModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_model, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NoticeModel model = noticeModelList.get(position);
        holder.tv.setText(model.getNotice());

    }

    @Override
    public int getItemCount() {
        return noticeModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv = (TextView) itemView.findViewById(R.id.notice);

        }
    }
}
