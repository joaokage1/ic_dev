package com.example.initialphase.activities.docs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.initialphase.R;

public class DocsCheckListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docs_check_list);

    }

    public void onClickDoCheckList(View view){
        Intent intent = new Intent(this,
                RequirementsCheckListActivity.class);
        startActivity(intent);
    }
}
