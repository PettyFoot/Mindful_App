package com.example.mindful.service.appointment.forms;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mindful.R;
import com.example.mindful.api.GeoNameApiHandler;
import com.example.mindful.service.appointment.AppointmentFragment;
import com.example.mindful.service.appointment.data.PersonalInfo;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalInfoForm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalInfoForm extends AppointmentFragment implements GeoNameApiHandler.OnCitiesFetchedListener {

    private Button dobSetBtn;
    private TextView dobTV;
    private ArrayAdapter<String> adapter;
    private AutoCompleteTextView autoCompleteTextView;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private PersonalInfo personalInfo;
    private GeoNameApiHandler geoNameApiHandler;
    private String TAG = "PersonalInfoForm";

    public PersonalInfoForm() {
        // Required empty public constructor
    }

    public PersonalInfoForm(String title, String description) {
        super(title, description);
        this.fragmentTitle = title;
        this.fragmentDescription = description;
    }

    public PersonalInfoForm(String title, String description, PersonalInfo personalInfo) {
        super(title, description);
        this.fragmentTitle = title;
        this.fragmentDescription = description;
        this.personalInfo = personalInfo;
    }


    public static PersonalInfoForm newInstance() {
        PersonalInfoForm fragment = new PersonalInfoForm();
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
        View rootView = inflater.inflate(R.layout.fragment_personal_info_form, container, false);

        //check if personInfo object has not been instantiated (a constructor with personalInfo param is used), and instantiate
        if (personalInfo == null) {
            personalInfo = new PersonalInfo();
        }

        //Make GeoName api object
        if (geoNameApiHandler == null) {
            geoNameApiHandler = new GeoNameApiHandler();
        }

        dobSetBtn = rootView.findViewById(R.id.dob_set_btn);
        dobTV = rootView.findViewById(R.id.dob_tv);

        dobSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "clicked date swet?");

                DatePickerDialog datePickerDialog = personalInfo.getDob().CreateDatePicker(requireActivity());
                datePickerDialog.show();
                if (datePickerDialog != null) {
                    datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                            //Set the value of the dob textview so that the user has feedback about the dob they just set
                            Log.d(TAG, "month: " + month + " day: " + dayOfMonth + " year: " + year);
                            personalInfo.getDob().setDate(month + 1, dayOfMonth, year);

//                          //personalInfo.getDob().getYear(), personalInfo.getDob().getMonth(), personalInfo.getDob().getDay()
                            LocalDate date = LocalDate.of(personalInfo.getDob().getYear(), personalInfo.getDob().getMonth(), personalInfo.getDob().getDay());
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

                            dobTV.setText(date.format(formatter));
                        }
                    });
                }


            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.d(TAG, "Clicked citites");
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    Log.d(TAG, "No fine location or coarse location permissions enabled");
                    geoNameApiHandler.fetchAllUSACities(PersonalInfoForm.this);
                }else {
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (lastKnownLocation != null) {
                        Log.d(TAG, String.valueOf(lastKnownLocation.getLatitude()) + String.valueOf(lastKnownLocation.getLongitude()));
                        // Handle the last known location
                        useLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

                    }else{
                        Log.d(TAG, "No last known location");
                    }
                }

            }
        };


        autoCompleteTextView = rootView.findViewById(R.id.city_auto_complete_tv);
        autoCompleteTextView.setOnClickListener(onClickListener);

        // adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1);
        adapter = new ArrayAdapter<>(requireActivity(), R.layout.drop_down_list);
        autoCompleteTextView.setAdapter(adapter);

        // Set the threshold for triggering the dropdown list
        autoCompleteTextView.setThreshold(1);

        // Set the filtering logic
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter the list of cities based on the entered text
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Handle the location update here
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                // Call the function to use the location for your desired purpose
                useLocation(latitude, longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        // Request location updates
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "no location permissions, attempting to grant");
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        }else{
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                Log.d(TAG,String.valueOf(lastKnownLocation.getLatitude()) + String.valueOf(lastKnownLocation.getLongitude()));
                // Handle the last known location
                useLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

        }

        //geoNameApiHandler.fetchAllUSACities(PersonalInfoForm.this);

        return rootView;

    }

    private void useLocation(double latitude, double longitude) {
        geoNameApiHandler.fetchAllCitiesNearby(this, new double[]{latitude, longitude}, 50);
    }

    public PersonalInfo getPersonalInfo() {
        return this.personalInfo;
    }

    @Override
    public void onCitiesFetched(ArrayList<String> cities) {
        // Process the fetched cities here
        Log.d(TAG, "Fetched cities: " + cities.toString());

        adapter.clear();
        adapter.addAll(cities);
        adapter.notifyDataSetChanged();
    }
}