package com.programmersjail.chemtunesorganicchemistry.videolec;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.programmersjail.chemtunesorganicchemistry.PlayerActivity;
import com.programmersjail.chemtunesorganicchemistry.R;
import com.programmersjail.chemtunesorganicchemistry.helper.SharedPrefManager;
import com.programmersjail.chemtunesorganicchemistry.helper.SubscriptionFee;
import com.programmersjail.chemtunesorganicchemistry.helper.URls;
import com.programmersjail.chemtunesorganicchemistry.helper.VolleySingleton;
import com.programmersjail.chemtunesorganicchemistry.registration.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoLectureModelAdapter extends RecyclerView.Adapter<VideoLectureModelAdapter.ViewHolder> {


    private Activity mCtx;
    private List<VideoLectureModel> videoLectureModelList;
    private User user;
    private SubscriptionFee due;

    public static final String KEY = "AIzaSyAeBMLy8sxe8taNpmR5lqeFqZPtuoQ4XAU";

    public VideoLectureModelAdapter(Activity mCtx, List<VideoLectureModel> videoLectureModelList) {
        this.mCtx = mCtx;
        this.videoLectureModelList = videoLectureModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_lecture_model, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final VideoLectureModel model = videoLectureModelList.get(position);


        holder.lecture_title.setText(model.getLectureTitle());
        holder.duration.setText(model.getDuration());
        holder.video_type.setText(model.getType());


        user = SharedPrefManager.getInstance(mCtx).getUser();
        final String phn = user.getPhoneNumber();
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_SUBSCRIPTION_FEE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {


                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                //Toast.makeText(mCtx, obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("tmu_students");

                                //userJson.getString("user");

                                //creating a new user object
                                due = new SubscriptionFee(
                                        userJson.getString("account_type"),
                                        userJson.getString("subscription_fee"),
                                        userJson.getString("package_name"),
                                        userJson.getString("package_limit")



                                );

                                holder.c1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        //mIntent.putExtra("video_url", videoLectureModelList.get(holder.getAdapterPosition()).getVideoID());

                                        if(due.getSubscriptionFee().equals("paid") || model.getType().equals("free")){

                                            //Toast.makeText(mCtx, "player", Toast.LENGTH_SHORT).show();


                                            Intent intent = new Intent(mCtx, PlayerActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.putExtra("video_url", model.getVideoID());
                                            intent.putExtra("chapterName", model.getChapter());
                                            intent.putExtra("categoryName", model.getCategory_name());

                                            mCtx.startActivity(intent);

                                        }else  {
                                            Toast.makeText(mCtx, "get account premium", Toast.LENGTH_SHORT).show();
                                            ViewDialog alert = new ViewDialog();
                                            alert.showDialog(mCtx, "Get Premium Account");
                                        }

                                    }
                                });



                            } else {
                                Toast.makeText(mCtx, "Your internet connection is slow", Toast.LENGTH_SHORT).show();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String message = null;
                        if (error instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(mCtx, message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                            Toast.makeText(mCtx, message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(mCtx, message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                            Toast.makeText(mCtx, message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(mCtx, message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                            Toast.makeText(mCtx, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone_number", phn);
                return params;
            }
        };

        VolleySingleton.getInstance(mCtx).addToRequestQueue(stringRequest);



    }

    @Override
    public int getItemCount() {
        return videoLectureModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        protected YouTubeThumbnailView youTubeThumbnailView;

        protected TextView lecture_title,duration,video_type;
        protected CardView c1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            c1=(CardView) itemView.findViewById(R.id.c1);
            lecture_title = (TextView) itemView.findViewById(R.id.video_title);
            duration = (TextView) itemView.findViewById(R.id.video_duration);
            video_type = (TextView) itemView.findViewById(R.id.video_type);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);



        }


    }

}
