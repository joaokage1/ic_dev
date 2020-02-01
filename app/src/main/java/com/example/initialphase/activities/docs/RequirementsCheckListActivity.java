package com.example.initialphase.activities.docs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.initialphase.R;
import com.example.initialphase.model.Pontuacao;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class RequirementsCheckListActivity extends AppCompatActivity {

    RadioGroup rg1,rg2,rg3,rg4,rg5,rg6,rg7,rg8,rg9,rg10,rg11,rg12;
    RadioButton rb_1_sim, rb_2_sim,rb_3_sim,rb_4_sim,rb_5_sim,rb_6_sim,rb_7_sim,rb_8_sim,rb_9_sim,rb_10_sim,rb_11_sim,rb_12_sim, rb_12_maior;

    TextView pontuacao;

    Button submit;

    DecimalFormat df = new DecimalFormat("#.00");
    private static double PT = 0;
    private static double PP = 12;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirements_check_list);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        rg1 = findViewById(R.id.rg1);
        rg2 = findViewById(R.id.rg2);
        rg3 = findViewById(R.id.rg3);
        rg4 = findViewById(R.id.rg4);
        rg5 = findViewById(R.id.rg5);
        rg6 = findViewById(R.id.rg6);
        rg7 = findViewById(R.id.rg7);
        rg8 = findViewById(R.id.rg8);
        rg9 = findViewById(R.id.rg9);
        rg10 = findViewById(R.id.rg10);
        rg11 = findViewById(R.id.rg11);
        rg12 = findViewById(R.id.rg12);

        rb_1_sim = findViewById(R.id.rb_1_tenho);
        rb_2_sim = findViewById(R.id.rb_2_tenho);
        rb_3_sim = findViewById(R.id.rb_3_tenho);
        rb_4_sim = findViewById(R.id.rb_4_tenho);
        rb_5_sim = findViewById(R.id.rb_5_tenho);
        rb_6_sim = findViewById(R.id.rb_6_tenho);
        rb_7_sim = findViewById(R.id.rb_7_tenho);
        rb_8_sim = findViewById(R.id.rb_8_tenho);
        rb_9_sim = findViewById(R.id.rb_9_tenho);
        rb_10_sim = findViewById(R.id.rb_10_tenho);
        rb_11_sim = findViewById(R.id.rb_11_tenho);
        rb_12_sim = findViewById(R.id.rb_12_tenho);
        rb_12_maior = findViewById(R.id.rb_12_maior);

        pontuacao = findViewById(R.id.txt_pontuacao);

        submit = findViewById(R.id.btn_salvarResposta);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int marcacao = rg1.getCheckedRadioButtonId();
                if (marcacao == rb_1_sim.getId()){
                    PT = PT + 1;
                }
                marcacao = rg2.getCheckedRadioButtonId();
                if (marcacao == rb_2_sim.getId()){
                    PT = PT + 1;
                }
                marcacao = rg3.getCheckedRadioButtonId();
                if (marcacao == rb_3_sim.getId()){
                    PT = PT + 1;
                }
                marcacao = rg4.getCheckedRadioButtonId();
                if (marcacao == rb_4_sim.getId()){
                    PT = PT + 1;
                }
                marcacao = rg5.getCheckedRadioButtonId();
                if (marcacao == rb_5_sim.getId()){
                    PT = PT + 1;
                }
                marcacao = rg6.getCheckedRadioButtonId();
                if (marcacao == rb_6_sim.getId()){
                    PT = PT + 1;
                }
                marcacao = rg7.getCheckedRadioButtonId();
                if (marcacao == rb_7_sim.getId()){
                    PT = PT + 1;
                }
                marcacao = rg8.getCheckedRadioButtonId();
                if (marcacao == rb_8_sim.getId()){
                    PT = PT + 1;
                }
                marcacao = rg9.getCheckedRadioButtonId();
                if (marcacao == rb_9_sim.getId()){
                    PT = PT + 1;
                }
                marcacao = rg10.getCheckedRadioButtonId();
                if (marcacao == rb_10_sim.getId()){
                    PT = PT + 1;
                }
                marcacao = rg11.getCheckedRadioButtonId();
                if (marcacao == rb_11_sim.getId()){
                    PT = PT + 1;
                }
                marcacao = rg12.getCheckedRadioButtonId();
                if (marcacao == rb_12_sim.getId()){
                    PT = PT + 1;
                }
                if (marcacao == rb_12_maior.getId()){
                    PP = 11;
                }
                double porc = (PT / PP) * 100;

                Toast.makeText(getApplicationContext(),df.format(porc)+" %",Toast.LENGTH_LONG).show();

                DatabaseReference pontuacaoDocsReference = firebaseDatabase.getReference("PontuacaoDocs").child("pdocs").push();
                String key = pontuacaoDocsReference.getKey();
                String uid = firebaseUser.getUid();
                String uname = firebaseUser.getDisplayName();

                Pontuacao pontuacao = new Pontuacao();
                pontuacao.setContent(df.format(porc));
                pontuacao.setUid(uid);
                pontuacao.setUname(uname);
                pontuacao.setKey(key);

                pontuacaoDocsReference.setValue(pontuacao).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"fail to add : "+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

                finish();
            }
        });
    }
}
