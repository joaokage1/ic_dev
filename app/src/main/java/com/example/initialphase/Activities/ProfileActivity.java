package com.example.initialphase.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.transition.TransitionManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.initialphase.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    Button button;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private TextView ptxtName, txtx_pontuacao_profile, txt_p;
    private TextView ptxtMail, txt_ready;
    private ImageView imgPic, imageView4;

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
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(getApplicationContext(), Welcome.class);
                startActivity(intent);
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
    }
}
