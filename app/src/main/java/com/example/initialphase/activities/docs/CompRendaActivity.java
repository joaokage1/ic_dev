package com.example.initialphase.activities.docs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.initialphase.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class CompRendaActivity extends AppCompatActivity {

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference reference;

    Button btn_renda_autonomos , btn_comp_renda, btn_socioeconomica, btn_semrenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_renda);

        btn_comp_renda = findViewById(R.id.btn_comp_renda);
        btn_renda_autonomos = findViewById(R.id.btn_renda_autonomos);
        btn_socioeconomica = findViewById(R.id.btn_socioeconomica);
        btn_semrenda = findViewById(R.id.btn_semrenda);

        btn_comp_renda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadDocs("ANEXO V - RELAÇÃO DOS DOCUMENTOS PARA A COMPROVAÇÃO DE RENDA");
            }
        });

        btn_semrenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadDocs("ANEXO IX - PESSOA SEM RENDA");
            }
        });

        btn_renda_autonomos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadDocs("ANEXO VI - DECLARAÇÃO DE RENDA PARA AUTÔNOMOS");
            }
        });

        btn_socioeconomica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadDocs("ANEXO III - DECLARAÇÃO SOCIOECONÔMICA");
            }
        });

    }

    private void downloadDocs(String nome) {
        storageReference = firebaseStorage.getInstance().getReference();
        reference = storageReference.child(nome + ".docx");

        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                downloadFile(CompRendaActivity.this,nome, ".docx", DIRECTORY_DOWNLOADS,url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Não foi possível fazer o Download",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadManager.enqueue(request);
    }
}
