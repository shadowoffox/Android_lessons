/*
package com.example.testapp.Sevices;

import android.app.IntentService;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.annotation.Nullable;
import com.example.testapp.Fragments.WeatherFrament;

public class BgService extends IntentService {

    public BgService() {
        super("background_service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        getSensors();
    }

    private void getSensors(){
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        Sensor sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if (sensorTemperature != null){
            sensorManager.registerListener(sensorsListener, sensorTemperature, SensorManager.SENSOR_DELAY_NORMAL);}
        if (sensorHumidity != null){
            sensorManager.registerListener(sensorsListener, sensorHumidity, SensorManager.SENSOR_DELAY_NORMAL);}
    }

    private void showMeATemperature(SensorEvent event) {
        WeatherFrament.textTemperature = "Temperature is " + event.values[0];
    }

    private void showMeAHumidity(SensorEvent event){
        WeatherFrament.textHumidity = "Humidity is " + event.values[0];
    }

    private SensorEventListener sensorsListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
                showMeATemperature(event);
            }
            if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
                showMeAHumidity(event);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}
*/
