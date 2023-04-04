package com.programmersjail.chemtunesorganicchemistry.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.programmersjail.chemtunesorganicchemistry.registration.SignIn;
import com.programmersjail.chemtunesorganicchemistry.registration.User;


public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "naim_rahat";
    private static final String KEY_ID = "keyid";
    private static final String KEY_USER_NAME = "keyUserName";
    private static final String KEY_PHONE_NUMBER = "keyPhoneNumber";
    private static final String KEY_PASSWORD = "keyPassowrd";
    private static final String KEY_COLLAGE_NAME = "keyCollageName";
    private static final String KEY_HSC_EXAM_TIME = "keyHSCExamTime";
    private static final String KEY_ACCOUNT_TYPE = "keyAccountType";




    private static SharedPrefManager mInstance;
    private static Context mCtx;


    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }



    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USER_NAME,user.getUserName());
        editor.putString(KEY_PHONE_NUMBER,user.getPhoneNumber());
        editor.putString(KEY_PASSWORD,user.getPassword());
        editor.putString(KEY_COLLAGE_NAME, user.getCollageName());
        editor.putString(KEY_HSC_EXAM_TIME,user.getHscExamTime());
        editor.putString(KEY_ACCOUNT_TYPE, user.getAccountType());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PASSWORD, null) != null;
    }


    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USER_NAME, null),
                sharedPreferences.getString(KEY_PHONE_NUMBER, null),
                sharedPreferences.getString(KEY_PASSWORD,null),
                sharedPreferences.getString(KEY_COLLAGE_NAME, null),
                sharedPreferences.getString(KEY_HSC_EXAM_TIME,null),
                sharedPreferences.getString(KEY_ACCOUNT_TYPE, null)

        );
    }

    //this method will logout the user
     public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, SignIn.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP ));
    }


}
