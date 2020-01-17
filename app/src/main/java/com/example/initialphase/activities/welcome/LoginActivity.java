package com.example.initialphase.activities.welcome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.initialphase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText login_mail;
    private EditText login_password;

    private ProgressBar login_progressbar;

    private Button login_btn;
    private Button reg_btn_on_login;
    private TextView btn_reset_password;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_mail = findViewById(R.id.login_mail);
        login_password = findViewById(R.id.login_password);
        login_progressbar = findViewById(R.id.login_progressbar);
        login_btn = findViewById(R.id.login_btn);
        reg_btn_on_login = findViewById(R.id.reg_btn_on_login);
        btn_reset_password = findViewById(R.id.btn_reset_password);

        login_progressbar.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();

        reg_btn_on_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,
                        ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg_btn_on_login.setVisibility(View.INVISIBLE);
                login_btn.setVisibility(View.INVISIBLE);
                login_progressbar.setVisibility(View.VISIBLE);


                final String mail = login_mail.getText().toString();
                final String password = login_password.getText().toString();

                if (mail.isEmpty() || password.isEmpty()){
                    showMessage(getBaseContext().getString(R.string.PleaseVerifyallfields));
                    login_progressbar.setVisibility(View.INVISIBLE);
                    login_btn.setVisibility(View.VISIBLE);
                    reg_btn_on_login.setVisibility(View.VISIBLE);
                }else{
                    sigIn(mail,password);
                }
            }
        });
    }

    private void sigIn(String mail, String password) {

        mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    login_progressbar.setVisibility(View.INVISIBLE);
                    login_btn.setVisibility(View.VISIBLE);
                    reg_btn_on_login.setVisibility(View.VISIBLE);

                    updateUI();
                }else{
                    showMessage(task.getException().getMessage());
                    login_progressbar.setVisibility(View.INVISIBLE);
                    login_btn.setVisibility(View.VISIBLE);
                    reg_btn_on_login.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    private void updateUI() {
        Intent intent = new Intent(LoginActivity.this,
                MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
            updateUI();
        }
    }
}
