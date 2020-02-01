package com.example.initialphase.activities.docs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.initialphase.R;
import com.example.initialphase.model.Pontuacao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DocsCheckListActivity extends AppCompatActivity {

    TextView txt_pontuacao;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseDatabase firebaseDatabase;
    List<Pontuacao> listPontuacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docs_check_list);

        txt_pontuacao = findViewById(R.id.txt_pontuacao);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference pontosReferncia =  firebaseDatabase.getReference("PontuacaoDocs").child("pdocs");

        pontosReferncia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPontuacao = new ArrayList<>();
                for (DataSnapshot snap:dataSnapshot.getChildren()) {
                    Pontuacao p = snap.getValue(Pontuacao.class);
                    listPontuacao.add(p);
                }

                Pontuacao p = new Pontuacao();
                for (Pontuacao pontuacao: listPontuacao ) {
                    if (pontuacao.getUname().equalsIgnoreCase(mAuth.getCurrentUser().getDisplayName())){
                        p = pontuacao;
                    }
                }

                if (p.getContent() == null){
                    txt_pontuacao.setText("0 %");
                }else{
                    txt_pontuacao.setText(p.getContent() + " %");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onClickDoCheckList(View view){
        Intent intent = new Intent(this,
                RequirementsCheckListActivity.class);
        startActivity(intent);
    }
}
