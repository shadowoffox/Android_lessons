package com.example.testapp.Fragments;

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
    public static String  strMoisture ="n/a";
    public static String strWind_speed ="n/a";
    public static String strPressure = "n/a";
    public static String textTemperature="n/a";
    public static final List<Weather> states = new ArrayList();
    private final WeatherAdapter adapter = new WeatherAdapter(states);
    private RecyclerView recyclerView;
    private TextView textView;
    private static String city;

    public WeatherFrament newInstance(String data) {
       Bundle args = new Bundle();
       args.putString(TOWN, data);

       WeatherFrament frament = new WeatherFrament();
       frament.setArguments(args);

        city =data;

       return frament;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        textView = view.findViewById(R.id.text_view);
        textView.setText(city);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}




