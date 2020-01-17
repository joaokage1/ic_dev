package com.example.initialphase.activities.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.initialphase.activities.info.ExperiencesMainActivity;
import com.example.initialphase.activities.info.PaisesECulturasActivity;
import com.example.initialphase.activities.info.ProvasAnterioresLinguasActivity;
import com.example.initialphase.activities.docs.DocsActivity;
import com.example.initialphase.activities.profile.ProfileActivity;
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
        Intent i = new Intent(this, ExperiencesMainActivity.class);
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

    public void onClickPaises(View view) {
        Intent i = new Intent(this, PaisesECulturasActivity.class);
        startActivity(i);
    }

    public void onClickinscricao(View view){
        String url = "http://www.iftm.edu.br/internacional/editais/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
