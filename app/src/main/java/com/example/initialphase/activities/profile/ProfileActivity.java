package com.example.initialphase.activities.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.transition.TransitionManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.initialphase.activities.welcome.Welcome;
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

public class ProfileActivity extends AppCompatActivity {

    Button button;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseDatabase firebaseDatabase;
    private TextView ptxtName, txtx_pontuacao_profile, txt_p;
    private TextView ptxtMail, txt_ready;
    private ImageView imgPic, imageView4;
    List<Pontuacao> listPontuacao;

    private boolean isOpen = false;
    private ConstraintSet layout1,layout2;
    private ConstraintLayout constraintLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.ptxtName = findViewById(R.id.txt_name_profile);
        this.ptxtMail = findViewById(R.id.txt_mail_profile);
        this.imgPic = findViewById(R.id.img_photo_profile);
        imageView4 = findViewById(R.id.imageView4);
        txtx_pontuacao_profile = findViewById(R.id.txtx_pontuacao_profile);
        txt_ready = findViewById(R.id.txt_ready);
        txt_p = findViewById(R.id.txt_p);
        layout1 = new ConstraintSet();
        layout2 = new ConstraintSet();
        constraintLayout = findViewById(R.id.constraint);

        layout2.clone(this,R.layout.expanded_profile);
        layout1.clone(constraintLayout);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        this.ptxtName.setText(currentUser.getDisplayName());
        this.ptxtMail.setText(currentUser.getEmail());
        Glide.with(this).load(currentUser.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(imgPic);


        button = findViewById(R.id.btn_logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        ProfileActivity.this);
                alertDialog.setTitle("LogOut");
                alertDialog.setMessage("Tem certeza que deseja sair ?");
                alertDialog.setIcon(R.drawable.logosemfundo);
                alertDialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(), Welcome.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                        finish();
                    }
                });
                alertDialog.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                AlertDialog alertDialogMain = alertDialog.create();
                alertDialogMain.show();
            }
        });

        imgPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!isOpen) {
                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout2.applyTo(constraintLayout);
                    txt_ready.setVisibility(View.INVISIBLE);
                    isOpen = !isOpen ;
                }

                else {

                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout1.applyTo(constraintLayout);
                    isOpen = !isOpen ;

                }

            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference pontosReferncia =  firebaseDatabase.getReference("Pontuacao").child("pprofile");

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
                    txtx_pontuacao_profile.setText("0");
                }else{
                    txtx_pontuacao_profile.setText(p.getContent());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onClickCheckListProfile(View view){
        Intent intent = new Intent(this,
                CheckListProfileActivity.class);
        startActivity(intent);
    }

    public void onClickVideosProfile(View view){
        Intent intent = new Intent(this,
                VideosActivity.class);
        startActivity(intent);
    }

    public void onClickForumProfile(View view){
        Intent intent = new Intent(this,
                ForumActivity.class);
        startActivity(intent);
    }
}
