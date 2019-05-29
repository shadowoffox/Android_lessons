package com.example.testapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView textView = findViewById(R.id.textView);

        textView.setText(getIntent().getStringExtra("TOWN"));

        Bundle arguments = getIntent().getExtras();
        String  strMoisture ="";
        String strWind_speed ="";
        String strPressure = "";
        if(arguments!=null){
            boolean moisture = arguments.getBoolean("moisture");
            boolean wind_speed = arguments.getBoolean("wind_speed");
            boolean pressure = arguments.getBoolean("pressure");

            if (moisture){ strMoisture ="50%";}
            if (wind_speed) {strWind_speed = "5m/h";}
            if (pressure) {strPressure = "1017mbar";}

        }



        ListView listView = findViewById(R.id.listView);
        final String[] days = new String[] {
                "Пн + градусы + " + strMoisture + " " + strWind_speed +" " + strPressure,
                "Вт+ градусы + " + strMoisture + " " + strWind_speed +" " + strPressure,
                "Ср+ градусы + " + strMoisture + " " + strWind_speed +" " + strPressure,
                "Чт+ градусы +  " + strMoisture + " " + strWind_speed +" " + strPressure,
                "Птн+ градусы + " + strMoisture + " " + strWind_speed +" " + strPressure,
                "Сб+ градусы + " + strMoisture + " " + strWind_speed +" " + strPressure,
                "Вс+ градусы + " + strMoisture + " " + strWind_speed +" " + strPressure
        };
        //TODO сделать отсчет от "сегодня" и далее до конца недели. сделать/найти ico для показывания в строках в зависимости от погоды.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, days);

        listView.setAdapter(adapter);

    }

}
