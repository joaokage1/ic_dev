package com.example.initialphase.activities.docs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.initialphase.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class DocsActivity extends AppCompatActivity {

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docs);
    }

    public void onClickRenda(View view){
        Intent intent = new Intent(this,
                CompRendaActivity.class);
        startActivity(intent);
    }

    public void onClickCheckList(View view){
        Intent intent = new Intent(this,
                DocsCheckListActivity.class);
        startActivity(intent);
    }

    public void onClickDownloadRelatorioMensal(View view){
        downloadRelatorioMensal();
    }

    public void onClickDownloadTermoCompromisso(View view){
        downloadDocs("ANEXO II termo_compromisso.docx");
    }

    public void onClickDownloadRecurso(View view){
        downloadDocs("ANEXO IV – REQUERIMENTO DE RECURSO.docx");
    }

    public void onClickDownloadAptidao(View view){
        downloadDocs("ANEXO XI - DECLARAÇÃO DE APTIDÃO FÍSICA E MENTAL.docx");
    }

    private void downloadRelatorioMensal() {
        storageReference = firebaseStorage.getInstance().getReference();
        reference = storageReference.child("modelo_relatorio_de_viagem_auxilio_participacao_em_atividades_e_eventos.doc");

        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                downloadFile(DocsActivity.this,"modelo_relatorio_de_viagem_auxilio_participacao_em_atividades_e_eventos", ".doc", DIRECTORY_DOWNLOADS,url);
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


    private void downloadDocs(String nome) {
        storageReference = firebaseStorage.getInstance().getReference();
        reference = storageReference.child(nome);

        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                downloadFile(DocsActivity.this,nome, ".docx", DIRECTORY_DOWNLOADS,url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Não foi possível fazer o Download",Toast.LENGTH_LONG).show();
            }
        });
    }
}
