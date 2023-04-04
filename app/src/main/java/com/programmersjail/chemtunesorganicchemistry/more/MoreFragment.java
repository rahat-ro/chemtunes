package com.programmersjail.chemtunesorganicchemistry.more;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.programmersjail.chemtunesorganicchemistry.R;
import com.programmersjail.chemtunesorganicchemistry.helper.ChapterName;
import com.programmersjail.chemtunesorganicchemistry.helper.ChapterNameAdapter;
import com.programmersjail.chemtunesorganicchemistry.helper.URls;
import com.programmersjail.chemtunesorganicchemistry.helper.VolleySingleton;
import com.programmersjail.chemtunesorganicchemistry.more.MoreModel;
import com.programmersjail.chemtunesorganicchemistry.more.MoreModelAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MoreFragment extends Fragment {

    private RecyclerView recyclerViewTwo;
    private GridLayoutManager gridLayoutManager;
    private List<MoreModel> moreModelList;


    public MoreFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_more, container, false);

        recyclerViewTwo = (RecyclerView) rootView.findViewById(R.id.more_rv);
        recyclerViewTwo.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        recyclerViewTwo.setLayoutManager(gridLayoutManager);
        loadMoreDetails ();
        moreModelList = new ArrayList<>();

        return rootView;



    }

    private void loadMoreDetails () {

        //first getting the values
        // final String categoryName = tv.getText().toString().trim();

        final String more = "more";
        final String typeName = more.toString();

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
                params.put("type", more);
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);



    }
}