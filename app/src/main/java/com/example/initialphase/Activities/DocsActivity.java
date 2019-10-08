package com.example.initialphase.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.initialphase.R;

public class DocsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docs);
    }

    public void onClickRenda(View view){
        Intent intent = new Intent(this,
                CompRendaActivity.class);
        startActivity(intent);
    }
}
