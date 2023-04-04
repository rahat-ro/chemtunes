package com.programmersjail.chemtunesorganicchemistry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.programmersjail.chemtunesorganicchemistry.helper.SharedPrefManager;
import com.programmersjail.chemtunesorganicchemistry.home.HomeFragment;
import com.programmersjail.chemtunesorganicchemistry.liveclass.LiveClassFragment;
import com.programmersjail.chemtunesorganicchemistry.more.MoreFragment;
import com.programmersjail.chemtunesorganicchemistry.registration.ProfileFragment;
import com.programmersjail.chemtunesorganicchemistry.registration.SignIn;
import com.programmersjail.chemtunesorganicchemistry.studyui.StudyFragment;
import com.programmersjail.chemtunesorganicchemistry.helper.ViewPagerAdapter;

import static com.programmersjail.chemtunesorganicchemistry.R.drawable.logo_four;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    MenuItem prevMenuItem;

    private HomeFragment homeFragment;
    private StudyFragment studyFragment;
    private LiveClassFragment liveClassFragment;
    private ProfileFragment profileFragment;
    private MoreFragment moreFragment;
    //CategoryFragment categoryFragment;




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // mTextMessage.setText(R.string.title_home);
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    // mTextMessage.setText(R.string.title_dashboard);
                    viewPager.setCurrentItem(1);
                    return true;

                case R.id.navigation_live_class:
                    // mTextMessage.setText(R.string.title_notifications);
                    viewPager.setCurrentItem(2);
                    return true;

                case R.id.navigation_notifications:
                    // mTextMessage.setText(R.string.title_notifications);
                    viewPager.setCurrentItem(3);
                    return true;

                case R.id.navigation_more:
                    // mTextMessage.setText(R.string.title_notifications);
                    viewPager.setCurrentItem(4);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /** if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }**/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(getResources().getDrawable(logo_four));
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, SignIn.class));
        }


        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(viewPager);





    }



    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        studyFragment = new StudyFragment();
        liveClassFragment = new LiveClassFragment();
        profileFragment = new ProfileFragment();
        moreFragment = new MoreFragment();

        adapter.addFragment(homeFragment);
        adapter.addFragment(studyFragment);
        adapter.addFragment(liveClassFragment);
        adapter.addFragment(profileFragment);
        adapter.addFragment(moreFragment);

        viewPager.setAdapter(adapter);
    }

    public class ViewDialogExit  {


        public void showDialog(Activity activity, String msg){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_dialog);

            TextView text = (TextView) dialog.findViewById(R.id.dialog_msg);
            text.setText(msg);

            ImageView dialogButtonC = (ImageView) dialog.findViewById(R.id.cancel);
            dialogButtonC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            ImageView dialogButtonD = (ImageView) dialog.findViewById(R.id.done);
            dialogButtonD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                    finishAffinity();
                    System.exit(0);
                }
            });

            dialog.show();

        }

    }
    @Override
    public void onBackPressed(){

        //Toast.makeText(getApplicationContext(), "get account premium", Toast.LENGTH_SHORT).show();
        ViewDialogExit alert = new ViewDialogExit();
        alert.showDialog(MainActivity.this, "Want to EXIT ?");



    }

}