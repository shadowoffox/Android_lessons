package com.example.testapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

public class ViewHolderWeather extends ViewHolder {

    TextView resWeather;
    ImageView icoWeather;

    ViewHolderWeather(@NonNull View itemView) {
        super(itemView);
        initViews();
    }

    @Override
    public void bindView(Object element) {
        resWeather.setText(((Weather) element).getRes());
        icoWeather.setImageResource(((Weather) element).getWeatherResource());
    }

    private void initViews(){
        resWeather = itemView.findViewById(R.id.res_weather);
        icoWeather = itemView.findViewById(R.id.ico_weather);
    }
}
