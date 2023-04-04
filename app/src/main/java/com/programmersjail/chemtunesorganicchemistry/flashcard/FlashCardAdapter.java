package com.programmersjail.chemtunesorganicchemistry.flashcard;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class FlashCardAdapter extends RecyclerView.Adapter<FlashCardAdapter.ViewHolder> {

    private Activity mCtx;
    private List<FlashCardModel> flashCardModelList;
    private User user;
    private SubscriptionFee due;



    public FlashCardAdapter(Activity mCtx, List<FlashCardModel> flashCardModelList) {
        this.mCtx = mCtx;
        this.flashCardModelList = flashCardModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.flash_card, parent, false);
        return new FlashCardAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        final FlashCardModel model = flashCardModelList.get(position);
        Glide.with(mCtx).load(model.getFontImg()).into(holder.fontImg);
        //Glide.with(mCtx).load(model.getBackImg()).into(holder.backImg);
        holder.fontTitle.setText(model.getFontTitle());
        holder.flashType.setText(model.getType());

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
                               // Toast.makeText(mCtx, obj.getString("message"), Toast.LENGTH_SHORT).show();

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

                                //..................................................................................





                                //t7.setText(due.getDue());



                            } else {
                                Toast.makeText(mCtx, obj.getString("message"), Toast.LENGTH_SHORT).show();
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



        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(due.getSubscriptionFee().equals("paid") || model.getType().equals("free")){

                  //  Toast.makeText(mCtx, "player", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mCtx, FlashPlayerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("backImg", model.getBackImg());
                    intent.putExtra("fontImg", model.getFontImg());
                    intent.putExtra("backTitle", model.getBackTitle());
                    intent.putExtra("fontTitle", model.getFontTitle());
                    intent.putExtra("chapterName", model.getChapterName());

                    mCtx.startActivity(intent);

                }else  {
                    Toast.makeText(mCtx, "get account premium", Toast.LENGTH_SHORT).show();
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(mCtx, "Get Premium Account");
                }
            }
        });



    }




    @Override
    public int getItemCount() {
        return flashCardModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView fontTitle,flashType;
        private ImageView fontImg;
        private RelativeLayout frameLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           // backImg = (ImageView) itemView.findViewById(R.id.c_back_img);
            fontImg = (ImageView) itemView.findViewById(R.id.flash_img);
            fontTitle = (TextView) itemView.findViewById(R.id.flash_title);
            flashType = (TextView) itemView.findViewById(R.id.flash_type);
            //backTitle = (TextView) itemView.findViewById(R.id.c_back_title);
            frameLayout = (RelativeLayout) itemView.findViewById(R.id.f_card);

        }


    }




}
