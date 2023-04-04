package com.programmersjail.chemtunesorganicchemistry.studyui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.programmersjail.chemtunesorganicchemistry.R;

import java.util.Arrays;
import java.util.List;

public class StudyFragment extends Fragment {



    private TextView tv;
    private RecyclerView recyclerView,rv;
    private GridLayoutManager gridLayoutManager,gl;
    private List<StudyLayoutModel> studyLayoutModel;
    private StudyLayoutModelAdapter adapter;
    private StudyLayoutModelAdapterTwo adapterOne;

    public StudyFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootView = inflater.inflate(R.layout.fragment_study, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.s_rv);
        rv = (RecyclerView) rootView.findViewById(R.id.s_rv1);

        studyModelOne();
        studyModelTwo();

       return rootView;
    }



    private void studyModelOne() {

        //........................CategoryFive......................

        studyLayoutModel = Arrays.asList(new StudyLayoutModel("FlashCard", R.drawable.ic_baseline_flip_24),
                new StudyLayoutModel("Notes", R.drawable.ic_baseline_notes_24)
        );

        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new StudyLayoutModelAdapter(studyLayoutModel);
        recyclerView.setAdapter(adapter);



    }

    private void studyModelTwo() {

        //........................CategoryFive......................

        studyLayoutModel = Arrays.asList(new StudyLayoutModel("Video Lectures", R.drawable.ic_baseline_ondemand_video_24),
                new StudyLayoutModel("Question Solution", R.drawable.ic_baseline_question_answer_24)
        );

        rv.setHasFixedSize(true);
        gl = new GridLayoutManager(getActivity(),1);
        rv.setLayoutManager(gl);
        adapterOne = new StudyLayoutModelAdapterTwo(studyLayoutModel);
        rv.setAdapter(adapterOne);



    }


}