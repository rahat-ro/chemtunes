package com.programmersjail.chemtunesorganicchemistry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class TextActivity extends AppCompatActivity {

    private TextView t1,t2,t3,t4,t5;
    private ImageView im1,im2,im3,im4,im5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));

        t1 = (TextView) findViewById(R.id.t11);
        t2 = (TextView) findViewById(R.id.t12);
        t3 = (TextView) findViewById(R.id.t13);
        t4 = (TextView) findViewById(R.id.t14);


        final Bundle mBundle = getIntent().getExtras();
        if(mBundle != null){

            getSupportActionBar().setTitle(mBundle.getInt("title"));
            t1.setText(mBundle.getInt("description"));
            t2.setText(mBundle.getInt("descriptionOne"));
            t3.setText(mBundle.getInt("descriptionTwo"));
            t4.setText(mBundle.getInt("descriptionThree"));
            //t4.setText(mBundle.getInt("description3"));




        }


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