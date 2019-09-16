package com.example.initialphase.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.initialphase.Activities.Experiences;
import com.example.initialphase.Activities.Profile;
import com.example.initialphase.R;
import com.example.initialphase.SlideAdapter;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickProfile(View view) {
        Intent i = new Intent(this, Profile.class);
        startActivity(i);
    }

    public void onClickExperiences(View view) {
        Intent i = new Intent(this, Experiences.class);
        startActivity(i);
    }
}
