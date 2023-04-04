package com.programmersjail.chemtunesorganicchemistry.registration;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.programmersjail.chemtunesorganicchemistry.chempak.ChemPackageName;
import com.programmersjail.chemtunesorganicchemistry.payment.PaymentActivity;
import com.programmersjail.chemtunesorganicchemistry.R;
import com.programmersjail.chemtunesorganicchemistry.helper.SharedPrefManager;
import com.programmersjail.chemtunesorganicchemistry.helper.SubscriptionFee;
import com.programmersjail.chemtunesorganicchemistry.helper.URls;
import com.programmersjail.chemtunesorganicchemistry.helper.VolleySingleton;
import com.programmersjail.chemtunesorganicchemistry.payment.PaymentHistory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {

    private Button LogOut,update;
    private TextView updateProfile,name,phoneNum,collageName,hscExam,accountType,
            upInfo,upInfos,subscriptionFee,paymentHistory,packName,monthlyLimit;
    private User user;
    String title;


    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        updateProfile = (TextView) rootView.findViewById(R.id.update_profile);
        LogOut = (Button) rootView.findViewById(R.id.logOut);

        name = (TextView) rootView.findViewById(R.id.view_name);
        phoneNum = (TextView) rootView.findViewById(R.id.view_ph_no);
        collageName = (TextView) rootView.findViewById(R.id.view_collage_name);
        hscExam = (TextView) rootView.findViewById(R.id.view_hsc);
        accountType = (TextView) rootView.findViewById(R.id.view_account_type);
        subscriptionFee = (TextView) rootView.findViewById(R.id.view_subscription_fee);
        upInfo = (TextView) rootView.findViewById(R.id.up_info);
        upInfos = (TextView) rootView.findViewById(R.id.up_info_s);
        paymentHistory = (TextView) rootView.findViewById(R.id.payment_history);

        title = paymentHistory.getText().toString().trim();

        packName = (TextView) rootView.findViewById(R.id.view_package_name);
        monthlyLimit = (TextView) rootView.findViewById(R.id.view_limit);

        paymentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(v.getContext(), PaymentHistory.class);
                i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i2.putExtra("title",title);
                v.getContext().startActivity(i2);
            }
        });

        upInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(v.getContext(), ChemPackageName.class);
                i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // i2.putExtra("title",studyLayoutModelList.get(holder.getAdapterPosition()).getStudyTitle());
                v.getContext().startActivity(i2);
            }
        });

        upInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), ChemPackageName.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // i2.putExtra("title",studyLayoutModelList.get(holder.getAdapterPosition()).getStudyTitle());
                v.getContext().startActivity(i);
            }
        });

        //getting the current user
        User user = SharedPrefManager.getInstance(getActivity()).getUser();

        name.setText(user.getUserName());
        phoneNum.setText(user.getPhoneNumber());
        collageName.setText(user.getCollageName());
        hscExam.setText(user.getHscExamTime());
        accountType.setText(user.getAccountType());

        if(user.getAccountType().equals("get premium account")){
            upInfo.setVisibility(View.VISIBLE);
        }else {
            upInfo.setVisibility(View.GONE);
        }


        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), UpdateProfile.class);
                startActivity(i);
            }
        });


        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getActivity()).logout();
                getActivity().finish();
            }
        });


       // Intent i = new Intent(MainActivity.this,UpdateProfile.class);
        //startActivity(i);

        loadSubscriptionStatus();


        return rootView;
    }

    private void loadSubscriptionStatus(){

        user = SharedPrefManager.getInstance(getActivity()).getUser();

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
                                final SubscriptionFee due = new SubscriptionFee(
                                        userJson.getString("account_type"),
                                        userJson.getString("subscription_fee"),
                                        userJson.getString("package_name"),
                                        userJson.getString("package_limit")



                                );


                                subscriptionFee.setText(due.getSubscriptionFee());
                                packName.setText(due.getPackageName());
                                monthlyLimit.setText(due.getPackageLimit());
                                accountType.setText(due.getAccountType());

                                if(due.getSubscriptionFee().equals("due")){
                                    upInfos.setVisibility(View.VISIBLE);
                                }else {
                                    upInfos.setVisibility(View.GONE);
                                }



                            } else {
                                Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("phone_number", phn);
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);


    }


}