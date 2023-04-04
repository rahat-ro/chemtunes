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


public class SlideThree extends Fragment {


    private TextView de;

    public SlideThree() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_slide_three, container, false);

        de = (TextView) rootView.findViewById(R.id.de);
        de.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TextActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("title",R.string.c);
                i.putExtra("description",R.string.c1);
                i.putExtra("descriptionOne",R.string.c2);
                i.putExtra("descriptionTwo",R.string.c3);
                i.putExtra("descriptionThree",R.string.f);
                startActivity(i);
            }
        });


        return rootView;
    }
}