package com.example.mindful.service.calender;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mindful.R;
import com.example.mindful.service.calender.adapter.CalenderAdapter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalenderViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalenderViewFragment extends Fragment implements CalenderAdapter.OnItemListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    final private String TAG = "CalenderViewFragment";

    //Views
    private TextView monthYearText;

    Button previousMonthButton, nextMonthButton;
    private RecyclerView calenderRecyclerView;
    private LinearLayout dateNavigationLayout, dayOfWeekLayout, recylerViewContainer;
    private LocalDate selectedDate;

    //private CalenderEventEditorFragment calenderEventEditorFragment;
    //private CalendarDBHelper calendarDBHelper;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalenderViewFragment() {
        // Required empty public constructor
    }


    public static CalenderViewFragment newInstance(String param1, String param2) {
        CalenderViewFragment fragment = new CalenderViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_calender_view, container, false);

        //Hook view type members into views
        InitWidgets(rootView);

        Log.d(TAG, "onCreateView");

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View tempView = view.findViewById(R.id.calenderRecyclerView);
        selectedDate = LocalDate.now();
        setMonthView();

        previousMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousMonthAction(view);
            }
        });

        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextMonthAction(view);
            }
        });

        Log.d(TAG, "onViewCreated");
    }




    private void setMonthView() {


        monthYearText.setText(monthYearFromData(selectedDate));
        ArrayList<Date> daysInMonth = daysInMonthFinder(selectedDate);

        CalenderAdapter calenderAdapter = new CalenderAdapter(daysInMonth, this, getContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calenderRecyclerView.setLayoutManager(layoutManager);
        calenderRecyclerView.setAdapter(calenderAdapter);


    }

    private ArrayList<Date> daysInMonthFinder(LocalDate selectedDate) {

        ArrayList<Date> daysInMonthArr = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(selectedDate);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        int year = selectedDate.getYear();

        for(int i = 1; i <= 42; i++){

            //if the first day of the month is sunday run the algorithm this way
            if(dayOfWeek == 7){
                if(i> daysInMonth){
                    Log.d(TAG, "Not valid date, i: " + i);
                    daysInMonthArr.add(new Date(0, 0,  year, 0));
                }else{
                    daysInMonthArr.add(new Date(selectedDate.getMonthValue(), i, year, i));

                }

            }
            //If the first day of the month is not sunday run the algorithm this way
            else{
                if(i <= dayOfWeek  || i > daysInMonth + dayOfWeek){
                    Log.d(TAG, "Not valid date, i: " + i);
                    daysInMonthArr.add(new Date(0, 0,  year, 0));
                }else{
                    LocalDate dateInMonth = LocalDate.of(year, selectedDate.getMonthValue(), i-dayOfWeek);
                    daysInMonthArr.add(new Date(selectedDate.getMonthValue(), i-dayOfWeek, year, dateInMonth.getDayOfWeek().getValue()));
                }
            }
        }

        return daysInMonthArr;
    }

    private String monthYearFromData(LocalDate selectedDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return selectedDate.format(formatter);


    }

    @Override
    public void onItemClick(int position, String dayText) {

        Log.d(TAG, "day selected: " + dayText + " position: " + position);


    }


    @Override
    public void OnDateChange(boolean IsChangingForward){

    }

    public void previousMonthAction(View view) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        LocalDate localDateCheck = selectedDate.plusMonths(1);
        if(localDateCheck!=null){
            Log.d(TAG, localDateCheck.getDayOfMonth() + " day");
        }
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }


    private void InitWidgets(View view) {

        monthYearText = view.findViewById(R.id.monthYearTV);
        calenderRecyclerView = view.findViewById(R.id.calenderRecyclerView);
        dateNavigationLayout = view.findViewById(R.id.dateNavigationLayout);
        dayOfWeekLayout = view.findViewById(R.id.dayOfWeekLayout);
        previousMonthButton = view.findViewById(R.id.previous_month_btn);
        nextMonthButton = view.findViewById(R.id.next_month_btn);

        recylerViewContainer = view.findViewById(R.id.container);
    }


}