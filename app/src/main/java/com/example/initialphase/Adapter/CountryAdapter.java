package com.example.initialphase.Adapter;

import android.content.Context;
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

        }
    }
}
