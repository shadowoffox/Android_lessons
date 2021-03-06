package com.example.testapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<ViewHolderWeather> {
    private List<Weather> weatherList;

    public WeatherAdapter(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public ViewHolderWeather onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolderWeather(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weth_layout, parent, false)){

            @Override
            public void bindView(Object element) {}
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderWeather holder, int position) {
        Weather weather= weatherList.get(position);
        holder.resWeather.setText(weather.getRes());
        holder.icoWeather.setImageResource(weather.getWeatherResource());
        //holder.itemView.setBackgroundResource(R.drawable.cloudy);
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}

