package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listView);

        final String[] days = new String[] {
                "Пн + градусы + состояние",
                "Вт+ градусы + состояние",
                "Ср+ градусы + состояние",
                "Чт+ градусы + состояние",
                "Птн+ градусы + состояние",
                "Сб+ градусы + состояние",
                "Вс+ градусы + состояние"
        };
       //TODO сделать отсчет от "сегодня" и далее до конца недели. сделать/найти ico для показывания в строках в зависимости от погоды.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, days);

        listView.setAdapter(adapter);

    }

}
