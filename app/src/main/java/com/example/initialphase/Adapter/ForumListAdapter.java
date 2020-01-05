package com.example.initialphase.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.initialphase.Activities.ForumDetailActivity;
import com.example.initialphase.R;
import com.example.initialphase.model.Forum;

import java.util.List;

public class ForumListAdapter extends RecyclerView.Adapter<ForumListAdapter.ForumCountryViewHolder> {

    Context mContext;
    List<Forum> list;

    public ForumListAdapter(Context mContext, List<Forum> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ForumCountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.forum_view_holder,parent,false);
        return new ForumCountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumCountryViewHolder holder, int position) {
        holder.nameForum.setText(list.get(position).getTitle());
        Glide.with(mContext).load(list.get(position).getUserPhoto()).apply(RequestOptions.circleCropTransform()).into(holder.imgUser);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ForumCountryViewHolder extends RecyclerView.ViewHolder {

        TextView nameForum;
        ImageView imgUser;

        public ForumCountryViewHolder(@NonNull View itemView) {
            super(itemView);

            nameForum = itemView.findViewById(R.id.title_forum);
            imgUser = itemView.findViewById(R.id.img_user_forum);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent forumDetailActivity = new Intent(mContext, ForumDetailActivity.class);

                    int position = getAdapterPosition();

                    forumDetailActivity.putExtra("title",list.get(position).getTitle());
                    forumDetailActivity.putExtra("description",list.get(position).getDescription());
                    forumDetailActivity.putExtra("forumKey",list.get(position).getForumKey());
                    forumDetailActivity.putExtra("userPhoto",list.get(position).getUserPhoto());
                    long timestamp  = (long) list.get(position).getTimestamp();
                    forumDetailActivity.putExtra("postDate",timestamp) ;
                    forumDetailActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(forumDetailActivity);
                }
            });

        }
    }
}
