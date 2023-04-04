package com.programmersjail.chemtunesorganicchemistry.slider;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.programmersjail.chemtunesorganicchemistry.R;
import com.programmersjail.chemtunesorganicchemistry.TextActivity;


public class SlideTwo extends Fragment {


    private TextView de;

    public SlideTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_slide_two, container, false);

        de = (TextView) rootView.findViewById(R.id.dee);
        de.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TextActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("title",R.string.b);
                i.putExtra("description",R.string.b1);
                i.putExtra("descriptionOne",R.string.b2);
                i.putExtra("descriptionTwo",R.string.b3);
                i.putExtra("descriptionThree",R.string.f);
                startActivity(i);
            }
        });


        return rootView;
    }
}