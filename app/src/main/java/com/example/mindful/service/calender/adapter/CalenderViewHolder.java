package com.example.mindful.service.calender.adapter;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindful.R;
import com.example.mindful.service.calender.adapter.CalenderAdapter;

public class CalenderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView dayOfMonth;
    private Button dayOfMonthButton;
    private final CalenderAdapter.OnItemListener onItemListener;
    final private String TAG = "CalenderViewHolder";

    public CalenderViewHolder(@NonNull View itemView, CalenderAdapter.OnItemListener onItemListener) {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.dayOfMonthTV);
        dayOfMonthButton = itemView.findViewById(R.id.dayOfMonthBtn);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        dayOfMonthButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        Log.d(TAG, "DUMBIE");
        onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
        startColorAnimation(dayOfMonthButton);
        Log.d(TAG, (String) dayOfMonth.getText());
    }



    private void startColorAnimation(View view) {

        int colorStart = view.getSolidColor();
        int colorEnd = 0xFFFF0000;

        ValueAnimator colorAnim = ObjectAnimator.ofInt(view, "backgroundColor",
                colorStart, colorEnd);
        colorAnim.setDuration(100);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(1);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
    }

    public TextView getDayOfMonth() {
        return dayOfMonth;
    }

    public Button getDayOfMonthButton() {
        return dayOfMonthButton;
    }
}
