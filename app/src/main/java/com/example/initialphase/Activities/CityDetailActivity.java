package com.example.initialphase.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.initialphase.Adapter.CommentAdapter;
import com.example.initialphase.R;
import com.example.initialphase.model.Comment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

public class CityDetailActivity extends AppCompatActivity {

    ImageView imgPost,imgCurrentUser, imgPhoto;
    TextView curiosidades, title, estadia, custo, transporte, contatos, universidades;
    EditText editTextComment;
    Button btnAddComment;
    String cityKey;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    RecyclerView RvComment;
    CommentAdapter commentAdapter;
    List<Comment> listComment;
    Stack<Comment> pilhaComment;
    static String COMMENT_KEY = "Comment" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);

        RvComment = findViewById(R.id.rv_comment);
        imgPost =findViewById(R.id.post_detail_img);
        imgCurrentUser = findViewById(R.id.post_detail_currentuser_img);

        title = findViewById(R.id.post_detail_title);

        curiosidades = findViewById(R.id.post_detail_desc);
        transporte = findViewById(R.id.post_detail_transporte);
        estadia = findViewById(R.id.post_detail_estadia);
        custo = findViewById(R.id.post_detail_custos);
        contatos = findViewById(R.id.post_detail_contatos);
        universidades = findViewById(R.id.post_detail_universidades);
        imgPhoto = findViewById(R.id.img_cidade_universidade);

        editTextComment = findViewById(R.id.post_detail_comment);
        btnAddComment = findViewById(R.id.post_detail_add_comment_btn);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnAddComment.setVisibility(View.INVISIBLE);
                DatabaseReference commentReference = firebaseDatabase.getReference(COMMENT_KEY).child(cityKey).push();
                String comment_content = editTextComment.getText().toString();
                String uid = firebaseUser.getUid();
                String uname = firebaseUser.getDisplayName();
                String uimg = firebaseUser.getPhotoUrl().toString();
                Comment comment = new Comment(comment_content,uid,uimg,uname);

                commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showMessage("Show de bola");
                        editTextComment.setText("");
                        btnAddComment.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("falha ao adicionar comentário : "+e.getMessage());
                    }
                });

            }
        });

        String postImage = getIntent().getExtras().getString("cityFlag") ;
        Glide.with(this).load(postImage).into(imgPost);

        String postTitle = getIntent().getExtras().getString("title");
        title.setText(postTitle);

        String bairro = getIntent().getExtras().getString("bairro");
        estadia.setText(bairro);

        String contato = getIntent().getExtras().getString("contatos");
        contatos.setText(contato);

        String curiosidade = getIntent().getExtras().getString("curiosidades");
        curiosidades.setText(curiosidade);

        String custos = getIntent().getExtras().getString("custo");
        custo.setText(custos);

        String transportes = getIntent().getExtras().getString("transporte");
        transporte.setText(transportes);

        String universidade = getIntent().getExtras().getString("universidades");
        universidades.setText(universidade);

        String photo = getIntent().getExtras().getString("photo") ;
        Glide.with(this).load(photo).apply(RequestOptions.circleCropTransform()).into(imgPhoto);

        Glide.with(this).load(firebaseUser.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(imgCurrentUser);

        cityKey = getIntent().getExtras().getString("cityKey");

        iniRvComment();

    }

    @Override
    protected void onStart() {
        super.onStart();
        iniRvComment();
    }

    private void iniRvComment() {

        RvComment.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(cityKey);

        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComment = new ArrayList<>();
                pilhaComment = new Stack<>();
                for (DataSnapshot snap:dataSnapshot.getChildren()) {

                    Comment comment = snap.getValue(Comment.class);
                    listComment.add(comment) ;

                }


                for(Comment c: listComment){
                    pilhaComment.push(c);
                }

                listComment = new ArrayList<>();


                for(int i=pilhaComment.size(); i>0; i-- ){
                    listComment.add(pilhaComment.pop());
                }

                if(listComment.size()>0){
                    commentAdapter = new CommentAdapter(getApplicationContext(),listComment, firebaseDatabase, cityKey, firebaseUser, CityDetailActivity.this, COMMENT_KEY);
                    RvComment.setAdapter(commentAdapter);
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void showMessage(String message) {

        Toast.makeText(this,message,Toast.LENGTH_LONG).show();

    }
}
