package com.example.initialphase.activities.info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.initialphase.Adapter.CountryAdapter;
import com.example.initialphase.R;
import com.example.initialphase.model.City;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private Button fab;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Dialog popAddCity;
    TextView popup_name_city,
            popup_curiosidades_cidade,
            popup_contatos_cidade,
            popup_bairros_cidade,
            popup_transporte_cidade,
            popup_imagem_cidade,
            popup_pais_cidade,
            popup_lugares_cidade,
            popup_imagem_cidade2;
    ProgressBar popUpProgressBar;
    ImageView popUpAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paises_eculturas);

        countriesRV = findViewById(R.id.rvPaises);
        countriesRV.setHasFixedSize(true);
        countriesRV.setLayoutManager( new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Cities");

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        fab = findViewById(R.id.fab);

        if (currentUser.getEmail().equals("jvgsilva180@gmail.com")){
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popAddCity.show();
                }
            });
        }else{
            fab.setVisibility(View.INVISIBLE);
        }

        iniPopup();

    }

    private void iniPopup() {
        popAddCity = new Dialog(this);
        popAddCity.setContentView(R.layout.pop_up_add_city);
        popAddCity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popAddCity.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        popAddCity.getWindow().getAttributes().gravity = Gravity.TOP;

        popup_name_city = popAddCity.findViewById(R.id.popup_name_city);
        popup_curiosidades_cidade = popAddCity.findViewById(R.id.popup_curiosidades_cidade);
        popup_contatos_cidade = popAddCity.findViewById(R.id.popup_contatos_cidade);
        popup_bairros_cidade = popAddCity.findViewById(R.id.popup_bairros_cidade);
        popup_transporte_cidade = popAddCity.findViewById(R.id.popup_transporte_cidade);
        popup_imagem_cidade = popAddCity.findViewById(R.id.popup_imagem_cidade);
        popup_pais_cidade = popAddCity.findViewById(R.id.popup_pais_cidade);
        popup_lugares_cidade = popAddCity.findViewById(R.id.popup_lugares_cidade);
        popUpProgressBar = popAddCity.findViewById(R.id.popup_progressBar_cidade);
        popup_imagem_cidade2 = popAddCity.findViewById(R.id.popup_imagem_cidade2);
        popUpAddBtn = popAddCity.findViewById(R.id.popup_add_cidade);

        popUpAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpAddBtn.setVisibility(View.INVISIBLE);
                popUpProgressBar.setVisibility(View.VISIBLE);

                City city = new City(popup_name_city.getText().toString(),
                        popup_imagem_cidade.getText().toString(),
                        popup_pais_cidade.getText().toString(),
                        popup_bairros_cidade.getText().toString(),
                        popup_contatos_cidade.getText().toString(),
                        popup_curiosidades_cidade.getText().toString(),
                        null,
                        popup_transporte_cidade.getText().toString(),
                        null,
                        popup_imagem_cidade2.getText().toString(),
                        popup_lugares_cidade.getText().toString());

                try {
                    if (!popup_name_city.getText().toString().isEmpty()
                            && !popup_imagem_cidade.getText().toString().isEmpty()
                            && !popup_pais_cidade.getText().toString().isEmpty()
                            && !popup_bairros_cidade.getText().toString().isEmpty()
                            && !popup_contatos_cidade.getText().toString().isEmpty()
                            && !popup_curiosidades_cidade.getText().toString().isEmpty()
                            && !popup_transporte_cidade.getText().toString().isEmpty()
                            && !popup_imagem_cidade2.getText().toString().isEmpty()
                            && !popup_lugares_cidade.getText().toString().isEmpty()){
                        addCidade(city);
                        popup_name_city.setText("");
                        popup_curiosidades_cidade.setText("");
                        popup_contatos_cidade.setText("");
                        popup_bairros_cidade.setText("");
                        popup_transporte_cidade.setText("");
                        popup_imagem_cidade.setText("");
                        popup_pais_cidade.setText("");
                        popup_lugares_cidade.setText("");
                        popup_imagem_cidade2.setText("");
                    }else {
                        showMessage(getBaseContext().getString(R.string.Allfieldsmustbefilled));
                        popUpAddBtn.setVisibility(View.VISIBLE);
                        popUpProgressBar.setVisibility(View.INVISIBLE);
                    }
                }catch (Exception e){
                    showMessage(e.toString());
                }

            }
        });

    }

    private void addCidade(City city) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("Cities").push();
        String key = myRef.getKey();
        city.setCityKey(key);

        myRef.setValue(city).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("Postagem adicionada com sucesso!!");
                popUpAddBtn.setVisibility(View.VISIBLE);
                popUpProgressBar.setVisibility(View.INVISIBLE);
                popAddCity.dismiss();
            }
        });
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

    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
    }
}
