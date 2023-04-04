package com.programmersjail.chemtunesorganicchemistry.flashcard;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.programmersjail.chemtunesorganicchemistry.R;
import com.programmersjail.chemtunesorganicchemistry.helper.SharedPrefManager;
import com.programmersjail.chemtunesorganicchemistry.helper.SubscriptionFee;
import com.programmersjail.chemtunesorganicchemistry.helper.URls;
import com.programmersjail.chemtunesorganicchemistry.helper.VolleySingleton;
import com.programmersjail.chemtunesorganicchemistry.registration.User;
import com.programmersjail.chemtunesorganicchemistry.videolec.ViewDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlashCardAdapterTwo extends RecyclerView.Adapter<FlashCardAdapterTwo.ViewHolder> {

    private Activity mCtx;
    private List<FlashCardModel> flashCardModelList;



    public FlashCardAdapterTwo(Activity mCtx, List<FlashCardModel> flashCardModelList) {
        this.mCtx = mCtx;
        this.flashCardModelList = flashCardModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.flash_card_two, parent, false);
        return new FlashCardAdapterTwo.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        final FlashCardModel model = flashCardModelList.get(position);
        Glide.with(mCtx).load(model.getFontImg()).into(holder.fontImg);
        //Glide.with(mCtx).load(model.getBackImg()).into(holder.backImg);
        holder.fontTitle.setText(model.getFontTitle());
        holder.flashType.setText(model.getType());




    }




    @Override
    public int getItemCount() {
        return flashCardModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView fontTitle,flashType;
        private ImageView fontImg;
        private FrameLayout frameLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           // backImg = (ImageView) itemView.findViewById(R.id.c_back_img);
            fontImg = (ImageView) itemView.findViewById(R.id.flash_img);
            fontTitle = (TextView) itemView.findViewById(R.id.flash_title);
            //backTitle = (TextView) itemView.findViewById(R.id.c_back_title);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.f_card);
            flashType = (TextView) itemView.findViewById(R.id.flash_type);

        }


    }




}
