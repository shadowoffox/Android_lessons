package com.example.testapp.Fragments;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.testapp.DataBase.DataBaseHelper;
import com.example.testapp.DataBase.WeatherTable;
import com.example.testapp.ListViewAdapter;
import com.example.testapp.R;
import com.example.testapp.Weather;
import com.example.testapp.WeatherLoader;
import com.example.testapp.rest.OpenWeatherRepo;
import com.example.testapp.rest.entities.WeatherRequestRestModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
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
    private final Handler handler = new Handler();
    public static String city;
    private Spinner spinner;
    private Button toWetherFragment;
    private Button saveTown;
    private SharedPreferences townPreference;
    private SQLiteDatabase database;
    private ListView listView;
    private TextView textView;
    private ListViewAdapter historyAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_town ,container,false);
        initialViews(view);
        setSpinner();
        townPreference = PreferenceManager.getDefaultSharedPreferences(getContext());
        loadPreference(townPreference);
        updateWeatherData(city);
        initDB();
        initListView(view);

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
            haveATown(city);
        });

        return view;
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

    private void renderWeather(WeatherRequestRestModel restModel) {

            setDetails(restModel.main.humidity,restModel.main.pressure,restModel.wind.speed);
            setCurrentTemp(restModel.main.temp);
            setWeatherIcon(restModel.weather[0].id,restModel.sys.sunrise * 1000,restModel.sys.sunset * 1000);
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
    private void initDB(){
        database = new DataBaseHelper(getContext()).getWritableDatabase();
    }
    private void initialViews(View view){
        spinner = view.findViewById(R.id.spinner_towns);
        toWetherFragment = view.findViewById(R.id.btn_next);
        saveTown = view.findViewById(R.id.btn_save);
    }

    private void initListView(View view){

        listView = view.findViewById(R.id.list_wiev);
        textView = view.findViewById(R.id.text_view);

        listView.setEmptyView(textView);
        historyAdapter = new ListViewAdapter(getContext(),database);
        listView.setAdapter(historyAdapter);

    }

    private void haveATown(String city){
        //всё это на кнопку

        if (WeatherTable.getTownWeather(database,city)==null){
            historyAdapter.addNewElement();
        }
        else {
            historyAdapter.editElement();
        }
    }
}
