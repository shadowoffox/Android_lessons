package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    public static final String TOWN = "TOWN";
    Button button;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
//    EditText editText;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner1);
        button = findViewById(R.id.button2);
//        editText = findViewById(R.id.editText);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.Towns, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        button.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            String msg = spinner.getSelectedItem().toString();
         //   String msg = String.valueOf(editText.getText());

            boolean moisture = checkBox1.isChecked();
            boolean wind_speed = checkBox2.isChecked();
            boolean pressure = checkBox3.isChecked();

            intent.putExtra(TOWN, msg);
            intent.putExtra("moisture", moisture);
            intent.putExtra("wind_speed", wind_speed);
            intent.putExtra("pressure", pressure);

            startActivity(intent);

        });

    }

}
