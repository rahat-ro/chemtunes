package com.programmersjail.chemtunesorganicchemistry.videolec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

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
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.programmersjail.chemtunesorganicchemistry.R;
import com.programmersjail.chemtunesorganicchemistry.helper.ChapterName;
import com.programmersjail.chemtunesorganicchemistry.helper.ChapterNameAdapter;
import com.programmersjail.chemtunesorganicchemistry.helper.SharedPrefManager;
import com.programmersjail.chemtunesorganicchemistry.helper.SubscriptionFee;
import com.programmersjail.chemtunesorganicchemistry.helper.URls;
import com.programmersjail.chemtunesorganicchemistry.helper.VolleySingleton;
import com.programmersjail.chemtunesorganicchemistry.registration.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoLecture extends AppCompatActivity {

    private RecyclerView recyclerView,recyclerViewOne;
    private List<VideoLectureModel> videoLectureModelList;
    private LinearLayoutManager layoutManager;
    private List<ChapterName> chapterNameList;
    private ChapterName name;
    private User user;
    private SubscriptionFee due;
    private ProgressBar progressBar;


    private View ChildViewTwo;
    private int RecyclerViewItemPositionTwo;

    private String category,packLimit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_lecture);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));

        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.VISIBLE);
        loadSubscriptionStatus();

        //.........................................................

        final Bundle mBundle = getIntent().getExtras();
        if(mBundle != null){

            category = mBundle.getString("title");
            getSupportActionBar().setTitle(mBundle.getString("title"));



        }

        //...........................................................

        recyclerViewOne = (RecyclerView) findViewById(R.id.video_lecture_rv_one);
        recyclerViewOne.setHasFixedSize(true);
        recyclerViewOne.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        //..............................................................

        recyclerView = (RecyclerView) findViewById(R.id.video_lecture_rv);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        //..............................................................



        //..............................................................


        recyclerViewOne.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(VideoLecture.this, new GestureDetector.SimpleOnGestureListener() {

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
                    name = (chapterNameList.get(RecyclerViewItemPositionTwo));

                    loadVideoLecture ();
                    videoLectureModelList = new ArrayList<>();


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



    }

    private void loadSubscriptionStatus(){

        user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

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
                                // Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

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

                                if(due.getPackageName().equals("no package")){

                                    if(category.equals("Question Solution")){

                                        loadChapterNameTwo();
                                        chapterNameList = new ArrayList<>();
                                        loadChapterNameFirstPositionTwo();
                                        progressBar.setVisibility(View.GONE);

                                    }else {
                                        loadChapterName();
                                        chapterNameList = new ArrayList<>();
                                        loadChapterNameFirstPosition();
                                        progressBar.setVisibility(View.GONE);
                                    }



                                }else if(due.getPackageLimit().equals("pending")) {


                                    if(category.equals("Question Solution")){

                                        loadChapterNameTwo();
                                        chapterNameList = new ArrayList<>();
                                        loadChapterNameFirstPositionTwo();
                                        progressBar.setVisibility(View.GONE);

                                    }else {

                                        loadChapterName();
                                        chapterNameList = new ArrayList<>();
                                        loadChapterNameFirstPosition();
                                        progressBar.setVisibility(View.GONE);
                                    }



                                }else {

                                    if(category.equals("Question Solution")){

                                        loadChapterNameTwo();
                                        chapterNameList = new ArrayList<>();
                                        loadChapterNameFirstPositionTwo();
                                        progressBar.setVisibility(View.GONE);

                                    }else {

                                        loadChapterNameLimit();
                                        chapterNameList = new ArrayList<>();
                                        loadChapterNameFirstPosition();
                                        progressBar.setVisibility(View.GONE);

                                    }


                                }





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

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }

    private void loadChapterName () {

        //first getting the values
        // final String categoryName = tv.getText().toString().trim();

        //final String categoryName = abc.toString();

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_CHAPTER_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {



                            JSONArray array = new JSONArray(response);


                            if(chapterNameList != null){
                                chapterNameList.clear();
                            }


                            for (int i = 0; i < array.length(); i++) {


                                JSONObject room = array.getJSONObject(i);
                                chapterNameList.add(new ChapterName(

                                        room.getString("chapter_name")



                                ));
                            }

                            ChapterNameAdapter adapter = new ChapterNameAdapter(VideoLecture.this,chapterNameList);
                            // adapter.notifyDataSetChanged();
                            recyclerViewOne.setAdapter(adapter);





                        } catch (JSONException e) {
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
                //params.put("sqlshowvalue", categoryName);
                return params;
            }
        };

        VolleySingleton.getInstance(VideoLecture.this).addToRequestQueue(stringRequest);



    }
    private void loadChapterNameLimit () {


        final String packageLimit = due.getPackageLimit().toString();

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_CHAPTER_NAME_LIMIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {



                            JSONArray array = new JSONArray(response);


                            if(chapterNameList != null){
                                chapterNameList.clear();
                            }


                            for (int i = 0; i < array.length(); i++) {


                                JSONObject room = array.getJSONObject(i);
                                chapterNameList.add(new ChapterName(

                                        room.getString("chapter_name")



                                ));
                            }

                            ChapterNameAdapter adapter = new ChapterNameAdapter(VideoLecture.this,chapterNameList);
                            // adapter.notifyDataSetChanged();
                            recyclerViewOne.setAdapter(adapter);





                        } catch (JSONException e) {
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
                //params.put("sqlshowvalue", categoryName);
                params.put("pakLimit", packageLimit);
                return params;
            }
        };

        VolleySingleton.getInstance(VideoLecture.this).addToRequestQueue(stringRequest);



    }
    private void loadChapterNameFirstPosition () {


        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_CHAPTER_NAME_FIRST_POSITION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {



                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                // Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("tmu_students");

                                //userJson.getString("user");

                                //creating a new user object
                                name = new ChapterName(
                                        userJson.getString("chapter_name")


                                );



                                loadVideoLecture ();
                                videoLectureModelList = new ArrayList<>();


                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }



                        } catch (JSONException e) {
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
                //params.put("sqlshowvalue", categoryName);
                return params;
            }
        };

        VolleySingleton.getInstance(VideoLecture.this).addToRequestQueue(stringRequest);



    }

    private void loadChapterNameTwo () {

        //first getting the values
        // final String categoryName = tv.getText().toString().trim();

        //final String categoryName = abc.toString();

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_CHAPTER_NAME_TWO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {



                            JSONArray array = new JSONArray(response);


                            if(chapterNameList != null){
                                chapterNameList.clear();
                            }


                            for (int i = 0; i < array.length(); i++) {


                                JSONObject room = array.getJSONObject(i);
                                chapterNameList.add(new ChapterName(

                                        room.getString("chapter_name")



                                ));
                            }

                            ChapterNameAdapter adapter = new ChapterNameAdapter(VideoLecture.this,chapterNameList);
                            // adapter.notifyDataSetChanged();
                            recyclerViewOne.setAdapter(adapter);





                        } catch (JSONException e) {
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
                //params.put("sqlshowvalue", categoryName);
                return params;
            }
        };

        VolleySingleton.getInstance(VideoLecture.this).addToRequestQueue(stringRequest);



    }
    private void loadChapterNameFirstPositionTwo () {


        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_CHAPTER_NAME_FIRST_POSITION_TWO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {



                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                // Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("tmu_students");

                                //userJson.getString("user");

                                //creating a new user object
                                name = new ChapterName(
                                        userJson.getString("chapter_name")


                                );



                                loadVideoLecture ();
                                videoLectureModelList = new ArrayList<>();


                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }



                        } catch (JSONException e) {
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
                //params.put("sqlshowvalue", categoryName);
                return params;
            }
        };

        VolleySingleton.getInstance(VideoLecture.this).addToRequestQueue(stringRequest);



    }

    private void loadVideoLecture () {


        //first getting the values
        final String categoryName = category.toString();
        final String chapterName = name.getChapterName();



        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_VIDEO_LECTURE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {



                            JSONArray array = new JSONArray(response);


                            if(videoLectureModelList != null){
                                videoLectureModelList.clear();
                            }


                            for (int i = 0; i < array.length(); i++) {


                                JSONObject room = array.getJSONObject(i);
                                videoLectureModelList.add(new VideoLectureModel(

                                        room.getString("video_ID"),
                                        room.getString("lecture_name"),
                                        room.getString("duration"),
                                        room.getString("type"),
                                        room.getString("chapter"),
                                        room.getString("category_name")


                                ));
                            }

                            VideoLectureModelAdapter adapter = new VideoLectureModelAdapter(VideoLecture.this,videoLectureModelList);
                            // adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);


                            //........................................................................






                            //.......................................................................


                        } catch (JSONException e) {
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
                params.put("chapter", chapterName);
                params.put("category_name", categoryName);

                return params;
            }
        };

        VolleySingleton.getInstance(VideoLecture.this).addToRequestQueue(stringRequest);



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here


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