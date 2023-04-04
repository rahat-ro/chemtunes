package com.programmersjail.chemtunesorganicchemistry.flashcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlashPlayerActivity extends AppCompatActivity {

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private View mCardFrontLayout;
    private View mCardBackLayout;

    private RecyclerView recyclerView;
    private List<FlashCardModel> flashCardModelList;

    private TextView fontTitle,backTitle;
    private ImageView backImg,fontImg;
    private String chapterName;
   // private FlashCardModel cardModel;
    private View ChildViewTwo;
    private int RecyclerViewItemPositionTwo ;

    private User user;
    private SubscriptionFee due;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_player);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


         backImg = (ImageView) findViewById(R.id.c_back_img);
         fontImg = (ImageView) findViewById(R.id.c_font_img);
         fontTitle = (TextView) findViewById(R.id.c_font_title);
         backTitle = (TextView) findViewById(R.id.c_back_title);

        recyclerView = (RecyclerView) findViewById(R.id.ff_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        final Bundle mBundle = getIntent().getExtras();
        if(mBundle != null){

            Glide.with(this).load(mBundle.get("backImg")).into(backImg);
            Glide.with(this).load(mBundle.get("fontImg")).into(fontImg);
            fontTitle.setText(mBundle.getString("fontTitle"));
            backTitle.setText(mBundle.getString("backTitle"));
            chapterName = mBundle.getString("chapterName");

            getSupportActionBar().setTitle(mBundle.getString("fontTitle"));



        }

        findViews();
        loadAnimations();
        changeCameraDistance();
        loadFlashCard();
        flashCardModelList = new ArrayList<>();
        loadSubscriptionFee();


        //..................................................................................


        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildViewTwo = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());


                if(ChildViewTwo != null && gestureDetector.onTouchEvent(motionEvent)) {

                    RecyclerViewItemPositionTwo = Recyclerview.getChildAdapterPosition(ChildViewTwo);
                    //  Toast.makeText(getActivity(), (CharSequence) newsCategoryNameList.get(RecyclerViewItemPosition), Toast.LENGTH_LONG).show();

                    FlashCardModel cardModel = (flashCardModelList.get(RecyclerViewItemPositionTwo));

                    if(due.getSubscriptionFee().equals("paid") || cardModel.getType().equals("free") ){


                        Glide.with(FlashPlayerActivity.this).load(cardModel.getBackImg()).into(backImg);
                        Glide.with(FlashPlayerActivity.this).load(cardModel.getFontImg()).into(fontImg);
                        fontTitle.setText(cardModel.getFontTitle());
                        backTitle.setText(cardModel.getBackTitle());


                    }else  {
                        Toast.makeText(getApplicationContext(), "get account premium", Toast.LENGTH_SHORT).show();
                        ViewDialog alert = new ViewDialog();
                        alert.showDialog(FlashPlayerActivity.this, "Get Premium Account");
                    }


                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {


            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });





        //..................................................................................




    }

    /**@Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }**/

    private void  loadSubscriptionFee(){

        user = SharedPrefManager.getInstance(this).getUser();

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


                                //t7.setText(due.getDue());



                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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

        VolleySingleton.getInstance(FlashPlayerActivity.this).addToRequestQueue(stringRequest);


    }

    private void loadFlashCard () {

        //first getting the values
        // final String categoryName = tv.getText().toString().trim();

        final String categoryName = chapterName.toString();

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_FLASH_CARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {



                            JSONArray array = new JSONArray(response);


                            if(flashCardModelList != null){
                                flashCardModelList.clear();
                            }


                            for (int i = 0; i < array.length(); i++) {


                                JSONObject room = array.getJSONObject(i);
                                flashCardModelList.add(new FlashCardModel(

                                        room.getString("back_title"),
                                        room.getString("back_img"),
                                        room.getString("font_title"),
                                        room.getString("font_img"),
                                        room.getString("chapter_name"),
                                        room.getString("type")


                                ));
                            }

                            FlashCardAdapterTwo adapter = new FlashCardAdapterTwo(FlashPlayerActivity.this,flashCardModelList);
                            // adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Your internet connection is slow", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("chapter_name", categoryName);
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);



    }

    private void findViews() {
        mCardBackLayout = findViewById(R.id.card_back);
        mCardFrontLayout = findViewById(R.id.card_front);
    }

    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        mCardBackLayout.setCameraDistance(scale);
    }

    @SuppressLint("ResourceType")
    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.in_animation);
    }



    public void flipCard(View view) {
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardBackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else {
            mSetRightOut.setTarget(mCardBackLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                // Intent intent = new Intent(NewsDiaplayActivity.this, MainActivity.class);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                //startActivity(intent);

                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    super.onBackPressed();
                }


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}