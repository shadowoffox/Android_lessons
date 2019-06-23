package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String textTemperature;
    private String textHumidity;
    private Sensor sensorTemperature;
    private Sensor sensorHumidity;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        initSideMenu(toolbar);
        getSensors();
        System.out.println("temp@@@@@@@@@"+textTemperature);
        System.out.println("hum@@@@@@@@@@"+textHumidity);
        Bundle bundle = new Bundle();
        bundle.putString("TEMPERATURE",textTemperature);
        bundle.putString("HUMIDITY",textHumidity);

        if (savedInstanceState==null) {
            Fragment fragment = new TownFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.drawer_layout, fragment)
                    .commit();
        }

    }

    private void getSensors(){
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if (sensorTemperature != null){
        sensorManager.registerListener(sensorsListener,sensorTemperature, SensorManager.SENSOR_DELAY_NORMAL);}
        if (sensorHumidity != null){
        sensorManager.registerListener(sensorsListener,sensorHumidity, SensorManager.SENSOR_DELAY_NORMAL);}
    }

    private void showMeATemerature(SensorEvent event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Temperature is ").append(event.values[0]);
        textTemperature = stringBuilder.toString();
    }
    private void showMeHumidity(SensorEvent event){
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Humidity is ").append(event.values[0]);
        textHumidity = stringBuilder1.toString();
    }


    SensorEventListener sensorsListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
                showMeATemerature(event);
            }
            if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
                showMeHumidity(event);
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    private void initSideMenu(Toolbar toolbar){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,0, 0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        System.out.println(""+id);

        if (id == R.id.about_me) {
            Toast toast =Toast.makeText(getApplicationContext(),getString(R.string.den_is_cool), LENGTH_LONG);
            toast.show();
        } else if(id == R.id.feedback) {
            Toast toast =Toast.makeText(getApplicationContext(),"See you later",Toast.LENGTH_LONG);
            toast.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
