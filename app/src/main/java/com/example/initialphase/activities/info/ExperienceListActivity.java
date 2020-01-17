package com.example.initialphase.activities.info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.initialphase.Adapter.ExperienceAdapter;
import com.example.initialphase.Adapter.ForumListAdapter;
import com.example.initialphase.R;
import com.example.initialphase.model.Experiencia;
import com.example.initialphase.model.Forum;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExperienceListActivity extends AppCompatActivity {

    private ArrayList<Experiencia> list;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ExperienceAdapter adapter;
    private RecyclerView experienciaRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_list);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Experiencia");

        experienciaRecyclerView = findViewById(R.id.rvExperiencias);
        experienciaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        experienciaRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {

                    Experiencia experiencia = postsnap.getValue(Experiencia.class);
                    list.add(experiencia);

                }

                adapter = new ExperienceAdapter(getBaseContext(),list);
                experienciaRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
