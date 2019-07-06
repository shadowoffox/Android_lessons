package com.example.testapp.DataBase;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class WeatherTable {

    private final static String TABE_NAME = "Weather";
    private final static String COLUMN_ID = "ID";
    private final static String COLUMN_TOWN = "TOWN";
    private final static String COLUMN_TEMPERATURE = "TEMPERATURE";
    private final static String COLUMN_HUMIDITY = "HUMIDITY";
    private final static String COLUMN_WIND = "WIND";
    private final static String COLUMN_PRESSURE = "PRESSURE";

    static void createTable(SQLiteDatabase database){
        database.execSQL("CREATE TABLE " + TABE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TOWN + " TEXT," +
                COLUMN_TEMPERATURE + " REAL," + COLUMN_HUMIDITY + " REAL," + COLUMN_WIND + " REAL," + COLUMN_PRESSURE + " REAL);");
    }

    static void addNewTown(SQLiteDatabase database, String town, float temperature, float humidity, float wind, float pressure){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOWN,town);
        values.put(COLUMN_TEMPERATURE,temperature);
        values.put(COLUMN_HUMIDITY,humidity);
        values.put(COLUMN_WIND,wind);
        values.put(COLUMN_PRESSURE,pressure);

        database.insert(TABE_NAME,null,values);
    }

    static void editTown(SQLiteDatabase database, String town, float temperature, float humidity, float wind, float pressure){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOWN,town);
        values.put(COLUMN_TEMPERATURE,temperature);
        values.put(COLUMN_HUMIDITY,humidity);
        values.put(COLUMN_WIND,wind);
        values.put(COLUMN_PRESSURE,pressure);

        database.update(TABE_NAME, values, town,null);
    }

}
