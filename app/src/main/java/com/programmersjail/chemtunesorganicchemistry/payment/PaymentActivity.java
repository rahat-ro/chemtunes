package com.programmersjail.chemtunesorganicchemistry.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.programmersjail.chemtunesorganicchemistry.R;
import com.programmersjail.chemtunesorganicchemistry.helper.SharedPrefManager;
import com.programmersjail.chemtunesorganicchemistry.helper.URls;
import com.programmersjail.chemtunesorganicchemistry.helper.VolleySingleton;
import com.programmersjail.chemtunesorganicchemistry.registration.ProfileLandingActivity;
import com.programmersjail.chemtunesorganicchemistry.registration.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class PaymentActivity extends AppCompatActivity {


    private TextView t1,t2,t3,t4,t7,t8,t9,t12,t112;
    private EditText t5,t6;
    private Button payment;
    private String name,limit;
    private User user;

    public static final String DATE_FORMAT_7 = "EEE, MMM d, ''yy";
    public static final String DATE_FORMAT_1 = "hh:mm a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Payment");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));


        t1 = (TextView) findViewById(R.id.p_name);
        t2 = (TextView) findViewById(R.id.p_phone_number);
        t3 = (TextView) findViewById(R.id.p_collage_name);
        t4 = (TextView) findViewById(R.id.p_subscription_fee);
        t5 = (EditText) findViewById(R.id.p_mob_banking_no);
        t6 = (EditText) findViewById(R.id.p_trans_id);
        t12 = (TextView) findViewById(R.id.package_name);
        t112 = (TextView) findViewById(R.id.month_limit);
        //t7 = (TextView) itemView.findViewById(R.id.p_amount);
        t8 = (TextView) findViewById(R.id.p_date);
        t9 = (TextView) findViewById(R.id.p_time);
        payment = (Button) findViewById(R.id.btnPayment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPayment();
            }
        });

        user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        t1.setText(user.getUserName());
        t2.setText(user.getPhoneNumber());
        t3.setText(user.getCollageName());
        t4.setText("pending");

        t8.setText(getCurrentDate());
        t9.setText(getCurrentTime());


        final Bundle mBundle = getIntent().getExtras();
        if(mBundle != null){

            name = mBundle.getString("name");
            limit = mBundle.getString("limit");

            t12.setText(name);
            t112.setText(limit);


        }


    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_7);
        //dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }
    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        //dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }


    private void userPayment() {

        //first getting the values
        final String p_name = t1.getText().toString();
        final String p_phone_number = t2.getText().toString();
        final String p_collage_name = t3.getText().toString();
        final String p_subscription_fee = t4.getText().toString();
        final String p_mob_banking_no = t5.getText().toString();
        final String p_trans_id = t6.getText().toString();
        //final String p_amount = t7.getText().toString();
        final String p_date = t8.getText().toString();
        final String p_time = t9.getText().toString();
        final String pakName = t12.getText().toString();
        final String pakLimit = t112.getText().toString();



        //validating inputs
        if (TextUtils.isEmpty(p_mob_banking_no)) {
            t5.setError("mob banking number");
            t5.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(p_trans_id)) {
            t6.setError("Please enter your TrxID");
            t6.requestFocus();
            return;
        }



        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_PAYMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");
                                //userJson.getString("user");

                                //creating a new user object
                                /**User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("user_name"),
                                        userJson.getString("phone_number"),
                                        userJson.getString("password"),
                                        userJson.getString("collage_name"),
                                        userJson.getString("hsc_exam_time"),
                                        userJson.getString("account_type")

                                );**/

                                userUpDateProfileLan();


                                //starting the profile activity
                                startActivity(new Intent(getApplicationContext(),PaymentHistory.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                                                Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP));
                                finish();

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
                params.put("user_name", p_name);
                params.put("phone_number", p_phone_number);
                params.put("collage_name", p_collage_name);
                params.put("subscription_fee", p_subscription_fee);
                params.put("mob_banking_no", p_mob_banking_no);
                params.put("trans_id", p_trans_id);
                params.put("date", p_date);
                params.put("time", p_time);
                params.put("package_title", pakName);
                params.put("month_limit", pakLimit);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    private void userUpDateProfileLan() {


        String lim = "pending";
        final String PhoneNumber = user.getPhoneNumber();
        final String Name = t12.getText().toString();
        final String Limit = lim.toString();
        final String Fee = lim.toString();
        final String accountType = lim.toString();

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_UPDATE_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);


                            //userSignIn();

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
                params.put("phone_number", PhoneNumber);
                params.put("package_name", Name);
                params.put("package_limit", Limit);
                params.put("subscription_fee", Limit);
                params.put("account_type", accountType);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

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