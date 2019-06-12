package com.example.testapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<ViewHolder> {


    public WeatherAdapter(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    List<Weather> weatherList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weth_layout,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Weather weather= weatherList.get(position);
    holder.resWeather.setText(weather.getRes());
    holder.icoWeather.setImageResource(weather.getWeatherResource());
    }

    @Override
    public int getItemCount() {
        System.out.println("hi from adapter"+ weatherList.size());
        return weatherList.size();
    }
}
