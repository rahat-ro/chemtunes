package com.programmersjail.chemtunesorganicchemistry;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.programmersjail.chemtunesorganicchemistry.helper.SharedPrefManager;
import com.programmersjail.chemtunesorganicchemistry.helper.SubscriptionFee;
import com.programmersjail.chemtunesorganicchemistry.helper.URls;
import com.programmersjail.chemtunesorganicchemistry.helper.VolleySingleton;
import com.programmersjail.chemtunesorganicchemistry.payment.PaymentActivity;
import com.programmersjail.chemtunesorganicchemistry.registration.User;
import com.programmersjail.chemtunesorganicchemistry.videolec.VideoLectureModel;
import com.programmersjail.chemtunesorganicchemistry.videolec.VideoLectureModelAdapterTwo;
import com.programmersjail.chemtunesorganicchemistry.videolec.ViewDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {


    public static final String DEVELOPER_KEY = "AIzaSyAeBMLy8sxe8taNpmR5lqeFqZPtuoQ4XAU";
    private YouTubePlayerView youTubeView;
    private static final String TAG = PaymentActivity.class.getSimpleName();


    private YouTubePlayer mPlayer;

    private View mPlayButtonLayout;
    private TextView mPlayTimeTextView;
    private ImageButton fullScreen;

    private Handler mHandler = null;
    private SeekBar mSeekBar;

    private RecyclerView recyclerView;
    private List<VideoLectureModel> videoLectureModelList;
    private LinearLayoutManager layoutManager;
    private String chapterName,categoryName,url;
    private User user;
    private SubscriptionFee due;

    private VideoLectureModel model;

    private View ChildView;
    private int RecyclerViewItemPosition;

    private RelativeLayout endLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);



        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);
        youTubeView.initialize(DEVELOPER_KEY,this);
        endLayer = (RelativeLayout) findViewById(R.id.end_layer);

        //Add play button to explicitly play video in YouTubePlayerView
        mPlayButtonLayout = findViewById(R.id.video_control);
        //findViewById(R.id.play_video).setOnClickListener(this);
        //findViewById(R.id.pause_video).setOnClickListener(this);
        fullScreen = findViewById(R.id.full_screen);

        mPlayTimeTextView = (TextView) findViewById(R.id.play_time);
        mSeekBar = (SeekBar) findViewById(R.id.video_seekbar);
        mSeekBar.setOnSeekBarChangeListener(mVideoSeekBarChangeListener);

        mHandler = new Handler();





        //......................................................................................

        recyclerView = (RecyclerView) findViewById(R.id.video_lecture_rvv);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        final Bundle mBundle = getIntent().getExtras();
        if(mBundle != null){


            chapterName = mBundle.getString("chapterName");
            categoryName = mBundle.getString("categoryName");

            //getSupportActionBar().setTitle(mBundle.getString("fontTitle"));



        }



        loadVideoLecture ();
        videoLectureModelList = new ArrayList<>();




    }

    private void loadVideoLecture () {

        //first getting the values
        final String CategoryName = categoryName.toString();
        final String ChapterName = chapterName.toString();

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

                            VideoLectureModelAdapterTwo adapter = new VideoLectureModelAdapterTwo(PlayerActivity.this,videoLectureModelList);
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
                        Toast.makeText(getApplicationContext(), "Your internet connection is slow", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("chapter", ChapterName);
                params.put("category_name", CategoryName);
                return params;
            }
        };

        VolleySingleton.getInstance(PlayerActivity.this).addToRequestQueue(stringRequest);



    }



    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer player, final boolean wasRestored) {
        if (null == player) return;
        mPlayer = player;

        displayCurrentTime();

        //.......................................................................................

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
                               // Toast.makeText(PlayerActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

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




                                //.......................................................................................
                                recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

                                    GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

                                        @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                                            return true;
                                        }

                                    });
                                    @Override
                                    public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                                        ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());


                                        if(ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {

                                            RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(ChildView);
                                            //  Toast.makeText(getActivity(), (CharSequence) newsCategoryNameList.get(RecyclerViewItemPosition), Toast.LENGTH_LONG).show();
                                            model = (videoLectureModelList.get(RecyclerViewItemPosition));

                                            url = model.getVideoID();

                                            if(due.getSubscriptionFee().equals("paid") || model.getType().equals("free")){

                                                if (!wasRestored) {

                                                    player.loadVideo(String.valueOf(url),0);
                                                }

                                            }else  {
                                                Toast.makeText(PlayerActivity.this, "get account premium", Toast.LENGTH_SHORT).show();
                                                ViewDialog alert = new ViewDialog();
                                                alert.showDialog(PlayerActivity.this, "Get Premium Account");
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



                                //....................................................................


                            } else {
                                Toast.makeText(PlayerActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PlayerActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone_number", phn);
                return params;
            }
        };

        VolleySingleton.getInstance(PlayerActivity.this).addToRequestQueue(stringRequest);




        //.......................................................................................

         url = getIntent().getExtras().getString("video_url");
        // Start buffering
        if (!wasRestored) {


            player.loadVideo(String.valueOf(url),0);
        }




        player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
        mPlayButtonLayout.setVisibility(View.VISIBLE);

        // Add listeners to YouTubePlayer instance
        player.setPlayerStateChangeListener(mPlayerStateChangeListener);
        player.setPlaybackEventListener(mPlaybackEventListener);

        fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.setFullscreen(true);
            }
        });






    }

    YouTubePlayer.PlaybackEventListener mPlaybackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
            mHandler.removeCallbacks(runnable);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onPlaying() {
           mHandler.postDelayed(runnable, 100);
            displayCurrentTime();
        }

        @Override
        public void onSeekTo(int arg0) {
            mHandler.postDelayed(runnable, 100);
        }

        @Override
        public void onStopped() {
            mHandler.removeCallbacks(runnable);
        }
    };

    YouTubePlayer.PlayerStateChangeListener mPlayerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onVideoStarted() {
            displayCurrentTime();

        }
    };

    SeekBar.OnSeekBarChangeListener mVideoSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            long lengthPlayed = (mPlayer.getDurationMillis() * seekBar.getProgress()) / 100;
            mPlayer.seekToMillis((int) lengthPlayed);

        }
    };



    @RequiresApi(api = Build.VERSION_CODES.N)
    private void displayCurrentTime() {
        if (null == mPlayer) return;
        String formattedTime = formatTime(mPlayer.getDurationMillis() - mPlayer.getCurrentTimeMillis());
        mPlayTimeTextView.setText(formattedTime);

        int playPercent = (int) (((float) mPlayer.getCurrentTimeMillis()/(float) mPlayer.getDurationMillis()) * 100);
        // System.out.println("get youtube displayTime 2 : "+playPercent);
        // update live progress
        mSeekBar.setProgress(playPercent, true);

    }

    private String formatTime(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        int hours = minutes / 60;

        return  String.format("%02d:%02d", minutes % 60, seconds % 60);
    }


    private Runnable runnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {
            displayCurrentTime();
            mHandler.postDelayed(this, 100);

            String time = mPlayTimeTextView.getText().toString();

            if(time.equals("00:00")){
                endLayer.setVisibility(View.GONE);
                mPlayer.setFullscreen(false);
            }else {
                endLayer.setVisibility(View.VISIBLE);
            }
        }
    };


    @Override
    public void onBackPressed(){


        Toast.makeText(this, "pause video and exit", Toast.LENGTH_SHORT).show();
        
        if(mPlayer.isPlaying()){
            mPlayer.setFullscreen(false);
        }else {
            finish();
        }



    }


}