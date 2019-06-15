package com.example.testapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ViewHolderAdv extends ViewHolder {

    TextView resWeather;


    public ViewHolderAdv(@NonNull View itemView) {
        super(itemView);

        resWeather = itemView.findViewById(R.id.resWeather);

    }

    @Override
    public void bindView(Object element) {
        resWeather.setText(((Adv) element).getAdv());
    }
}
