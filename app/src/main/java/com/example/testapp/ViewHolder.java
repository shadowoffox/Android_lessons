package com.example.testapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView resWeather;
    ImageView icoWeather;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        resWeather = itemView.findViewById(R.id.resWeather);
        icoWeather = itemView.findViewById(R.id.icoWeather);
    }
}
