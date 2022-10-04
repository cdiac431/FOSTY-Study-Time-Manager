package com.example.fosty;

import static androidx.navigation.Navigation.findNavController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    View theInflatedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);



        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        theInflatedView = inflater.inflate(R.layout.fragment_study_panel, null);
        theInflatedView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));

        final DrawerLayout drawerLayout = theInflatedView.findViewById(R.id.drawerLayoutHeader);
        drawerLayout.setScrimColor(Color.TRANSPARENT);

        theInflatedView.findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });



    }







    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void loginButton (View v){
        setContentView(theInflatedView);
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.GRAY));
        navigationView.setItemIconTintList(ColorStateList.valueOf(Color.GRAY));


        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

}