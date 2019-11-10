package com.example.initialphase.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.initialphase.R;

public class RequirementsCheckListActivity extends AppCompatActivity {

    RadioGroup rg1,rg2;
    RadioButton rb_1_tenho,rb_1_nao_tenho, rb_2_tenho,rb_2_nao_tenho;

    TextView pontuacao;

    Button submit;

    private static double PT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirements_check_list);

        rg1 = findViewById(R.id.rg1);
        rg2 = findViewById(R.id.rg2);

        rb_1_tenho = findViewById(R.id.rb_1_tenho);
        rb_2_tenho = findViewById(R.id.rb_2_tenho);

        rb_1_nao_tenho = findViewById(R.id.rb_1_nao_tenho);
        rb_2_nao_tenho = findViewById(R.id.rb_2_nao_tenho);

        pontuacao = findViewById(R.id.txt_pontuacao);

        submit = findViewById(R.id.btn_salvarResposta);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rb1 = rg1.getCheckedRadioButtonId();
                if (rb1 == rb_1_tenho.getId()){
                    PT = PT + 1;
                }
                int rb2 = rg2.getCheckedRadioButtonId();
                if (rb2 == rb_2_tenho.getId()){
                    PT = PT + 1;
                }
                double porc = (PT / 2) * 100;

                //pontuacao.setText(porc + "%");

                PT = 0;

                Toast.makeText(getApplicationContext(),porc+"%",Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }
}
