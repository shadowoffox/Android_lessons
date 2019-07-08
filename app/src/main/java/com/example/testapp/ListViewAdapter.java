package com.example.testapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.testapp.DataBase.WeatherTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.testapp.Fragments.TownFragment.city;
import static com.example.testapp.Fragments.WeatherFrament.strMoisture;
import static com.example.testapp.Fragments.WeatherFrament.strPressure;
import static com.example.testapp.Fragments.WeatherFrament.strWind_speed;
import static com.example.testapp.Fragments.WeatherFrament.textTemperature;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<String> elements;
    private Context context;
    private LayoutInflater inflater;
    private SQLiteDatabase database;

    public ListViewAdapter(Context context, SQLiteDatabase database) {
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.database = database;

        elements = WeatherTable.getTownWeather(database,city);
    }

    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public Object getItem(int position) {
        return elements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addNewElement(){
        WeatherTable.addNewTown(database,city,textTemperature,strMoisture,strWind_speed,strPressure);


        System.out.println("qqqqqqqqq----");
        notifyDataSetChanged();
    }

    public void editElement(){
        if (elements.size()>0){
            WeatherTable.editTown(database,city,textTemperature,strMoisture,strWind_speed,strPressure);

            elements.set(elements.size()-1, Arrays.toString(new String[]{city,textTemperature,strMoisture,strWind_speed,strPressure}));
            System.out.println("qqqqqqqqq----");
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = inflater.inflate(R.layout.list_view_item,parent,false);
        }
        System.out.println(elements);
        String text = elements.get(position);
        TextView textView = convertView.findViewById(R.id.element_text);
        textView.setText(text);

        return convertView;
    }
}
