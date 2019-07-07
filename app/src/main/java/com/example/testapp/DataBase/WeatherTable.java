package com.example.testapp.DataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class WeatherTable {

    private final static String TABE_NAME = "Weather";
    private final static String COLUMN_ID = "ID";
    private final static String COLUMN_TOWN = "TOWN";
    private final static String COLUMN_TEMPERATURE = "TEMPERATURE";
    private final static String COLUMN_HUMIDITY = "HUMIDITY";
    private final static String COLUMN_WIND = "WIND";
    private final static String COLUMN_PRESSURE = "PRESSURE";

    public static void createTable(SQLiteDatabase database){
        database.execSQL("CREATE TABLE " + TABE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TOWN + " TEXT," +
                COLUMN_TEMPERATURE + " REAL," + COLUMN_HUMIDITY + " REAL," + COLUMN_WIND + " REAL," + COLUMN_PRESSURE + " REAL);");
    }

    public static void addNewTown(SQLiteDatabase database, String town, String temperature, String humidity, String wind, String pressure){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOWN,town);
        values.put(COLUMN_TEMPERATURE,temperature);
        values.put(COLUMN_HUMIDITY,humidity);
        values.put(COLUMN_WIND,wind);
        values.put(COLUMN_PRESSURE,pressure);

        database.insert(TABE_NAME,null,values);
    }

    public static void editTown(SQLiteDatabase database, String town, String temperature, String humidity, String wind, String pressure){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOWN,town);
        values.put(COLUMN_TEMPERATURE,temperature);
        values.put(COLUMN_HUMIDITY,humidity);
        values.put(COLUMN_WIND,wind);
        values.put(COLUMN_PRESSURE,pressure);

        database.update(TABE_NAME, values, town,null);
    }

    public static ArrayList<String> getTownWeather(SQLiteDatabase database, String city){
        if(database.getPageSize()>1){
        Cursor cursor = database.query(TABE_NAME, new String[]{COLUMN_TOWN},city,null,null,null,null);

        return getResultFromCursor(cursor);}

        else {return null;}
    }

    static ArrayList<String> getResultFromCursor(Cursor cursor){
        ArrayList<String> result = null;

        if(cursor != null && cursor.moveToFirst()) {
            result = new ArrayList<>(cursor.getCount());

            int noteIdx = cursor.getColumnIndex(COLUMN_TOWN);
            do {
                result.add(cursor.getString(noteIdx));
            } while (cursor.moveToNext());
        }

        try { cursor.close(); } catch (Exception ignored) {}
        return result == null ? new ArrayList<String>(0) : result;
    }

}
