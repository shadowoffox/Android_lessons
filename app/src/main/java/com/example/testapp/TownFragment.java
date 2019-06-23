package com.example.testapp;


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
import java.util.Objects;

public class TownFragment extends Fragment {

    private Button toWetherFragment;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private Spinner spinner;
    private String textTemperature;
    private String textHumidity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_town ,container,false);

        spinner = view.findViewById(R.id.spinner2);
        toWetherFragment = view.findViewById(R.id.button_2);
        checkBox1 = view.findViewById(R.id.checkBox_1);
        checkBox2 = view.findViewById(R.id.checkBox_2);
        checkBox3 = view.findViewById(R.id.checkBox_3);
        Bundle bundle = this.getArguments();
        if (bundle !=null){
            textTemperature = bundle.getString("TEMPERATURE");
            textHumidity = bundle.getString("HUMIDITY");
        }

        System.out.println("temp!!!!!!!!!!!!"+textTemperature);
        System.out.println("hum!!!!!!!!!"+textHumidity);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.Towns, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        toWetherFragment.setOnClickListener(v -> {
            String msg = spinner.getSelectedItem().toString();
            boolean moisture = checkBox1.isChecked();
            boolean wind_speed = checkBox2.isChecked();
            boolean pressure = checkBox3.isChecked();

            Bundle bundle1 = new Bundle();
            bundle1.putString("TEMPERATURE",textTemperature);
            bundle1.putString("HUMIDITY",textHumidity);

            if (getFragmentManager() != null) {
                Fragment fragment = new WeatherFrament().newInstance(msg,moisture,wind_speed,pressure);
                fragment.setArguments(bundle1);
                getFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout, fragment)
                        .addToBackStack(TownFragment.class.getName())
                        .commit();
            }
            //   String msg = String.valueOf(editText.getText());

        });

        return view;
    }
}
