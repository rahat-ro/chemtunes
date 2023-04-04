package com.programmersjail.chemtunesorganicchemistry.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
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
import com.programmersjail.chemtunesorganicchemistry.MainActivity;
import com.programmersjail.chemtunesorganicchemistry.R;
import com.programmersjail.chemtunesorganicchemistry.helper.SharedPrefManager;
import com.programmersjail.chemtunesorganicchemistry.helper.URls;
import com.programmersjail.chemtunesorganicchemistry.helper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private EditText phoneNumber,password;
    private Button btnSignUp;

    public static final String DATE_FORMAT_7 = "EEE, MMM d, ''yy";
    public static final String DATE_FORMAT_1 = "hh:mm a";



    private TextView t1,t2,t3;

    private String android_id;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ProfileLandingActivity.class));
            return;
        }

        phoneNumber = (EditText) findViewById(R.id.input_phn_no);
        password = (EditText) findViewById(R.id.input_pass);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignUp();
            }
        });




         android_id = Settings.Secure.getString(this.getContentResolver(),
                 Settings.Secure.ANDROID_ID);

         t1 = (TextView) findViewById(R.id.t1);
         t1.setText(getCurrentDate());
         t2 = (TextView) findViewById(R.id.t2);
         t2.setText(getCurrentTime());
         t3 = (TextView) findViewById(R.id.t3);
         t3.setText(android_id);




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


    private void userSignUp() {



        //first getting the values
        final String PhoneNumber = phoneNumber.getText().toString();
        final String Password = password.getText().toString();
        final String accountType = "get premium account";
        final String subscriptionFee = "not subscribed";
        final String packageName = "no package";
        final String packageLimit = "zero";

        final String date = t1.getText().toString();
        final String time = t2.getText().toString();
        final String mac = t3.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(PhoneNumber)) {
            phoneNumber.setError("Please enter your phone number");
            phoneNumber.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Password)) {
            phoneNumber.setError("Please enter your password");
            phoneNumber.requestFocus();
            return;
        }



        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_SIGN_UP,
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
                                User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("user_name"),
                                        userJson.getString("phone_number"),
                                        userJson.getString("password"),
                                        userJson.getString("collage_name"),
                                        userJson.getString("hsc_exam_time"),
                                        userJson.getString("account_type")

                                );


                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
                                startActivity(new Intent(getApplicationContext(), ProfileLandingActivity.class)
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
                params.put("phone_number", PhoneNumber);
                params.put("password", Password);
                params.put("account_type", accountType);
                params.put("subscription_fee", subscriptionFee);
                params.put("date", date);
                params.put("time", time);
                params.put("mac", mac);
                params.put("package_name", packageName);
                params.put("package_limit", packageLimit);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}