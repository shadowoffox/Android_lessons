package com.example.testapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.testapp.R;
import com.example.testapp.Weather;
import com.example.testapp.rest.OpenHereWeatherRepo;
import com.example.testapp.rest.OpenWeatherRepo;
import com.example.testapp.rest.entities.WeatherRequestRestModel;

import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.testapp.Fragments.WeatherFrament.states;
import static com.example.testapp.Fragments.WeatherFrament.strMoisture;
import static com.example.testapp.Fragments.WeatherFrament.strPressure;
import static com.example.testapp.Fragments.WeatherFrament.strWind_speed;
import static com.example.testapp.Fragments.WeatherFrament.textTemperature;

public class TownFragment extends Fragment {

    private static final String SAVE_MY_TOWN = "save_my_town";
    public static String city;
    private Spinner spinner;
    private Button toWetherFragment;
    private Button saveTown;
    private SharedPreferences townPreference;
    private TextView yourLocationView = null;

    private final static String MSG_NO_DATA = "No data";

    private LocationManager mLocManager = null;
    private LocationListener mLocListener = null;

    private final int permissionRequestCode = 12345;

    private Location loc;
    private String lat;
    private String lon;
    private StringBuilder sb = new StringBuilder();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_town, container, false);
        initialViews(view);

        mLocManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        checkPermiss();

        setSpinner();
        townPreference = PreferenceManager.getDefaultSharedPreferences(getContext());
        loadPreference(townPreference);
        updateWeatherData(city);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.Towns);
                city = choose[position];
                updateWeatherData(city);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                updateWeatherData(city);
            }
        });

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



    private void checkPermiss(){

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            final String[] permissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
           // ActivityCompat.requestPermissions(getActivity(),permissions,permissionRequestCode);
            requestPermissions(permissions,permissionRequestCode);

        } else
            {loc = mLocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            lat = String.valueOf(loc.getLatitude());
            lon = String.valueOf(loc.getLongitude());
            hereWeatherData(lat,lon);
            yourLocationView.setText(sb.toString());
            }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode == permissionRequestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                checkPermiss();
                Toast.makeText(getContext(), "Спасибо!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(),
                        "Извините, апп без данного разрешения может работать неправильно",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setSpinner(){
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.Towns, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    private void savePreference(SharedPreferences sharedPreferences){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(SAVE_MY_TOWN, spinner.getSelectedItemPosition());
        editor.apply();

        Toast.makeText(getContext(),getString(R.string.TOWN_IS_SAVED)+ city, LENGTH_LONG).show();
    }

    private void loadPreference (SharedPreferences sharedPreferences){
        spinner.setSelection(sharedPreferences.getInt(SAVE_MY_TOWN,1));
    }
    private void updateWeatherData(final String city) {
        OpenWeatherRepo.getSingleton().getAPI().loadWeather(city + ", RU",
                "762ee61f52313fbd10a4eb54ae4d4de2", "metric")
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequestRestModel> call,
                                           @NonNull Response<WeatherRequestRestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            renderWeather(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequestRestModel> call, Throwable t) {
                        Toast.makeText(getContext(), "ОШИБКА СЕТИ!!!",
                                Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });

    }

    private void hereWeatherData(String lat, String lon) {
        System.out.println("Че каво?");
        OpenHereWeatherRepo.getSingleton().getAPI().loadWeather(lat,lon,
                "762ee61f52313fbd10a4eb54ae4d4de2","metric")
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequestRestModel> call,
                                           @NonNull Response<WeatherRequestRestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            renderHereWeather(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequestRestModel> call, Throwable t) {
                        Toast.makeText(getContext(), "ОШИБКА СЕТИ!!!",
                                Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });

    }

    private void renderWeather(WeatherRequestRestModel restModel) {

            setDetails(restModel.main.humidity,restModel.main.pressure,restModel.wind.speed);
            setCurrentTemp(restModel.main.temp);
            setWeatherIcon(restModel.weather[0].id,restModel.sys.sunrise * 1000,restModel.sys.sunset * 1000);
    }
    private void renderHereWeather(WeatherRequestRestModel restModel) {
            setHereTownName(restModel.name);
            setHereCurrentTemp(restModel.main.temp);
            setHereDetails(restModel.main.humidity,restModel.main.pressure,restModel.wind.speed);
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;

        if(actualId == 800) {
            long currentTime = new Date().getTime();
            if(currentTime >= sunrise && currentTime < sunset) {
                states.clear();
                states.add(new Weather( String.format("Temperature %s \n Moisture %s \n Wind %s \n Pressure %s",textTemperature,strMoisture,strWind_speed,strPressure), R.drawable.sunny));

            } else {
                states.clear();
                states.add(new Weather ( String.format("Temperature %s \n Moisture %s \n Wind %s \n Pressure %s",textTemperature,strMoisture,strWind_speed,strPressure), R.drawable.sunny));
            }
        } else {
            switch (id) {
                case 2: {
                    states.clear();
                    states.add(new Weather (String.format("Temperature %s \n Moisture %s \n Wind %s \n Pressure %s",textTemperature,strMoisture,strWind_speed,strPressure), R.drawable.stormy));
                    break;
                }
                case 3: {
                    states.clear();
                    states.add(new Weather ( String.format("Temperature %s \n Moisture %s \n Wind %s \n Pressure %s",textTemperature,strMoisture,strWind_speed,strPressure), R.drawable.rainy));
                    break;
                }
                case 5: {
                    states.clear();
                    states.add(new Weather ( String.format("Temperature %s \n Moisture %s \n Wind %s \n Pressure %s",textTemperature,strMoisture,strWind_speed,strPressure), R.drawable.rainy2));
                    break;
                }
                case 8: {
                    states.clear();
                    states.add(new Weather ( String.format("Temperature %s \n Moisture %s \n Wind %s \n Pressure %s",textTemperature,strMoisture,strWind_speed,strPressure), R.drawable.cloudy));
                    break;
                }
            }
        }
    }

    private void setCurrentTemp(float fTemperature) {
        textTemperature = String.format(Locale.getDefault(), "%.1f", fTemperature);
    }

    private void setDetails(float fMoisture, float fPressure, float fWindSpeed){
            strMoisture = fMoisture + " %";
            strPressure = fPressure + " hPa";
            strWind_speed= fWindSpeed + " m/s";
    }

    private void setHereTownName(String name){
        sb.append("You are in ").append(name).append(". The wether here is: ").append("\n");
    }

    private void setHereCurrentTemp(float fTemperature) {
        sb.append("tempeteure ").append(String.format(Locale.getDefault(), "%.1f", fTemperature)).append("\n");

    }
    private void setHereDetails(float fMoisture, float fPressure, float fWindSpeed){
            sb.append("Moisture is ").append(fMoisture).append(" % ").append("\n");
            sb.append("Pressure is ").append(fPressure).append(" hPa ").append("\n");
            sb.append("WindSpeed is ").append(fWindSpeed).append(" m/s ").append("\n");
    }


    private void initialViews(View view){
        spinner = view.findViewById(R.id.spinner_towns);
        toWetherFragment = view.findViewById(R.id.btn_next);
        saveTown = view.findViewById(R.id.btn_save);

        yourLocationView = view.findViewById(R.id.your_location);
    }



}
