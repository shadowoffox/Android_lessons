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

    private Button button;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    //    EditText editText;
    private Spinner spinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_town ,container,false);

        spinner = view.findViewById(R.id.spinner2);
        button = view.findViewById(R.id.button_2);
//        editText = view.findViewById(R.id.editText);
        checkBox1 = view.findViewById(R.id.checkBox_1);
        checkBox2 = view.findViewById(R.id.checkBox_2);
        checkBox3 = view.findViewById(R.id.checkBox_3);

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.Towns, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        button.setOnClickListener(v -> {
            String msg = spinner.getSelectedItem().toString();
            boolean moisture = checkBox1.isChecked();
            boolean wind_speed = checkBox2.isChecked();
            boolean pressure = checkBox3.isChecked();

            if (getFragmentManager() != null) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout, WeatherFrament.newInstance(msg,moisture,wind_speed,pressure))
                        .addToBackStack(TownFragment.class.getName())
                        .commit();
            }
            //   String msg = String.valueOf(editText.getText());

        });

        return view;
    }
}
