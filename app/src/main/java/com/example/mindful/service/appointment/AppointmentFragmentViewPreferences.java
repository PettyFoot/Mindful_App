package com.example.mindful.service.appointment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mindful.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppointmentFragmentViewPreferences#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentFragmentViewPreferences extends AppointmentFragment {



    public AppointmentFragmentViewPreferences() {
        // Required empty public constructor
    }



    //constructor for fragment with a title attribute to pass in
    public AppointmentFragmentViewPreferences(String title) {
        this.fragmentTitle = title;
    }

    //constructor for fragment with a title, description to pass in
    public AppointmentFragmentViewPreferences(String title, String description) {
        this.fragmentTitle = title;
        this.fragmentDescription = description;
    }



    public static AppointmentFragmentViewPreferences newInstance(String param1, String param2) {
        AppointmentFragmentViewPreferences fragment = new AppointmentFragmentViewPreferences();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointment_view_preferences, container, false);
    }
}