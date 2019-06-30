package com.example.testapp.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.testapp.R;
import com.example.testapp.Weather;
import com.example.testapp.WeatherLoader;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import static android.widget.Toast.LENGTH_LONG;
import static com.example.testapp.Fragments.WeatherFrament.states;
import static com.example.testapp.Fragments.WeatherFrament.strMoisture;
import static com.example.testapp.Fragments.WeatherFrament.strPressure;
import static com.example.testapp.Fragments.WeatherFrament.strWind_speed;
import static com.example.testapp.Fragments.WeatherFrament.textTemperature;

public class TownFragment extends Fragment {

    public static final String SAVE_MY_TOWN = "save_my_town";
    private final Handler handler = new Handler();
    private boolean moisture;
    private boolean wind_speed;
    private boolean pressure;
    private String city;
    private Spinner spinner;
    private SharedPreferences townPreference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_town ,container,false);

        spinner = view.findViewById(R.id.spinner_towns);
        Button toWetherFragment = view.findViewById(R.id.btn_next);
        Button saveTown = view.findViewById(R.id.btn_save);
        CheckBox chbxMoisture = view.findViewById(R.id.chbx_moisture);
        CheckBox chbxWind = view.findViewById(R.id.chbx_wind_speed);
        CheckBox chbxPressure = view.findViewById(R.id.chbx_pressure);

        moisture = chbxMoisture.isChecked();
        wind_speed = chbxWind.isChecked();
        pressure = chbxPressure.isChecked();

        townPreference = PreferenceManager.getDefaultSharedPreferences(getContext());

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.Towns, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        loadPreference(townPreference);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.Towns);
                city = choose[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        chbxMoisture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    moisture=true;
                    updateWeatherData(city);
                }
                else {moisture=false;
                    updateWeatherData(city);
                }
            }
        });

        chbxWind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    wind_speed=true;
                    updateWeatherData(city);
                }
                else {wind_speed=false;
                    updateWeatherData(city);
                }

            }
        });

        chbxPressure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    pressure=true;
                    updateWeatherData(city);
                }
                else {pressure=false;
                    updateWeatherData(city);
                }
            }
        });

        updateWeatherData(city);

        saveTown.setOnClickListener(v -> {
            savePreference(townPreference);
        });

        toWetherFragment.setOnClickListener(v -> {
            if (getFragmentManager() != null) {
                Fragment fragment = new WeatherFrament().newInstance(city);
                getFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout, fragment)
                        .addToBackStack(TownFragment.class.getName())
                        .commit();
            }
        });
        return view;
    }

    private void savePreference(SharedPreferences sharedPreferences){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(SAVE_MY_TOWN, spinner.getSelectedItemPosition());
        editor.apply();

        Toast.makeText(getContext(),getString(R.string.TOWN_IS_SAVED)+ city, LENGTH_LONG).show();
    }

    private void loadPreference (SharedPreferences sharedPreferences){
        spinner.setSelection(sharedPreferences.getInt(SAVE_MY_TOWN,0));
    }

    private void updateWeatherData(final String city) {
        new Thread() {
            @Override
            public void run() {
                final JSONObject jsonObject = WeatherLoader.getJSONData(city);
                if(jsonObject == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "lace_not_found", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            states.clear();
                            renderWeather(jsonObject);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject jsonObject) {

        try {
            JSONObject details = jsonObject.getJSONArray("weather").getJSONObject(0);
            JSONObject main = jsonObject.getJSONObject("main");
            JSONObject wind = jsonObject.getJSONObject("wind");

            setDetails(wind, main);
            setCurrentTemp(main);
            setWeatherIcon(details.getInt("id"),jsonObject.getJSONObject("sys").getLong("sunrise") * 1000,jsonObject.getJSONObject("sys").getLong("sunset") * 1000);

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";

        if(actualId == 800) {
            long currentTime = new Date().getTime();
            if(currentTime >= sunrise && currentTime < sunset) {
                states.add(new Weather( String.format("Temperature %s \n Moisture %s \n Wind %s \n Pressure %s",textTemperature,strMoisture,strWind_speed,strPressure), R.drawable.sunny));
            } else {
                states.add(new Weather ( String.format("Temperature %s \n Moisture %s \n Wind %s \n Pressure %s",textTemperature,strMoisture,strWind_speed,strPressure), R.drawable.sunny));
            }
        } else {
            switch (id) {
                case 2: {
                    states.add(new Weather (String.format("Temperature %s \n Moisture %s \n Wind %s \n Pressure %s",textTemperature,strMoisture,strWind_speed,strPressure), R.drawable.stormy));
                    break;
                }
                case 3: {
                    states.add(new Weather ( String.format("Temperature %s \n Moisture %s \n Wind %s \n Pressure %s",textTemperature,strMoisture,strWind_speed,strPressure), R.drawable.rainy));
                    break;
                }
                case 5: {
                    states.add(new Weather ( String.format("Temperature %s \n Moisture %s \n Wind %s \n Pressure %s",textTemperature,strMoisture,strWind_speed,strPressure), R.drawable.rainy2));
                    break;
                }
                case 8: {
                    states.add(new Weather ( String.format("Temperature %s \n Moisture %s \n Wind %s \n Pressure %s",textTemperature,strMoisture,strWind_speed,strPressure), R.drawable.cloudy));

                    break;
                }
            }
        }
    }

    private void setCurrentTemp(JSONObject main) throws JSONException {
        textTemperature = String.format(Locale.getDefault(), "%.1f", main.getDouble("temp"));
    }

    private void setDetails(JSONObject wind, JSONObject main) throws JSONException {
        if (moisture){
            strMoisture = main.getString("humidity") + " %";
        }
        if ( pressure) {
            strPressure = main.getString("pressure") + " hPa";
        }
        if (wind_speed) {
             strWind_speed= wind.getString("speed") + " m/s";
        }
    }
}
