package com.example.testapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class WeatherFrament extends Fragment {

    public static final String TOWN = "TOWN";

    static String  strMoisture ="";
    static String strWind_speed ="";
    static String strPressure = "";
    List<Weather> states = new ArrayList();
    ListView list;
    TextView textView;
    static boolean moisture;
    static boolean wind_speed;
    static boolean pressure;

    public static WeatherFrament newInstance(String data, boolean myMoisture, boolean myWind_speed, boolean myPressure) {
       Bundle args = new Bundle();
       args.putString(TOWN, data);
       WeatherFrament frament = new WeatherFrament();
       frament.setArguments(args);

         moisture = myMoisture;
         wind_speed= myWind_speed;
         pressure = myPressure;

        if (moisture){
            strMoisture = "50%";
        }
        if (wind_speed) {
            strWind_speed = "5m/h";
        }
        if (pressure) {
            strPressure = "1017mbar";
        }
       return frament;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        list = view.findViewById(R.id.listView);

        textView = view.findViewById(R.id.textView);
        textView.setText(getArguments().getString(TOWN));

        setInitialData();

        //TODO сделать отсчет от "сегодня" и далее до конца недели. сделать/найти ico для показывания в строках в зависимости от погоды.
        StateAdapter stateAdapter = new StateAdapter(getActivity(), R.layout.item_weth_layout, states);
        list.setAdapter(stateAdapter);


        return view;
    }

    private void setInitialData(){
        states.add(new Weather ( String.format("Пн +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.cloudy));
        states.add(new Weather ( String.format("Вт +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.cloudy2));
        states.add(new Weather ( String.format("Ср +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.cloudy3));
        states.add(new Weather ( String.format("Чт +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.rainy));
        states.add(new Weather ( String.format("Птн +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.rainy2));
        states.add(new Weather ( String.format("Сб +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.stormy));
        states.add(new Weather ( String.format("Вс +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.sunny));

    }
}
