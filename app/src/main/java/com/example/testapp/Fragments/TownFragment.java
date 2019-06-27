package com.example.testapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.testapp.R;
import java.util.Objects;

public class TownFragment extends Fragment {
    private Button toWetherFragment;
    private CheckBox chbxMoisture;
    private CheckBox chbxWind;
    private CheckBox chbxPressure;
    private Spinner spinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_town ,container,false);

        spinner = view.findViewById(R.id.spinner_towns);
        toWetherFragment = view.findViewById(R.id.btn_next);
        chbxMoisture = view.findViewById(R.id.chbx_moisture);
        chbxWind = view.findViewById(R.id.chbx_wind_speed);
        chbxPressure = view.findViewById(R.id.chbx_pressure);

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.Towns, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        toWetherFragment.setOnClickListener(v -> {
            String msg = spinner.getSelectedItem().toString();
            boolean moisture = chbxMoisture.isChecked();
            boolean wind_speed = chbxWind.isChecked();
            boolean pressure = chbxPressure.isChecked();

            if (getFragmentManager() != null) {
                Fragment fragment = new WeatherFrament().newInstance(msg,moisture,wind_speed,pressure);
                getFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout, fragment)
                        .addToBackStack(TownFragment.class.getName())
                        .commit();
            }
        });
        return view;
    }
}
