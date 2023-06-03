package com.example.mindful.service.appointment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.viewpager2.widget.ViewPager2;


import com.example.mindful.service.appointment.adapter.AppointmentTabPagerAdapter;
import com.example.mindful.R;
import com.example.mindful.service.appointment.forms.PersonalInfoForm;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppointmentsViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentsViewFragment extends Fragment {


    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    private String TAG = "AppointmentsViewFragment";

    public AppointmentsViewFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AppointmentsViewFragment newInstance(String param1, String param2) {
        AppointmentsViewFragment fragment = new AppointmentsViewFragment();
        Bundle args = new Bundle();
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
        View rootView =  inflater.inflate(R.layout.fragment_appointments_view, container, false);

        InitWidgets(rootView);

        //(R) = required, else optional
        List<AppointmentFragment> fragments = new ArrayList<>();
        fragments.add(new PersonalInfoForm("Personal Info",
                "Form for basic patient identity info, name, dob, addresses, gender"));
        fragments.add(new AppointmentFragmentViewInformation("Medical Conditions",
                "holder tab for more forms"));
        fragments.add(new AppointmentFragmentViewInformation("Medications",
                "holder tab for more forms"));
        fragments.add(new AppointmentFragmentViewInformation("Surgical History",
                "holder tab for more forms"));
        fragments.add(new AppointmentFragmentViewInformation("Lifestyle Habits",
                "holder tab for more forms"));
        fragments.add(new AppointmentFragmentViewInformation("Allergies",
                "holder tab for more forms"));
        fragments.add(new AppointmentFragmentViewInformation("Emergency Contacts",
                "holder tab for more forms"));
        fragments.add(new AppointmentFragmentViewInformation("Current Symptoms",
                "holder tab for more forms"));



        AppointmentTabPagerAdapter adapter = new AppointmentTabPagerAdapter(requireActivity(), fragments);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                            tab.setText(fragments.get(position).getFragmentTitle());

                }).attach();


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        Log.d(TAG, String.valueOf(width));
        for(int i = 0; i < tabLayout.getTabCount(); i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if(tab != null){
                Log.d(TAG, "tab exists");
                Log.d(TAG, tab.getClass().getName());
                View tabView = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);;
                if(tabView != null){
                    Log.d(TAG, tabView.getClass().getName());
                    ViewGroup.LayoutParams layoutParams = tabView.getLayoutParams();
                    layoutParams.width = width/2;
                    tabView.setLayoutParams(layoutParams);
                    //tab.setText(fragments.get(0).getFragmentTitle());
                }
            }

        }

        //ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(displayMetrics.widthPixels, tabLayout.getHeight());
       // tabLayout.setLayoutParams(layoutParams);

        return rootView;
    }


    private void InitWidgets(View rootView) {

        viewPager = rootView.findViewById(R.id.viewPager);
        tabLayout = rootView.findViewById(R.id.tabLayout);
    }

    private void adjustTabWidth(TabLayout tabLayout) {
        ViewGroup slidingTabStrip = (ViewGroup) tabLayout.getChildAt(0);
        int tabCount = slidingTabStrip.getChildCount();
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int desiredTabWidth = screenWidth / tabCount;

        for (int i = 0; i < tabCount; i++) {
            View tabView = slidingTabStrip.getChildAt(i);
            ViewGroup.LayoutParams layoutParams = tabView.getLayoutParams();
            layoutParams.width = desiredTabWidth;
            tabView.setLayoutParams(layoutParams);
        }
    }
}