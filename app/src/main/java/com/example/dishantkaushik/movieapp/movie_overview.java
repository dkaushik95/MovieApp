package com.example.dishantkaushik.movieapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class movie_overview extends Fragment {

    public movie_overview(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_movie_overview, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView title,year,rating,overview;
        title=(TextView)getActivity().findViewById(R.id.movie_title);
        year=(TextView)getActivity().findViewById(R.id.year_data);
        rating=(TextView)getActivity().findViewById(R.id.rating_text);
        overview=(TextView)getActivity().findViewById(R.id.overview_text);
        title.setText(getArguments().getString("title"));
        year.setText(getArguments().getString("release_date").substring(0,4));
        rating.setText(getArguments().getString("rating"));
        overview.setText(getArguments().getString("overview"));
    }
}
