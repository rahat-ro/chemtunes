package com.programmersjail.chemtunesorganicchemistry.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class ProfileLandingActivity extends AppCompatActivity {

    private EditText name,collageName,HscExamTime,password;
    private Button updateButton;
    private TextView phNo,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_landing);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));



        phNo = (TextView) findViewById(R.id.l_phn_no);
        pass = (TextView) findViewById(R.id.l_pass);
        name = (EditText) findViewById(R.id.l_update_name);
        collageName = (EditText) findViewById(R.id.l_update_collage_name);
        HscExamTime = (EditText) findViewById(R.id.l_update_hsc_exam_time);
        updateButton = (Button) findViewById(R.id.l_btnUpdate);

        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        //setting the values to the textviews
        //textViewId.setText(String.valueOf(user.getId()));
        phNo.setText(user.getPhoneNumber());
        pass.setText(user.getPassword());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userUpDateProfileLan();

            }
        });


    }

    private void userUpDateProfileLan() {

        final String PhoneNumber = phNo.getText().toString();
        final String Name = name.getText().toString();
        final String CollageName = collageName.getText().toString();
        final String HSC = HscExamTime.getText().toString();



        //validating inputs
        if (TextUtils.isEmpty(Name)) {
            name.setError("Please enter your name");
            name.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(CollageName)) {
            collageName.setError("Please enter your collage name");
            collageName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(HSC)) {
            HscExamTime.setError("Please enter your HSC Exam year");
            HscExamTime.requestFocus();
            return;
        }




        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_UPDATE_PROFILE_LAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);


                            userSignIn();

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
                params.put("user_name", Name);
                params.put("collage_name", CollageName);
                params.put("hsc_exam_time", HSC);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void userSignIn() {

        //first getting the values
        final String PhoneNumber = phNo.getText().toString();
        // final String Password = password.getText().toString();


        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_SIGN_IN_TWO,
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
                                JSONObject userJson = obj.getJSONObject("chemtunes_students");
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
                                startActivity(new Intent(getApplicationContext(), MainActivity.class)
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
                // params.put("password", Password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

}