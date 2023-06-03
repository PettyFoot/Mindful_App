package com.example.mindful.service.calender.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindful.R;
import com.example.mindful.service.calender.Date;

import java.util.ArrayList;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderViewHolder> {

    //private final ArrayList<DayOfMonth> daysOfMonth;
    private final OnItemListener onItemListener;
    private final Context context;
    private int size;

    private final String TAG = "CalenderAdapter";

    private ArrayList<Date> daysOfMonth;


    public CalenderAdapter(ArrayList<Date> daysOfMonth, OnItemListener onItemListener, Context context) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        this.context = context;


    }

    @NonNull
    @Override
    public CalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calender_cell_month_view,  parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.16);

        return new CalenderViewHolder(view, onItemListener);
    }


    @Override
    public void onBindViewHolder(@NonNull CalenderViewHolder holder, int position) {

        holder.getDayOfMonth().setText(daysOfMonth.get(position).getDayS());
       // Log.d(TAG, holder.getDayOfMonth().getClass().getName());

    }

    @Override
    public int getItemCount() {
        //Log.d(TAG, String.valueOf(daysOfMonth.size()));
        return daysOfMonth.size();
    }

    public void setSize(int size) {
        this.size = size;
    }



    public interface  OnItemListener
    {
        void onItemClick(int position, String dayText);
        void OnDateChange(boolean IsChangingForward);
    }


}
