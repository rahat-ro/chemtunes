package com.programmersjail.chemtunesorganicchemistry.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.material.tabs.TabLayout;
import com.programmersjail.chemtunesorganicchemistry.R;
import com.programmersjail.chemtunesorganicchemistry.chempak.ChemPackageModel;
import com.programmersjail.chemtunesorganicchemistry.chempak.ChemPackageModelAdapter;
import com.programmersjail.chemtunesorganicchemistry.helper.ChapterName;
import com.programmersjail.chemtunesorganicchemistry.helper.ChapterNameAdapter;
import com.programmersjail.chemtunesorganicchemistry.helper.URls;
import com.programmersjail.chemtunesorganicchemistry.helper.ViewPagerAdapter;
import com.programmersjail.chemtunesorganicchemistry.helper.VolleySingleton;
import com.programmersjail.chemtunesorganicchemistry.more.MoreModel;
import com.programmersjail.chemtunesorganicchemistry.more.MoreModelAdapter;
import com.programmersjail.chemtunesorganicchemistry.notice.NoticeModel;
import com.programmersjail.chemtunesorganicchemistry.notice.NoticeModelAdapter;
import com.programmersjail.chemtunesorganicchemistry.slider.SlideOne;
import com.programmersjail.chemtunesorganicchemistry.slider.SlideThree;
import com.programmersjail.chemtunesorganicchemistry.slider.SlideTwo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private ViewPager slider;
    private SlideOne slideOne;
    private SlideTwo slideTwo;
    private SlideThree slideThree;
    private TabLayout tabLayout;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 10000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 10000; // time in milliseconds between successive task executions.

    private RecyclerView recyclerView,recyclerViewOne,recyclerViewTwo;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager layoutManager;

    private List<ChemPackageModel> chemPackageModelList;
    private List<MoreModel> moreModelList;
    private List<NoticeModel> noticeModelList;

    private TextView tv;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        slider = (ViewPager) rootView.findViewById(R.id.slider_viewpager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(slider);

        tv = (TextView) rootView.findViewById(R.id.course);

        slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(slider);

        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 4-1) {
                    currentPage = 0;
                }
                slider.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
        //......................................................................................


        recyclerViewOne = (RecyclerView) rootView.findViewById(R.id.chem_notice_rv);
        recyclerViewOne.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerViewOne.setLayoutManager(layoutManager);
        loadNotice();
        noticeModelList = new ArrayList<>();

        //...................................................................................

        recyclerView = (RecyclerView) rootView.findViewById(R.id.chem_course_rv);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        loadMoreDetails ();
        moreModelList = new ArrayList<>();

        //.........................................................................................

        recyclerViewTwo = (RecyclerView) rootView.findViewById(R.id.chem_package_rv);
        recyclerViewTwo.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        recyclerViewTwo.setLayoutManager(gridLayoutManager);
        loadChemPackage();
        chemPackageModelList = new ArrayList<>();

        //.........................................................................................


        return rootView;
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        slideOne = new SlideOne();
        slideTwo = new SlideTwo();
        slideThree = new SlideThree();

        adapter.addFragment(slideOne);
        adapter.addFragment(slideTwo);
        adapter.addFragment(slideThree);

        viewPager.setAdapter(adapter);
    }

    private void loadNotice () {

        //first getting the values
        // final String categoryName = tv.getText().toString().trim();
        //final String categoryName = fee.getText().toString();

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_ANNOUNCEMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {



                            JSONArray array = new JSONArray(response);


                            if(noticeModelList != null){
                                noticeModelList.clear();
                            }


                            for (int i = 0; i < array.length(); i++) {


                                JSONObject room = array.getJSONObject(i);
                                noticeModelList.add(new NoticeModel(

                                        room.getString("name")



                                ));
                            }

                            NoticeModelAdapter adapter = new NoticeModelAdapter(getActivity(),noticeModelList);
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
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //  params.put("type", categoryName);
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);



    }

    private void loadMoreDetails () {

        //first getting the values
        // final String categoryName = tv.getText().toString().trim();


        final String typeName = tv.getText().toString();

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_CHEM_MORE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {



                            JSONArray array = new JSONArray(response);


                            if(moreModelList != null){
                                moreModelList.clear();
                            }


                            for (int i = 0; i < array.length(); i++) {


                                JSONObject room = array.getJSONObject(i);
                                moreModelList.add(new MoreModel(

                                        room.getString("title"),
                                        room.getString("description"),
                                        room.getString("titleTwo"),
                                        room.getString("descriptionTwo")



                                ));
                            }

                            MoreModelAdapter adapter = new MoreModelAdapter(getActivity(),moreModelList);
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
                        String message = null;
                        if (error instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("type", typeName);
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);



    }

    private void loadChemPackage(){

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_CHEM_PACKAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {



                            JSONArray array = new JSONArray(response);


                            if(chemPackageModelList != null){
                                chemPackageModelList.clear();
                            }


                            for (int i = 0; i < array.length(); i++) {


                                JSONObject room = array.getJSONObject(i);
                                chemPackageModelList.add(new ChemPackageModel(

                                        room.getString("package_title"),
                                        room.getString("package_s_dis"),
                                        room.getString("package_dis"),
                                        room.getString("package_img"),
                                        room.getString("package_duration"),
                                        room.getString("package_fee"),
                                        room.getString("package_limit")








                                ));
                            }

                            ChemPackageModelAdapter adapter = new ChemPackageModelAdapter(getActivity(),chemPackageModelList);
                            // adapter.notifyDataSetChanged();
                            recyclerViewTwo.setAdapter(adapter);





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
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //  params.put("type", categoryName);
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }



}