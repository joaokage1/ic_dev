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
import com.example.initialphase.Activities.CityDetailActivity;
import com.example.initialphase.R;
import com.example.initialphase.model.City;

import java.util.List;

public class CountryAdapter  extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    Context mContext;
    List<City> list;

    public CountryAdapter(Context mContext, List<City> list){
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.country_view_holder,parent,false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        holder.cityName.setText(list.get(position).getName());
        Glide.with(mContext).load(list.get(position).getCityFlag()).into(holder.imgCity);
        Glide.with(mContext).load(list.get(position).getCountryFlag()).apply(RequestOptions.circleCropTransform()).into(holder.imgCountry);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder{

        TextView cityName;
        ImageView imgCity, imgCountry;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);

            cityName = itemView.findViewById(R.id.cityName);
            imgCity = itemView.findViewById(R.id.cityFlag);
            imgCountry = itemView.findViewById(R.id.countryFlag);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent postDetailActivity = new Intent(mContext, CityDetailActivity.class);
                    int position = getAdapterPosition();

                    postDetailActivity.putExtra("title",list.get(position).getName());
                    postDetailActivity.putExtra("cityFlag",list.get(position).getCityFlag());
                    postDetailActivity.putExtra("bairro",list.get(position).getBairro());
                    postDetailActivity.putExtra("cityKey",list.get(position).getCityKey());
                    postDetailActivity.putExtra("contatos",list.get(position).getContatos());
                    postDetailActivity.putExtra("curiosidades", list.get(position).getCuriosidades());
                    postDetailActivity.putExtra("custo", list.get(position).getCusto());
                    postDetailActivity.putExtra("transporte", list.get(position).getTransporte());
                    postDetailActivity.putExtra("universidades", list.get(position).getUniversidades());
                    postDetailActivity.putExtra("photo", list.get(position).getPhoto());
                    postDetailActivity.putExtra("lugares", list.get(position).getLugares());
                    postDetailActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(postDetailActivity);
                }
            });

        }
    }
}
