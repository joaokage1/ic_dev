package com.example.initialphase.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.initialphase.Activities.ProfileActivity;
import com.example.initialphase.Activities.Welcome;
import com.example.initialphase.R;
import com.example.initialphase.model.Comment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context mContext;
    private List<Comment> mData;
    static String COMMENT_KEY = "Comment" ;
    FirebaseDatabase firebaseDatabase;
    String cityKey;
    FirebaseUser firebaseUser;
    Activity activity;


    public CommentAdapter(Context mContext, List<Comment> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public CommentAdapter(Context mContext, List<Comment> mData, FirebaseDatabase db, String key, FirebaseUser firebaseUser, Activity activity) {
        this.mContext = mContext;
        this.mData = mData;
        firebaseDatabase = db;
        cityKey = key;
        this.firebaseUser = firebaseUser;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.comment_view_holder,parent,false);
        return new CommentViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

        Glide.with(mContext).load(mData.get(position).getUimg()).apply(RequestOptions.circleCropTransform()).into(holder.img_user);
        holder.tv_name.setText(mData.get(position).getUname());
        holder.tv_content.setText(mData.get(position).getContent());
        holder.tv_date.setText(timestampToString((Long)mData.get(position).getTimestamp()));

        if (!holder.tv_name.getText().equals(firebaseUser.getDisplayName())){
            holder.btn_delete.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{

        ImageView img_user;
        TextView tv_name,tv_content,tv_date;
        Button btn_delete;

        public CommentViewHolder(View itemView) {
            super(itemView);
            img_user = itemView.findViewById(R.id.comment_user_img);
            tv_name = itemView.findViewById(R.id.comment_username);
            tv_content = itemView.findViewById(R.id.comment_content);
            tv_date = itemView.findViewById(R.id.comment_date);
            btn_delete = itemView.findViewById(R.id.btn_delete_comment);

            btn_delete.setOnClickListener(v -> {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        activity);
                alertDialog.setTitle("Excluir comentário");
                alertDialog.setMessage("Tem certeza que deseja excluir ?");
                alertDialog.setIcon(R.drawable.logosemfundo);
                alertDialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference commentRef1 = firebaseDatabase.getReference(COMMENT_KEY).child(cityKey);

                        commentRef1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snap:dataSnapshot.getChildren()){
                                    Comment comment = snap.getValue(Comment.class);
                                    int pos = mData.indexOf(comment);

                                    if (mData.size() == 1
                                            && comment.getUid().equals(firebaseUser.getUid())
                                            && comment.getContent().equals(tv_content.getText())){
                                        commentRef1.removeValue();
                                        mData.remove(pos +1);
                                        notifyItemRemoved(pos  +1);
                                        return;
                                    }else if (comment.getUid().equals(firebaseUser.getUid())
                                            && comment.getContent().equals(tv_content.getText())){


                                        commentRef1.child(snap.getKey()).removeValue();
                                        mData.remove(pos  +1);
                                        notifyItemRemoved(pos  +1);
                                        return;
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                });
                alertDialog.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                AlertDialog alertDialogMain = alertDialog.create();
                alertDialogMain.show();


            });

        }
    }



    private String timestampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd/MM/yy",calendar).toString();
        return date;
    }


}
