package com.example.mindful.service.appointment.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mindful.service.appointment.AppointmentFragment;

import java.util.List;

public class AppointmentTabPagerAdapter extends FragmentStateAdapter {

    private List<AppointmentFragment> fragments;

    public AppointmentTabPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<AppointmentFragment> fragments) {
        super(fragmentActivity);

        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
