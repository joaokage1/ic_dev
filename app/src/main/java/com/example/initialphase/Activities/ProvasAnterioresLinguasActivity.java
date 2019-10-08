package com.example.initialphase.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.initialphase.R;

public class ProvasAnterioresLinguasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas_anteriores_linguas);
    }

    public void onClickProlif(View view){
        String url = "http://www.iftm.edu.br/internacional/testes/prolif/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void onClickIsF(View view){
        String url = "http://www.iftm.edu.br/internacional/testes/isf/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
