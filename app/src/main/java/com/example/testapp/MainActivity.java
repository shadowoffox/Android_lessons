package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button3 = findViewById(R.id.button3);
        button3.setEnabled(false);

        Switch switch1 = findViewById(R.id.switch1);
        switch1.setBackgroundColor(Color.YELLOW);
    }

}
