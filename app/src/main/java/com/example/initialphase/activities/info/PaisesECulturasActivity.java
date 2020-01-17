package com.example.initialphase.activities.info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.initialphase.Adapter.CountryAdapter;
import com.example.initialphase.R;
import com.example.initialphase.model.City;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PaisesECulturasActivity extends AppCompatActivity {

    private RecyclerView countriesRV;
    private CountryAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference ;
    private List<City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paises_eculturas);

        countriesRV = findViewById(R.id.rvPaises);
        countriesRV.setHasFixedSize(true);
        countriesRV.setLayoutManager( new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Cities");
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cityList = new ArrayList<>();

                for (DataSnapshot citySnap: dataSnapshot.getChildren()){

                    City city = citySnap.getValue(City.class);
                    cityList.add(city);
                }

                adapter = new CountryAdapter(getBaseContext(),cityList);
                countriesRV.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
