package com.example.testapp.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.testapp.R;
import com.example.testapp.Weather;
import com.example.testapp.WeatherAdapter;
import java.util.ArrayList;
import java.util.List;

public class WeatherFrament extends Fragment {
    private static final String TOWN = "TOWN";
    private static String  strMoisture ="";
    private static String strWind_speed ="";
    private static String strPressure = "";
    public static String textTemperature="";
    public static String textHumidity="";
    private final List<Weather> states = new ArrayList();
    private final WeatherAdapter adapter = new WeatherAdapter(states);
    private RecyclerView recyclerView;
    private TextView textView;

    public static WeatherFrament newInstance(String data, boolean myMoisture, boolean myWind_speed, boolean myPressure) {
       Bundle args = new Bundle();
       args.putString(TOWN, data);

       WeatherFrament frament = new WeatherFrament();
       frament.setArguments(args);

        if (myMoisture){
            strMoisture = "50%";
        }
        if (myWind_speed) {
            strWind_speed = "5m/h";
        }
        if (myPressure) {
            strPressure = "1017mbar";
        }
       return frament;

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        textView = view.findViewById(R.id.text_view);

       // initViews();

        String values = getArguments().getString(TOWN);

        MyAsyncTask asyncTask = new MyAsyncTask();
        asyncTask.execute(values);

        setInitialData();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void setInitialData(){
        states.clear();
        states.add(new Weather ( String.format("Пн +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.cloudy));
        states.add(new Weather ( String.format("Вт +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.cloudy2));
        states.add(new Weather ( String.format("Ср +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.cloudy3));
        states.add(new Weather ( String.format("Чт +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.rainy));
        states.add(new Weather ( String.format("Птн +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.rainy2));
        states.add(new Weather ( String.format("Сб +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.stormy));
        states.add(new Weather ( String.format("Вс +20 %s %s %s",strMoisture,strWind_speed,strPressure), R.drawable.sunny));
        states.add(new Weather ( textTemperature, R.drawable.temperature));
        states.add(new Weather ( textHumidity, R.drawable.humidity));
    }

    private class MyAsyncTask extends AsyncTask<String, String, Integer> {

        @Override
        protected void onPreExecute() {
            textView.setText("");
        }

        @Override
        protected Integer doInBackground(String... params) {
            if(params == null || params.length == 0) {
                throw new IllegalArgumentException("Параметры не должны быть пустыми");
            }

            for(int i = 0; i < params.length; i++) {
                sleep();
                publishProgress(String.valueOf(i), params[i]);
            }

            return params.length;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if(values != null && values.length > 1) {
                textView.append(values[1]);
            } else {
                textView.append("Нет данных!\n");
            }
        }

        private void sleep() {
            try {
                Thread.sleep((long) 500);
            } catch (Exception ignored) {}
        }
    }
}




