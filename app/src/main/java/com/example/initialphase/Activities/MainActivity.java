package com.example.initialphase.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.initialphase.R;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickProfile(View view) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void onClickExperiences(View view) {
        Intent i = new Intent(this, Experiences.class);
        startActivity(i);
    }

    public void onClickDocs(View view) {
        Intent i = new Intent(this, DocsActivity.class);
        startActivity(i);
    }

    public void onClickProvasAnteriores(View view) {
        Intent i = new Intent(this, ProvasAnterioresLinguasActivity.class);
        startActivity(i);
    }
}
