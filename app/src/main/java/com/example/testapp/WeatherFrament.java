package com.example.testapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;

public class WeatherFrament extends Fragment {

    private static final String TOWN = "TOWN";
    private static String  strMoisture ="";
    private static String strWind_speed ="";
    private static String strPressure = "";
    private  String textTemperature="";
    private  String textHumidity="";
    final List<Weather> states = new ArrayList();
    final WeatherAdapter adapter = new WeatherAdapter(states);
    private RecyclerView recyclerView;
    private TextView textView;
    private static boolean moisture;
    private static boolean wind_speed;
    private static boolean pressure;

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
        recyclerView = view.findViewById(R.id.recyclerView);
        textView = view.findViewById(R.id.textView);
        textView.setText(getArguments().getString(TOWN));
        getSensors();
        setInitialData();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        System.out.println(states.size());
        System.out.println("temp!!"+textTemperature);
        System.out.println("hum!!"+textHumidity);

        return view;

    }


    private void getSensors(){
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        Sensor sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        Sensor sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
       // if (sensorTemperature != null){
            sensorManager.registerListener(sensorsListener, sensorTemperature, SensorManager.SENSOR_DELAY_NORMAL);//}
        //if (sensorHumidity != null){
            sensorManager.registerListener(sensorsListener, sensorHumidity, SensorManager.SENSOR_DELAY_NORMAL);//}
    }

    private void showMeATemerature(SensorEvent event) {
        textTemperature = "Temperature is " + event.values[0];

    }
    private void showMeHumidity(SensorEvent event){
        textHumidity = "Humidity is " + event.values[0];
    }


    private SensorEventListener sensorsListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
          //  if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
                showMeATemerature(event);
           // }
           // if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
                showMeHumidity(event);
           // }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };



    private void setInitialData(){
        states.add(new Weather ( String.format("Пн +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.cloudy));
        states.add(new Weather ( String.format("Вт +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.cloudy2));
        states.add(new Weather ( String.format("Ср +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.cloudy3));
        states.add(new Weather ( String.format("Чт +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.rainy));
        states.add(new Weather ( String.format("Птн +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.rainy2));
        states.add(new Weather ( String.format("Сб +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.stormy));
        states.add(new Weather ( String.format("Вс +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.sunny));
        states.add(new Weather ( textTemperature, R.drawable.sunny));
        states.add(new Weather ( textHumidity, R.drawable.rainy2));
    }
}
