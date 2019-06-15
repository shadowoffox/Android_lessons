package com.example.testapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ViewHolderWeather extends ViewHolder {

    TextView resWeather;
    ImageView icoWeather;

    public ViewHolderWeather(@NonNull View itemView) {
        super(itemView);

        resWeather = itemView.findViewById(R.id.resWeather);
        icoWeather = itemView.findViewById(R.id.icoWeather);
    }

    @Override
    public void bindView(Object element) {
        resWeather.setText(((Weather) element).getRes());
        icoWeather.setImageResource(((Weather) element).getWeatherResource());
    }
}
