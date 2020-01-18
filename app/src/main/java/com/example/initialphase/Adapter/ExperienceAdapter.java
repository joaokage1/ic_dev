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
import com.example.initialphase.R;
import com.example.initialphase.activities.info.CityDetailActivity;
import com.example.initialphase.activities.info.ExperienceDetailActivity;
import com.example.initialphase.model.Experiencia;

import java.util.List;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder> {

    Context mContext;
    List<Experiencia> list;

    public ExperienceAdapter(Context mContext, List<Experiencia> list){
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.basic_view_holder,parent,false);
        return new ExperienceAdapter.ExperienceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienceViewHolder holder, int position) {
        holder.name.setText(list.get(position).getNome());
        Glide.with(mContext).load(list.get(position).getPicture()).into(holder.imgExp);
        Glide.with(mContext).load(list.get(position).getUserPhoto()).apply(RequestOptions.circleCropTransform()).into(holder.imgUser);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ExperienceViewHolder  extends RecyclerView.ViewHolder{

        TextView name;
        ImageView imgExp, imgUser;

        public ExperienceViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.cityName);
            imgExp = itemView.findViewById(R.id.cityFlag);
            imgUser = itemView.findViewById(R.id.countryFlag);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent postDetailActivity = new Intent(mContext, ExperienceDetailActivity.class);
                    int position = getAdapterPosition();

                    postDetailActivity.putExtra("curso",list.get(position).getCurso());
                    postDetailActivity.putExtra("desc",list.get(position).getDesc());
                    postDetailActivity.putExtra("experienciaKey",list.get(position).getExperienciaKey());
                    postDetailActivity.putExtra("nome",list.get(position).getNome());
                    postDetailActivity.putExtra("picture",list.get(position).getPicture());
                    postDetailActivity.putExtra("userID", list.get(position).getUserID());
                    postDetailActivity.putExtra("userPhoto", list.get(position).getUserPhoto());
                    postDetailActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(postDetailActivity);
                }
            });
        }
    }
}
