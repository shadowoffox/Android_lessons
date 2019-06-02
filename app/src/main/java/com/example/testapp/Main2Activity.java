package com.example.testapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    String  strMoisture ="";
    String strWind_speed ="";
    String strPressure = "";

        Formatter pn = new Formatter();
        Formatter vt = new Formatter();
        Formatter sr = new Formatter();
        Formatter cht = new Formatter();
        Formatter ptn = new Formatter();
        Formatter sb = new Formatter();
        Formatter vs = new Formatter();

    private List<Weather> states = new ArrayList();

    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        list = findViewById(R.id.listView);

        TextView textView = findViewById(R.id.textView);
        textView.setText(getIntent().getStringExtra("TOWN"));

        Bundle arguments = getIntent().getExtras();

        if(arguments != null){
            boolean moisture = arguments.getBoolean("moisture");
            boolean wind_speed = arguments.getBoolean("wind_speed");
            boolean pressure = arguments.getBoolean("pressure");

            if (moisture){
                strMoisture = "50%";
            }
            if (wind_speed) {
                strWind_speed = "5m/h";
            }
            if (pressure) {
                strPressure = "1017mbar";
            }

        }
        setInitialData();

        //TODO сделать отсчет от "сегодня" и далее до конца недели. сделать/найти ico для показывания в строках в зависимости от погоды.
        StateAdapter stateAdapter = new StateAdapter(this, R.layout.item_weth_layout, states);
        list.setAdapter(stateAdapter);

    }
    private void setInitialData(){
        states.add(new Weather ( String.valueOf(pn.format("Пн +20 %s %s %s",strMoisture,strWind_speed,strPressure)), R.drawable.cloudy));
        states.add(new Weather ( String.valueOf(vt.format("Вт +20 %s %s %s",strMoisture,strWind_speed,strPressure)), R.drawable.cloudy2));
        states.add(new Weather ( String.valueOf(sr.format("Ср +20 %s %s %s",strMoisture,strWind_speed,strPressure)), R.drawable.cloudy3));
        states.add(new Weather ( String.valueOf(cht.format("Чт +20 %s %s %s",strMoisture,strWind_speed,strPressure)), R.drawable.rainy));
        states.add(new Weather ( String.valueOf(ptn.format("Птн +20 %s %s %s",strMoisture,strWind_speed,strPressure)), R.drawable.rainy2));
        states.add(new Weather ( String.valueOf(sb.format("Сб +20 %s %s %s",strMoisture,strWind_speed,strPressure)), R.drawable.stormy));
        states.add(new Weather ( String.valueOf(vs.format("Вс +20 %s %s %s",strMoisture,strWind_speed,strPressure)), R.drawable.sunny));

    }
}
