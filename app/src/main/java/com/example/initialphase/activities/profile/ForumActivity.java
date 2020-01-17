package com.example.initialphase.activities.profile;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.initialphase.Adapter.ForumListAdapter;
import com.example.initialphase.R;
import com.example.initialphase.model.Forum;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ForumActivity extends AppCompatActivity {

    private Dialog popAddForum;
    private TextView popUpDescription, popUpTitle;
    private ProgressBar popUpProgressBar;
    private ImageView popUpUserImage, forumAddBtn;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private Button botaoAddForum;
    private ForumListAdapter forumAdapter;
    private RecyclerView forumRecyclerView;
    private ArrayList<Forum> list;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Forum");

        forumRecyclerView = findViewById(R.id.forumRV);
        forumRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        forumRecyclerView.setHasFixedSize(true);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        botaoAddForum = findViewById(R.id.buttonAddForum);
        iniPopup();
        botaoAddForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popAddForum.show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        this.databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {

                    Forum forum = postsnap.getValue(Forum.class);
                    list.add(forum);

                }

                forumAdapter = new ForumListAdapter(getBaseContext(),list);
                forumRecyclerView.setAdapter(forumAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void iniPopup() {
        popAddForum = new Dialog(this);
        popAddForum.setContentView(R.layout.popup_add_forum);
        popAddForum.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popAddForum.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        popAddForum.getWindow().getAttributes().gravity = Gravity.TOP;

        popUpUserImage = popAddForum.findViewById(R.id.forum_popup_user_image);
        popUpDescription = popAddForum.findViewById(R.id.forum_popup_description);
        popUpTitle = popAddForum.findViewById(R.id.forum_popup_title);
        popUpProgressBar = popAddForum.findViewById(R.id.forum_popup_progressBar);
        forumAddBtn = popAddForum.findViewById(R.id.forum_popup_add);

        Glide.with(this).load(currentUser.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(popUpUserImage);

        forumAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forumAddBtn.setVisibility(View.INVISIBLE);
                popUpProgressBar.setVisibility(View.VISIBLE);


                if (!popUpDescription.getText().toString().isEmpty()
                        && !popUpTitle.getText().toString().isEmpty()){

                    Forum forum = new Forum(popUpTitle.getText().toString(), popUpDescription.getText().toString(), currentUser.getUid(), currentUser.getPhotoUrl().toString());
                    addForum(forum);

                }else{
                    showMessage(getBaseContext().getString(R.string.Allfieldsmustbefilled));
                    forumAddBtn.setVisibility(View.VISIBLE);
                    popUpProgressBar.setVisibility(View.INVISIBLE);
                }

            }
        });

    }

    private void addForum(Forum forum) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("Forum").push();
        String key = myRef.getKey();
        forum.setForumKey(key);

        myRef.setValue(forum).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("Postagem adicionada com sucesso!!");
                forumAddBtn.setVisibility(View.VISIBLE);
                popUpProgressBar.setVisibility(View.INVISIBLE);
                popAddForum.dismiss();
                popUpDescription.setText("");
                popUpTitle.setText("");
            }
        });
    }

    private void showMessage(String s) {
        Toast.makeText(getBaseContext(),s,Toast.LENGTH_LONG).show();
    }

}
