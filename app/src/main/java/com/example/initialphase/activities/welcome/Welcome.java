package com.example.initialphase.activities.welcome;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.initialphase.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import static java.security.AccessController.getContext;

public class Welcome extends AppCompatActivity {

    LinearLayout l1,l2;
    Button btnsub, btnlog;
    Animation uptodown,downtoup;

    private FirebaseAuth mAuth;

    //LogIn with Google
    static final int GOOGLE_SIGN = 123;
    Button login_btn_gmail;
    GoogleSignInClient mGoogleSignInClient;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mAuth = FirebaseAuth.getInstance();
        btnlog = (Button)findViewById(R.id.button_log);
        l1 = (LinearLayout) findViewById(R.id.l1);
        l2 = (LinearLayout) findViewById(R.id.l2);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);
        progressBar = findViewById(R.id.progressBar_google);

        login_btn_gmail = findViewById(R.id.button_log_gmail);

        GoogleSignInOptions options = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, options);

        login_btn_gmail.setOnClickListener(v -> SignInGmail());
    }

    void SignInGmail(){
        progressBar.setVisibility(View.VISIBLE);
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, GOOGLE_SIGN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.INVISIBLE);

                        FirebaseUser user = mAuth.getCurrentUser();
                        final EditText taskEditText = new EditText(this);
                        AlertDialog dialog = new AlertDialog.Builder(this)
                                .setTitle("Digite seu nome de viajante !")
                                .setMessage("Seja bem vindo ao interTravellin!!")
                                .setView(taskEditText)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String task = String.valueOf(taskEditText.getText());
                                        if (task.isEmpty()){
                                            return;
                                        }else {
                                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(task)
                                                    .build();
                                            user.updateProfile(profileChangeRequest);
                                            updateUI();
                                        }
                                    }
                                })
                                .create();
                        dialog.show();


                    } else {
                        progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN){
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null){
                    firebaseAuthWithGoogle(account);
                }

            }catch (ApiException e){
                e.printStackTrace();
            }

        }
    }

    public void logOnClick(View view){
        Intent homeActivity = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(homeActivity);
        finish();
    }

    private void updateUI() {
        Intent intent = new Intent(this,
                MainActivity.class);
        startActivity(intent);
        finish();
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
