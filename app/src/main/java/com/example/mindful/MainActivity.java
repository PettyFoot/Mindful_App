package com.example.mindful;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.example.mindful.service.appointment.AppointmentsViewFragment;
import com.example.mindful.service.profile.ProfileViewFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_Open, R.string.menu_Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        Log.d(TAG, "nav_home");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        Fragment currentFragment = fragmentManager.findFragmentById(R.id.mainFragmentWindow); // Replace `R.id.mainFragmentWindow` with the ID of the container where the fragment is displayed

                        if (currentFragment != null) {
                            fragmentManager.beginTransaction().remove(currentFragment).commit();
                        }

                        return true;
                    case R.id.nav_Profile:
                        Log.d(TAG, "nav_Profile");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        ProfileViewFragment profileViewFragment = new ProfileViewFragment();
                        fragmentTransaction.replace(R.id.mainFragmentWindow, profileViewFragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.nav_Appointment:
                        Log.d(TAG, "nav_Appointment");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        AppointmentsViewFragment appointmentsViewFragment = new AppointmentsViewFragment();
                        fragmentTransaction.replace(R.id.mainFragmentWindow, appointmentsViewFragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.nav_Calender:
                        Log.d(TAG, "nav_Calender");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.nav_Chat:
                        Log.d(TAG, "nav_Chat");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.nav_HelpSupport:
                        Log.d(TAG, "nav_HelpSupport");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                }



                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}