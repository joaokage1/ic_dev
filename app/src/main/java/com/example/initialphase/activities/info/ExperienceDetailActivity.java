package com.example.initialphase.activities.info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExperienceDetailActivity extends AppCompatActivity {

    ImageView img_person,img_detail_experience, img_current_user_experience;
    TextView txt_nome_experiencia, txt_curso_experience, txt_experiencia_detail;
    EditText editTextComment;
    Button btnAddComment;
    String experienceKey;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    RecyclerView RvComment;
    CommentAdapter commentAdapter;
    List<Comment> listComment;
    Stack<Comment> pilhaComment;
    static String COMMENT_KEY = "experienceComment" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_detail);


        RvComment = findViewById(R.id.rv_comment_experience);
        img_person = findViewById(R.id.img_person);
        img_detail_experience = findViewById(R.id.img_detail_experience);
        img_current_user_experience = findViewById(R.id.img_current_user_experience);

        txt_nome_experiencia = findViewById(R.id.txt_nome_experiencia);
        txt_curso_experience = findViewById(R.id.txt_curso_experience);
        txt_experiencia_detail = findViewById(R.id.txt_experiencia_detail);

        editTextComment = findViewById(R.id.et_comment_experience);
        btnAddComment = findViewById(R.id.btn_add_comment_experience);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnAddComment.setVisibility(View.INVISIBLE);
                DatabaseReference commentReference = firebaseDatabase.getReference(COMMENT_KEY).child(experienceKey).push();
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

        String postImage = getIntent().getExtras().getString("picture") ;
        Glide.with(this).load(postImage).into(img_detail_experience);

        String postTitle = getIntent().getExtras().getString("nome");
        txt_nome_experiencia.setText(postTitle);

        String desc = getIntent().getExtras().getString("desc");
        txt_experiencia_detail.setText(desc);

        String curso = getIntent().getExtras().getString("curso");
        txt_curso_experience.setText(curso);

        String photo = getIntent().getExtras().getString("userPhoto") ;
        Glide.with(this).load(photo).apply(RequestOptions.circleCropTransform()).into(img_person);

        Glide.with(this).load(firebaseUser.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(img_current_user_experience);

        experienceKey = getIntent().getExtras().getString("experienciaKey");

        iniRvComment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        iniRvComment();
    }

    private void iniRvComment() {

        RvComment.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(experienceKey);

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
                    commentAdapter = new CommentAdapter(getApplicationContext(),listComment, firebaseDatabase, experienceKey, firebaseUser, ExperienceDetailActivity.this, COMMENT_KEY);
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
