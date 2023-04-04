package com.programmersjail.chemtunesorganicchemistry.payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
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
import com.programmersjail.chemtunesorganicchemistry.flashcard.FlashCardActivity;
import com.programmersjail.chemtunesorganicchemistry.helper.SharedPrefManager;
import com.programmersjail.chemtunesorganicchemistry.helper.URls;
import com.programmersjail.chemtunesorganicchemistry.helper.VolleySingleton;
import com.programmersjail.chemtunesorganicchemistry.registration.User;
import com.programmersjail.chemtunesorganicchemistry.videolec.VideoLecture;
import com.programmersjail.chemtunesorganicchemistry.videolec.VideoLectureModel;
import com.programmersjail.chemtunesorganicchemistry.videolec.VideoLectureModelAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentHistory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<PaymentHistoryModel> paymentHistoryModelList;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));


        recyclerView = (RecyclerView) findViewById(R.id.payment_rv);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);


        loadPaymentHistory();
        paymentHistoryModelList = new ArrayList<>();

        //............................................................................

        final Bundle mBundle = getIntent().getExtras();
        if(mBundle != null){


            getSupportActionBar().setTitle(mBundle.getString("title"));



        }

        //............................................................................




    }

    private void loadPaymentHistory() {

        //first getting the values
        User user = SharedPrefManager.getInstance(this).getUser();

        final String phoneNumber = user.getPhoneNumber();
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_PAYMENT_HISTORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {



                            JSONArray array = new JSONArray(response);


                            if(paymentHistoryModelList != null){
                                paymentHistoryModelList.clear();
                            }


                            for (int i = 0; i < array.length(); i++) {


                                JSONObject room = array.getJSONObject(i);
                                paymentHistoryModelList.add(new PaymentHistoryModel(


                                        room.getString("user_name"),
                                        room.getString("phone_number"),
                                        room.getString("collage_name"),
                                        room.getString("subscription_fee"),
                                        room.getString("mob_banking_no"),
                                        room.getString("trans_id"),
                                        room.getString("amount"),
                                        room.getString("date"),
                                        room.getString("time"),
                                        room.getString("package_title"),
                                        room.getString("month_limit")


                                ));
                            }

                            PaymentHistoryModelAdapter adapter = new PaymentHistoryModelAdapter(PaymentHistory.this,paymentHistoryModelList);
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
                params.put("phone_number", phoneNumber);
                return params;
            }
        };

        VolleySingleton.getInstance(PaymentHistory.this).addToRequestQueue(stringRequest);


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