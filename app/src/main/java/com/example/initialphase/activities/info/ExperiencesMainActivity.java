package com.example.initialphase.activities.info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.initialphase.R;
import com.example.initialphase.model.Experiencia;
import com.example.initialphase.model.Forum;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ExperiencesMainActivity extends AppCompatActivity {

    Button btn_adicionar_experiência, btn_visualizar;
    TextView popup_title_experience, popup_curso_experience, popup_desc_experience;
    ImageView popup_img_experience, popup_user_image_experience,popup_add_experience;
    private ProgressBar popup_progressBar_experience;
    private Dialog popAddExperiencia;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    static int REQUESTCODE = 2;
    static int PRegCode = 2;
    private Uri pickedImgUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiences_main);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Experiencia");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        btn_adicionar_experiência = findViewById(R.id.btn_adicionar_experiência);
        btn_visualizar = findViewById(R.id.btn_visualizar);

        iniPopup();
        setupPopUpImageClick();

        btn_adicionar_experiência.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAddExperiencia.show();
            }
        });

        btn_visualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),ExperienceListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void iniPopup() {
        popAddExperiencia = new Dialog(this);
        popAddExperiencia.setContentView(R.layout.pop_up_add_experience);
        popAddExperiencia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popAddExperiencia.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        popAddExperiencia.getWindow().getAttributes().gravity = Gravity.TOP;

        popup_title_experience = popAddExperiencia.findViewById(R.id.popup_title_experience);
        popup_curso_experience = popAddExperiencia.findViewById(R.id.popup_curso_experience);
        popup_desc_experience = popAddExperiencia.findViewById(R.id.popup_desc_experience);
        popup_img_experience = popAddExperiencia.findViewById(R.id.popup_img_experience);
        popup_user_image_experience = popAddExperiencia.findViewById(R.id.popup_user_image_experience);
        popup_progressBar_experience = popAddExperiencia.findViewById(R.id.popup_progressBar_experience);
        popup_add_experience = popAddExperiencia.findViewById(R.id.popup_add_experience);

       Glide.with(this).load(currentUser.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(popup_user_image_experience);

        popup_add_experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_add_experience.setVisibility(View.INVISIBLE);
                popup_progressBar_experience.setVisibility(View.VISIBLE);


                if (!popup_desc_experience.getText().toString().isEmpty()
                        && !popup_title_experience.getText().toString().isEmpty()
                        && !popup_curso_experience.getText().toString().isEmpty()
                        && pickedImgUri != null){

                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("experienciaImage");
                    final StorageReference imageFilePath = storageReference.child(pickedImgUri.getLastPathSegment());
                    imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageDownloadLink = uri.toString();
                                    Experiencia experiencia = new Experiencia(
                                            popup_title_experience.getText().toString()
                                            ,popup_curso_experience.getText().toString()
                                            ,popup_desc_experience.getText().toString()
                                            ,imageDownloadLink
                                            ,currentUser.getUid()
                                            ,currentUser.getPhotoUrl().toString());
                                    addExperiencia(experiencia);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    showMessage(e.getMessage());
                                    popup_add_experience.setVisibility(View.VISIBLE);
                                    popup_progressBar_experience.setVisibility(View.INVISIBLE);
                                }
                            });
                        }
                    });

                }else{
                    showMessage(getBaseContext().getString(R.string.Allfieldsmustbefilled));
                    popup_add_experience.setVisibility(View.VISIBLE);
                    popup_progressBar_experience.setVisibility(View.INVISIBLE);
                }

            }
        });

    }

    private void setupPopUpImageClick() {
        this.popup_img_experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestForPermission();
            }
        });
    }

    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(ExperiencesMainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ExperiencesMainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PRegCode);
        }else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/+");
        startActivityForResult(galleryIntent, REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null){
            pickedImgUri = data.getData();
            this.popup_img_experience.setImageURI(pickedImgUri);
        }
    }

    private void addExperiencia(Experiencia experiencia) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("Experiencia").push();
        String key = myRef.getKey();
        experiencia.setExperienciaKey(key);

        myRef.setValue(experiencia).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("Postagem adicionada com sucesso!!");
                popup_add_experience.setVisibility(View.VISIBLE);
                popup_progressBar_experience.setVisibility(View.INVISIBLE);
                popAddExperiencia.dismiss();
                popup_desc_experience.setText("");
                popup_title_experience.setText("");
                popup_curso_experience.setText("");
                pickedImgUri = null;
            }
        });
    }

    private void showMessage(String s) {
        Toast.makeText(getBaseContext(),s,Toast.LENGTH_LONG).show();
    }
}
