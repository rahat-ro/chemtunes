package com.programmersjail.chemtunesorganicchemistry.slider;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.programmersjail.chemtunesorganicchemistry.R;
import com.programmersjail.chemtunesorganicchemistry.TextActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;


public class SlideOne extends Fragment {


    private ImageView gif;
    private TextView de;



    public SlideOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_slide_one, container, false);

        gif = (ImageView) rootView.findViewById(R.id.slide_video); // initiate a video view
        String uri = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.ss;
        Glide.with(this).load(uri).into(gif);

        de = (TextView) rootView.findViewById(R.id.deee);
        de.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TextActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("title",R.string.a);
                i.putExtra("description",R.string.a1);
                i.putExtra("descriptionOne",R.string.f);
                i.putExtra("descriptionTwo",R.string.f);
                i.putExtra("descriptionThree",R.string.f);

                startActivity(i);
            }
        });

        return  rootView;
    }
}